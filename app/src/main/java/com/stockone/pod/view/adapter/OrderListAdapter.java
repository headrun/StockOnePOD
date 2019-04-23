package com.stockone.pod.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.stockone.pod.R;
import com.stockone.pod.callback.OnReloadListener;
import com.stockone.pod.model.OrderDetails;
import com.stockone.pod.utils.ActivityManager;
import com.stockone.pod.utils.Constants;
import com.stockone.pod.utils.General;
import com.stockone.pod.view.pages.MainActivity;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>{

    private Context context;
    public static List<OrderDetails.DataBean> list = new ArrayList<>();
    public static List<OrderDetails.DataBean> offlineList = General.getManifestList(General.getManifestNo());

    public OrderListAdapter(Context context) {
        this.context = context;
        offlineList = General.getManifestList(General.getManifestNo());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_order_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final OrderDetails.DataBean model = list.get(i);
        final OrderDetails.DataBean offlineModel = offlineList.get(i);

        if (!NetworkUtils.isConnected()){

            viewHolder.customer_name.setText(offlineModel.getCustomer_name());
            viewHolder.loan_proposal_id.setText(offlineModel.getLoan_proposal_id());
            viewHolder.list_serial_no.setText(offlineModel.getSerial_number());

            if (offlineModel.isRefusal()){
                viewHolder.pod_status.setText("Refusal");
                viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.yellow));

                viewHolder.itemView.setOnClickListener(v -> ActivityManager.RefusalDetails(context, offlineModel.getLoan_proposal_id(), offlineModel.getCustomer_name(), offlineModel.getModel(),
                        offlineModel.getDistrict(), offlineModel.getMobile_no(), offlineModel.getAlternative_mobile_no(),
                        offlineModel.getSerial_number(), General.getManifestNo(),i, offlineModel.isReturned(), offlineModel.getId_type(), offlineModel.getId_card(),
                        offlineModel.getSigned_invoice_copy(), offlineModel.getId_proof_number()));

            }else {

                if (offlineModel.isPod_status()){
                    viewHolder.pod_status.setText("Completed");
                    viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.green));

                    viewHolder.itemView.setOnClickListener(v -> ActivityManager.OrderDetails(context, offlineModel.getLoan_proposal_id(), offlineModel.getCustomer_name(), offlineModel.getModel(),
                            offlineModel.getDistrict(), offlineModel.getMobile_no(), offlineModel.getAlternative_mobile_no(),
                            offlineModel.getSerial_number(), General.getManifestNo(), offlineModel.getId_type(), offlineModel.getId_card(),
                            offlineModel.getSigned_invoice_copy(), offlineModel.getId_proof_number(), i, false));

                }else if (!offlineModel.isPod_status()){
                    viewHolder.pod_status.setText("Pending");
                    viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.red));

                    viewHolder.itemView.setOnClickListener(v -> {

                        final NormalDialog dialog = new NormalDialog(context);
                        dialog.content("Please select order status")
                                .title("Order Status")
                                .style(NormalDialog.STYLE_TWO)
                                .titleTextSize(23)
                                .btnText("Accept", "Refusal")
                                .btnTextColor(R.color.green, R.color.red)
                                .show();
                        dialog.setCanceledOnTouchOutside(true);

                        dialog.setOnBtnClickL(
                                () -> {
                                    dialog.dismiss();
                                    ActivityManager.OrderDetails(context, offlineModel.getLoan_proposal_id(), offlineModel.getCustomer_name(), offlineModel.getModel(),
                                            offlineModel.getDistrict(), offlineModel.getMobile_no(), offlineModel.getAlternative_mobile_no(),
                                            offlineModel.getSerial_number(), General.getManifestNo(), offlineModel.getId_type(), offlineModel.getId_card(),
                                            offlineModel.getSigned_invoice_copy(), offlineModel.getId_proof_number(), i, false);

                                },
                                () -> {
                                    dialog.dismiss();
                                    ActivityManager.RefusalDetails(context, offlineModel.getLoan_proposal_id(), offlineModel.getCustomer_name(), offlineModel.getModel(),
                                            offlineModel.getDistrict(), offlineModel.getMobile_no(), offlineModel.getAlternative_mobile_no(),
                                            offlineModel.getSerial_number(), General.getManifestNo(),i, offlineModel.isReturned(), offlineModel.getId_type(), offlineModel.getId_card(),
                                            offlineModel.getSigned_invoice_copy(), offlineModel.getId_proof_number());

                                });

                    });

                }

            }

        }
        else {

            viewHolder.customer_name.setText(model.getCustomer_name());
            viewHolder.loan_proposal_id.setText(model.getLoan_proposal_id());
            viewHolder.list_serial_no.setText(model.getSerial_number());

            viewHolder.itemView.setOnClickListener(v -> {

                final NormalDialog dialog = new NormalDialog(context);
                dialog.content("Please select order status")
                        .title("Order Status")
                        .style(NormalDialog.STYLE_TWO)
                        .titleTextSize(23)
                        .btnText("Accept", "Refusal")
                        .btnTextColor(R.color.green, R.color.red)
                        .show();
                dialog.setCanceledOnTouchOutside(true);

                dialog.setOnBtnClickL(
                        () -> {
                            dialog.dismiss();
                            ActivityManager.OrderDetails(context, model.getLoan_proposal_id(), model.getCustomer_name(), model.getModel(),
                                    model.getDistrict(), model.getMobile_no(), model.getAlternative_mobile_no(),
                                    model.getSerial_number(), General.getManifestNo(), model.getId_type(), model.getId_card(),
                                    model.getSigned_invoice_copy(), model.getId_proof_number(),i, false);

                        },
                        () -> {
                            dialog.dismiss();
                            ActivityManager.RefusalDetails(context, model.getLoan_proposal_id(), model.getCustomer_name(), model.getModel(),
                                    model.getDistrict(), model.getMobile_no(), model.getAlternative_mobile_no(),
                                    model.getSerial_number(), General.getManifestNo(),i, model.isReturned(), model.getId_type(), model.getId_card(),
                                    model.getSigned_invoice_copy(), model.getId_proof_number());

                        });
            });


            getOrderDetails(model.getLoan_proposal_id(), viewHolder, model, i);

        }

        if (!offlineModel.isUploaded()){
            viewHolder.img_not_uploaded.setVisibility(View.VISIBLE);
        }else {
            viewHolder.img_not_uploaded.setVisibility(View.INVISIBLE);
        }

        int count = i + 1;

        viewHolder.sl_no.setText(count+".");

        if (!offlineModel.isUploaded()){

            viewHolder.img_not_uploaded.setVisibility(View.VISIBLE);
            StorageReference storageReference;
            DatabaseReference mDatabase;

            storageReference = FirebaseStorage.getInstance().getReference();
            mDatabase = FirebaseDatabase.getInstance().getReference(Constants.ORDER_DETAILS);

            if (offlineModel.getSigned_invoice_copy() != null) {

                final StorageReference acknowledgment_ref = storageReference.child("acknowledgmnt_images/" + offlineModel.getLoan_proposal_id() + "_2");
                final StorageReference id_ref = storageReference.child("id_images/" + offlineModel.getLoan_proposal_id() + "_1");
                acknowledgment_ref.putFile(Uri.parse(offlineModel.getSigned_invoice_copy()))
                        .addOnSuccessListener(taskSnapshot -> {

                            acknowledgment_ref.getDownloadUrl().addOnSuccessListener(uri -> offlineModel.setSigned_invoice_copy(uri.toString()));

                            id_ref.putFile(Uri.parse(offlineModel.getId_card()))
                                    .addOnSuccessListener(taskSnapshot1 -> id_ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                        offlineModel.setId_card(uri.toString());
                                        offlineModel.setLoan_proposal_id(offlineModel.getLoan_proposal_id());
                                        offlineModel.setCustomer_name(offlineModel.getCustomer_name());
                                        offlineModel.setDistrict(offlineModel.getDistrict());
                                        offlineModel.setModel(offlineModel.getModel());
                                        offlineModel.setMobile_no(offlineModel.getMobile_no());
                                        offlineModel.setAlternative_mobile_no(offlineModel.getAlternative_mobile_no());
                                        offlineModel.setPod_status(true);
                                        offlineModel.setSerial_number(offlineModel.getSerial_number());
                                        offlineModel.setTime(System.currentTimeMillis());
                                        offlineModel.setUid(offlineModel.getUid());
                                        offlineModel.setId_type(offlineModel.getId_type());
                                        offlineModel.setId_proof_number(offlineModel.getId_proof_number());
                                        offlineModel.setManifest_no(offlineModel.getManifest_no());
                                        offlineModel.setUploaded(true);
                                        model.setUploaded(true);

                                        // Getting the ID from firebase database.
                                        String IDFromServer = offlineModel.getLoan_proposal_id();
                                        mDatabase.child(IDFromServer).setValue(offlineModel);
                                        viewHolder.img_not_uploaded.setVisibility(View.INVISIBLE);

                                    }))
                                    .addOnFailureListener(exception -> Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show())
                                    .addOnProgressListener(taskSnapshot12 -> {
                                        //calculating progress percentage
                                        double progress = (100.0 * taskSnapshot12.getBytesTransferred()) / taskSnapshot12.getTotalByteCount();

                                    });
                        })
                        .addOnFailureListener(exception -> Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show())
                        .addOnProgressListener(taskSnapshot -> {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        });
            }else {

                offlineModel.setLoan_proposal_id(offlineModel.getLoan_proposal_id());
                offlineModel.setCustomer_name(offlineModel.getCustomer_name());
                offlineModel.setDistrict(offlineModel.getDistrict());
                offlineModel.setModel(offlineModel.getModel());
                offlineModel.setMobile_no(offlineModel.getMobile_no());
                offlineModel.setAlternative_mobile_no(offlineModel.getAlternative_mobile_no());
                offlineModel.setPod_status(true);
                offlineModel.setSerial_number(offlineModel.getSerial_number());
                offlineModel.setTime(System.currentTimeMillis());
                offlineModel.setUid(offlineModel.getUid());
                offlineModel.setRefusal(true);
                offlineModel.setRefusal_reason(offlineModel.getRefusal_reason());
                offlineModel.setManifest_no(offlineModel.getManifest_no());
                offlineModel.setUploaded(true);
                model.setUploaded(true);

                // Getting the ID from firebase database.
                String IDFromServer = offlineModel.getLoan_proposal_id();
                mDatabase.child(IDFromServer).setValue(offlineModel);
                viewHolder.img_not_uploaded.setVisibility(View.INVISIBLE);

            }
        }


    }

    @Override
    public int getItemCount() {

        if (!NetworkUtils.isConnected()){
            return offlineList == null ? 0 : offlineList.size();
        }else {
            return list == null ? 0 : list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.loan_proposal_id) TextView loan_proposal_id;
        @BindView(R.id.customer_name) TextView customer_name;
        @BindView(R.id.pod_status) TextView pod_status;
        @BindView(R.id.sl_no) TextView sl_no;
        @BindView(R.id.list_serial_no) TextView list_serial_no;
        @BindView(R.id.img_not_uploaded) ImageView img_not_uploaded;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setList(List<OrderDetails.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<OrderDetails.DataBean> list){

        for (OrderDetails.DataBean dataBean: list){

            this.list.add(dataBean);
        }
    }

    public void setOfflineList(List<OrderDetails.DataBean> offlineList) {
        this.offlineList = offlineList;
        notifyDataSetChanged();
    }

    public void addOfflineList(List<OrderDetails.DataBean> offlineList){

        for (OrderDetails.DataBean dataBean: offlineList){

            this.offlineList.add(dataBean);
        }
    }

    private void getOrderDetails(String loan_id, ViewHolder viewHolder, OrderDetails.DataBean model, int pos){

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(Constants.ORDER_DETAILS);

        mDatabase.child(loan_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){

                    if (dataSnapshot.child("refusal").getValue() != null){

                        if (dataSnapshot.child("refusal").getValue().equals(true)){

                            if (model.isReturned()){
                                viewHolder.pod_status.setText("Returned");
                                viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.orange));
                            }else {
                                viewHolder.pod_status.setText("Refusal");
                                viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.yellow));
                            }

                            viewHolder.itemView.setOnClickListener(v -> ActivityManager.RefusalDetails(context, model.getLoan_proposal_id(), model.getCustomer_name(), model.getModel(),
                                    model.getDistrict(), model.getMobile_no(), model.getAlternative_mobile_no(),
                                    model.getSerial_number(), General.getManifestNo(),pos, model.isReturned(), model.getId_type(), model.getId_card(),
                                    model.getSigned_invoice_copy(), model.getId_proof_number()));

                        }else {

                            if (model.isReturned()){
                                viewHolder.pod_status.setText("Returned");
                                viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.orange));
                            }else {
                                viewHolder.pod_status.setText("Completed");
                                viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.green));
                            }

                            viewHolder.itemView.setOnClickListener(v -> ActivityManager.OrderDetails(context, model.getLoan_proposal_id(), model.getCustomer_name(), model.getModel(),
                                    model.getDistrict(), model.getMobile_no(), model.getAlternative_mobile_no(),
                                    model.getSerial_number(), General.getManifestNo(), model.getId_type(), model.getId_card(),
                                    model.getSigned_invoice_copy(), model.getId_proof_number(),pos, false));

                        }

                    } else {

                        if (model.isReturned()){
                            viewHolder.pod_status.setText("Returned");
                            viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.orange));
                        }else {
                            viewHolder.pod_status.setText("Completed");
                            viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.green));
                        }

                        viewHolder.itemView.setOnClickListener(v -> ActivityManager.OrderDetails(context, model.getLoan_proposal_id(), model.getCustomer_name(), model.getModel(),
                                model.getDistrict(), model.getMobile_no(), model.getAlternative_mobile_no(),
                                model.getSerial_number(), General.getManifestNo(), model.getId_type(), model.getId_card(),
                                model.getSigned_invoice_copy(), model.getId_proof_number(),pos, false));

                    }

                }else {

                    if (model.isReturned()){
                        viewHolder.pod_status.setText("Returned");
                        viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.orange));
                        viewHolder.itemView.setOnClickListener(v -> ToastUtils.showLong("Order is already returned"));
                    }else {
                        viewHolder.pod_status.setText("Pending");
                        viewHolder.pod_status.setTextColor(context.getResources().getColor(R.color.red));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
