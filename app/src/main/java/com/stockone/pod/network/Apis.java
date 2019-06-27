package com.stockone.pod.network;

public class Apis {

//    private static final String HOST = "http://95.216.96.177:9001/rest_api/";
    private static final String HOST = "https://api.stockone.in/rest_api/";
    public static final String ORDER_DETAILS = HOST+"app_shipment_info_data/?manifest_number={manifest_number}/{page}";
    public static final String CONFIRM_ORDER = HOST + "confirm_order_request/?loan_proposal_id={loan_proposal_id}";


//    public static final String ORDER_DETAILS = HOST+"app_shipment_info_data/?manifest_number={manifest_number}";

//    manifest_number=86042934

    //Loan id = 5111933000076094

}
