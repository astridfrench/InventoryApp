/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */
package com.example.myinventory;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.myinventory.database.Product;
import com.example.myinventory.database.ProductDataBase;

import java.util.List;

public class SmsPermissionActivity extends AppCompatActivity {

    private static final int REQUEST_SMS_PERMISSION = 1;
    private static final String CHANNEL_ID = "SMS_NOTIFICATION_CHANNEL";
    private TextView textViewPermissionStatus;
    private ProductDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set from xml
        setContentView(R.layout.activity_sms_permission);

        textViewPermissionStatus = findViewById(R.id.textViewPermissionStatus);
        Button buttonAllow = findViewById(R.id.buttonAllow);
        Button buttonDeny = findViewById(R.id.buttonDeny);

        // Initialize database
        db = new ProductDataBase(this);
        createNotificationChannel();

        // Set for Allow Button
        buttonAllow.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_SMS_PERMISSION);
            } else {
                textViewPermissionStatus.setText("SMS Permission Granted");
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
                checkZeroQuantityProducts();
            }
        });

        // Set for Deny Button
        buttonDeny.setOnClickListener(v -> {
            textViewPermissionStatus.setText("SMS Permission Denied");
            Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                textViewPermissionStatus.setText("SMS Permission Granted");
                Toast.makeText(this, "SMS Permission Granted", Toast.LENGTH_SHORT).show();
                checkZeroQuantityProducts();
            } else {
                textViewPermissionStatus.setText("SMS Permission Denied");
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "SMS Notification Channel";
            String description = "Channel for SMS permission notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(String productName) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications) // Ensure this drawable exists
                .setContentTitle("Product Alert")
                .setContentText("A product with zero quantity was found: " + productName)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());
    }

    private void checkZeroQuantityProducts() {
        List<Product> zeroQuantityProducts = db.getProductsWithZeroQuantity();
        Log.d("SmsPermissionActivity", "Number of zero quantity products: " + zeroQuantityProducts.size()); // Debug line
        for (Product product : zeroQuantityProducts) {
            Log.d("SmsPermissionActivity", "Product with zero quantity: " + product.getProductName()); // Debug line
            sendNotification(product.getProductName());
        }
    }
}

