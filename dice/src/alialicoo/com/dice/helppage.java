package alialicoo.com.dice;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ViewFlipper;

public class helppage extends Activity {

    ViewFlipper help_details;
    int dint = 0;
    int pint = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helppage);

        help_details = (ViewFlipper) findViewById(R.id.help_details);

        help_details.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dint = (int) event.getRawX();
                    break;

                case MotionEvent.ACTION_UP:
                    int up = (int) event.getRawX();

                    if ((up - dint) < 30) {
                        pint++;
                        if (pint > 5) {
                            finish();
                            // overridePendingTransition(
                            // R.anim.activity_slide_up_in,
                            // R.anim.activity_slide_down_out
                            // );
                        }
                        help_details.showNext();
                    }

                    // if((up-dint)>30&&pint>0)
                    // {
                    // help_details.showPrevious();
                    // pint--;
                    // }
                    break;

                default:
                    break;
                }

                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Editor sp = getSharedPreferences("data", 0).edit();
        sp.putBoolean("helpshowflag", true);
        sp.commit();
    }

}
