package com.stockone.pod.view.pages;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.mindorks.paracamera.Camera;
import com.stockone.pod.R;
import com.stockone.pod.model.UserDetails;
import com.stockone.pod.network.OnStringResponse;
import com.stockone.pod.network.UserApi;
import com.stockone.pod.utils.ActivityManager;
import com.stockone.pod.utils.Constants;
import com.stockone.pod.utils.General;
import com.stockone.pod.view.adapter.OrderListAdapter;
import com.wang.avi.AVLoadingIndicatorView;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetails extends AppCompatActivity {

    @BindView(R.id.toolbar_order_details) Toolbar toolbar;
    @BindView(R.id.img_order_details) ImageView img_order_details;
    @BindView(R.id.img_id_card) ImageView img_id_card;
    @BindView(R.id.img_ackldgmnt_slip) ImageView img_ackldgmnt_slip;
    @BindView(R.id.loan_id) EditText loan;
    @BindView(R.id.order_customer) EditText order_customer;
    @BindView(R.id.model_desc) EditText model;
    @BindView(R.id.order_district) EditText order_district;
    @BindView(R.id.mob_number) EditText mob_number;
    @BindView(R.id.serial_no) EditText serial;
    @BindView(R.id.img_ackldgmnt_click) ImageView img_ackldgmnt_click;
    @BindView(R.id.img_id_click) ImageView img_id_click;
    @BindView(R.id.btn_order_submit) Button btn_order_submit;
    @BindView(R.id.avi_order) AVLoadingIndicatorView avi;
    @BindView(R.id.btn_order_submitted) Button btn_order_submitted;
    @BindView(R.id.spinner1) BetterSpinner spinner1;
    @BindView(R.id.rl_proof) RelativeLayout rl_proof;
    @BindView(R.id.txt_id_proof_no) TextView txt_id_proof_no;
    @BindView(R.id.et_id_proof_no) EditText et_id_proof_no;
    @BindView(R.id.id_type_text) TextView id_type_text;
    @BindView(R.id.alter_number) EditText alter_number;
    @BindView(R.id.scan_qr) CardView scan_qr;
    @BindDrawable(R.drawable.bg) Drawable bg;
    Camera camera;
    Bitmap bmp_id_card, bmp_acknowldgmnt_slip;

    String loan_id, customer_name, model_desc, district, serial_no,
    manifest_number, id_type, id_card, signed_invoice_copy, id_proof_number = "";
    int position;
    String mob_no, alt_mob_no;
    Boolean from_refusal;

    private FirebaseAuth mAuth;

    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private com.stockone.pod.model.OrderDetails.DataBean order_model = new com.stockone.pod.model.OrderDetails.DataBean();


    @OnClick(R.id.scan_qr)
    void onQrScannedClicked(){

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            Intent intent = new Intent(OrderDetails.this, ScanBarcode.class);
                            startActivityForResult(intent, 2);

                        }else {
                        }
                        if(report.isAnyPermissionPermanentlyDenied()){
                            ActivityManager.PERMISSION_TAB(OrderDetails.this);
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        avi.hide();

        if (!NetworkUtils.isConnected()){
            avi.hide();
        }

        Glide.with(this).load(bg).into(img_order_details);

        mAuth = FirebaseAuth.getInstance();

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

        id_type = bundle.getString("id_type");
        id_card = bundle.getString("id_card");
        signed_invoice_copy = bundle.getString("signed_invoice_copy");
        id_proof_number = bundle.getString("id_proof_number");
        position = bundle.getInt("position");
        from_refusal = bundle.getBoolean("from_refusal");

        if (!from_refusal){

            if (!NetworkUtils.isConnected()){

                if (!id_type.equals(" ")){

                    btn_order_submit.setVisibility(View.GONE);
                    btn_order_submitted.setVisibility(View.VISIBLE);
                    img_id_click.setVisibility(View.GONE);
                    img_ackldgmnt_click.setVisibility(View.GONE);
                    et_id_proof_no.setFocusableInTouchMode(false);
                    id_type_text.setVisibility(View.VISIBLE);
                    spinner1.setVisibility(View.GONE);
                    Glide
                            .with(OrderDetails.this)
                            .load(id_card)
                            .into(img_id_card);
                    Glide
                            .with(OrderDetails.this)
                            .load(signed_invoice_copy)
                            .into(img_ackldgmnt_slip);
                    et_id_proof_no.setText(id_proof_number);
                    id_type_text.setText(id_type);

                }else {

                    btn_order_submit.setVisibility(View.VISIBLE);
                    btn_order_submitted.setVisibility(View.GONE);
                    img_id_click.setVisibility(View.VISIBLE);
                    img_ackldgmnt_click.setVisibility(View.VISIBLE);
                    et_id_proof_no.setFocusableInTouchMode(true);
                    id_type_text.setVisibility(View.GONE);
                    spinner1.setVisibility(View.VISIBLE);
                    avi.hide();
                }
            }
        }

        loan.setText(loan_id);
        order_customer.setText(customer_name);
        model.setText(model_desc);
        order_district.setText(district);
        serial.setText(serial_no);
//        String ph_no = new DecimalFormat("#").format(mob_no);
//        String alt_no = new DecimalFormat("#").format(alt_mob_no);
        mob_number.setText(mob_no);
        alter_number.setText(alt_mob_no);

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.ORDER_DETAILS);

        String[] list = getResources().getStringArray(R.array.proof);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        spinner1.setAdapter(adapter);

        spinner1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (spinner1.getText().toString().equals("Aadhar Card")){
                    scan_qr.setVisibility(View.VISIBLE);
                }else {
                    scan_qr.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (NetworkUtils.isConnected()){

            getOrderDetails();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            bmp_acknowldgmnt_slip = camera.getCameraBitmap();
            if(bmp_acknowldgmnt_slip != null) {
                getImageUri(bmp_acknowldgmnt_slip);
                img_ackldgmnt_slip.setImageBitmap(bmp_acknowldgmnt_slip);
            }else{
                Toast.makeText(this.getApplicationContext(),"Picture not taken!",Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == 0) {
            bmp_id_card = camera.getCameraBitmap();
            if (bmp_id_card != null) {
                img_id_card.setImageBitmap(bmp_id_card);
            } else {
                Toast.makeText(this.getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == 2){

            String result=data.getStringExtra("barcode");

            char first = result.charAt(0);
            LogUtils.e("FIRST ->"+ first);
            String html = "<";

            if (!html.equals(String.valueOf(first))){
                et_id_proof_no.setText(result);
                return;
            }

            String sStringToParse;

            sStringToParse = new String(result);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setValidating(false);
            DocumentBuilder db = null;
            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            Document doc = null;
            try {
                doc = db.parse(new ByteArrayInputStream(sStringToParse.getBytes("utf-8")));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            NodeList nlRecords = doc.getElementsByTagName("PrintLetterBarcodeData");

            int num = nlRecords.getLength();

            for (int i = 0; i < num; i++) {
                Element node = (Element) nlRecords.item(i);

                System.out.println("List attributes for node: " + node.getNodeName());

                // get a map containing the attributes of this node
                NamedNodeMap attributes = node.getAttributes();

                // get the number of nodes in this map
                int numAttrs = attributes.getLength();

                for (int j = 0; j < numAttrs; j++) {
                    Attr attr = (Attr) attributes.item(j);

                    String attrName = attr.getNodeName();
                    String attrValue = attr.getNodeValue();

                    if (attrName.equals("uid")){
                        et_id_proof_no.setText(attrValue);
                        break;
                    }

                    // Do your stuff here
                    System.out.println("Found attribute: " + attrName + " with value: " + attrValue);

                }
//                Attr attr = (Attr) attributes.item(0);
//                String attrName = attr.getNodeName();
//
//                String attrValue = attr.getNodeValue();
//
//                LogUtils.e(attrValue);
//
//                et_id_proof_no.setText(attrValue);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestPermission(final int requestcode){

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(report.areAllPermissionsGranted()){
                            initializeCamera(requestcode);
                            try {
                                camera.takePicture();
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }else {
                        }
                        if(report.isAnyPermissionPermanentlyDenied()){
                            ActivityManager.PERMISSION_TAB(OrderDetails.this);
                        }

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    @OnClick(R.id.img_ackldgmnt_click)
    void onAcknowledgmentCliked(){

        requestPermission(1);

    }

    @OnClick(R.id.img_id_click)
    void onIDClicked(){

        requestPermission(0);
    }

    private void initializeCamera(int requestcode){

        // Build the camera
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(requestcode)
                .setDirectory("POD")
                .setName("pod_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);

    }

    @OnClick(R.id.btn_order_submit)
    void onOrderSubmission(){

        String id_type = spinner1.getText().toString().trim();
        String proof_no = et_id_proof_no.getText().toString().trim();

        if (TextUtils.isEmpty(id_type)) {
            ToastUtils.showLong("Please select ID Type");
            return;
        }

        if (TextUtils.isEmpty(proof_no)) {
            ToastUtils.showLong("Please enter ID Proof Number");
            return;
        }

        if (bmp_id_card == null && bmp_acknowldgmnt_slip == null){

            ToastUtils.showLong("Please upload ID card and Signed Invoice Copy");
            return;
        }

        btn_order_submit.setText("Please wait...");

        uploadFiles();
        btn_order_submit.setVisibility(View.GONE);
        btn_order_submitted.setVisibility(View.VISIBLE);
        img_id_click.setVisibility(View.GONE);
        img_ackldgmnt_click.setVisibility(View.GONE);
        et_id_proof_no.setFocusableInTouchMode(false);
    }

    private void uploadFiles(){

        if (!NetworkUtils.isConnected()){
            order_model.setSigned_invoice_copy(getImageUri(bmp_acknowldgmnt_slip).toString());
            order_model.setId_card(getImageUri(bmp_id_card).toString());
            order_model.setLoan_proposal_id(loan.getText().toString());
            order_model.setCustomer_name(order_customer.getText().toString());
            order_model.setDistrict(order_district.getText().toString());
            order_model.setModel(model.getText().toString());
            order_model.setMobile_no(mob_no);
            order_model.setAlternative_mobile_no(alt_mob_no);
            order_model.setPod_status(true);
            order_model.setSerial_number(serial.getText().toString());
            order_model.setTime(System.currentTimeMillis());
            order_model.setUid(mAuth.getCurrentUser().getUid());
            order_model.setId_type(spinner1.getText().toString());
            order_model.setId_proof_number(et_id_proof_no.getText().toString());
            order_model.setManifest_no(manifest_number);
            order_model.setUploaded(false);
            OrderListAdapter.offlineList.set(position, order_model);
            OrderListAdapter.list.set(position, order_model);
            General.setManifest(manifest_number, OrderListAdapter.offlineList);

        } else {

            if (bmp_acknowldgmnt_slip != null) {

                final StorageReference acknowledgment_ref = storageReference.child("acknowledgmnt_images/"+loan.getText().toString()+"_2");
                final StorageReference id_ref = storageReference.child("id_images/"+loan.getText().toString()+"_1");
                acknowledgment_ref.putFile(getImageUri(bmp_acknowldgmnt_slip))
                        .addOnSuccessListener(taskSnapshot -> {

                            acknowledgment_ref.getDownloadUrl().addOnSuccessListener(uri -> order_model.setSigned_invoice_copy(uri.toString()));

                            id_ref.putFile(getImageUri(bmp_id_card))
                                    .addOnSuccessListener(taskSnapshot1 -> id_ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                        order_model.setId_card(uri.toString());
                                        order_model.setLoan_proposal_id(loan.getText().toString());
                                        order_model.setCustomer_name(order_customer.getText().toString());
                                        order_model.setDistrict(order_district.getText().toString());
                                        order_model.setModel(model.getText().toString());
                                        order_model.setMobile_no(mob_no);
                                        order_model.setAlternative_mobile_no(alt_mob_no);
                                        order_model.setPod_status(true);
                                        order_model.setSerial_number(serial.getText().toString());
                                        order_model.setTime(System.currentTimeMillis());
                                        order_model.setUid(mAuth.getCurrentUser().getUid());
                                        order_model.setId_type(spinner1.getText().toString());
                                        order_model.setId_proof_number(et_id_proof_no.getText().toString());
                                        order_model.setManifest_no(manifest_number);
                                        order_model.setUploaded(true);
                                        order_model.setRefusal(false);
                                        OrderListAdapter.offlineList.set(position, order_model);
                                        General.setManifest(manifest_number, OrderListAdapter.offlineList);

                                        // Getting the ID from firebase database.
                                        String IDFromServer = loan.getText().toString();
                                        mDatabase.child(IDFromServer).setValue(order_model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                getOrderConfirmation();
                                            }
                                        });

                                    }))
                                    .addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show())
                                    .addOnProgressListener(taskSnapshot12 -> {
                                        //calculating progress percentage
                                        double progress = (100.0 * taskSnapshot12.getBytesTransferred()) / taskSnapshot12.getTotalByteCount();

                                    });
                        })
                        .addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show())
                        .addOnProgressListener(taskSnapshot -> {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        });
            }

        }

    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void getOrderDetails(){

        avi.show();

        mDatabase.child(loan_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null){

                    if (from_refusal){

                        btn_order_submit.setVisibility(View.VISIBLE);
                        btn_order_submitted.setVisibility(View.GONE);
                        img_id_click.setVisibility(View.VISIBLE);
                        img_ackldgmnt_click.setVisibility(View.VISIBLE);
                        et_id_proof_no.setFocusableInTouchMode(true);
                        id_type_text.setVisibility(View.GONE);
                        spinner1.setVisibility(View.VISIBLE);
                    }else {

                        btn_order_submit.setVisibility(View.GONE);
                        btn_order_submitted.setVisibility(View.VISIBLE);
                        img_id_click.setVisibility(View.GONE);
                        img_ackldgmnt_click.setVisibility(View.GONE);
                        et_id_proof_no.setFocusableInTouchMode(false);
                        id_type_text.setVisibility(View.VISIBLE);
                        spinner1.setVisibility(View.GONE);
                        Glide
                                .with(OrderDetails.this)
                                .load(dataSnapshot.child("id_card").getValue())
                                .into(img_id_card);
                        Glide
                                .with(OrderDetails.this)
                                .load(dataSnapshot.child("signed_invoice_copy").getValue())
                                .into(img_ackldgmnt_slip);
                        et_id_proof_no.setText(dataSnapshot.child("id_proof_number").getValue()+"");
                        id_type_text.setText(dataSnapshot.child("id_type").getValue()+"");

                    }
                    avi.hide();

                }else {

                    btn_order_submit.setVisibility(View.VISIBLE);
                    btn_order_submitted.setVisibility(View.GONE);
                    img_id_click.setVisibility(View.VISIBLE);
                    img_ackldgmnt_click.setVisibility(View.VISIBLE);
                    et_id_proof_no.setFocusableInTouchMode(true);
                    id_type_text.setVisibility(View.GONE);
                    spinner1.setVisibility(View.VISIBLE);
                    avi.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                avi.hide();
            }
        });
    }

    private void getOrderConfirmation(){

        UserApi.getConfirmOrder(loan.getText().toString(), new OnStringResponse() {
            @Override
            public void onSuc(String data) {

                LogUtils.e("Success");

                updateRecordsDB("success");

            }

            @Override
            public void onFail(int code, String msg) {

                updateRecordsDB("failed");
            }

            @Override
            public void onErr(String cause) {

               updateRecordsDB("failed");
                LogUtils.e(cause);
            }
        });
    }

    private void updateRecordsDB(String status){

        Map<String, Object> map = new HashMap<>();
        map.put("confirmation_time", System.currentTimeMillis());
        map.put("status", status);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Constants.ORDER_CONFIRMATION);
        String IDFromServer = loan.getText().toString();
        databaseReference.child(IDFromServer).setValue(map);
    }
}
