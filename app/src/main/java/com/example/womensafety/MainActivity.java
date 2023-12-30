package com.example.womensafety;

import static android.Manifest.permission.CALL_PHONE;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private TextView locationTextView;
    private final int locationPermissionCode = 1;
    private ImageButton emergencyButton;
    private ImageButton mainAcitity2Button;
    private FloatingActionButton reloadButton;
    private FloatingActionButton  informationButton;
    private LinearLayout popupContainer;
    long lastClickTime;
    boolean waitingForSecondClick;
    public static String currentLocationLink;
    String liveLocationLink;
    DatabaseHandler myDB;


    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popupContainer = findViewById(R.id.popupContainer);
        informationButton = findViewById(R.id.fab);
        reloadButton = findViewById(R.id.fab2);
        locationTextView = findViewById(R.id.textView2);
        emergencyButton = findViewById(R.id.emergencyButton);
        mainAcitity2Button = findViewById(R.id.MainActivity2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // Initialize the fusedLocationClient
        myDB = new DatabaseHandler(this);

        // Check if GPS and mobile data are enabled.
        if (!isGPSEnabled()) {
            // If GPS is not enabled, open the location settings.
            openLocationSettings();
        }

        if (!isMobileDataEnabled()) {
            // If mobile data is not enabled, open the mobile data settings.
            openMobileDataSettings();
        }

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermission()) {
                    getLocation();
                } else {
                    requestPermission();
                }
            }
        });

        if (checkPermission()) {
            getLocation();
        } else {
            requestPermission();
        }

        informationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                showPopup();
            }
        });

        mainAcitity2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity_2nd.class);
                startActivity(intent);
            }
        });

        final long DOUBLE_CLICK_TIME_DELTA = 300; // Adjust this value for your double click timing

        // Variables to track the timing of clicks
        lastClickTime = 0;
        waitingForSecondClick = false;

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long currentClickTime = System.currentTimeMillis();

                // If we're waiting for a second click, it's a double click
                if (waitingForSecondClick) {
                    waitingForSecondClick = false;
                    // Double click action
                    Intent intent = new Intent(MainActivity.this, EmergencyHelpline.class);
                    startActivity(intent);
                } else {
                    // It's not a double click; wait for a potential second click
                    waitingForSecondClick = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (waitingForSecondClick) {
                                waitingForSecondClick = false;
                                // Single click action
                                Intent intent = new Intent(MainActivity.this, EmergencyContact_2nd.class);
                                startActivity(intent);
                            }
                        }
                    }, DOUBLE_CLICK_TIME_DELTA);
                }

                lastClickTime = currentClickTime;
            }
        });

        emergencyButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getApplicationContext(), currentLocationLink, Toast.LENGTH_SHORT).show();
                call("*100");
                String msg= "I NEED HELP! My Location is : "+ currentLocationLink + " Please assist.";
                sendSms(msg,true);
                return true;
            }
        });
    }


    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isMobileDataEnabled() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE && activeNetwork.isConnected();
    }

    private void openLocationSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);

        // Check if GPS and mobile data are enabled.
        if (!isGPSEnabled()) {
            //Still If GPS is not enabled, open the location settings.
            Toast.makeText(MainActivity.this, "Please Turn On Mobile Data and GPS.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openMobileDataSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
            startActivity(intent);
        } else {
            Intent intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
            startActivity(intent);
        }
        if (!isMobileDataEnabled()) {
            //Still If mobile data is not enabled, open the mobile data settings.
            Toast.makeText(MainActivity.this, "Please Turn On Mobile Data and GPS.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, locationPermissionCode);
    }

    private void getLocation() {
        if (checkPermission()) {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(), new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        // Create a Google Maps link using the latitude and longitude
                        currentLocationLink = "https://maps.google.com/maps?f=q&q=(" + latitude + "," + longitude + ")";
                        liveLocationLink = currentLocationLink + "&mode=driving";
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        try {
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            if (!addresses.isEmpty()) {
                                Address address = addresses.get(0);
                                String fullAddress = address.getAddressLine(0);
                                locationTextView.setText("Latitude: " + latitude + "  Longitude: " + longitude + "\n" + fullAddress + "\n By Pravin Dhembare  2023");
                            } else {
                                locationTextView.setText("No address found for this location.");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            locationTextView.setText("Error retrieving location.");
                        }
                    } else {
                        locationTextView.setText("Location not available.");
                    }
                }
            }, null);
        } else {
            requestPermission();
        }
    }


    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000); // Update location every 10 seconds
        locationRequest.setFastestInterval(5000); // The fastest update interval, in milliseconds
        return locationRequest;
    }

    public void showPopup() {
        // Set the visibility of the popup to VISIBLE
        popupContainer.setVisibility(View.VISIBLE);
    }

    public void closePopup(View view) {
        // Set the visibility of the popup to GONE
        popupContainer.setVisibility(View.GONE);
    }

    private void call(String num) {
        Intent i = new Intent( Intent.ACTION_CALL);
        i.setData(Uri.parse( "tel:"+num));
        if(ContextCompat.checkSelfPermission( getApplicationContext(),CALL_PHONE )== PackageManager.PERMISSION_GRANTED){
            startActivity( i );
        }
        else {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(new String[]{CALL_PHONE},1);
            }
        }

    }

    private static final int SMS_PERMISSION_REQUEST_CODE = 1;

    private void sendSms(String msg, boolean b) {
        // Check if SMS permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // If not granted, request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_REQUEST_CODE);
        } else {
            // SMS permission is granted, proceed with sending SMS
            Cursor cursor = myDB.getListContent();
            if (cursor.getCount() == 0) {
                Toast.makeText(MainActivity.this, "Emergency Contact Not Found.", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    String phoneNumber = cursor.getString(2); // Column index of PHONE_NUMBER
                    if (phoneNumber != "+91") {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("+91" + phoneNumber, null, msg, null, null);
                        Toast.makeText(MainActivity.this, "SMS send Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now send the SMS
                // You can call sendSms() here or add a button click event to trigger it
            } else {
                // Permission denied
                Toast.makeText(this, "SMS permission is required to send emergency messages.", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Call super
    }




}
