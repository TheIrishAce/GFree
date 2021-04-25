package com.gfree_application.gfree.DashboardPackage.HamburgerMenuPackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.gfree_application.gfree.DashboardPackage.UserDashboardActivity;
import com.gfree_application.gfree.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

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
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, UserDashboardActivity.class));
                    finish();
                } else {
                    //if login fails
                    try
                    {
                        throw task.getException();
                    }
                    // if user enters wrong email.
                    catch (FirebaseAuthInvalidUserException invalidEmail)
                    {
                        Log.d("Login Check", "onComplete: invalid_email");
                        Toast.makeText(LoginActivity.this, "Email is incorrect", Toast.LENGTH_SHORT).show();
                        // TODO: take your actions!
                    }
                    // if user enters wrong password.
                    catch (FirebaseAuthInvalidCredentialsException wrongPassword)
                    {
                        Log.d("Login Check", "onComplete: wrong_password");
                        Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        // TODO: Take your action
                    }
                    catch (Exception e)
                    {
                        Log.d("Login Check", "onComplete: " + e.getMessage());
                    }
                }
            }
        });
    }
}