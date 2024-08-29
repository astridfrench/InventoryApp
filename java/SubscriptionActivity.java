/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubscriptionActivity extends AppCompatActivity {

    private TextView textViewSubscriptionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set to the xml
        setContentView(R.layout.activity_subscription);

        // Initialize UI
        textViewSubscriptionStatus = findViewById(R.id.textViewSubscriptionStatus);
        Button buttonForFree = findViewById(R.id.buttonForFree);
        Button buttonForLifetime = findViewById(R.id.buttonForLifetime);

        // Set click listener for Free Button
        buttonForFree.setOnClickListener(v -> {
            // Navigate to Home Activity
            textViewSubscriptionStatus.setText("Thank you for your subscription");
            Intent intent = new Intent(SubscriptionActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // Set click listener for Lifetime Button
        buttonForLifetime.setOnClickListener(v -> {
            // Navigate to Home Activity
            textViewSubscriptionStatus.setText("Thank you for your subscription");
            Intent intent = new Intent(SubscriptionActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
