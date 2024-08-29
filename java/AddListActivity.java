/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myinventory.database.Product;
import com.example.myinventory.database.ProductDataBase;

public class AddListActivity extends AppCompatActivity {

    //declare variables
    private EditText editTextProductName;
    private EditText editTextDescription;
    private EditText editTextQuantity;
    private EditText editTextSellPrice;
    private EditText editTextBuyPrice;
    private Button buttonSave;
    private Button buttonBackToList;
    private ProductDataBase productDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set to the xml file
        setContentView(R.layout.activity_add_list);

        // Initialize UI components from xml
        editTextProductName = findViewById(R.id.editTextProductName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextSellPrice = findViewById(R.id.editTextSellPrice);
        editTextBuyPrice = findViewById(R.id.editTextBuyPrice);
        buttonSave = findViewById(R.id.buttonSave);
        buttonBackToList = findViewById(R.id.buttonBackToList);

        // Initialize database
        productDatabase = new ProductDataBase(this);

        // Set click listener for Save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        buttonBackToList.setOnClickListener(v -> {
            // Navigate to ListActivity.class
            Intent intent = new Intent(AddListActivity.this, ListActivity.class);
            startActivity(intent);
        });
    }

    private void saveData() {
        // Input values after save the data
        String productName = editTextProductName.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();
        String quantityStr = editTextQuantity.getText().toString().trim();
        String sellPriceStr = editTextSellPrice.getText().toString().trim();
        String buyPriceStr = editTextBuyPrice.getText().toString().trim();

        // Validate input
        if (productName.isEmpty() || description.isEmpty() || quantityStr.isEmpty() || sellPriceStr.isEmpty() || buyPriceStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        //Validate the number, we dont want user input like alphabet into the collumn of qty, Sell price and Buy Price
        int quantity;
        double sellPrice, buyPrice;
        try {
            quantity = Integer.parseInt(quantityStr);
            sellPrice = Double.parseDouble(sellPriceStr);
            buyPrice = Double.parseDouble(buyPriceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Product
        Product product = new Product();
        product.setProductName(productName);
        product.setDescription(description);
        product.setQuantity(quantity);
        product.setSellPrice(sellPrice);
        product.setBuyPrice(buyPrice);

        // Save the product and transfer to the database
        productDatabase.addProduct(product);
        Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();

        // Clear the text to none, therefore the user can keep adding stuff without to back and fort to the ADD icon
        editTextProductName.setText("");
        editTextDescription.setText("");
        editTextQuantity.setText("");
        editTextSellPrice.setText("");
        editTextBuyPrice.setText("");
    }
}
