package com.magic.picshow.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.PayResult;
import com.jess.arms.utils.UiUtils;
import com.magic.picshow.R;
import com.magic.picshow.app.WXPayEvent;
import com.magic.picshow.di.component.DaggerPayComponent;
import com.magic.picshow.di.module.PayModule;
import com.magic.picshow.mvp.contract.PayContract;
import com.magic.picshow.mvp.model.entity.OrderAli;
import com.magic.picshow.mvp.model.entity.OrderWX;
import com.magic.picshow.mvp.model.entity.PhotoDetails;
import com.magic.picshow.mvp.presenter.PayPresenter;
import com.magic.picshow.mvp.ui.activity.LoginActivity;
import com.magic.picshow.mvp.ui.activity.PhotoDetailActivity;
import com.ta.utdid2.android.utils.StringUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import common.AppComponent;
import common.Constant;
import common.WEFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by snowwolf on 17/1/29.
 */

public class PayFragment extends WEFragment<PayPresenter> implements PayContract.View, View.OnClickListener {


    @BindView(R.id.price)
    TextView price;

    private PhotoDetails.Product mProduct;
    public static final int ALI_PAY = 0;
    public static final int WX_PAY = 1;
    private int photo_id;

    public static PayFragment newInstance(PhotoDetails.Product product, int photo_id) {
        PayFragment fragment = new PayFragment();
        final Bundle args = new Bundle();
        args.putInt("photo_id", photo_id);
        args.putSerializable("mProduct", product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photo_id = getArguments().getInt("photo_id");
        mProduct = (PhotoDetails.Product) getArguments().getSerializable("mProduct");

        EventBus.getDefault().register(this);
    }

    public PayFragment() {

    }

    public PayFragment(PhotoDetails.Product product, int photo_id) {
        mProduct = product;
        this.photo_id = photo_id;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerPayComponent
                .builder()
                .appComponent(appComponent)
                .payModule(new PayModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_pay, null, false);
    }

    @Override
    protected void initData() {
        price.setText(getResources().getString(R.string.pay_num).replace("*", mProduct.getPrice() + ""));
        initWx();
    }

    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传bundle,里面存一个what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事,和message同理
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在内部onActivityCreated中
     * 初始化就可以了
     *
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void alipay(OrderAli mOrder) {

//        boolean rsa2 = (Constant.RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(Constant.APPID, rsa2, "");
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? Constant.RSA2_PRIVATE : Constant.RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;

        final String orderInfo = mOrder.getOrderStr();
        if (StringUtils.isEmpty(orderInfo)) {
            return;
        }
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                final Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i(TAG, result.toString());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        PayResult payResult = new PayResult((Map<String, String>) result);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            mPresenter.paySuccess(photo_id, mProduct.getId(), mWeApplication.getmUser().getId(), ALI_PAY);//相册id  产品id 用户id
                            Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                            //这边需要刷新相册
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }


    @Override
    @OnClick({R.id.ali_pay_btn, R.id.wechart_pay_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ali_pay_btn:
                if (mWeApplication.getmUser().getId() == 0) {
                    LoginActivity.show(getActivity());
                } else {
                    mPresenter.creatOrderALI(mProduct.getPrice(), mProduct.getId(), photo_id, mWeApplication.getmUser().getId());
                }
                break;
            case R.id.wechart_pay_btn:
                if (mWeApplication.getmUser().getId() == 0) {
                    LoginActivity.show(getActivity());
                } else {
                    mPresenter.creatOrderWX(mProduct.getPrice(), mProduct.getId(), photo_id, mWeApplication.getmUser().getId());

                }
                break;
            default:
                break;
        }
    }

    public void startLoad() {
        Log.d(TAG, "startLoad()");
    }

    public void stopLoad() {
        Log.d(TAG, "stopLoad()");
    }

    public void pauseLoad() {
        Log.d(TAG, "pauseLoad()");
    }

    @Override
    public void paySuccess() {
        //通知后台支付完成后应该。。
        ((PhotoDetailActivity) getActivity()).payProduct();
    }

    @Override
    public void orderCreatedAli(OrderAli mOrder) {
        alipay(mOrder);
    }

    @Override
    public void orderCreatedWX(OrderWX mOrder) {
        wechartpay(mOrder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //////////////////////////微信支付相关代码/////////////////////////

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    private void initWx() {
        api = WXAPIFactory.createWXAPI(mWeApplication, Constant.APP_ID, false);
        api.registerApp(Constant.APP_ID);
    }

    private void wechartpay(OrderWX mOrder) {
        PayReq req = new PayReq();
        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
        req.appId = mOrder.getAppid();
        req.partnerId = mOrder.getPartnerid();
        req.prepayId = mOrder.getPrepayid();
        req.nonceStr = mOrder.getNoncestr();
        req.timeStamp = mOrder.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = mOrder.getSign();
        req.extData = "app data"; // optional
        Toast.makeText(mWeApplication, "正常调起支付", Toast.LENGTH_SHORT).show();
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }


    @Subscriber()
    public void onEventMainThread(WXPayEvent event) {
        Log.e("WXPayEvent", event.getPayResult() + "");
        if (event.getPayResult() == 0) {

            mPresenter.paySuccess(photo_id, mProduct.getId(), mWeApplication.getmUser().getId(), ALI_PAY);//相册id  产品id 用户id
            Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
        }
    }
}
