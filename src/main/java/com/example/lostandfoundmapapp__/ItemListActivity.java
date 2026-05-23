package com.example.lostandfoundmapapp__;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    LinearLayout itemContainer;
    DatabaseHelper databaseHelper;
    ArrayList<ItemModel> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        itemContainer = findViewById(R.id.itemContainer);
        databaseHelper = new DatabaseHelper(this);

        loadItems();
    }

    private void loadItems() {

        itemList = databaseHelper.getAllItems();

        itemContainer.removeAllViews();

        if (itemList.isEmpty()) {
            TextView emptyText = new TextView(this);
            emptyText.setText("No lost or found items saved yet.");
            emptyText.setTextSize(18);
            emptyText.setPadding(20, 20, 20, 20);
            itemContainer.addView(emptyText);
            return;
        }

        for (ItemModel item : itemList) {

            TextView textView = new TextView(this);

            String shortDetails =
                    item.getType() + " Item\n"
                            + "Name: " + item.getName() + "\n"
                            + "Location: " + item.getLocation() + "\n"
                            + "Tap to view details";

            textView.setText(shortDetails);
            textView.setTextSize(16);
            textView.setPadding(20, 20, 20, 20);
            textView.setBackgroundColor(0xFFFFFFFF);

            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            params.setMargins(0, 0, 0, 20);
            textView.setLayoutParams(params);

            textView.setOnClickListener(v -> {

                Intent intent =
                        new Intent(
                                ItemListActivity.this,
                                ItemDetailsActivity.class
                        );

                intent.putExtra("type", item.getType());
                intent.putExtra("name", item.getName());
                intent.putExtra("phone", item.getPhone());
                intent.putExtra("description", item.getDescription());
                intent.putExtra("date", item.getDate());
                intent.putExtra("location", item.getLocation());
                intent.putExtra("latitude", item.getLatitude());
                intent.putExtra("longitude", item.getLongitude());

                startActivity(intent);
            });

            itemContainer.addView(textView);
        }
    }
}