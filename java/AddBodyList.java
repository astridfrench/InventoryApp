/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.myinventory.database.Product;

public class AddBodyList {

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public static View createListItemView(@NonNull Context context, @Nullable Product product, final OnItemClickListener listener) {
        View listItemView = LayoutInflater.from(context).inflate(R.layout.activity_body_list_item, null);

        //Declare variable from xml
        TextView idTextView = listItemView.findViewById(R.id.item_id);
        TextView nameTextView = listItemView.findViewById(R.id.item_name);
        TextView priceTextView = listItemView.findViewById(R.id.item_price);
        TextView quantityTextView = listItemView.findViewById(R.id.item_qty);
        ImageButton editButton = listItemView.findViewById(R.id.edit_button);

        // Set values
        if (product != null) {
            idTextView.setText(String.valueOf(product.getId()));
            nameTextView.setText(product.getProductName());
            priceTextView.setText(String.format("$%.2f", product.getSellPrice()));
            quantityTextView.setText(String.valueOf(product.getQuantity()));

            listItemView.setOnClickListener(v -> listener.onItemClick(product));

            // Set click listener for the edit button
            editButton.setOnClickListener(v -> {
                //Set to navigate to EditInventoryActivity.class
                Intent intent = new Intent(context, EditInventoryActivity.class);
                intent.putExtra("PRODUCT_ID", product.getId());
                context.startActivity(intent);
            });
        }

        return listItemView;
    }
}


