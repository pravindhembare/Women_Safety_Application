package com.example.womensafety;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class TrafficRights  extends AppCompatActivity {

    private LinearLayout block1;
    private LinearLayout block2;
    private LinearLayout block3;
    private LinearLayout block4;
    private LinearLayout block5;
    private LinearLayout block6;
    private LinearLayout block7;
    private LinearLayout block8;
    private LinearLayout block9;
    private LinearLayout block10;

    private LinearLayout popupContainer1;
    private LinearLayout popupContainer2;
    private LinearLayout popupContainer3;
    private LinearLayout popupContainer4;
    private LinearLayout popupContainer5;
    private LinearLayout popupContainer6;
    private LinearLayout popupContainer7;
    private LinearLayout popupContainer8;
    private LinearLayout popupContainer9;
    private LinearLayout popupContainer10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_rights);

        block1 = findViewById(R.id.block1);
        block2 = findViewById(R.id.block2);
        block3 = findViewById(R.id.block3);
        block4 = findViewById(R.id.block4);
        block5 = findViewById(R.id.block5);
        block6 = findViewById(R.id.block6);
        block7 = findViewById(R.id.block7);
        block8 = findViewById(R.id.block8);
        block9 = findViewById(R.id.block9);
        block10 = findViewById(R.id.block10);
        popupContainer1 = findViewById(R.id.popupContainerBlock1);
        popupContainer2 = findViewById(R.id.popupContainerBlock2);
        popupContainer3 = findViewById(R.id.popupContainerBlock3);
        popupContainer4 = findViewById(R.id.popupContainerBlock4);
        popupContainer5 = findViewById(R.id.popupContainerBlock5);
        popupContainer6 = findViewById(R.id.popupContainerBlock6);
        popupContainer7 = findViewById(R.id.popupContainerBlock7);
        popupContainer8 = findViewById(R.id.popupContainerBlock8);
        popupContainer9 = findViewById(R.id.popupContainerBlock9);
        popupContainer10 = findViewById(R.id.popupContainerBlock10);

        block1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup1();
            }
        });
        block2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup2();
            }
        });
        block3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup3();
            }
        });
        block4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup4();
            }
        });
        block5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup5();
            }
        });
        block6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup6();
            }
        });
        block7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup7();
            }
        });
        block8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup8();
            }
        });
        block9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup9();
            }
        });
        block10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the popup when the button is clicked
                closeMenu();
                showPopup10();
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
    public void showPopup6() {
        // Set the visibility of the popup to VISIBLE
        popupContainer6.setVisibility(View.VISIBLE);
    }
    public void showPopup7() {
        // Set the visibility of the popup to VISIBLE
        popupContainer7.setVisibility(View.VISIBLE);
    }
    public void showPopup8() {
        // Set the visibility of the popup to VISIBLE
        popupContainer8.setVisibility(View.VISIBLE);
    }
    public void showPopup9() {
        // Set the visibility of the popup to VISIBLE
        popupContainer9.setVisibility(View.VISIBLE);
    }
    public void showPopup10() {
        // Set the visibility of the popup to VISIBLE
        popupContainer10.setVisibility(View.VISIBLE);
    }
    public void closeMenu() {
        // Set the visibility of the popup to GONE
        block1.setVisibility(View.GONE);
        block2.setVisibility(View.GONE);
        block3.setVisibility(View.GONE);
        block4.setVisibility(View.GONE);
        block5.setVisibility(View.GONE);
        block6.setVisibility(View.GONE);
        block7.setVisibility(View.GONE);
        block8.setVisibility(View.GONE);
        block9.setVisibility(View.GONE);
        block10.setVisibility(View.GONE);
    }

    public void closePopup(View view) {
        // Set the visibility of the popup to GONE
        popupContainer1.setVisibility(View.GONE);
        popupContainer2.setVisibility(View.GONE);
        popupContainer3.setVisibility(View.GONE);
        popupContainer4.setVisibility(View.GONE);
        popupContainer5.setVisibility(View.GONE);
        popupContainer6.setVisibility(View.GONE);
        popupContainer7.setVisibility(View.GONE);
        popupContainer8.setVisibility(View.GONE);
        popupContainer9.setVisibility(View.GONE);
        popupContainer10.setVisibility(View.GONE);
        block1.setVisibility(View.VISIBLE);
        block2.setVisibility(View.VISIBLE);
        block3.setVisibility(View.VISIBLE);
        block4.setVisibility(View.VISIBLE);
        block5.setVisibility(View.VISIBLE);
        block6.setVisibility(View.VISIBLE);
        block7.setVisibility(View.VISIBLE);
        block8.setVisibility(View.VISIBLE);
        block9.setVisibility(View.VISIBLE);
        block10.setVisibility(View.VISIBLE);
    }
}