package com.stockone.pod.network;

import android.util.Log;

import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.stockone.pod.model.RequestHeader;

public class RequestListener implements StringRequestListener {

    private OnStringResponse onStringResponse;

    public RequestListener(OnStringResponse onStringResponse) {
        this.onStringResponse = onStringResponse;
    }

    @Override
    public void onResponse(String response) {

        Log.e("NETWORK ", "-> " + response);
        try {

            RequestHeader requestHeader = new Gson().fromJson(response, RequestHeader.class);
            if (requestHeader.isSuc()) {
                onStringResponse.onSuc(response);
                Log.e("NETWORK SUC", "-> " + response);
            } else {
                onStringResponse.onFail(requestHeader.getCode(), requestHeader.getMsg());
                Log.e("NETWORK FAIL", "-> " + response);
            }
        } catch (Exception e) {
            onStringResponse.onErr(response);
            Log.e("NETWORK ERR", "-> " + e.getMessage());
        }
    }

    @Override
    public void onError(ANError anError) {

        LogUtils.e("NTWRK ERR", "-> " + anError);
        onStringResponse.onErr(anError.getErrorBody());
    }
}