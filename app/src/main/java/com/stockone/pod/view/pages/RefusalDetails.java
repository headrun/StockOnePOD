package com.stockone.pod.view.pages;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
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
import com.stockone.pod.utils.General;
import com.stockone.pod.view.adapter.OrderListAdapter;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RefusalDetails extends AppCompatActivity {

    @BindView(R.id.toolbar_refusal_details) Toolbar toolbar;
    @BindView(R.id.img_refusal_details) ImageView img_refusal_details;
    @BindView(R.id.loan_id) EditText loan;
    @BindView(R.id.order_customer) EditText order_customer;
    @BindView(R.id.et_reason) EditText et_reason;
    @BindView(R.id.spinner_reason) BetterSpinner spinner_reason;
    @BindView(R.id.btn_refusal_submit) Button btn_refusal_submit;
    @BindView(R.id.reason_text) TextView reason_text;
    @BindView(R.id.txt_reason) TextView txt_reason;
    @BindView(R.id.btn_refusal_submitted) Button btn_refusal_submitted;
    @BindView(R.id.update_order_rl) RelativeLayout update_order_rl;

    String loan_id, customer_name, model_desc, district, serial_no,
            manifest_number, id_type, id_card, signed_invoice_copy, id_proof_number = "";
    int position;
    Boolean returned;
    String mob_no, alt_mob_no;
    private FirebaseAuth mAuth;
    private com.stockone.pod.model.OrderDetails.DataBean order_model = new com.stockone.pod.model.OrderDetails.DataBean();
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refusal_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.ORDER_DETAILS);

        mAuth = FirebaseAuth.getInstance();

        Glide.with(this).load(R.drawable.bg).into(img_refusal_details);


        Intent extras = getIntent();
        Bundle bundle = extras.getExtras();
        if(bundle == null){
            ToastUtils.showLong("Some problem occurs.");
            finish();
            return;
        }
        loan_id = bundle.getString("loan_id");
        customer_name = bundle.getString("customer_name");
        model_desc = bundle.getString("model_desc");
        district = bundle.getString("district");
        mob_no = bundle.getString("phone");
        alt_mob_no = bundle.getString("alt_phone");
        serial_no = bundle.getString("serial_no");
        manifest_number = bundle.getString("manifest_number");
        position = bundle.getInt("position");
        returned = bundle.getBoolean("returned");
        id_type = bundle.getString("id_type");
        id_card = bundle.getString("id_card");
        signed_invoice_copy = bundle.getString("signed_invoice_copy");
        id_proof_number = bundle.getString("id_proof_number");

        loan.setText(loan_id);
        order_customer.setText(customer_name);

        String[] list = getResources().getStringArray(R.array.reason);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        spinner_reason.setAdapter(adapter);

        spinner_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (spinner_reason.getText().toString().equals("Others")){
                    et_reason.setVisibility(View.VISIBLE);
                }else {
                    et_reason.setVisibility(View.GONE);
                }
            }
        });

        getOrderDetails();

        if (returned){
            btn_refusal_submit.setVisibility(View.GONE);
            btn_refusal_submitted.setVisibility(View.VISIBLE);
            reason_text.setVisibility(View.VISIBLE);
            spinner_reason.setVisibility(View.GONE);
            update_order_rl.setVisibility(View.GONE);
        }else {

            update_order_rl.setVisibility(View.VISIBLE);
            btn_refusal_submit.setVisibility(View.VISIBLE);
            btn_refusal_submitted.setVisibility(View.GONE);
            reason_text.setVisibility(View.VISIBLE);
            spinner_reason.setVisibility(View.VISIBLE);
            spinner_reason.setHint("Update Reason");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_refusal_submit)
    void onRefusalSubmit(){

        String reason = spinner_reason.getText().toString().trim();
        String other_reason = et_reason.getText().toString().trim();

        if (TextUtils.isEmpty(reason)) {
            ToastUtils.showLong("Please select reason");
            return;
        }

        if (reason.equals("Others")){
            if (TextUtils.isEmpty(other_reason)) {
                ToastUtils.showLong("Please mention reason");
                return;
            }
        }

        btn_refusal_submit.setText("Please wait...");
        btn_refusal_submit.setVisibility(View.GONE);
        btn_refusal_submitted.setVisibility(View.VISIBLE);

        if (!NetworkUtils.isConnected()){
            order_model.setLoan_proposal_id(loan.getText().toString());
            order_model.setCustomer_name(order_customer.getText().toString());
            order_model.setDistrict(district);
            order_model.setModel(model_desc);
            order_model.setMobile_no(mob_no);
            order_model.setAlternative_mobile_no(alt_mob_no);
            order_model.setPod_status(true);
            order_model.setSerial_number(serial_no);
            order_model.setTime(System.currentTimeMillis());
            order_model.setUid(mAuth.getCurrentUser().getUid());
            order_model.setRefusal(true);
            if (reason.equals("Others")){
                order_model.setRefusal_reason(other_reason);
            }else {
                order_model.setRefusal_reason(reason);
            }
            order_model.setManifest_no(manifest_number);
            order_model.setUploaded(false);
            OrderListAdapter.offlineList.set(position, order_model);
            OrderListAdapter.list.set(position, order_model);
            LogUtils.e("Offline refusal");
            General.setManifest(manifest_number, OrderListAdapter.offlineList);

        }else {
            order_model.setLoan_proposal_id(loan.getText().toString());
            order_model.setCustomer_name(order_customer.getText().toString());
            order_model.setDistrict(district);
            order_model.setModel(model_desc);
            order_model.setMobile_no(mob_no);
            order_model.setAlternative_mobile_no(alt_mob_no);
            order_model.setPod_status(true);
            order_model.setSerial_number(serial_no);
            order_model.setTime(System.currentTimeMillis());
            order_model.setUid(mAuth.getCurrentUser().getUid());
            order_model.setRefusal(true);
            if (reason.equals("Others")){
                order_model.setRefusal_reason(other_reason);
            }else {
                order_model.setRefusal_reason(reason);
            }
            order_model.setManifest_no(manifest_number);
            order_model.setUploaded(true);

            LogUtils.e(manifest_number);
            OrderListAdapter.offlineList.set(position, order_model);
            General.setManifest(manifest_number, OrderListAdapter.offlineList);
            // Getting the ID from firebase database.
            String IDFromServer = loan.getText().toString();
            mDatabase.child(IDFromServer).setValue(order_model);
        }
    }

    private void getOrderDetails(){

        mDatabase.child(loan_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null){

                    if (dataSnapshot.child("refusal").getValue().equals(false)){

                        update_order_rl.setVisibility(View.GONE);
                        btn_refusal_submit.setVisibility(View.GONE);
                        btn_refusal_submitted.setVisibility(View.VISIBLE);
                        btn_refusal_submitted.setText("Order Accepted");
                        reason_text.setVisibility(View.GONE);
                        spinner_reason.setVisibility(View.GONE);
                        txt_reason.setVisibility(View.GONE);
                    }

                    reason_text.setText(dataSnapshot.child("refusal_reason").getValue()+"");

                }else {

                    btn_refusal_submit.setVisibility(View.VISIBLE);
                    btn_refusal_submitted.setVisibility(View.GONE);
                    reason_text.setVisibility(View.GONE);
                    spinner_reason.setVisibility(View.VISIBLE);
                    update_order_rl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.update_order_rl)
    void onUpdateOrderClicked(){

        ActivityManager.OrderDetails(this, loan_id, customer_name, model_desc,
                district, mob_no, alt_mob_no,
                serial_no, manifest_number, id_type, id_card,
                signed_invoice_copy, id_proof_number, position, true);
    }

}
