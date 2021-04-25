package com.gfree_application.gfree.DashboardPackage.HamburgerMenuPackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gfree_application.gfree.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button register;

    private FirebaseAuth auth;  //Global firebase auth variable.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.RegisterEmailTextView);       //TextView
        password = findViewById(R.id.RegisterPasswordTextView); //Censored TextView
        confirmPassword = findViewById(R.id.RegisterConfirmPasswordTextView); //Censored TextView
        register = findViewById(R.id.ConfirmRegisterButton);  //Button

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();
                String passwordConfirmText = confirmPassword.getText().toString();

                if(TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText)){
                    Toast.makeText(RegisterActivity.this, "Empty user credentials!", Toast.LENGTH_LONG).show();
                }
                else if(passwordText.length() < 6){
                    Toast.makeText(RegisterActivity.this, "Password too short, mimimum 6 characters!", Toast.LENGTH_LONG).show();
                }
                else if(!passwordText.equals(passwordConfirmText) ){
                    Toast.makeText(RegisterActivity.this, "Passwords don't match!", Toast.LENGTH_LONG).show();
                }
                else {
                    registerUser(emailText, passwordText);
                }
            }
        });
    }

    private void registerUser(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Registration Completed!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                }
                else if (!task.isSuccessful()){
                    try
                    {
                        throw task.getException();
                    }
                    // if user enters wrong email.
                    catch (FirebaseAuthWeakPasswordException weakPassword)
                    {
                        Log.d("Registration Check", "onComplete: weak_password");
                        Toast.makeText(RegisterActivity.this, "Weak Password!", Toast.LENGTH_SHORT).show();
                        // TODO: take your actions!
                    }
                    // if user enters wrong password.
                    catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                    {
                        Log.d("Registration Check", "onComplete: malformed_email");
                        Toast.makeText(RegisterActivity.this, "Email is malformed! Double check it.", Toast.LENGTH_SHORT).show();
                        // TODO: Take your action
                    }
                    catch (FirebaseAuthUserCollisionException existEmail)
                    {
                        Log.d("Registration Check", "onComplete: exist_email");
                        Toast.makeText(RegisterActivity.this, "Email is already linked to an account!", Toast.LENGTH_SHORT).show();
                        // TODO: Take your action
                    }
                    catch (Exception e)
                    {
                        Log.d("Registration Check", "onComplete: " + e.getMessage());
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}