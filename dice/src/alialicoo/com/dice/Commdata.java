package alialicoo.com.dice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.List;

import org.apache.http.NameValuePair;

import android.R.bool;
import android.content.Context;
import android.location.Location;

public class Commdata {
    
    public final static int versionCode_local = 16;    
    public static int versionCode_net=0;
    
    public static Context AppContext=null;
    
    public static  List<ListDownbean> downdiceslist;
    public final static int maxdices = 9;

    public static long start;
    
    public static boolean listflag=true;
    public static boolean refsdl=true;
//    public static boolean canplay=true;
    public static boolean autocoveredflag=false;
    
    public final static String svraddr="http://www.alialicoo.com/diceappsvr/";
    public final static String pstaddr = svraddr+"android_pst.php";
    public final static String pstaddr_exit = svraddr+"android_dr.php";
    public final static String pstaddr_count = svraddr+"dc_rank_count.php";
    public final static String getaddr_userrat=svraddr+"getuserrat.php";
    public final static String getdc_downlist_addr=svraddr+"dc_dwnlist.php";
    public final static String update_addrs=svraddr+"dice_update.php";
    public static String apkdownloadaddrs="";
     
    public static Location publiclm; 
  
    public static boolean helpshowflag=false;
    public static float userrat_f=0;
    
    public static String MD5ID = null;
    
    public static String dbpath = "/data/data/alialicoo.com.dice/databases/";
    public static String dbname = "dicedb.db";
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    public static boolean exit = false;

    public static List<NameValuePair> postparams;
    public static List<NameValuePair> postparams_exit;
    public static List<dice_bean2> dices;

    public static String return_str;

    

 

   

   
    
   

}
