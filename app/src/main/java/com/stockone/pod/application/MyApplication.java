package com.stockone.pod.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.androidnetworking.AndroidNetworking;
import com.blankj.utilcode.util.Utils;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import io.paperdb.Paper;

public class MyApplication extends Application {

    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
        Utils.init(this);
        AndroidNetworking.initialize(getApplicationContext());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseRemoteConfig.getInstance();

    }
}
