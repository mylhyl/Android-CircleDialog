package com.mylhyl.circledialog.sample;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hupei on 2018/2/3.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
