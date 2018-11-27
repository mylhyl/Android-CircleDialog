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
        //        ScaleLayoutConfig.init(this.getApplicationContext(),480,800);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
