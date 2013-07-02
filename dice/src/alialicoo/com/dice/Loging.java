package alialicoo.com.dice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Loging extends Activity{
    EditText loging_username=null;
    EditText loging_passwords=null;
    Button  loging_ok=null;
    Button loging_cancel=null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loging);
        findViews();
        setCommondListener();
    }
    
    void findViews(){
        loging_username=(EditText)findViewById(R.id.loging_username);
        loging_passwords=(EditText)findViewById(R.id.loging_passwords);
        loging_ok=(Button)findViewById(R.id.loging_ok);     
        loging_cancel=(Button)findViewById(R.id.loging_cancel);       
    }
    
    void setCommondListener(){
        loging_ok.setOnClickListener(new UIclicked());
        loging_cancel.setOnClickListener(new UIclicked());
    }
    
    class UIclicked implements OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
            case R.id.loging_ok:
                
                break;
            case R.id.loging_cancel:
                finish();
                break;

            default:
                break;
            }
            
        }        
    }

}
