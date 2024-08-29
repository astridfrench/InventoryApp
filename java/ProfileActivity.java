/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */
package com.example.myinventory;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myinventory.database.DataBaseUser;
import com.example.myinventory.database.User;

public class ProfileActivity extends AppCompatActivity {
    // Declare variables
    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private EditText editTextPassword;
    private EditText editTextName;
    private Button buttonSaveProfile;
    private Button buttonDeleteProfile;
    private DataBaseUser db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout from XML
        setContentView(R.layout.activity_profile);

        editTextUsername = findViewById(R.id.edit_username);
        editTextEmail = findViewById(R.id.edit_email);
        editTextPhone = findViewById(R.id.edit_phone);
        editTextName = findViewById(R.id.edit_name);
        editTextPassword = findViewById(R.id.edit_password);
        buttonSaveProfile = findViewById(R.id.save_button);
        buttonDeleteProfile = findViewById(R.id.delete_button);

        // Connect to database
        db = new DataBaseUser(this);

        // Get the userId connected to database
        userId = getIntent().getIntExtra("id", -1);
        User user = db.getUserById(userId);
        // If user is not null
        if (user != null) {
            editTextName.setText(user.getName());
            editTextUsername.setText(user.getUsername());
            editTextEmail.setText(user.getEmail());
            editTextPhone.setText(user.getPhone());
            editTextPassword.setText(user.getPassword());
        }

        buttonSaveProfile.setOnClickListener(v -> {
            // Get updated user details
            String name = editTextName.getText().toString();
            String username = editTextUsername.getText().toString();
            String email = editTextEmail.getText().toString();
            String phone = editTextPhone.getText().toString();
            String password = editTextPassword.getText().toString();

            db.updateUser(new User(userId, username, name, email, phone, password));

            Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        });

        buttonDeleteProfile.setOnClickListener(v -> {
            if (userId != -1) {
                db.deleteUser(userId);
                Toast.makeText(ProfileActivity.this, "Profile deleted", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ProfileActivity.this, "Error: User ID is invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


