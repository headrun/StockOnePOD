package com.stockone.pod.view.pages;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.airbnb.lottie.LottieAnimationView;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.stockone.pod.R;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanBarcode extends AppCompatActivity {

    @BindView(R.id.toolbar_scan)
    Toolbar toolbar;
    @BindView(R.id.camera_preview)
    SurfaceView cameraPreview;
    @BindView(R.id.lottie_barcode)
    LottieAnimationView lottie;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lottie.setAnimation("barcode_scanner.json");
        lottie.loop(true);
        lottie.playAnimation();

        init();

    }

    public void init(){

        barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS | Barcode.EAN_13 | Barcode.EAN_8 | Barcode.UPC_A | Barcode.UPC_E | Barcode.CODE_39 | Barcode.CODE_93 |
                Barcode.CODE_128 | Barcode.ITF | Barcode.CODABAR | Barcode.ISBN | Barcode.QR_CODE | Barcode.DATA_MATRIX | Barcode.PDF417 | Barcode.AZTEC ).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
//                .setRequestedFps(30.0f) //30.0f
                .setAutoFocusEnabled(true)
                .build();

        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                try {
                    if (ActivityCompat.checkSelfPermission(ScanBarcode.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0){
                    if (count == 0) {
                        LogUtils.e(barcodes.valueAt(0).rawValue);

                        String barcode_id = barcodes.valueAt(0).rawValue;
                            count++;
                            Intent intent = new Intent();
                            intent.putExtra("barcode", barcode_id);
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                    }
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
