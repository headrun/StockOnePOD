package com.stockone.pod.view.pages;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stockone.pod.R;
import com.stockone.pod.utils.ActivityManager;
import com.stockone.pod.utils.Constants;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Splash extends AppCompatActivity {

    @BindView(R.id.bg_splash) ImageView bg_splash;
    @BindView(R.id.lottie_splash) LottieAnimationView lottie;
    @BindView(R.id.avi_splash) AVLoadingIndicatorView avi;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.bg).into(bg_splash);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            lottie.setAnimation("truck.json");
            lottie.loop(true);
            lottie.playAnimation();
        }else {

            avi.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null){

            getUpdate(mAuth.getCurrentUser().getUid());

        }else {

            ActivityManager.LOGIN(Splash.this);
            finish();
        }
    }

    private void getUpdate(final String uid){


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.USER_DETAILS);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(uid)){
                    ActivityManager.HOME(Splash.this);
                    finish();
                }else {
                    ActivityManager.UserDetails(Splash.this);
                    finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
