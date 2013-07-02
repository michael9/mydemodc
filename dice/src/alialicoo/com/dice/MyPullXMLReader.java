package alialicoo.com.dice;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class MyPullXMLReader {
    
    public static List<ListDownbean> readXML(InputStream inStream) {
        XmlPullParser parser = Xml.newPullParser();
        ListDownbean currentDownbean = null;
        List<ListDownbean> Downbeans = null;
        try {
            parser.setInput(inStream, "UTF-8");
            int eventType = parser.getEventType();
            
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {

                case XmlPullParser.START_DOCUMENT:// 文档开始事件,可以进行数据初始化处理
                    Downbeans = new ArrayList<ListDownbean>();
                    break;

                case XmlPullParser.START_TAG:// 开始元素事件
                    String name = parser.getName();

                    if (name.equalsIgnoreCase("dice")) {
                        currentDownbean = new ListDownbean();
                        currentDownbean.DICE_ID=parser.getAttributeValue(null, "DICE_ID");
                        currentDownbean.type=Integer.parseInt( parser.getAttributeValue(null, "type"));                        
                        currentDownbean.setIcon_addr(parser.getAttributeValue(null, "icon_addr"));
//                        currentDownbean.setImage_addr(parser.getAttributeValue(null, "image_addr"));
                        currentDownbean.setImage_addr(parser.getAttributeValue(null, "dc_bmp1"));
                        currentDownbean.author=parser.getAttributeValue(null, "author");
                        currentDownbean.ds_name=parser.getAttributeValue(null, "dc_name");
                        currentDownbean.rank=Integer.parseInt(parser.getAttributeValue(null, "rank"));
                        //                        currentPerson.setId(new Integer(parser.getAttributeValue(null, "id")));
                    } 
                    break;

                case XmlPullParser.END_TAG:// 结束元素事件
                    if (parser.getName().equalsIgnoreCase("dice") && currentDownbean != null) {
                        Downbeans.add(currentDownbean);
                        currentDownbean = null;
                    }
                    break;
                }
                eventType = parser.next();
            }
            inStream.close();
        } catch (Exception e) {
        return null;
        }
        return Downbeans;
    }
}
