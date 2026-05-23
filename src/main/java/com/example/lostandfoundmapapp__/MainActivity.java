package com.example.lostandfoundmapapp__;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnCreate;
    Button btnShowAll;
    Button btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreate = findViewById(R.id.btnCreate);
        btnShowAll = findViewById(R.id.btnShowAll);
        btnMap = findViewById(R.id.btnMap);

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateAdvertActivity.class);
            startActivity(intent);
        });

        btnShowAll.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ItemListActivity.class);
            startActivity(intent);
        });

        btnMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        });
    }
}