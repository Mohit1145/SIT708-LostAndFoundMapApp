package com.example.lostandfoundmapapp__;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView textViewDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        textViewDetails = findViewById(R.id.textViewDetails);

        String type = getIntent().getStringExtra("type");
        String name = getIntent().getStringExtra("name");
        String phone = getIntent().getStringExtra("phone");
        String description = getIntent().getStringExtra("description");
        String date = getIntent().getStringExtra("date");
        String location = getIntent().getStringExtra("location");
        double latitude = getIntent().getDoubleExtra("latitude", 0.0);
        double longitude = getIntent().getDoubleExtra("longitude", 0.0);

        String details =
                "Type: " + type + "\n\n" +
                        "Item Name: " + name + "\n\n" +
                        "Phone: " + phone + "\n\n" +
                        "Description: " + description + "\n\n" +
                        "Date: " + date + "\n\n" +
                        "Location: " + location + "\n\n" +
                        "Latitude: " + latitude + "\n\n" +
                        "Longitude: " + longitude;

        textViewDetails.setText(details);
    }
}