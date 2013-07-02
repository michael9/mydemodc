package alialicoo.com.dice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class hpost {

    public void post2(List params, String addr) {

        if (params == null || params.size() == 0 || addr == null)
            return;

        /* 建立HTTP Post连线 */

        HttpPost httpRequest = new HttpPost(addr);
        // Post运作传送变数必须用NameValuePair[]阵列储存
        // 传参数 服务端获取的方法为request.getParameter("name")
        // List<NameValuePair> params = new ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair("test", "androidpost2"));

        try {
            // 发出HTTP request
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            // 取得HTTP response
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
            // 若状态码为200 ok
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 取出回应字串
                Commdata.return_str = EntityUtils.toString(httpResponse.getEntity());
            }

        } catch (IOException e) {
            return;
        }
    }
    
}
