/*
* Name: Astrid French
* Date: 15 August 2024
* Prof. Jerome DiMarzio
* CS360 - Project Three
* */

package com.example.myinventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myinventory.database.Product;
import com.example.myinventory.database.ProductDataBase;

public class EditInventoryActivity extends AppCompatActivity {

    //Declare variables
    private EditText idEditText;
    private EditText nameEditText;
    private EditText sellPriceEditText;
    private EditText buyPriceEditText;
    private EditText quantityEditText;
    private Button saveButton;
    private Button deleteButton;
    private ProductDataBase db;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set it from the xml layout
        setContentView(R.layout.activity_edit_inventory);

        idEditText = findViewById(R.id.edit_id);
        nameEditText = findViewById(R.id.edit_name);
        sellPriceEditText = findViewById(R.id.edit_sell_price);
        buyPriceEditText = findViewById(R.id.edit_buy_price);
        quantityEditText = findViewById(R.id.edit_quantity);
        saveButton = findViewById(R.id.save_button);
        deleteButton = findViewById(R.id.delete_button);

        //connect to the database
        db = new ProductDataBase(this);

        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);

        if (productId != -1) {
            // Get product details from database
            product = db.getProductById(productId);

            if (product != null) {
                idEditText.setText(String.valueOf(product.getId()));
                nameEditText.setText(product.getProductName());
                sellPriceEditText.setText(String.format("%.2f", product.getSellPrice()));
                buyPriceEditText.setText(String.format("%.2f", product.getBuyPrice()));
                quantityEditText.setText(String.valueOf(product.getQuantity()));

                //Save button
                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Updating product details
                        product.setProductName(nameEditText.getText().toString());
                        product.setSellPrice(Double.parseDouble(sellPriceEditText.getText().toString()));
                        product.setBuyPrice(Double.parseDouble(buyPriceEditText.getText().toString()));
                        product.setQuantity(Integer.parseInt(quantityEditText.getText().toString()));

                        // Save updated product details to Screen and database
                        db.updateProduct(product);

                        // Return to the list
                        Intent intent = new Intent(EditInventoryActivity.this, ListActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                // Delete Button
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Confirm deletion
                        new AlertDialog.Builder(EditInventoryActivity.this)
                                .setTitle("Delete Item")
                                .setMessage("Are you sure you want to delete this item?")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Delete product from database
                                        db.deleteProduct(product.getId());

                                        //return to the activity list
                                        Intent intent = new Intent(EditInventoryActivity.this, ListActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null)
                                .show();
                    }
                });
            }
        }
    }
}
