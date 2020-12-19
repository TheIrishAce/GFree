package com.gfree_application.gfree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class CreateResterauntReviewActivity extends AppCompatActivity {

    private Button submitReview;
    private TextInputEditText createReviewTextInput;
    private EditText createReviewRestaurantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_resteraunt_review);

        submitReview = findViewById(R.id.submit_review_button);
        createReviewTextInput = findViewById(R.id.create_review_text_input);
        createReviewRestaurantName = findViewById(R.id.create_review_resteraunt_name);

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createReviewRestaurantName.getText().toString().isEmpty()){
                    Toast.makeText(CreateResterauntReviewActivity.this, "Restaurant name is empty!", Toast.LENGTH_SHORT).show();
                }
                else if(createReviewTextInput.getText() == null || createReviewTextInput.length() < 10){
                    Toast.makeText(CreateResterauntReviewActivity.this, "Review body is empty or isn't larger than 10 characters long.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CreateResterauntReviewActivity.this, "REVIEW SUBMITTED!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(CreateResterauntReviewActivity.this, DashboardActivity.class));
                }
            }
        });
    }
}