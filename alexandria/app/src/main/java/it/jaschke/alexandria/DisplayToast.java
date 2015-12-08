package it.jaschke.alexandria;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by matthiasko on 12/8/15.
 */
public class DisplayToast implements Runnable {
    private final Context mContext;
    String mText;

    public DisplayToast(Context mContext, String text){
        this.mContext = mContext;
        mText = text;
    }

    public void run(){
        Toast.makeText(mContext, mText, Toast.LENGTH_LONG).show();
    }
}
