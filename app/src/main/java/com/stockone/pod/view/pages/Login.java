package com.stockone.pod.view.pages;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.stockone.pod.BuildConfig;
import com.stockone.pod.R;
import com.stockone.pod.utils.ActivityManager;
import com.stockone.pod.utils.Constants;
import com.stockone.pod.utils.General;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.btn_sign_in) CardView btnSignIn;
    @BindView(R.id.avi_login) AVLoadingIndicatorView avi;
    @BindView(R.id.otp_rl) RelativeLayout otp_parent;
    @BindView(R.id.phone_rl) RelativeLayout number_parent;
    @BindView(R.id.edt_phone_no) EditText phoneNo;
    @BindView(R.id.edt_otp) EditText otpNo;
    @BindView(R.id.avi_otp) AVLoadingIndicatorView avi_otp;
    @BindColor(R.color.login_text) int bar_color;
    @BindView(R.id.img_login) ImageView img_login;
    @BindView(R.id.text_phone) TextView text_phone;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private FirebaseAuth mAuth;
    String color;

    @Override
    @AddTrace(name = "onCreateTrace", enabled = true /* optional */)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        Glide.with(this).load(R.drawable.bg).into(img_login);

        mAuth = FirebaseAuth.getInstance();

        BarUtils.setColor(this, bar_color);
        avi.hide();
        avi_otp.hide();
        phoneNo.requestFocus();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);
                LogUtils.e("onVerificationCompleted:" + phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                LogUtils.e("onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    LogUtils.e("Invalid Request");

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    LogUtils.e("The SMS quota for the project has been exceeded");
                }

            }
            @Override
            public void onCodeSent (String s, PhoneAuthProvider.ForceResendingToken token){
                super.onCodeSent(s, token);

                LogUtils.e( "onCodeSent:" + s);

                // Save verification ID and resending token so we can use them later
                mVerificationId = s;
                mResendToken = token;
                number_parent.setVisibility(View.GONE);
                otp_parent.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCodeAutoRetrievalTimeOut (String s){
                super.onCodeAutoRetrievalTimeOut(s);
            }

        };
    }

    @OnClick(R.id.btn_confirm)
    void onConfirmOTP(){

        confirmOTP();

    }

    @OnClick(R.id.btn_sign_in)
    void onSignClicked(){

        if (General.validatePhoneNumber(phoneNo)){
            btnSignIn.setVisibility(View.GONE);
            avi.show();
            confirmLogin();
            General.hideKeyboard(this, phoneNo);
        }
        else {
            btnSignIn.setVisibility(View.VISIBLE);
            avi.hide();
        }

    }

    private void confirmOTP(){

        String code = otpNo.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showLong("Field cannot be empty");
            avi_otp.hide();
            return;
        }
        avi_otp.show();
        verifyPhoneNumberWithCode(mVerificationId, code);
    }

    private void confirmLogin(){

        String phoneNumber = "+91"+phoneNo.getText().toString();
        otpNo.requestFocus();

        avi.show();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);

    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
        avi_otp.hide();
        if (mAuth.getCurrentUser() != null){

            getUpdate(mAuth.getCurrentUser().getUid());
        }
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                token);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            LogUtils.e( "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            LogUtils.e(user.getPhoneNumber());
                            if (mAuth.getCurrentUser() != null){

                                getUpdate(mAuth.getCurrentUser().getUid());
                            }
                        } else {
                            LogUtils.e( "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ToastUtils.showLong("Invalid code.");
                            }
                            ToastUtils.showLong("sign in failed");
                        }

                    }

                });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @OnClick(R.id.txt_resend_otp)
    void onResendOTP(){

        String phoneNumber = "+91"+phoneNo.getText().toString();
        resendVerificationCode(phoneNumber, mResendToken);
    }

    private void getUpdate(final String uid){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.USER_DETAILS);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.hasChild(uid)){
                            ActivityManager.HOME(Login.this);
                            finish();
                        }else {
                            ActivityManager.UserDetails(Login.this);
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
