/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set from activity_help xml, its just short announcement
        setContentView(R.layout.activity_help);
    }
}
