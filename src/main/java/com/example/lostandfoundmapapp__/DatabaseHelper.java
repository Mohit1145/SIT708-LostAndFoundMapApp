package com.example.lostandfoundmapapp__;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LostFound.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "items";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + "("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "type TEXT,"
                + "name TEXT,"
                + "phone TEXT,"
                + "description TEXT,"
                + "date TEXT,"
                + "location TEXT,"
                + "imageUri TEXT,"
                + "latitude REAL,"
                + "longitude REAL"
                + ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(String type, String name, String phone,
                              String description, String date,
                              String location, String imageUri,
                              double latitude, double longitude) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("type", type);
        values.put("name", name);
        values.put("phone", phone);
        values.put("description", description);
        values.put("date", date);
        values.put("location", location);
        values.put("imageUri", imageUri);
        values.put("latitude", latitude);
        values.put("longitude", longitude);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }

    public ArrayList<ItemModel> getAllItems() {

        ArrayList<ItemModel> itemList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {

            do {
                ItemModel item = new ItemModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getDouble(8),
                        cursor.getDouble(9)
                );

                itemList.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return itemList;
    }
}