/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */
package com.example.myinventory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myinventory.database.Product;
import com.example.myinventory.database.ProductDataBase;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    //Declare variables
    private ListView bodyListView;
    private ImageButton addItemButton;
    private ImageButton homeButton;
    private ImageButton deleteButton;
    private TextView textViewUserName;
    private ProductDataBase db;
    private List<Product> products;
    private CustomAdapter adapter;
    private Product selectedProduct; // To store the product to be deleted

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Set from xml
        setContentView(R.layout.activity_list);

        // Initialize the variables from xml
        bodyListView = findViewById(R.id.bodyListView);
        addItemButton = findViewById(R.id.addItemButton);
        homeButton = findViewById(R.id.homeButton);
        deleteButton = findViewById(R.id.delete);
        textViewUserName = findViewById(R.id.textViewUserName);

        // connect to database
        db = new ProductDataBase(this);
        products = db.getAllProducts();

        adapter = new CustomAdapter(this, products);
        bodyListView.setAdapter(adapter);

        // Set up item click listener for the AddBodyList
        bodyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedProduct = products.get(position); // Store the selected product
            }
        });

        // Set up button click listeners
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AddListActivity
                Intent intent = new Intent(ListActivity.this, AddListActivity.class);
                startActivity(intent);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Home Activity
                Intent intent = new Intent(ListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProduct != null) {
                    // To delete the list
                    new AlertDialog.Builder(ListActivity.this)
                            .setTitle("Delete Item")
                            .setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Delete the product from the database
                                    db.deleteProduct(selectedProduct.getId());

                                    // Remove the product from the list/UI screen
                                    products.remove(selectedProduct);
                                    adapter.notifyDataSetChanged();
                                    selectedProduct = null;
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .show();
                } else {
                }
            }
        });

        // Set username
        textViewUserName.setText("Hi, Admin...");
    }

    //Adapted from AddBodyList to ListActivity
    private class CustomAdapter extends ArrayAdapter<Product> {

        public CustomAdapter(Context context, List<Product> products) {
            super(context, R.layout.activity_body_list_item, products);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = AddBodyList.createListItemView(getContext(), getItem(position), new AddBodyList.OnItemClickListener() {
                    @Override
                    public void onItemClick(Product product) {
                        selectedProduct = product;
                    }
                });
            }
            return convertView;
        }
    }
}
