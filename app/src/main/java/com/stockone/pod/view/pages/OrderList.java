package com.stockone.pod.view.pages;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.stockone.pod.R;
import com.stockone.pod.callback.OnReloadListener;
import com.stockone.pod.model.OrderDetails;
import com.stockone.pod.network.OnStringResponse;
import com.stockone.pod.network.UserApi;
import com.stockone.pod.utils.Constants;
import com.stockone.pod.utils.General;
import com.stockone.pod.view.adapter.OrderListAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderList extends AppCompatActivity {

    @BindView(R.id.rv_order_list) RecyclerView recyclerView;
    @BindView(R.id.toolbar_order) Toolbar toolbar;
    @BindView(R.id.img_order) ImageView img_order;
    @BindView(R.id.avi_order_list) AVLoadingIndicatorView avi;
    @BindView(R.id.avi_bottom) AVLoadingIndicatorView avi_bottom;
    private OrderListAdapter adapter;
    private String manifest_number;
    private List<OrderDetails.DataBean> list = new ArrayList<>();
    private DatabaseReference mDatabase;
    LinearLayoutManager linearLayoutManager;
    private boolean loading = true;
    private int previousTotal = 0;
    private int visibleThreshold = 10;
    int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent extras = getIntent();
        Bundle bundle = extras.getExtras();
        if(bundle == null){
            ToastUtils.showLong("Some problem occurs.");
            finish();
            return;
        }
        manifest_number = bundle.getString("manifest_number");

        Glide.with(this).load(R.drawable.bg).into(img_order);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderListAdapter(this);
        recyclerView.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.ORDER_DETAILS);

        if (NetworkUtils.isConnected()){
            getOrderDetails(1);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!NetworkUtils.isConnected()){

            OrderListAdapter.list = General.getManifestList(manifest_number);
            adapter.setOfflineList(General.getManifestList(manifest_number));
            recyclerView.setAdapter(adapter);
            avi.hide();
        }

        if (NetworkUtils.isConnected()) {

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    if (dy > 0) {

                        int visibleItemCount = linearLayoutManager.getChildCount();
                        int totalItemCount = linearLayoutManager.getItemCount();
                        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                        if (loading) {
                            if (totalItemCount > previousTotal) {
                                loading = false;
                                previousTotal = totalItemCount;
                            }
                        }
                        if (!loading && (totalItemCount - visibleItemCount)
                                <= (firstVisibleItemPosition + visibleThreshold)) {
                            // End has been reached
                            count++;
                            getOrderDetails(count);
                            avi_bottom.setVisibility(View.VISIBLE);
                            loading = true;
                        }
                    }
                }
            });
        }

    }

    private void getOrderDetails(int page){

        UserApi.getOrderDetails(manifest_number, page, new OnStringResponse() {
            @Override
            public void onSuc(String data) {
                OrderDetails orderDetails = new Gson().fromJson(data, OrderDetails.class);
                if(page == 1) {
                    adapter.setList(orderDetails.getData());
                }
                else{
                    adapter.addOfflineList(orderDetails.getData());
                    OrderListAdapter.list = General.getManifestList(manifest_number);
                    General.setManifest(manifest_number, OrderListAdapter.offlineList);
                    adapter.addList(orderDetails.getData());
                    avi_bottom.setVisibility(View.GONE);
                }
                avi.hide();
            }

            @Override
            public void onFail(int code, String msg) {

                ToastUtils.showLong(msg);

                avi.hide();
            }

            @Override
            public void onErr(String cause) {

                ToastUtils.showLong("Oops something went wrong !!");
                avi.hide();
                avi_bottom.setVisibility(View.GONE);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
