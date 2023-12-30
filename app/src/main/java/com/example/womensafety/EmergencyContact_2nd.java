package com.example.womensafety;

import static android.Manifest.permission.CALL_PHONE;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class EmergencyContact_2nd extends AppCompatActivity {
    RecyclerView linearLayout;
    DatabaseHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact2nd);

        linearLayout = findViewById(R.id.list);
        linearLayout.setLayoutManager(new LinearLayoutManager(this));
        myDB = new DatabaseHandler(this);
        loadData();
    }

    private void loadData() {
        Cursor data = myDB.getListContent();
        if (data.getCount() == 0) {
            Toast.makeText(EmergencyContact_2nd.this, "There is no content", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<EmergencyContact_2ndItem> theList = new ArrayList<>();

            while (data.moveToNext()) {
                String name = data.getString(1);
                String number = data.getString(2);
                theList.add(new EmergencyContact_2ndItem(name, number));
            }

            EmergencyContact_2ndAdapter adapter = new EmergencyContact_2ndAdapter(theList);
            linearLayout.setAdapter(adapter);
        }
    }

}

class EmergencyContact_2ndAdapter extends RecyclerView.Adapter<EmergencyContact_2ndAdapter.HelplineViewHolder> {
    private ArrayList<EmergencyContact_2ndItem> helplineList;

    EmergencyContact_2ndAdapter(ArrayList<EmergencyContact_2ndItem> helplineList) {
        this.helplineList = helplineList;
    }


    @NonNull
    @Override
    public HelplineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new HelplineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HelplineViewHolder holder, int position) {
        final EmergencyContact_2ndItem currentItem = helplineList.get(position);
        holder.nameTextView.setText(currentItem.getName());
        holder.numberTextView.setText(currentItem.getNumber());

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = currentItem.getNumber();
                holder.nameTextView.setText(phoneNumber);
            }
        });

        holder.callIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the call icon is clicked, you can get the number from the item
                String phoneNumber = currentItem.getNumber();
                // Handle the action to call the number (e.g., using an Intent)
                Toast.makeText(view.getContext(), "Calling To : " + phoneNumber, Toast.LENGTH_SHORT).show();
                call(phoneNumber, view);
            }
        });

        holder.msgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the phone number from the TextView
                String phoneNumber = currentItem.getNumber();

                // Launch the messaging app with the specified number
                openMessagingApp(phoneNumber, v);
            }
        });

        holder.wspIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the phone number from the TextView
                String phoneNumber = currentItem.getNumber();

                // Open WhatsApp with the specified number
                openWhatsApp(phoneNumber, v);
            }
        });

        holder.locIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the phone number and location link from the current item
                String phoneNumber = currentItem.getNumber();
                String locationLink = MainActivity.currentLocationLink;

                // Create an intent to open the messaging app
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + phoneNumber));
                intent.putExtra("sms_body", "Check out my location: " + locationLink);

                // Check if there's an activity that can handle this intent
                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                } else {
                    // Handle the case where no messaging app is available
                    Toast.makeText(v.getContext(), "No messaging app available", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return helplineList.size();
    }

    static class HelplineViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView numberTextView;
        ImageView callIcon;
        ImageView msgIcon;
        ImageView wspIcon;
        ImageView locIcon;

        HelplineViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            callIcon = itemView.findViewById(R.id.callIcon);
            msgIcon = itemView.findViewById(R.id.msgIcon);
            wspIcon = itemView.findViewById(R.id.wtpIcon);
            locIcon = itemView.findViewById(R.id.locIcon);
        }
    }

    private void call(String num, View view) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+ num));

        if (ContextCompat.checkSelfPermission(view.getContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            view.getContext().startActivity(i);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Request the CALL_PHONE permission
                ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{CALL_PHONE}, 1);
            }
        }
    }

    private void openMessagingApp(String phoneNumber, View itemView) {
        // Create an intent to open the messaging app
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:" + phoneNumber));

        // Check if there's an activity that can handle this intent
        if (intent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
            itemView.getContext().startActivity(intent);
        } else {
            // Handle the case where no messaging app is available
            Toast.makeText(itemView.getContext(), "No messaging app available", Toast.LENGTH_SHORT).show();
        }
    }


    private void openWhatsApp(String phoneNumber, View itemView) {
        // Format the phone number to include the country code
        String formattedNumber = "+91" + phoneNumber; // Change the country code as needed

        // Create an intent to open WhatsApp
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + formattedNumber));

        // Check if there's an activity that can handle this intent
        if (intent.resolveActivity(itemView.getContext().getPackageManager()) != null) {
            itemView.getContext().startActivity(intent);
        } else {
            // Handle the case where WhatsApp is not installed
            Toast.makeText(itemView.getContext(), "WhatsApp not installed", Toast.LENGTH_SHORT).show();
        }
    }
}
