package alialicoo.com.dice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.util.Log;

public class hget {

    public String getcontext(String urlstr){
        String resultData="";
        try{
            URL url=new URL(urlstr);          
            HttpURLConnection urlConn=(HttpURLConnection)url.openConnection(); 
//            Log.e("httpget", urlstr);
          //设置输入和输出流  
            urlConn.setDoOutput(true);  
            urlConn.setDoInput(true);  
//            
            urlConn.setRequestMethod("GET");  
//            //POST请求不能使用缓存  
//            urlConn.setUseCaches(false);  
            urlConn.setReadTimeout(20000);
            urlConn.setConnectTimeout(10000);
          
            //得到读取的内容(流)  
            InputStreamReader in = new InputStreamReader(urlConn.getInputStream()); 
          
            // 为输出创建BufferedReader  
            BufferedReader buffer = new BufferedReader(in);  
            String inputLine = null;  
            
//            Log.e("httpget", "read...");
            //使用循环来读取获得的数据  
            while (((inputLine = buffer.readLine()) != null))  
            {  
//                Log.e("httpget", "read..."+inputLine.length());
                //我们在每一行后面加上一个"\n"来换行  
                resultData += inputLine ;  
            }           
            
            //关闭InputStreamReader  
            in.close();  
            //关闭http连接  
            urlConn.disconnect(); 
//            Log.e("httpget", "disconnect");
            
        }catch (Exception e) {
        	e.printStackTrace();
    }
        return resultData;
    }
    
}
