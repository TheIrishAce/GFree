package com.gfree_application.gfree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;

public class PacketScannerActivity extends AppCompatActivity {

    private TextView textView;
    private SurfaceView surfaceView;
    private Button openScanButton, captureScanButton;

    private CameraSource cameraSource;
    private TextRecognizer textRecognizer;

    private TextToSpeech textToSpeech;

    private String stringResult = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet_scanner);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        openScanButton = findViewById(R.id.openScanButton);
        openScanButton.setBackgroundResource(R.drawable.circular_button);

//        if (String[]{Manifest.permission.CAMERA}) {
//        }
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //Nothing changed here keeps defaults
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraSource.release();
        textToSpeech.stop();
    }

    private void textRecognizer() {
        textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                .setAutoFocusEnabled(true)
                .setFocusMode("continuous-video")
                .setRequestedPreviewSize(1280, 1080)
                .build();
        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(PacketScannerActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        startActivity(new Intent(PacketScannerActivity.this, DashboardActivity.class));
                        Toast.makeText(PacketScannerActivity.this, "You must grant permission to use this feature!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        cameraSource.start(surfaceView.getHolder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
                textToSpeech.stop();
            }
        });

        textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(@NonNull Detector.Detections<TextBlock> detections) {
                captureScanButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SparseArray<TextBlock> sparseArray = detections.getDetectedItems(); //Get array of items detected
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i=0; i<sparseArray.size();i++){
                            TextBlock textBlock = sparseArray.valueAt(i);
                            if(textBlock != null && textBlock.getValue() != null){
                                stringBuilder.append(textBlock.getValue() + " ");   //Convert what was detected into a StringBuilder.
                            }
                        }

                        final String stringText = stringBuilder.toString();
                        Handler  handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                stringResult = stringText;
                                resultObtained();
                            }
                        });
                    }
                });
            }
        });
    }

    private void resultObtained(){
        setContentView(R.layout.activity_packet_scanner);
        textView = findViewById(R.id.scanDataTextView);
        textView.setText(stringResult);
        textToSpeech.speak(stringResult, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void buttonStart(View view){
        setContentView(R.layout.camera_screen_surfaceview);
        captureScanButton = findViewById(R.id.captureScanButton);
        captureScanButton.setBackgroundResource(R.drawable.circular_button);

        textRecognizer();
    }
}