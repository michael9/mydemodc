package alialicoo.com.dice;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CoverLayout extends Activity {

    ImageButton coveropen, coverpeek;
    ImageView coverimg;
    // WebView myadview;
    // boolean webviewshowflag;
    boolean coverflag;
    AnimationSet peek_open,peek_close,open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coverlayout);
        findViews();
        setCommondListener();
        creatAnimation();
    }

    void findViews() {
        coveropen = (ImageButton) findViewById(R.id.cover_open);
        coverpeek = (ImageButton) findViewById(R.id.cover_peek);
        coverimg = (ImageView) findViewById(R.id.cover_cover);
        // myadview = (WebView) findViewById(R.id.myadview);
        // myadview.getSettings().setJavaScriptEnabled(true);
        // myadview.getSettings().setBuiltInZoomControls(true); // 显示放大缩小
        // myadview.getSettings().setSupportZoom(true);// 可以缩放
        // myadview.getSettings().setDefaultZoom(ZoomDensity.CLOSE);// 默认缩放模式
        // myadview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // myadview.setInitialScale(100);
        // myadview.getSettings().setLoadWithOverviewMode(true);
        // myadview.getSettings().setUseWideViewPort(true);
        // String
        // url="http://www.graphycalc.com/#sinc(x%20*%2010)%20*%20sinc(y%20*%2010)";
        // String url = "http://www.alialicoo.com";
        // String url="file:///android_asset/html5_01.html";
        // myadview.loadUrl(url);
        // webviewshowflag = false;
        coverflag = true;
    }

    void setCommondListener() {
        coveropen.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                coverimg.clearAnimation();
                coverimg.startAnimation(open);
//                finish();
            }

        });

        coverpeek.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    coverflag = false;
                    show();
                    break;
                case MotionEvent.ACTION_UP:
                    coverflag = true;
                    show();
                    break;

                default:
                    break;
                }

                return false;
            }
        });

        // myadview.setWebViewClient(new WebViewClient() {
        // @Override
        // public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // // TODO Auto-generated method stub
        // super.onPageStarted(view, url, favicon);
        // }
        //
        // @Override
        // public void onPageFinished(WebView view, String url) {
        // // TODO Auto-generated method stub
        // super.onPageFinished(view, url);
        // webviewshowflag=true;
        // show();
        // }
        // });

        // myadview.setWebChromeClient(new WebChromeClient() {
        //
        // @Override
        // public void onProgressChanged(WebView view, int newProgress) {
        // // TODO Auto-generated method stub
        // super.onProgressChanged(view, newProgress);
        // if (newProgress >= 100) {
        // webviewshowflag = true;
        // show();
        // }
        // }
        //
        // });
    }

    void show() {
        if (coverflag) {
            coverimg.clearAnimation();
            coverimg.startAnimation(peek_close);
            coverimg.setVisibility(View.VISIBLE);
          
            // if (webviewshowflag) {
            // myadview.setVisibility(View.VISIBLE);
            // }
        } else {
            coverimg.clearAnimation();
            coverimg.startAnimation(peek_open);          
            coverimg.setVisibility(View.INVISIBLE);
            // myadview.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        // return super.onKeyDown(keyCode, event);
        return false;
    }
    
    void creatAnimation()
    {
        peek_open=new AnimationSet(true);
        AlphaAnimation poa=new AlphaAnimation(1, 0);
        poa.setDuration(600);
        RotateAnimation por=new RotateAnimation(0,-90,Animation.RELATIVE_TO_SELF,
                0,Animation.RELATIVE_TO_SELF,1);
        por.setDuration(600);
        peek_open.addAnimation(poa);
        peek_open.addAnimation(por);
        
        peek_close=new AnimationSet(true);
        AlphaAnimation pca=new AlphaAnimation(0,1);
        pca.setDuration(600);
        RotateAnimation pcr=new RotateAnimation(-90,0,Animation.RELATIVE_TO_SELF,
                0,Animation.RELATIVE_TO_SELF,1);
        pcr.setDuration(600);
        peek_close.addAnimation(pca);
        peek_close.addAnimation(pcr);
      
        open=new AnimationSet(true);
        AlphaAnimation pa=new AlphaAnimation(1,0);
        pa.setDuration(600);
        ScaleAnimation ps=new ScaleAnimation(1, 1.5f, 1, 1.5f, 
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ps.setDuration(600);
        open.addAnimation(pa);
        open.addAnimation(ps);
        open.setAnimationListener(new AnimationListener() {
            
            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                finish();
            }
        });
        
    }

}
