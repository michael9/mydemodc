package alialicoo.com.dice;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppUpdate {

    public void downapp() {
        downth dth = new downth();
        dth.start();
    }
}

class downth extends Thread {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();

        try {
            URL url = new URL(Commdata.update_addrs);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置输入和输出流
            // urlConn.setDoOutput(true);
            // urlConn.setDoInput(true);
            //
            // urlConn.setRequestMethod("GET");
            // POST请求不能使用缓存
            // urlConn.setUseCaches(false);
            // urlConn.setReadTimeout(20000);
            // urlConn.setConnectTimeout(10000);

            // 得到读取的内容(流)
            long fl = urlConn.getContentLength();

            // InputStreamReader in = new
            // InputStreamReader(urlConn.getInputStream());
            // 为输出创建BufferedReader
            // BufferedReader buffer = new BufferedReader(in);
            InputStream inputStream = null;
            inputStream = urlConn.getInputStream();
            int iAPK_SIZE = 1024 * 2;
            byte[] APKByte = new byte[iAPK_SIZE];
            ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
            int len;
            while ((len = inputStream.read(APKByte)) > 0) {
                byteOutStream.write(APKByte, 0, len);
            }
            // 使用循环来读取获得的数据

            // 关闭InputStreamReader
            // in.close();
            // 关闭http连接
            urlConn.disconnect();

        } catch (Exception e) {
            // TODO: handle exception
            return;
        }
    }
}