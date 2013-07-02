package alialicoo.com.dice;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

public class photoedit extends Activity {

    ImageView photoimage = null;
    ImageButton sharebutton = null;
    // ImageView tgimage=null;
    AnimationDrawable mad;
    String filefullpath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoedit);

        filefullpath = getIntent().getStringExtra("path");
        photoimage = (ImageView) findViewById(R.id.photoimage);
        photoimage.setImageBitmap(BitmapFactory.decodeFile(filefullpath));// ImageCS.sFileFullPath
        /*
         * tgimage=(ImageView)findViewById(R.id.tgimage);
         * tgimage.setOnTouchListener(new OnTouchListener() {
         * 
         * int[] temp = new int[] { 0, 0 };
         * 
         * @Override public boolean onTouch(View v, MotionEvent event) { // TODO
         * Auto-generated method stub int x = (int) event.getRawX(); int y =
         * (int) event.getRawY();
         * 
         * switch(event.getAction()){ case MotionEvent .ACTION_DOWN: temp[0] =
         * (int) event.getX(); temp[1] = y - v.getTop(); break; case MotionEvent
         * .ACTION_MOVE: v.layout(x - temp[0], y - temp[1], x + v.getWidth() -
         * temp[0], y - temp[1] + v.getHeight()); v.postInvalidate(); break; }
         * return false; } });
         */

        sharebutton = (ImageButton) findViewById(R.id.sharebutton);
        sharebutton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                // intent.setType("text/plain");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                intent.putExtra(Intent.EXTRA_TEXT, "[情趣骰子证据分享] " + sdf.format(new Date()));
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filefullpath));// ImageCS.sFileFullPath
                startActivity(intent.createChooser(intent, "happydic share:"));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.activity_slide_up_in, R.anim.activity_slide_down_out);
        }
        // return super.onKeyDown(keyCode, event);
        return false;
    }

}
