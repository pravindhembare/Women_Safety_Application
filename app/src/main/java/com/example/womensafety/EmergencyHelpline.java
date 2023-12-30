package com.example.womensafety;

import static android.Manifest.permission.CALL_PHONE;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class EmergencyHelpline extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<EmergencyHelplineItem> helplineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_helpline);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        helplineList = new ArrayList<>();

        // static records to the list
        helplineList.add(new EmergencyHelplineItem("Police", "100"));
        helplineList.add(new EmergencyHelplineItem("Fire", "101"));
        helplineList.add(new EmergencyHelplineItem("Ambulance", "102"));
        helplineList.add(new EmergencyHelplineItem("Air Ambulance", "9540161344"));
        helplineList.add(new EmergencyHelplineItem("Child Helpline", "1098"));
        helplineList.add(new EmergencyHelplineItem("Women Helpline", "1091"));
        helplineList.add(new EmergencyHelplineItem("Nirbhaya Helpline", "112"));
        helplineList.add(new EmergencyHelplineItem("Cyber Helpline", "155620"));
        helplineList.add(new EmergencyHelplineItem("LPG Leak Helpline", "1906"));
        helplineList.add(new EmergencyHelplineItem("Women Domestic  Abuse", "181"));
        helplineList.add(new EmergencyHelplineItem("Missing Child And Women", "1094"));
        helplineList.add(new EmergencyHelplineItem("Senior Citizen Helpline", "14567"));
        helplineList.add(new EmergencyHelplineItem("Railway Accident Emg", "1072"));
        helplineList.add(new EmergencyHelplineItem("Road Accident Emergency", "1073"));
        helplineList.add(new EmergencyHelplineItem("Kisan Call Centre", "18001801551"));

        EmergencyHelplineAdapter adapter = new EmergencyHelplineAdapter(helplineList);
        recyclerView.setAdapter(adapter);
    }
}

class EmergencyHelplineAdapter extends RecyclerView.Adapter<EmergencyHelplineAdapter.HelplineViewHolder> {
    private ArrayList<EmergencyHelplineItem> helplineList;

    EmergencyHelplineAdapter(ArrayList<EmergencyHelplineItem> helplineList) {
        this.helplineList = helplineList;
    }

    @NonNull
    @Override
    public HelplineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new HelplineViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final HelplineViewHolder holder, int position) {
        final EmergencyHelplineItem currentItem = helplineList.get(position);
        holder.nameTextView.setText(currentItem.getName());
        holder.numberTextView.setText(currentItem.getNumber());

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
    }

    @Override
    public int getItemCount() {
        return helplineList.size();
    }

    static class HelplineViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView numberTextView;
        ImageView callIcon;

        HelplineViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
            callIcon = itemView.findViewById(R.id.callIcon);
        }
    }

    private void call(String num, View view) {
        Intent i = new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse("tel:"+ "*" + num));

        if (ContextCompat.checkSelfPermission(view.getContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            view.getContext().startActivity(i);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Request the CALL_PHONE permission
                ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{CALL_PHONE}, 1);
            }
        }
    }

}
