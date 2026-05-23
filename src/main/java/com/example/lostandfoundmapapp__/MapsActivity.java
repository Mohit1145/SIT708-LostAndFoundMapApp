package com.example.lostandfoundmapapp__;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private GoogleMap mMap;

    private EditText editSearchLocation;

    private Button btnSearchLocation;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        editSearchLocation =
                findViewById(R.id.editSearchLocation);

        btnSearchLocation =
                findViewById(R.id.btnSearchLocation);

        fusedLocationClient =
                LocationServices
                        .getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment)
                        getSupportFragmentManager()
                                .findFragmentById(R.id.map);

        if (mapFragment != null) {

            mapFragment.getMapAsync(this);
        }

        btnSearchLocation.setOnClickListener(v -> {

            String query =
                    editSearchLocation
                            .getText()
                            .toString()
                            .trim();

            // WOLLERT
            if (query.equalsIgnoreCase("Wollert")) {

                LatLng wollert =
                        new LatLng(-37.5967, 145.0330);

                showLocation(wollert, "Wollert");

                return;
            }

            // DEAKIN
            if (query.equalsIgnoreCase("Deakin")) {

                LatLng deakin =
                        new LatLng(-37.8476, 145.1149);

                showLocation(deakin, "Deakin University");

                return;
            }

            // MELBOURNE
            if (query.equalsIgnoreCase("Melbourne")) {

                LatLng melbourne =
                        new LatLng(-37.8136, 144.9631);

                showLocation(melbourne, "Melbourne");

                return;
            }

            Toast.makeText(
                    this,
                    "Location not found",
                    Toast.LENGTH_SHORT
            ).show();

        });
    }

    @Override
    public void onMapReady(
            @NonNull GoogleMap googleMap) {

        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    1
            );

            return;
        }

        mMap.setMyLocationEnabled(true);

        // DEFAULT MELBOURNE
        LatLng melbourne =
                new LatLng(-37.8136, 144.9631);

        showLocation(melbourne, "Melbourne");
    }

    private void showLocation(
            LatLng location,
            String title) {

        mMap.clear();

        mMap.addMarker(
                new MarkerOptions()
                        .position(location)
                        .title(title)
        );

        mMap.moveCamera(
                CameraUpdateFactory
                        .newLatLngZoom(location, 13)
        );

        mMap.addCircle(
                new CircleOptions()
                        .center(location)
                        .radius(5000)
                        .strokeWidth(4)
        );
    }
}