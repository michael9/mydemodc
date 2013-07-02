package alialicoo.com.dice;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActivityManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class phinfo {

    String phone_manufacturer = "";
    String phone_mode = "";
    String rom_vesion = "";
    String sdk_vesion = "";
    String screen_dm = "";
    String Language = "";
    String Country = "";
    String DeviceId = "";
    String SubscriberId = "";
    String MemoryClass = "";
    // String userAgent = "";
    // String macAddress = "";
    String ANDROID_ID = "";

    public void getPhoneInfoParams(Context mc) {
        Commdata.postparams = new ArrayList<NameValuePair>();
        Commdata.postparams.add(new BasicNameValuePair("APPname", "alialicoo.com.dice"));
        Commdata.postparams.add(new BasicNameValuePair("versionCode", ""+Commdata.versionCode_local));

        fullbaseinfo(mc);
        getphoneinfo(mc);
        getANDROID_ID(mc);

        ActivityManager activityManager = (ActivityManager) mc.getSystemService("activity");
        MemoryClass = "" + activityManager.getMemoryClass();
        Commdata.postparams.add(new BasicNameValuePair("MemoryClass", MemoryClass));

        String sstr = screen_dm + DeviceId + SubscriberId + ANDROID_ID;

        Commdata.MD5ID = MyUtil.md5encrypt(sstr);
        Commdata.postparams.add(new BasicNameValuePair("MD5ID", Commdata.MD5ID));

    }

    private void fullbaseinfo(Context mc) {
        try {
            phone_manufacturer = android.os.Build.MANUFACTURER;
            rom_vesion = android.os.Build.VERSION.RELEASE;
            sdk_vesion = android.os.Build.VERSION.SDK;
            phone_mode = android.os.Build.MODEL;
            DisplayMetrics dm = mc.getResources().getDisplayMetrics();
            Commdata.screenHeight = dm.heightPixels;
            Commdata.screenWidth = dm.widthPixels;
            screen_dm = dm.widthPixels + "X" + dm.heightPixels;
            Language = Locale.getDefault().getLanguage();
            Country = Locale.getDefault().getCountry();
        } catch (Exception e) {
            // TODO: handle exception
        }
        Commdata.postparams.add(new BasicNameValuePair("phone_manufacturer", phone_manufacturer));
        Commdata.postparams.add(new BasicNameValuePair("phone_mode", phone_mode));
        Commdata.postparams.add(new BasicNameValuePair("rom_vesion", rom_vesion));
        Commdata.postparams.add(new BasicNameValuePair("sdk_vesion", sdk_vesion));
        Commdata.postparams.add(new BasicNameValuePair("screen_dm", screen_dm));
        Commdata.postparams.add(new BasicNameValuePair("Language", Language));
        Commdata.postparams.add(new BasicNameValuePair("Country", Country));

    }

    private void getphoneinfo(Context mc) {
        try {
            TelephonyManager tm = (TelephonyManager) mc.getSystemService(Context.TELEPHONY_SERVICE);
            DeviceId = tm.getDeviceId();
            SubscriberId = tm.getSubscriberId();
        } catch (Exception e) {

        }
        Commdata.postparams.add(new BasicNameValuePair("DeviceId", DeviceId));
        Commdata.postparams.add(new BasicNameValuePair("SubscriberId", SubscriberId));
    }

    /*
     * private void userAgent(Context mc) { try { WebView wv = new WebView(mc);
     * userAgent = wv.getSettings().getUserAgentString(); } catch (Exception e)
     * {
     * 
     * } Commdate.postparams.add(new BasicNameValuePair("userAgent",
     * userAgent)); }
     */
    /*
     * private void getLocalMacAddress(Context mc) { try { WifiManager wifi =
     * (WifiManager) mc.getSystemService(Context.WIFI_SERVICE); WifiInfo info =
     * wifi.getConnectionInfo(); macAddress=info.getMacAddress(); } catch
     * (Exception e) {
     * 
     * } Commdate.postparams.add(new BasicNameValuePair("macAddress",
     * macAddress)); }
     */

    private void getANDROID_ID(Context mc) {
        try {
            ANDROID_ID = android.provider.Settings.System.getString(mc.getContentResolver(), "android_id");
        } catch (Exception e) {
            // TODO: handle exception
        }
        Commdata.postparams.add(new BasicNameValuePair("ANDROID_ID", ANDROID_ID));
    }

}
