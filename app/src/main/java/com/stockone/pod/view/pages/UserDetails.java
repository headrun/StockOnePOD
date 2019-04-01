package com.stockone.pod.view.pages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stockone.pod.R;
import com.stockone.pod.utils.ActivityManager;
import com.stockone.pod.utils.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetails extends AppCompatActivity {

    @BindView(R.id.img_user) ImageView img_user;
    @BindView(R.id.avi_user) AVLoadingIndicatorView avi;
    @BindView(R.id.btn_continue) CardView btn_continue;
    @BindView(R.id.et_first_name) EditText et_first_name;
    @BindView(R.id.et_last_name) EditText et_last_name;

    private String firstName, lastName;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.bg).into(img_user);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.USER_DETAILS);
    }

    @OnClick(R.id.btn_continue)
    void onButtonContinue(){

        firstName = et_first_name.getText().toString().trim();
        lastName = et_last_name.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            ToastUtils.showLong("Please enter First Name");
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            ToastUtils.showLong("Please enter Last Name");
            return;
        }

        avi.setVisibility(View.VISIBLE);
        avi.show();

        com.stockone.pod.model.UserDetails model = new com.stockone.pod.model.UserDetails();

        model.setName(firstName+" "+lastName);
        if (mAuth.getCurrentUser() != null) {
            model.setPhone_no(mAuth.getCurrentUser().getPhoneNumber());
            model.setUid(mAuth.getCurrentUser().getUid());
        }
        model.setCreated_at(System.currentTimeMillis());


        if (mAuth.getCurrentUser() != null) {
            databaseReference.child(mAuth.getCurrentUser().getUid()).setValue(model);
            ActivityManager.HOME(this);
            finish();
            avi.hide();
        }

    }
}
