package com.gfree_application.gfree.PacketScannerPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.gfree_application.gfree.DashboardPackage.UserDashboardActivity;
import com.gfree_application.gfree.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

public class PacketScannerActivity extends AppCompatActivity {

    private TextView dangerousIngredientTextView, warningIngredientTextView, safeIngredientTextView;
    private TableRow dangerousIngredientRow, warningIngredientRow, safeIngredientRow;
    //private ImageView dangerousIngredientRowIcon, warningIngredientRowIcon, safeIngredientRowIcon;
    private SurfaceView surfaceView;
    private Button openScanButton, captureScanButton;
    private CameraSource cameraSource;
    private TextRecognizer textRecognizer;
    private TextToSpeech textToSpeech;
    private String stringScannedImageResult = null;
    private ArrayList<String> scannedImageDangerousIngredients, scannedImageWarningIngredients, scannedImageSafeIngredients, scannedImageAllIngredients;
    private ImageTextConverter imgConverter = new ImageTextConverter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet_scanner);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);
        openScanButton = findViewById(R.id.openScanButton);
        openScanButton.setBackgroundResource(R.drawable.circular_button);

        dangerousIngredientRow = findViewById(R.id.dangerousIngredientRow);
        warningIngredientRow = findViewById(R.id.warningIngredientRow);
        safeIngredientRow = findViewById(R.id.safeIngredientRow);

        dangerousIngredientRow.clearAnimation();
        warningIngredientRow.clearAnimation();
        safeIngredientRow.clearAnimation();

        scannedImageDangerousIngredients = new ArrayList<String>();
        scannedImageWarningIngredients = new ArrayList<String>();
        scannedImageSafeIngredients = new ArrayList<String>();

