package com.magic.picshow.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;

import com.jess.arms.utils.UiUtils;
import com.magic.picshow.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppComponent;
import common.WEActivity;

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
 * Created by snowwolf on 17/2/28.
 */

public class AboutUsActivity extends WEActivity {


    @BindView(R.id.webview)
    WebView webview;

    private String loadUrl;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_about_us, null, false);
    }

    @Override
    protected void initData() {
        loadUrl = getIntent().getStringExtra("loadUrl");
        if (loadUrl == null || loadUrl.equals(""))
            loadUrl = "https://www.baidu.com/";
        webview.loadUrl(loadUrl);
    }


    public void killMyself() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    public static void show(Context context,String loadUrl) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        intent.putExtra("loadUrl", loadUrl);
        context.startActivity(intent);
    }

}