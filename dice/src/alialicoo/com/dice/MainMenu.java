package alialicoo.com.dice;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainMenu extends Activity {
    RelativeLayout menutb = null;
    LinearLayout menub = null;
    // ImageView menu1=null;
    LinearLayout menu02 = null;
    LinearLayout menu03 = null;
    // LinearLayout menu04 = null;
    CheckBox cb_autocovered = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
        // WindowManager.LayoutParams.FLAG_BLUR_BEHIND);// 背景变昏暗
        setContentView(R.layout.mainmenu);

        findViews();
        setCommondListener();
    }

    void findViews() {
        menutb = (RelativeLayout) findViewById(R.id.menutb);
        menub = (LinearLayout) findViewById(R.id.menub);
        // menu1=(ImageView)findViewById(R.id.menu1);
        menu02 = (LinearLayout) findViewById(R.id.menu_ll_02);
        menu03 = (LinearLayout) findViewById(R.id.menu_ll_03);
        // menu04 = (LinearLayout) findViewById(R.id.menu_ll_04);
        cb_autocovered = (CheckBox) findViewById(R.id.cb_autocovered);
        cb_autocovered.setChecked(Commdata.autocoveredflag);
    }

    void setCommondListener() {
        menutb.setOnClickListener(new UIclicked());
        menub.setOnClickListener(new UIclicked());
        // menu1.setOnClickListener(new UIclicked());
        menu02.setOnClickListener(new UIclicked());
        menu03.setOnClickListener(new UIclicked());
        // menu04.setOnClickListener(new UIclicked());
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Commdata.autocoveredflag = cb_autocovered.isChecked();
        Editor sp = getSharedPreferences("data", 0).edit();
        sp.putBoolean("autocovered", Commdata.autocoveredflag);
        sp.commit();
    }

    class UIclicked implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
            case R.id.menutb:

                break;

            // case R.id.menu1:
            // setResult(1);
            // break;

            case R.id.menu_ll_02:
                setResult(2);
                break;

            case R.id.menu_ll_03:
                setResult(3);
                break;

            // case R.id.menu_ll_04:
            // if (cb_autocovered.isChecked()) {
            // cb_autocovered.setChecked(false);
            // Commdata.autocoveredflag = false;
            // Editor sp = getSharedPreferences("data", 0).edit();
            // sp.putBoolean("autocovered", false);
            // sp.commit();
            // } else {
            // cb_autocovered.setChecked(true);
            // Commdata.autocoveredflag = true;
            // Editor sp = getSharedPreferences("data", 0).edit();
            // sp.putBoolean("autocovered", true);
            // sp.commit();
            // }
            // break;

            default:
                break;
            }
            finish();
            overridePendingTransition(R.anim.activity_slide_up_in2, R.anim.activity_slide_up_out2);
        }
    }

}
