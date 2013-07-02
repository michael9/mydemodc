package alialicoo.com.dice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

public class MyUtil {

    
   String getPhotoShareImageAdds(){
       String mtemp=null;
       mtemp=Environment.getExternalStorageDirectory().getAbsolutePath();
       mtemp+="/happydice_PhotoShareImage_"+System.currentTimeMillis()+".jpg";
       return mtemp;
   }
   
   String getScreenShareImageAdds(){
       String mtemp=null;
       mtemp=Environment.getExternalStorageDirectory().getAbsolutePath();
       mtemp+="/happydice_ScreenShareImage_"+System.currentTimeMillis()+".jpg";
       return mtemp;
   }
   
   public static InputStream String2InputStream(String str){
       ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
       return stream;
    }
   
   public static String md5encrypt(String str) {
       try {
           byte[] res = str.getBytes();
           MessageDigest md = MessageDigest.getInstance("MD5".toUpperCase());
           byte[] result = md.digest(res);
           for (int i = 0; i < result.length; i++) {
               md.update(result[i]);

           }
           byte[] hash = md.digest();
           StringBuffer d = new StringBuffer("");
           for (int i = 0; i < hash.length; i++) {
               int v = hash[i] & 0xFF;
               if (v < 16) {
                   d.append("0");
               }
               d.append(Integer.toString(v, 16).toUpperCase() + "");
           }
           return d.toString();
       } catch (Exception e) {
           // LOG.error("Encrypt MD5 failed.", e);
           return "";
       }
   }
  
}
