package alialicoo.com.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

public class Dice extends Activity {

    Timer firsttime;
    Timer mtimer = null;
    RelativeLayout tplayout = null;
    RelativeLayout dplayout = null;
    AnimationDrawable animator = null;
    SlidingDrawer sd = null;
    GridView mgv = null;
    ImageView mIV = null;
    boolean run_flag = false;
    // ImageView camerashare = null;
    // ImageView tableashare = null;
    Bitmap[] bits = null;
    ImageButton helpbutton = null;
    SensorManager mSensorManager;// 声明一个SensorManager
    Sensor accSensor = null;
    dice_view_bean[] dices_view = new dice_view_bean[Commdata.maxdices];
    float lastx = 0;
    int delseln = -1;
    LinearLayout main_ll;
    MyUtil myutil = new MyUtil();
    SysUtil sysutil;

    // String userratparams = null;
    String userratreturn = null;
    float userrat_f = 0;
    // RatingBar usrerat;
//    com.adview.AdViewLayout adviewbar = null;

    ImageButton mainmenu_btn = null;
    
    LocationManager m_location_manager ;

    // TextView addsc = null;
    // AnimationSet mas = null;
    // boolean helpshowflag=false;

    /******************************************************************************/
    Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 1:
                mIV.setVisibility(8);
                tplayout.invalidate();
                dplayout.setVisibility(0);
                SetDiceImage();
                if(Commdata.autocoveredflag)
                {
                    Intent intent=new Intent(Dice.this, CoverLayout.class);
                    startActivity(intent);
                }
                break;

