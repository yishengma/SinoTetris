package yishengma.sino_tetris.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by PirateHat on 18-10-8.
 */

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getsContext() {
        return sContext;
    }
}
