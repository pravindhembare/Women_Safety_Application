package com.example.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity_2nd extends AppCompatActivity {

    private LinearLayout emergency1;
    private LinearLayout emergency2;
    private LinearLayout emergency3;
    private LinearLayout emergency4;
    private LinearLayout emergency5;
    private LinearLayout popupContainer1;
    private LinearLayout popupContainer2;
    private LinearLayout popupContainer3;
    private LinearLayout popupContainer4;
    private LinearLayout popupContainer5;
    private LinearLayout subemergency1_1;
    private LinearLayout subemergency1_2;
    private LinearLayout subemergency2_1;
    private LinearLayout subemergency2_2;
    private LinearLayout subemergency3_1;
    private LinearLayout subemergency3_2;
    private LinearLayout subemergency3_3;
    private LinearLayout subemergency3_4;
    private LinearLayout subemergency4_1;
    private LinearLayout subemergency4_2;
    private LinearLayout subemergency4_3;
    private LinearLayout subemergency4_4;
    private LinearLayout subemergency5_1;
    private LinearLayout subemergency5_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2nd);

        emergency1 = findViewById(R.id.block1);
        emergency2 = findViewById(R.id.block2);
        emergency3 = findViewById(R.id.block3);
        emergency4 = findViewById(R.id.block4);
        emergency5 = findViewById(R.id.block5);
        popupContainer1 = findViewById(R.id.popupContainerBlock1);
        popupContainer2 = findViewById(R.id.popupContainerBlock2);
        popupContainer3 = findViewById(R.id.popupContainerBlock3);
        popupContainer4 = findViewById(R.id.popupContainerBlock4);
        popupContainer5 = findViewById(R.id.popupContainerBlock5);
        subemergency1_1 = findViewById(R.id.createContact);
        subemergency1_2 = findViewById(R.id.useContact);
        subemergency2_1 = findViewById(R.id.addDocument);
        subemergency2_2 = findViewById(R.id.useDocument);
        subemergency3_1 = findViewById(R.id.policeStation);
        subemergency3_2 = findViewById(R.id.hospital);
        subemergency3_3 = findViewById(R.id.petrolPump);
        subemergency3_4 = findViewById(R.id.hotel);
        subemergency4_1 = findViewById(R.id.womenRights);
        subemergency4_2 = findViewById(R.id.freedomRights);
        subemergency4_3 = findViewById(R.id.socialSecurity);
        subemergency4_4 = findViewById(R.id.trafficRights);
        subemergency5_1 = findViewById(R.id.menTips);
        subemergency5_2 = findViewById(R.id.womenTips);

        emergency1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup1();
            }
        });
        emergency2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup2();
            }
        });
        emergency3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup3();
            }
        });
        emergency4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup4();
            }
        });
        emergency5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup5();
            }
        });
        subemergency1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, EmergencyContact.class);
                startActivity(intent);
            }
        });
        subemergency1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, EmergencyContact_2nd.class);
                startActivity(intent);
            }
        });
        subemergency2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, EmergencyDocuments.class);
                startActivity(intent);
            }
        });
        subemergency2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, EmergencyDocument_2nd.class);
                startActivity(intent);
            }
        });
        subemergency3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a Uri with the search query for police stations near the current location
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=police station");

                // Create an Intent to open Google Maps with the search query
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText( getApplicationContext(),"Sorry...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        subemergency3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a Uri with the search query for police stations near the current location
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=hospital");

                // Create an Intent to open Google Maps with the search query
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText( getApplicationContext(),"Sorry...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        subemergency3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a Uri with the search query for police stations near the current location
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=near+Petrol+Pump");

                // Create an Intent to open Google Maps with the search query
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText( getApplicationContext(),"Sorry...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        subemergency3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a Uri with the search query for police stations near the current location
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=near hotel");

                // Create an Intent to open Google Maps with the search query
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    Toast.makeText( getApplicationContext(),"Sorry...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        subemergency4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, WomenRights.class);
                startActivity(intent);
            }
        });
        subemergency4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, FreedomRights.class);
                startActivity(intent);
            }
        });
        subemergency4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, SocialSecurity.class);
                startActivity(intent);
            }
        });
        subemergency4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, TrafficRights.class);
                startActivity(intent);
            }
        });
        subemergency5_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, MenSafetyTips.class);
                startActivity(intent);
            }
        });
        subemergency5_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity_2nd.this, WomenSafetyTips.class);
                startActivity(intent);
            }
        });
    }

    public void showPopup1() {
        // Set the visibility of the popup to VISIBLE
        popupContainer1.setVisibility(View.VISIBLE);
    }
    public void showPopup2() {
        // Set the visibility of the popup to VISIBLE
        popupContainer2.setVisibility(View.VISIBLE);
    }
    public void showPopup3() {
        // Set the visibility of the popup to VISIBLE
        popupContainer3.setVisibility(View.VISIBLE);
    }
    public void showPopup4() {
        // Set the visibility of the popup to VISIBLE
        popupContainer4.setVisibility(View.VISIBLE);
    }
    public void showPopup5() {
        // Set the visibility of the popup to VISIBLE
        popupContainer5.setVisibility(View.VISIBLE);
    }
    public void closePopup(View view) {
        // Set the visibility of the popup to GONE
        popupContainer1.setVisibility(View.GONE);
        popupContainer2.setVisibility(View.GONE);
        popupContainer3.setVisibility(View.GONE);
        popupContainer4.setVisibility(View.GONE);
        popupContainer5.setVisibility(View.GONE);
        emergency1.setVisibility(View.VISIBLE);
        emergency2.setVisibility(View.VISIBLE);
        emergency3.setVisibility(View.VISIBLE);
        emergency4.setVisibility(View.VISIBLE);
        emergency5.setVisibility(View.VISIBLE);
    }
    public void closeMenu() {
        // Set the visibility of the popup to GONE
        emergency1.setVisibility(View.GONE);
        emergency2.setVisibility(View.GONE);
        emergency3.setVisibility(View.GONE);
        emergency4.setVisibility(View.GONE);
        emergency5.setVisibility(View.GONE);
    }
}