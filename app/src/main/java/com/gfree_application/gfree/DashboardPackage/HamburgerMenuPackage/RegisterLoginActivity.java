package com.gfree_application.gfree.DashboardPackage.HamburgerMenuPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gfree_application.gfree.R;

public class RegisterLoginActivity extends AppCompatActivity {

    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_login);
        register = findViewById(R.id.RegisterButton);
        login = findViewById(R.id.LoginButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterLoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterLoginActivity.this, LoginActivity.class));
            }
        });
    }
}