            case 2:
                firsttime.cancel();
                try {
                    sd.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
                break;

            case 100:
                // usrerat.setRating(userrat_f);
                break;
            }

        }
    };

    class frist_task extends java.util.TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            Message ms = new Message();
            ms.what = 2;
            handler.sendMessage(ms);
        }
    }

    class Switch_task extends java.util.TimerTask {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            offanim();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
        case 1:
            if (resultCode == RESULT_OK) {
                setimage();
            }
            break;
        case 2:
            if (resultCode == 1)
                exiteven();
            break;

        case 3:
            if (resultCode == 1)
                deldice(delseln);
            break;

        case 4:
            switch (resultCode) {

            case 1:
                Intent intent = new Intent(Dice.this, Loging.class);
                startActivity(intent);
                break;
            case 2:
                tackPhoto();
                break;
            case 3:
                screencut();
                break;

            default:
                break;
            }

            break;
        default:
            break;
        }

    }

    /******************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        accSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Commdata.AppContext = this;
        ImageCS.sFileFullPath = myutil.getPhotoShareImageAdds();
        ImageCS.sFileFullPath_t = myutil.getScreenShareImageAdds();
        Commdata.dices = new ArrayList<dice_bean2>();

        phinfo mph = new phinfo();
        mph.getPhoneInfoParams(Commdata.AppContext);

        sysutil = new SysUtil(this);
        sysutil.checkversionCode();
        sysutil.readSP();
        sysutil.copydbfile();
        sysutil.read_dice_db();

        // post_thr
        thrgetinfo mthr = new thrgetinfo(Commdata.postparams, Commdata.pstaddr);
        mthr.start();

        firsttime = new Timer();
        firsttime.schedule(new frist_task(), 2200);

        mtimer = new Timer();

        int i;
        int dicesize = (Commdata.screenHeight - 30) / 3;
        for (i = 0; i < Commdata.maxdices; i++) {
            dices_view[i] = new dice_view_bean();
            dices_view[i].dice_w = dicesize;
            dices_view[i].dice_h = dicesize;
        }

        findViews();
        setCommondListener();

        this.checkNewVersion();
        
        ini_ds();

        // userratparams = "?MD5ID=" + Commdata.MD5ID;
        // LoadinfoTh mt = new LoadinfoTh();
        // mt.start();
        // mas = new AnimationSet(true);
        // AlphaAnimation a1 = new AlphaAnimation(0, 80);
        // a1.setDuration(800);
        // ScaleAnimation s1 = new ScaleAnimation(0f, 6f, 0f, 6f,
        // Animation.RELATIVE_TO_SELF, 0.3f,
        // Animation.RELATIVE_TO_SELF, 0.5f);
        // s1.setDuration(800);
        // mas.addAnimation(a1);
        // mas.addAnimation(s1);
        // mas.setFillBefore(true);
       
        m_location_manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Commdata.publiclm=m_location_manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }
    
    /**
     * 获取最新版本号.
     * 
     * @author heyi
     */
    public void checkNewVersion(){
    	String info = "";
    	try {
			info = String.valueOf(getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0).versionCode);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
    	String s = new hget().getcontext("http://10.130.44.21:9999/HttpTest/HttpTestServlet?version="+info);
    	if(!s.equals(null)&&!"".equals(s)){
    		Log.d("newVersion", s);
    		String myName = s.substring(s.lastIndexOf("/")+1, s.length());
    		Context context = getApplicationContext();
    		Handler myHandler = new Handler();
    		AppFileDownUtils utils = new AppFileDownUtils(context,myHandler,s,myName);
    		utils.start();
    	}
    }
    
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // if(Commdate.refsdl)
        // addsc.setVisibility(View.INVISIBLE);
        setgrid();
        mSensorManager.registerListener(lsn, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Commdata.start = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mSensorManager.unregisterListener(lsn, accSensor);
    }


    private void findViews() {
        main_ll = (LinearLayout) findViewById(R.id.main_ll);
        main_ll.setDrawingCacheEnabled(true);
        tplayout = (RelativeLayout) findViewById(R.id.tplayout);
        tplayout.setDrawingCacheEnabled(true);
        dplayout = (RelativeLayout) findViewById(R.id.dplayout);
        mgv = (GridView) findViewById(R.id.dice_slist);
        sd = (SlidingDrawer) findViewById(R.id.sd_dices);
        sd.open();
        helpbutton = (ImageButton) findViewById(R.id.helpbutton);
        // camerashare = (ImageView) findViewById(R.id.camerashare);
        // tableashare = (ImageView) findViewById(R.id.tableshare);
        mIV = (ImageView) findViewById(R.id.iv);
        mIV.setBackgroundResource(R.anim.dice_anim);
        animator = (AnimationDrawable) mIV.getBackground();
        mIV.setVisibility(8);
        // usrerat = (RatingBar) findViewById(R.id.usrerat);
        dices_view[0].div = (ImageView) findViewById(R.id.div1);
        dices_view[1].div = (ImageView) findViewById(R.id.div2);
        dices_view[2].div = (ImageView) findViewById(R.id.div3);
        dices_view[3].div = (ImageView) findViewById(R.id.div4);
        dices_view[4].div = (ImageView) findViewById(R.id.div5);
        dices_view[5].div = (ImageView) findViewById(R.id.div6);
        dices_view[6].div = (ImageView) findViewById(R.id.div7);
        dices_view[7].div = (ImageView) findViewById(R.id.div8);
        dices_view[8].div = (ImageView) findViewById(R.id.div9);
        mainmenu_btn = (ImageButton) findViewById(R.id.mainmenu_btn);
        // addsc = (TextView) findViewById(R.id.addsc);
        // Ad
    }

    private void setCommondListener() {
        dices_view[0].div.setOnLongClickListener(new Uilclicked());
        dices_view[1].div.setOnLongClickListener(new Uilclicked());
        dices_view[2].div.setOnLongClickListener(new Uilclicked());
        dices_view[3].div.setOnLongClickListener(new Uilclicked());
        dices_view[4].div.setOnLongClickListener(new Uilclicked());
        dices_view[5].div.setOnLongClickListener(new Uilclicked());
        dices_view[6].div.setOnLongClickListener(new Uilclicked());
        dices_view[7].div.setOnLongClickListener(new Uilclicked());
        dices_view[8].div.setOnLongClickListener(new Uilclicked());

        dices_view[0].div.setOnClickListener(new UIclicked());
        dices_view[1].div.setOnClickListener(new UIclicked());
        dices_view[2].div.setOnClickListener(new UIclicked());
        dices_view[3].div.setOnClickListener(new UIclicked());
        dices_view[4].div.setOnClickListener(new UIclicked());
        dices_view[5].div.setOnClickListener(new UIclicked());
        dices_view[6].div.setOnClickListener(new UIclicked());
        dices_view[7].div.setOnClickListener(new UIclicked());
        dices_view[8].div.setOnClickListener(new UIclicked());
        mgv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 < Commdata.dices.size()) {
                    adddice(arg2);
//                    showanim();
                } else {
                    Intent intent = new Intent(Dice.this, Add_page.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.activity_slide_r2l, R.anim.activity_slide_l2r);
                }
                sd.close();
            }
        });

        mgv.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 < Commdata.dices.size()) {
                    delseln = arg2;
                    Intent intent = new Intent(Dice.this, CMSGDlg.class);
                    intent.putExtra("msg_id", R.string.msg_give_up_the_dice);
                    startActivityForResult(intent, 3);
                }
                return false;
            }
        });

        tplayout.setOnClickListener(new UIclicked());
        helpbutton.setOnClickListener(new UIclicked());
        // camerashare.setOnClickListener(new UIclicked());
        // tableashare.setOnClickListener(new UIclicked());
        sd.setOnDrawerOpenListener(new OnDrawerOpenListener() {

            @Override
            public void onDrawerOpened() {
                // TODO Auto-generated method stub
                mgv.startLayoutAnimation();
            }
        });
        mainmenu_btn.setOnClickListener(new UIclicked());
        // Ad
        // adviewbar.setOnClickListener(new adclick());

    }

    /******************************************************************************/
    void getRandoms() {
        int i = 0;
        for (i = 0; i < Commdata.maxdices; i++) {
            dices_view[i].setRandoem((int) (Math.random() * 123));
        }
    }

    public void offanim() {
        if (animator.isRunning())
            animator.stop();
        handler.sendEmptyMessage(1);
    }

    public void showanim() {
        // Log.d("showanim","in");
        if (!animator.isRunning() && diceontable()) {
            // Log.d("showanim","run");
            dplayout.setVisibility(8);
            mIV.setVisibility(0);
            // mIV.setImageDrawable(null);
            animator.start();
            mtimer.schedule(new Switch_task(), 1000);
        }
    }

    boolean diceontable() {
        int i;
        for (i = 0; i < 9; i++) {
            if (dices_view[i].div.isShown())
                return true;
        }
        sd.open();
        Toast.makeText(this, R.string.msg_pick_the_dice, Toast.LENGTH_LONG).show();
        return false;
    }

    void ini_ds() {
        int i;
        for (i = 0; i < Commdata.maxdices; i++) {
            dices_view[i].div.setVisibility(8);
        }
        if (Commdata.dices.size() > 0)
            if (Commdata.dices.get(0) != null) {
                dices_view[0].mbean2 = Commdata.dices.get(0);
                dices_view[0].setRandoem(4);
                dices_view[0].setdiceimage();
            }
    }

    void setgrid() {
        Commdata.refsdl = false;
        sysutil.read_dice_db();
        mgv.setAdapter(new ImageAdapter(this));
    }

    void adddice(int selected) {
        int i = 0;
        for (i = 0; i < Commdata.maxdices; i++) {
            if (!dices_view[i].div.isShown()) {
                dices_view[i].mbean2 = Commdata.dices.get(selected);
                dices_view[i].setdiceimage();

                // dicedb db=new dicedb();
                // db.openDatabase();
                // db.updatecount( Commdate.dices.get(selected).DICE_ID);
                // db.close();
                List<NameValuePair> mp = new ArrayList<NameValuePair>();
                mp.add(new BasicNameValuePair("DICE_ID", Commdata.dices.get(selected).DICE_ID));
                mp.add(new BasicNameValuePair("MD5ID", Commdata.MD5ID));
                mp.add(new BasicNameValuePair("OPTION", "dc_use_cnt"));
                thrgetinfo mthr = new thrgetinfo(mp, Commdata.pstaddr_count);
                mthr.start();
                return;
            }
        }
    }

    void deldice(int selected) {
        if (selected < 0)
            return;

        if (Commdata.dices.get(selected).DICE_ID.equalsIgnoreCase("100000001")) {
            Toast.makeText(Dice.this, R.string.msg_delete_default_dice, 1500).show();
            return;
        }

        for (int i = 0; i < Commdata.maxdices; i++) {
            if (dices_view[i].mbean2 != null)
                if (dices_view[i].mbean2.DICE_ID.equalsIgnoreCase(Commdata.dices.get(selected).DICE_ID)) {
                    dices_view[i].div.setVisibility(8);
                }
        }

        dicedb db = new dicedb();
        db.openDatabase();
        db.delDice(Commdata.dices.get(selected).DICE_ID);
        db.close();
        setgrid();
    }

    public void SetDiceImage() {
        getRandoms();
    }

    void dicesbitmap() {
        int i = 0;
        bits = new Bitmap[Commdata.maxdices];
        for (i = 0; i < Commdata.maxdices; i++) {
            if (dices_view[i].div.isShown()) {
                bits[i] = dices_view[i].getMdiceimage();
            }
        }
    }

    void setimage() {
        ImageCS.deal_img(ImageCS.sFileFullPath);
        ImageCS.bitmap2file(ImageCS.add_dice_img(ImageCS.sFileFullPath, bits), ImageCS.sFileFullPath);
        Intent intent = new Intent(this, photoedit.class);
        intent.putExtra("path", ImageCS.sFileFullPath);
        startActivity(intent);
    }

    void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse("file://" + ImageCS.sFileFullPath));
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.activity_slide_up_in, R.anim.activity_slide_down_out);
    }

    void exiteven() {
//        int dr = (int) ((System.currentTimeMillis() - Commdata.start) / 1000);
//        Commdata.exit = true;
//        Commdata.postparams_exit = new ArrayList<NameValuePair>();
//        Commdata.postparams_exit.add(new BasicNameValuePair("APPname", "alialicoo.com.dice"));
//        Commdata.postparams_exit.add(new BasicNameValuePair("MD5ID", Commdata.MD5ID));
//        Commdata.postparams_exit.add(new BasicNameValuePair("duration", "" + dr));
//        Commdata.postparams_exit.add(new BasicNameValuePair("versionCode", "" + Commdata.versionCode_local));
//        thrgetinfo mthr = new thrgetinfo(Commdata.postparams_exit, Commdata.pstaddr_exit);
//        mthr.start();
        sysutil.delad_sdcard();
        finish();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    SensorEventListener lsn = new SensorEventListener() {
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // Log.d("onSensorChanged",""+event.values[SensorManager.DATA_X]+" "+event.values[SensorManager.DATA_Y]+" "+event.values[SensorManager.DATA_Z]);
            if (Math.abs(event.values[SensorManager.DATA_X] - lastx) > 6) {
                showanim();
                // // Log.d("showanim","onSensorChanged");
            }
            lastx = event.values[SensorManager.DATA_X];
        }
    };

    void tackPhoto() {
        dicesbitmap();
        startCamera();
    }

    void screencut() {
//        adviewbar.setVisibility(View.INVISIBLE);
        mainmenu_btn.setVisibility(View.INVISIBLE);
        Bitmap mb = main_ll.getDrawingCache();
//        adviewbar.setVisibility(View.VISIBLE);
        mainmenu_btn.setVisibility(View.VISIBLE);
        ImageCS.bitmap2file(mb, ImageCS.sFileFullPath_t);
        Intent intent = new Intent(Dice.this, photoedit.class);
        intent.putExtra("path", ImageCS.sFileFullPath_t);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_slide_up_in, R.anim.activity_slide_down_out);
    }

    class Uilclicked implements OnLongClickListener {
        @Override
        public boolean onLongClick(View view) {
            // TODO Auto-generated method stub
            switch (view.getId()) {
            case R.id.div1:
                dices_view[0].div.setVisibility(8);
                break;
            case R.id.div2:
                dices_view[1].div.setVisibility(8);
                break;
            case R.id.div3:
                dices_view[2].div.setVisibility(8);
                break;
            case R.id.div4:
                dices_view[3].div.setVisibility(8);
                break;
            case R.id.div5:
                dices_view[4].div.setVisibility(8);
                break;
            case R.id.div6:
                dices_view[5].div.setVisibility(8);
                break;
            case R.id.div7:
                dices_view[6].div.setVisibility(8);
                break;
            case R.id.div8:
                dices_view[7].div.setVisibility(8);
                break;
            case R.id.div9:
                dices_view[8].div.setVisibility(8);
                break;

            default:
                break;
            }

            return true;
        }
    }

    class UIclicked implements OnClickListener {
        @Override
        public void onClick(View v) {

            Intent intent;
            switch (v.getId()) {
            case R.id.helpbutton:
                intent = new Intent(Dice.this, helppage.class);
                startActivity(intent);
                // overridePendingTransition(
                // R.anim.activity_slide_up_in,
                // R.anim.activity_slide_down_out
                // );
                break;
            // case R.id.camerashare:
            // dicesbitmap();
            // startCamera();
            // intent = new Intent(Dice.this, MainMeun.class);
            // startActivity(intent);
            // LayoutInflater mLayoutInflater = (LayoutInflater)
            // Commdata.AppContext
            // .getSystemService(LAYOUT_INFLATER_SERVICE);
            // View music_popunwindwow =
            // mLayoutInflater.inflate(R.layout.mainmeun, null);
            // PopupWindow mPopupWindow = new
            // PopupWindow(music_popunwindwow, LayoutParams.FILL_PARENT,
            // LayoutParams.WRAP_CONTENT);
            // mPopupWindow.showAtLocation(findViewById(R.id.main_ll),
            // Gravity.CENTER, 0, 0);
            // break;
            // case R.id.tableshare:
            // adviewbar.setVisibility(8);
            // Bitmap mb = main_ll.getDrawingCache();
            // adviewbar.setVisibility(0);
            // ImageCS.bitmap2file(mb, ImageCS.sFileFullPath_t);
            // intent = new Intent(Dice.this, photoedit.class);
            // intent.putExtra("path", ImageCS.sFileFullPath_t);
            // startActivity(intent);
            // overridePendingTransition(R.anim.activity_slide_up_in,
            // R.anim.activity_slide_down_out);
            // break;

            case R.id.mainmenu_btn:
                intent = new Intent(Dice.this, MainMenu.class);
                startActivityForResult(intent, 4);
                overridePendingTransition(R.anim.activity_slide_up_in2, R.anim.activity_slide_up_out2);
                break;

//            case R.id.adviewbar:
//
//                break;

            default:
                showanim();
                break;
            }
        }
    };

    // 广告点击加分动画
    // void showaddscanim() {
    // addsc.startAnimation(mas);
    // }

    class LoadinfoTh extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            // if (userratparams == null)
            // return;
            hget get = new hget();
            // userratreturn = get.getcontext(Commdata.getaddr_userrat +
            // userratparams);
            // userrat_f=0;
            if (userratreturn != null) {
                try {
                    String[] strs = userratreturn.split("\\|");
                    int max_d = Integer.parseInt(strs[0]);
                    int max_c = Integer.parseInt(strs[1]);
                    int crat_d = Integer.parseInt(strs[2]);
                    int crat_c = Integer.parseInt(strs[3]);
                    if (max_d > 0 && max_c > 0 && crat_d > 0 && crat_c > 0) {
                        float dtr = (float) crat_d / (float) max_d;
                        float dcr = (float) crat_c / (float) max_c;
                        userrat_f = 5 * (dtr * 0.4f + dcr * 0.6f);
                        Editor sp = getSharedPreferences("data", 0).edit();
                        sp.putFloat("userat", userrat_f);
                        sp.commit();
                        Log.d("LoadinfoTh", "" + userrat_f);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            Message ms = new Message();
            ms.what = 100;
            handler.sendMessage(ms);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (sd.isOpened()) {
                sd.close();
            } else {
                Intent intent = new Intent(Dice.this, CMSGDlg.class);
                intent.putExtra("msg_id", R.string.msg_exit);
                startActivityForResult(intent, 2);
            }
        }
        return false;
    }

}