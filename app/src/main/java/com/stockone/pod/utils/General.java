package com.stockone.pod.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
import com.stockone.pod.model.OrderDetails;

import java.util.List;

import io.paperdb.Paper;

public class General {

    public static boolean validatePhoneNumber(EditText mPhoneNumberField) {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length()>10 || phoneNumber.length()<9) {
            ToastUtils.showLong("Invalid phone number");
            return false;
        }

        return true;
    }

    public static void hideKeyboard(Context context, EditText editText){

        InputMethodManager inputManager = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(editText.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void storeManifestNo(String manifest_number){
        Paper.book().write("manifest_number",manifest_number);
    }

    public static String getManifestNo(){
        return Paper.book().read("manifest_number");
    }


    public static void setManifest(String manifest_number, List<OrderDetails.DataBean> orderDetails){
//        if (getManifestList(manifest_number) != null)
//            orderDetails.addAll(getManifestList(manifest_number));
        Paper.book().write(manifest_number, orderDetails);
    }

    public static List<OrderDetails.DataBean> getManifestList(String manifest_number){

        return Paper.book().read(manifest_number);
    }

    public static void rateApp(Context context)
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details", context);
            context.startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details", context);
            context.startActivity(rateIntent);
        }
    }

    private static Intent rateIntentForUrl(String url, Context context)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, context.getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }


}
