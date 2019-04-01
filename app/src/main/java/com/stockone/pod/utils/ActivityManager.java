package com.stockone.pod.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.blankj.utilcode.util.ToastUtils;
import com.stockone.pod.view.pages.Login;
import com.stockone.pod.view.pages.MainActivity;
import com.stockone.pod.view.pages.OrderDetails;
import com.stockone.pod.view.pages.OrderList;
import com.stockone.pod.view.pages.RefusalDetails;
import com.stockone.pod.view.pages.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    public static void HOME(Context context){

        context.startActivity(new Intent(context, MainActivity.class));
    }

    public static void OrderList(Context context, String manifest_number){

        Intent intent = new Intent(context, OrderList.class);
        intent.putExtra("manifest_number", manifest_number);
        context.startActivity(intent);
    }

    public static void LOGIN(Context context){

        context.startActivity(new Intent(context, Login.class));
    }

    public static void UserDetails(Context context){

        context.startActivity(new Intent(context, UserDetails.class));
    }

    public static void OrderDetails(Context context, String loan_id, String customer_name, String model_desc,
                                    String district, String phone, String alt_phone, String serial_no,
                                    String manifest_number, String id_type, String id_card, String signed_invoice_copy,
                                    String id_proof_number, int position, boolean from_refusal){

        Intent intent = new Intent(context, OrderDetails.class);
        intent.putExtra("loan_id", loan_id);
        intent.putExtra("customer_name", customer_name);
        intent.putExtra("model_desc", model_desc);
        intent.putExtra("district", district);
        intent.putExtra("phone", phone);
        intent.putExtra("alt_phone", alt_phone);
        intent.putExtra("serial_no", serial_no);
        intent.putExtra("manifest_number", manifest_number);
        intent.putExtra("id_type", id_type);
        intent.putExtra("id_card", id_card);
        intent.putExtra("signed_invoice_copy", signed_invoice_copy);
        intent.putExtra("id_proof_number", id_proof_number);
        intent.putExtra("position", position);
        intent.putExtra("from_refusal", from_refusal);
        context.startActivity(intent);
    }

    public static void PERMISSION_TAB(Context context) {
        ToastUtils.showLong("Please Tap on Permission and allow us required permission.");
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void RefusalDetails(Context context, String loan_id, String customer_name, String model_desc,
                                    String district, String phone, String alt_phone, String serial_no,
                                    String manifest_number,int position, boolean returned, String id_type, String id_card,
                                    String signed_invoice_copy, String id_proof_number){

        Intent intent = new Intent(context, RefusalDetails.class);
        intent.putExtra("loan_id", loan_id);
        intent.putExtra("customer_name", customer_name);
        intent.putExtra("model_desc", model_desc);
        intent.putExtra("district", district);
        intent.putExtra("phone", phone);
        intent.putExtra("alt_phone", alt_phone);
        intent.putExtra("serial_no", serial_no);
        intent.putExtra("manifest_number", manifest_number);
        intent.putExtra("position", position);
        intent.putExtra("returned", returned);
        intent.putExtra("id_type", id_type);
        intent.putExtra("id_card", id_card);
        intent.putExtra("signed_invoice_copy", signed_invoice_copy);
        intent.putExtra("id_proof_number", id_proof_number);
        context.startActivity(intent);
    }
}
