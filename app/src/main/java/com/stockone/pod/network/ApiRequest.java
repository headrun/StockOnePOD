package com.stockone.pod.network;

import android.util.Log;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ConnectionQuality;
import com.androidnetworking.common.Priority;
import com.androidnetworking.interfaces.ConnectionQualityChangeListener;
import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ApiRequest  {

    public static void GET(String url, Map<String, String> params, OnStringResponse onStringResponse ){
        if(params == null){
            params = new HashMap<>();
        }
        Log.e("url", url);
        AndroidNetworking.get(url)
                .addPathParameter(params)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(new RequestListener(onStringResponse));
    }
}