//        dangerousIngredientRow.setVisibility(View.GONE);
//        warningIngredientRow.setVisibility(View.GONE);
//        safeIngredientRow.setVisibility(View.GONE);

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
        try {
            cameraSource.release();
            textToSpeech.stop();
        } catch (Exception e){
            //Toast.makeText(this, "Error" + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void textRecognizer() {
        scannedImageDangerousIngredients = new ArrayList<String>();
        scannedImageWarningIngredients = new ArrayList<String>();
        scannedImageSafeIngredients = new ArrayList<String>();
        scannedImageAllIngredients = new ArrayList<String>();

        clearIngridentTextFields();
        scannedImageDangerousIngredients.clear();
        scannedImageWarningIngredients.clear();
        scannedImageSafeIngredients.clear();
        scannedImageAllIngredients.clear();


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
                        startActivity(new Intent(PacketScannerActivity.this, UserDashboardActivity.class));
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

                        for (int i=0; i<sparseArray.size(); i++){
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
                                stringScannedImageResult = stringText;
                                StringTokenizer st = new StringTokenizer(stringScannedImageResult.toLowerCase(), ",");

                                String returnedIngredient = null;
                                while (st.hasMoreElements()){
                                    String CurrentWordToCheck = st.nextToken();
                                    try {
                                        returnedIngredient = imgConverter.checkDangerousIngredients(CurrentWordToCheck, st.countTokens());
                                        if ((!returnedIngredient.equalsIgnoreCase("")) || (!returnedIngredient.equalsIgnoreCase(null))){
                                            scannedImageDangerousIngredients.add(returnedIngredient);
                                        }
                                        returnedIngredient = imgConverter.checkWarningIngredients(CurrentWordToCheck, st.countTokens());
                                        if ((!returnedIngredient.equalsIgnoreCase("")) || (!returnedIngredient.equalsIgnoreCase(null))){
                                            scannedImageWarningIngredients.add(returnedIngredient);
                                        }
                                        returnedIngredient = imgConverter.checkSafeIngredients(CurrentWordToCheck, st.countTokens());
                                        if ((!returnedIngredient.equalsIgnoreCase(" ")) || (!returnedIngredient.equalsIgnoreCase(null))){
                                            scannedImageSafeIngredients.add(returnedIngredient);
                                        }
                                    } catch (Exception e){
                                        System.out.println(e);
                                    }

//                                    try {
//                                        returnedIngredient = imgConverter.checkWarningIngredients(CurrentWordToCheck, st.countTokens());
//                                        if ((!returnedIngredient.equalsIgnoreCase("")) || (!returnedIngredient.equalsIgnoreCase(null))){
//                                            scannedImageWarningIngredients.add(returnedIngredient);
//                                        }
//                                    } catch (Exception e){
//                                        System.out.println(e);
//                                    }
//
//                                    try {
//                                        returnedIngredient = imgConverter.checkSafeIngredients(CurrentWordToCheck, st.countTokens());
//                                        if ((!returnedIngredient.equalsIgnoreCase(" ")) || (!returnedIngredient.equalsIgnoreCase(null))){
//                                            scannedImageSafeIngredients.add(returnedIngredient);
//                                        }
//                                    } catch (Exception e){
//                                        System.out.println(e);
//                                    }
                                }
                                resultObtained();
                            }
                        });
                    }
                });
            }
        });
    }

    public void clearIngridentTextFields(){
        //Used to remove the placeholder message before the found ingredients are appended.
        dangerousIngredientTextView.setText("");
        warningIngredientTextView.setText("");
        safeIngredientTextView.setText("");
    }

    private void resultObtained(){
        setContentView(R.layout.activity_packet_scanner);
        dangerousIngredientTextView = findViewById(R.id.scanDataDangerousIngredientTextView);
        warningIngredientTextView = findViewById(R.id.scanDataWarningIngredientTextView);
        safeIngredientTextView = findViewById(R.id.scanDataSafeIngredientTextView);

        clearIngridentTextFields();

        //Set the colour for each textview red for dangerous, yellow for warnings, and green for safe.
        dangerousIngredientTextView.setTextColor(Color.parseColor("#a83232"));
        warningIngredientTextView.setTextColor(Color.parseColor("#a37931"));
        safeIngredientTextView.setTextColor(Color.parseColor("#46a331"));

        System.out.println("Dangerous Ingr: " + scannedImageDangerousIngredients.toString());
        System.out.println("Warning Ingr: " + scannedImageWarningIngredients.toString());
        System.out.println("Safe Ingr: " + scannedImageSafeIngredients.toString());

        //Removes any empty elements before the user see them.
        //Prehaps prevent the creation of empty elements instead of creating and then removing later?
        scannedImageDangerousIngredients.removeIf(String::isEmpty);
        scannedImageWarningIngredients.removeIf(String::isEmpty);
        scannedImageSafeIngredients.removeIf(String::isEmpty);

        // --- Debug for viewing list
        System.out.println("Dangerous Ingr: " + scannedImageDangerousIngredients.toString());
        System.out.println("Warning Ingr: " + scannedImageWarningIngredients.toString());
        System.out.println("Safe Ingr: " + scannedImageSafeIngredients.toString());


        Log.d("Dangerous Ingredient ArrayList:", scannedImageDangerousIngredients.toString());
        Log.d("Warning Ingredient ArrayList:", scannedImageWarningIngredients.toString());
        Log.d("Safe Ingredient ArrayList:", scannedImageSafeIngredients.toString());

        Log.d("Is Dangerous Ingredient ArrayList Empty:", String.valueOf(scannedImageDangerousIngredients.isEmpty()));
        Log.d("Is Warning Ingredient ArrayList Empty", String.valueOf(scannedImageWarningIngredients.isEmpty()));
        Log.d("Is Safe Ingredient ArrayList Empty", String.valueOf(scannedImageSafeIngredients.isEmpty()));

        ArrayList<String> ingredientsToDelete = new ArrayList<String>();
        boolean foundGlutenFree = false;

        //if gluten free safe is found then remove any negative gluten.
        for (int i=0; i<scannedImageSafeIngredients.size(); i++){
            if (scannedImageSafeIngredients.get(i).toLowerCase().contains("gluten free")) {
                foundGlutenFree = true;
            }
        }

        if (foundGlutenFree) {
            for (int i=0; i<scannedImageDangerousIngredients.size(); i++){
                if (scannedImageDangerousIngredients.get(i).toLowerCase().contains("gluten")){
                    ingredientsToDelete.add(scannedImageDangerousIngredients.get(i));
                }
            }

            scannedImageDangerousIngredients.removeAll(ingredientsToDelete);
        }



        for (int i =0; i < scannedImageDangerousIngredients.size(); i++){
            scannedImageDangerousIngredients = removeDuplicate(scannedImageDangerousIngredients);
            dangerousIngredientTextView.append(scannedImageDangerousIngredients.get(i)+ "\n");
        }
        for (int i =0; i < scannedImageWarningIngredients.size(); i++){
            scannedImageWarningIngredients = removeDuplicate(scannedImageWarningIngredients);
            warningIngredientTextView.append(scannedImageWarningIngredients.get(i) + "\n");
        }
        for (int i =0; i < scannedImageSafeIngredients.size(); i++){
            scannedImageSafeIngredients = removeDuplicate(scannedImageSafeIngredients);
            safeIngredientTextView.append(scannedImageSafeIngredients.get(i)+ "\n");
        }


        //Add different ingredient lists to one main joint list to be used in text to speech.
        scannedImageAllIngredients = new ArrayList<String>();
        scannedImageAllIngredients.addAll(scannedImageDangerousIngredients);
        scannedImageAllIngredients.addAll(scannedImageWarningIngredients);
        scannedImageAllIngredients.addAll(scannedImageSafeIngredients);
        scannedImageAllIngredients = removeDuplicate(scannedImageAllIngredients);

        //if at least 1 ingredient is added to the list. Begin text to speech.
        if (!scannedImageAllIngredients.isEmpty()){
            textToSpeech.speak(scannedImageAllIngredients.toString(), TextToSpeech.QUEUE_FLUSH, null, null);
//            scannedImageAllIngredients.clear();
//            scannedImageDangerousIngredients.clear();
//            scannedImageWarningIngredients.clear();
//            scannedImageSafeIngredients.clear();
        }
    }

    public ArrayList removeDuplicate(ArrayList incArrayList){
        //Filters out duplicates. Workaround for contains instead of matches. Potential refactoring!
        Set<String> set = new LinkedHashSet<>(incArrayList);
        incArrayList.clear();
        incArrayList.addAll(set);
        return incArrayList;
    }

    @Override
    public void onPause() {
        super.onPause();
        dangerousIngredientTextView = findViewById(R.id.scanDataDangerousIngredientTextView);
        warningIngredientTextView = findViewById(R.id.scanDataWarningIngredientTextView);
        safeIngredientTextView = findViewById(R.id.scanDataSafeIngredientTextView);


    }

    public void buttonStart(View view){
        setContentView(R.layout.camera_screen_surfaceview);
        captureScanButton = findViewById(R.id.captureScanButton);
        captureScanButton.setBackgroundResource(R.drawable.circular_button);

        textRecognizer();
    }
}