package com.magic.picshow.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.danikula.videocache.HttpProxyCacheServer;
import com.jess.arms.utils.ToastUtil;
import com.jess.arms.utils.UiUtils;
import com.magic.picshow.R;
import com.magic.picshow.di.component.DaggerVideoComponent;
import com.magic.picshow.di.module.VideoModule;
import com.magic.picshow.mvp.contract.VideoContract;
import com.magic.picshow.mvp.model.entity.PhotoDetails;
import com.magic.picshow.mvp.presenter.VideoPresenter;
import com.ta.utdid2.android.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.WEApplication;
import common.WEFragment;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

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

public class VideoFragment extends WEFragment<VideoPresenter> implements VideoContract.View {
    private static final String TAG = VideoFragment.class.getSimpleName();

//    private PhotoDetails.Resource mResource;

    @Nullable
    @BindView(R.id.video_view)
    JCVideoPlayerStandard videoView;

    private String mUrl;
    private boolean playAuto = false;

    /**
     * 视频缓存
     */
    private HttpProxyCacheServer proxy;

    public static VideoFragment newInstance(String mUrl,Boolean playAuto) {
        VideoFragment fragment = new VideoFragment();
        final Bundle args = new Bundle();
        args.putString("mUrl",mUrl);
        args.putBoolean("playAuto",playAuto);
        fragment.setArguments(args);
        return fragment;
    }

    public VideoFragment() {

    }


    public VideoFragment(String mUrl) {
        this.mUrl = mUrl;
    }

    @Override
    protected void setupFragmentComponent(AppComponent appComponent) {
        DaggerVideoComponent
                .builder()
                .appComponent(appComponent)
                .videoModule(new VideoModule(this))//请将VideoModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        proxy = WEApplication.getProxy(getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video, null, false);
    }

    @Override
    protected void initData() {
        if (playAuto)
            playMp4(mUrl);
    }

    /**
     * 播放视频
     */
    private void playMp4(String path) {
        if (StringUtils.isEmpty(path)){
            ToastUtil.showLong("播放地址错误");
        }
//        path="http://blob00.blob.core.chinacloudapi.cn/ieemoo-blob/ads/acb730dab44c5ace5fe3c539b1a909fe.mp4";
//        path="http://cevin.qiniudn.com/jieqoo-HD.mp4";
//        path="http://video.jiecao.fm/8/17/%E6%8A%AB%E8%90%A8.mp4";
//        final Uri uri = Uri.parse(path);
        if (path.startsWith("http")) {
            String proxyUrl = proxy.getProxyUrl(path);
            videoView.setUp(proxyUrl, JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "");
            videoView.startButton.performClick();
        } else {
            videoView.setUp(path, JCVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN, "");
            videoView.startButton.performClick();
        }
    }

//    public void startMp4() {
//        JCMediaManager.instance().mediaPlayer.start();
//    }
//
//    public void stopMp4() {
//        JCMediaManager.instance().mediaPlayer.stop();
//    }

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
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
//        JCMediaManager.instance().mediaPlayer.stop();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
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
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        mUrl = getArguments().getString("mUrl");
        playAuto = getArguments().getBoolean("playAuto");
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //相当于Fragment的onPause
//            JCMediaManager.instance().mediaPlayer.pause();
            Log.d("ztt", "MainFragment is onPase");
        } else {
            //相当于Fragment的onResume
//            JCMediaManager.instance().mediaPlayer.start();
            Log.d("ztt", "MainFragment is onResume");
        }
    }

    public void startLoad() {
        if (JCMediaManager.instance().mediaPlayer.isPlaying())
            return;
        if (!JCMediaManager.instance().mediaPlayer.isLooping()) {
            playMp4(mUrl);
            return;
        }
        JCMediaManager.instance().mediaPlayer.start();
        Log.d(TAG, "startLoad()");
    }

    public void pauseLoad() {
        if (JCMediaManager.instance().mediaPlayer.isPlaying())
            JCMediaManager.instance().mediaPlayer.pause();
        Log.d(TAG, "pauseLoad()");
    }

    public void stopLoad() {
        JCMediaManager.instance().mediaPlayer.stop();
        Log.d(TAG, "stopLoad()");
    }
}
