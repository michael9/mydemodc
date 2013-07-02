package alialicoo.com.dice;

import gifview.com.GifView;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class MyProgressDlg extends Activity {

    GifView gf = null;

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch (msg.what) {
            case 1:
                CloseDlg();
                break;

            default:
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprogressdlg);
        gf = (GifView) findViewById(R.id.gfview);
        gf.setGifImage(R.drawable.t822);
        gf.showAnimation();
        LoadinfoTh lt = new LoadinfoTh();
        lt.start();
    }

    void CloseDlg() {
        gf.destroy();
        finish();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        CloseDlg();
    }

    class LoadinfoTh extends Thread {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            // Log.e("LoadinfoTh", "start");
            hget get = new hget();
            String bstr = get.getcontext(Commdata.getdc_downlist_addr);
            // Log.e("LoadinfoTh", "bstr");
            Commdata.downdiceslist = MyPullXMLReader.readXML(MyUtil.String2InputStream(bstr));
            // Log.e("LoadinfoTh", "readxml");
            if (Commdata.downdiceslist != null) {
                Message ms = new Message();
                ms.what = 1;
                mhandler.sendMessage(ms);
            }
        }
    }
}
