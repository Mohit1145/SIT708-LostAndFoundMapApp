package com.example.lostandfoundmapapp__;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class CreateAdvertActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioLost, radioFound;

    EditText editName, editPhone, editDescription, editDate, editLocation;
    Button btnLocation, btnSave, btnUpload;
    ImageView imageItem;

    DatabaseHelper databaseHelper;
    FusedLocationProviderClient fusedLocationClient;

    double latitude = -37.8136;
    double longitude = 144.9631;

    String imageUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        radioGroup = findViewById(R.id.radioGroup);
        radioLost = findViewById(R.id.radioLost);
        radioFound = findViewById(R.id.radioFound);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editDescription = findViewById(R.id.editDescription);
        editDate = findViewById(R.id.editDate);
        editLocation = findViewById(R.id.editLocation);

        btnLocation = findViewById(R.id.btnLocation);
        btnSave = findViewById(R.id.btnSave);
        btnUpload = findViewById(R.id.btnUpload);

        imageItem = findViewById(R.id.imageItem);

        databaseHelper = new DatabaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnUpload.setOnClickListener(v -> selectImage());
        btnLocation.setOnClickListener(v -> getCurrentLocation());
        btnSave.setOnClickListener(v -> saveAdvert());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 100);
    }

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100
                && resultCode == RESULT_OK
                && data != null) {

            Uri uri = data.getData();

            if (uri != null) {
                imageUri = uri.toString();
                imageItem.setImageURI(uri);

                Toast.makeText(
                        this,
                        "Image Selected",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);

            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location != null) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        editLocation.setText(latitude + ", " + longitude);

                    } else {

                        latitude = -37.8136;
                        longitude = 144.9631;

                        editLocation.setText("Melbourne VIC, Australia");
                    }
                });
    }

    private void convertAddressToLatLng() {

        String addressText =
                editLocation.getText().toString();

        if (addressText.isEmpty()) {
            return;
        }

        if (addressText.contains(",")) {
            try {
                String[] parts = addressText.split(",");
                latitude = Double.parseDouble(parts[0].trim());
                longitude = Double.parseDouble(parts[1].trim());
                return;
            } catch (Exception ignored) {
            }
        }

        try {
            Geocoder geocoder = new Geocoder(this);

            List<Address> addresses =
                    geocoder.getFromLocationName(addressText, 1);

            if (addresses != null && !addresses.isEmpty()) {

                latitude = addresses.get(0).getLatitude();
                longitude = addresses.get(0).getLongitude();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAdvert() {

        convertAddressToLatLng();

        String type =
                radioLost.isChecked()
                        ? "Lost"
                        : "Found";

        boolean inserted =
                databaseHelper.insertItem(
                        type,
                        editName.getText().toString(),
                        editPhone.getText().toString(),
                        editDescription.getText().toString(),
                        editDate.getText().toString(),
                        editLocation.getText().toString(),
                        imageUri,
                        latitude,
                        longitude
                );

        if (inserted) {

            Toast.makeText(
                    this,
                    "Advert Saved Successfully",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Error Saving Advert",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}