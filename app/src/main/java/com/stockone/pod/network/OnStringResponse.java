package com.stockone.pod.network;

public interface OnStringResponse {

    void onSuc(String data);
    void onFail(int code, String msg);
    void onErr(String cause);

}
