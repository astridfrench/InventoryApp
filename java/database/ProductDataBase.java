/*
 * Name: Astrid French
 * Date: 15 August 2024
 * Prof. Jerome DiMarzio
 * CS360 - Project Three
 * */

package com.example.myinventory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDataBase extends SQLiteOpenHelper {

    //Create database with the name of product_database.db
    private static final String DATABASE_NAME = "product_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "product";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "product_name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_SELL_PRICE = "sell_price";
    private static final String COLUMN_BUY_PRICE = "buy_price";

    public ProductDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PRODUCTS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_QUANTITY + " INTEGER, "
                + COLUMN_SELL_PRICE + " REAL, "
                + COLUMN_BUY_PRICE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getProductName());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_SELL_PRICE, product.getSellPrice());
        values.put(COLUMN_BUY_PRICE, product.getBuyPrice());

        db.insert(TABLE_PRODUCTS, null, values);
    }

    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_QUANTITY, COLUMN_SELL_PRICE, COLUMN_BUY_PRICE},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Product product = new Product();
            product.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            product.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            product.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            product.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));
            product.setSellPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_SELL_PRICE)));
            product.setBuyPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_BUY_PRICE)));
            cursor.close();
            return product;
        }
        return null;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getProductName());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_SELL_PRICE, product.getSellPrice());
        values.put(COLUMN_BUY_PRICE, product.getBuyPrice());

        db.update(TABLE_PRODUCTS, values, COLUMN_ID + "=?", new String[]{String.valueOf(product.getId())});
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_QUANTITY, COLUMN_SELL_PRICE, COLUMN_BUY_PRICE},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                product.setProductName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                product.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                product.setQuantity(cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY)));
                product.setSellPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_SELL_PRICE)));
                product.setBuyPrice(cursor.getDouble(cursor.getColumnIndex(COLUMN_BUY_PRICE)));
                productList.add(product);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return productList;
    }
    public List<Product> getProductsWithZeroQuantity() {
        List<Product> zeroQuantityProducts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE quantity = 0", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                double sellPrice = cursor.getDouble(cursor.getColumnIndex("sellPrice"));
                double buyPrice = cursor.getDouble(cursor.getColumnIndex("buyPrice"));

                zeroQuantityProducts.add(new Product(id, name, description, quantity, sellPrice, buyPrice));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return zeroQuantityProducts;
    }

}
