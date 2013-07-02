package alialicoo.com.dice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class hgimage {

    public Bitmap getimg(String src) {
        if (src == null) {
            return null;
        }
        Bitmap mb=null;
        int iMAGE_PAGE_SIZE = 1024 * 2;
        byte[] imageByte = new byte[iMAGE_PAGE_SIZE];
        InputStream inputStream = null;
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        int len = 0;
        long st = 0;
        try {
            URL url = new URL(src);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.connect();
            inputStream = conn.getInputStream();
            while ((len = inputStream.read(imageByte)) > 0) {
                byteOutStream.write(imageByte, 0, len);
            }
            inputStream.close();
            mb = BitmapFactory.decodeByteArray(byteOutStream.toByteArray(), 0, byteOutStream.size());

        } catch (Exception e) {
            
        } 
        return mb;
    }
}
