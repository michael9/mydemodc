package alialicoo.com.dice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class dice_bean {
    private ImageView div = null;
    private int randoem = 0;
    private int[] ds = null;
    private boolean baf = false;
    AnimationDrawable mad;
    static public Context mc = null;
    Bitmap mdiceimage = null;

    public boolean isBaf() {
        return baf;
    }

    public void setBaf(boolean baf) {
        this.baf = baf;
    }

    private void setlin() {
        div.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                div.setVisibility(8);
                return true;
            }
        });
    }

    public ImageView getDiv() {
        return div;
    }

    public void setDiv(ImageView div) {
        this.div = div;
        setlin();
    }

    public int getRandoem() {
        return randoem;
    }

    private void setdiceimage(int i) {
        if (ds != null && div != null) {

            if (mad != null) {
                if (mad.isRunning()) {
                    mad.stop();
                }
            }

            if (!baf) {
                div.setImageResource(ds[i]);
                div.setBackgroundDrawable(null);
            } else {
                // mad=(AnimationDrawable)mc.getResources().getAnimation(ds[i]);
                div.setImageDrawable(null);
                div.setBackgroundResource(ds[i]);
                mad = (AnimationDrawable) div.getBackground();
                // div.setImageDrawable(mad);
                mad.start();
            }
        }
    }

    public void setRandoem(int randoem) {
        this.randoem = randoem;
        setdiceimage(randoem);
    }

    public int[] getDs() {
        return ds;
    }

    public void setDs(int[] ds) {
        this.ds = ds;
    }

    public void setshowflag(int flag) {
        div.setVisibility(flag);
    }

    public boolean getisshow() {
        return div.isShown();
    }

    public void setdicesize(int w, int h) {
        if (div != null) {
            RelativeLayout.LayoutParams mrl = (RelativeLayout.LayoutParams) div.getLayoutParams();
            mrl.width = w;
            mrl.height = h;
            div.setLayoutParams(mrl);
            div.invalidate();
        }
    }

}
