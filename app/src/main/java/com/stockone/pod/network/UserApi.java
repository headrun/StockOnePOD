package com.stockone.pod.network;


import java.util.HashMap;
import java.util.Map;

public class UserApi {


    public static void getOrderDetails(String manifest_number, int page, OnStringResponse onStringResponse){

        Map<String, String> params = new HashMap<>();
        params.put("manifest_number", manifest_number);
        params.put("page", String.valueOf(page));

        ApiRequest.GET(Apis.ORDER_DETAILS, params, onStringResponse);

    }


    public static void getConfirmOrder(String loan_proposal_id, OnStringResponse onStringResponse){

        Map<String, String> params = new HashMap<>();
        params.put("loan_proposal_id", loan_proposal_id);

        ApiRequest.GET(Apis.CONFIRM_ORDER, params, onStringResponse);

    }

}
