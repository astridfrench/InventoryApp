/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        ImageButton buttonListIcon = findViewById(R.id.buttonListIcon);
        ImageButton buttonProfileIcon = findViewById(R.id.buttonProfileIcon);
        ImageButton buttonSMSIcon = findViewById(R.id.buttonSMSIcon);
        ImageButton buttonHelpIcon = findViewById(R.id.buttonHelpIcon);

        // Set up click listeners for the buttons
        buttonListIcon.setOnClickListener(v -> {
            // Navigate to List Activity
            Intent intent = new Intent(HomeActivity.this, ListActivity.class);
            startActivity(intent);
        });

        buttonProfileIcon.setOnClickListener(v -> {
            // Navigate to Profile Activity
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        buttonSMSIcon.setOnClickListener(v -> {
            // Navigate to SmsPermissionActivity
            Intent intent = new Intent(HomeActivity.this, SmsPermissionActivity.class);
            startActivity(intent);
        });

        buttonHelpIcon.setOnClickListener(v -> {
            // Navigate to Help Activity
            Intent intent = new Intent(HomeActivity.this, HelpActivity.class);
            startActivity(intent);
        });



    }
}
