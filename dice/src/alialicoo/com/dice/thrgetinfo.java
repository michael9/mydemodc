package alialicoo.com.dice;

import java.util.List;

public class thrgetinfo extends Thread {
    List mpost_params=null;
    String mpost_addr=null;
    
    public thrgetinfo(List params, String addr) {
        // TODO Auto-generated constructor stub
        mpost_params=params;
        mpost_addr=addr;        
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        super.run();
        if(mpost_params.size()>0&&mpost_addr!=null){
        hpost mhp = new hpost();
        mhp.post2(mpost_params,mpost_addr);
        }
        return;
//        if (!Commdate.exit) {
//            mhp.post2(Commdate.postparams, Commdate.pstaddr);
//        } else {
//            mhp.post2(Commdate.postparams_exit, Commdate.pstaddr_exit);
//        }
        
    }

}
