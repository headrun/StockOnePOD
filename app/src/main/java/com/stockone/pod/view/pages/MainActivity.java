package com.stockone.pod.view.pages;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.stockone.pod.BuildConfig;
import com.stockone.pod.R;
import com.stockone.pod.model.OrderDetails;
import com.stockone.pod.network.Apis;
import com.stockone.pod.network.OnStringResponse;
import com.stockone.pod.network.UserApi;
import com.stockone.pod.utils.ActivityManager;
import com.stockone.pod.utils.Constants;
import com.stockone.pod.utils.General;
import com.stockone.pod.view.adapter.OrderListAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img_home) ImageView img_home;
    @BindView(R.id.manifest_no) EditText manifest_no;
    @BindView(R.id.avi_main) AVLoadingIndicatorView avi;
    @BindView(R.id.toolbar_home) Toolbar toolbar;
    @BindView(R.id.search_manifest) CardView search_manifest;
    @BindView(R.id.image_title) TextView image_title;
    @BindView(R.id.search_text) TextView search_text;
    @BindView(R.id.text_update) TextView text_update;
    @BindView(R.id.update_rl) RelativeLayout update_rl;

    private DatabaseReference mDatabase;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        avi.hide();

        Glide.with(this).load(R.drawable.bg).into(img_home);

        text_update.setPaintFlags(text_update.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Paper.book().delete("manifest_number");
        OrderListAdapter.list = new ArrayList<>();
        OrderListAdapter.offlineList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            String verCode = String.valueOf(pInfo.versionCode);
            getUpdate(verCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.search_manifest)
    void onSearch(){

        avi.show();

        final String manifest = manifest_no.getText().toString().trim();

        if (TextUtils.isEmpty(manifest)) {
            ToastUtils.showLong("Please Enter Manifest no.");
            avi.hide();
            return;
        }

        avi.show();

        search_manifest.setCardBackgroundColor(getResources().getColor(R.color.grey));
        search_text.setText("Please wait..");

        UserApi.getOrderDetails(manifest, 1, new OnStringResponse() {
            @Override
            public void onSuc(String data) {

                OrderDetails orderDetails = new Gson().fromJson(data, OrderDetails.class);
                General.storeManifestNo(manifest);
                General.setManifest(manifest, orderDetails.getData());
                LogUtils.e(General.getManifestList(manifest).size());
                ActivityManager.OrderList(MainActivity.this, manifest);
                avi.hide();
                search_manifest.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                search_text.setText("Search");

            }

            @Override
            public void onFail(int code, String msg) {
                ToastUtils.showLong(msg);
                avi.hide();
                search_manifest.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                search_text.setText("Search");
                LogUtils.e(msg);
            }

            @Override
            public void onErr(String cause) {

//                ToastUtils.showLong("Oops something went wrong..");

                if (Paper.book().exist(manifest)) {
                    General.storeManifestNo(manifest);
                    ActivityManager.OrderList(MainActivity.this, manifest);
                }else {
                    ToastUtils.showLong("Invalid manifest no!");
                    LogUtils.e(cause);
                }
                avi.hide();
                search_manifest.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                search_text.setText("Search");
            }
        });
    }

    @OnClick(R.id.logout)
    void onLogout(){

        final NormalDialog dialog = new NormalDialog(this);
        dialog.content("Are you sure want to Logout?")
                .title("Logout")
                .style(NormalDialog.STYLE_TWO)
                .titleTextSize(23)
                .btnText("Yes", "No")
                .show();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnBtnClickL(
                () -> {
                    dialog.dismiss();
                    FirebaseAuth.getInstance().signOut();
                    ActivityManager.LOGIN(MainActivity.this);
                    finish();
                },
                dialog::dismiss);
    }

    private void getUpdate(final String verCode){

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.APK_Path);
        mDatabase
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!dataSnapshot.hasChild(verCode)){

                            update_rl.setVisibility(View.VISIBLE);
                        }
                        else {

                            update_rl.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @OnClick(R.id.text_update)
    void onUpdateClicked(){

        General.rateApp(this);
    }
}
