package barqsoft.footballscores;

import android.app.Application;
import android.content.Context;

/**
 * Created by matthiasko on 12/21/15.
 */
public class MyApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}