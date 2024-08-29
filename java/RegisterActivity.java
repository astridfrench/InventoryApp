/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.myinventory.database.DataBaseUser;
import com.example.myinventory.database.User;

public class RegisterActivity extends Activity {

    //declare variables
    private EditText editTextName;
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private DataBaseUser db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set to xml
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        usernameEditText = findViewById(R.id.editTextUsername);
        emailEditText = findViewById(R.id.editTextEmail);
        phoneEditText = findViewById(R.id.editTextPhone);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.buttonRegister);
        db = new DataBaseUser(this);

        //navigate register button
        registerButton.setOnClickListener(v -> {
            //get user input
            String name = editTextName.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String email = emailEditText.getText().toString();
            String phone = phoneEditText.getText().toString();


            if (username.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty()|| password.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(0, name, username, password, email, phone);
            long result = db.insertUser(newUser);

            if (result != -1) {
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish(); // Return to login activity
            } else {
                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
