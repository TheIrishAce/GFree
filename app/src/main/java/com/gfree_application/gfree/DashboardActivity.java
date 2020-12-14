package com.gfree_application.gfree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private Toolbar toolbar;

    private Button barcodeScanner;
    private TextView scannedData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED); //Ask user for premission to use camera.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        scannedData = findViewById(R.id.resultsTextView);
    }

    public void BarcodeScanner (View view){
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBarcodeImageEnabled(false);
        intentIntegrator.initiateScan();
    }

     @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null){
            if(intentResult.getContents() == null){
                scannedData.setText("Incomplete Scan!");
                Toast.makeText(this, "Incomplete Scan!", Toast.LENGTH_SHORT).show();
            }
            else {
                scannedData.setText(intentResult.getContents());
                Toast.makeText(this, intentResult.getContents().toString(), Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
     }


    /*
       onNavigationItemSelected used to give functionality to items inside the hamburger menu.
       Add case for each item in the list.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged out!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                break;
            case R.id.contact_me:
                startActivity(new Intent(DashboardActivity.this, ContactActivity.class));
                break;
            case R.id.credits:
                startActivity(new Intent(DashboardActivity.this, CreditActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }
}

