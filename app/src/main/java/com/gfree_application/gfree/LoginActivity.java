package com.gfree_application.gfree;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;

    private FirebaseAuth auth;  //Global firebase auth variable.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.loginEmailTextView);
        password = findViewById(R.id.loginPasswordTextView);
        login = findViewById(R.id.confirmLoginButton);

        auth = FirebaseAuth.getInstance();  // Instanciate auth variable with this current instance.

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)){
                    Toast.makeText(LoginActivity.this, "Empty user credentials!", Toast.LENGTH_SHORT).show();
                }
                else {
                    loginUser(emailText, passwordText);
                }
            }
        });
    }

    private void loginUser(String email, String pass) {
        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
                finish();
            }
        });
    }
}