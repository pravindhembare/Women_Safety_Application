package com.example.womensafety;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class EmergencyDocument_2nd extends AppCompatActivity {

    private ArrayList<String> fileList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_document2nd);

        ListView listView = findViewById(R.id.fileList);

        // Initialize the adapter
        fileList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, fileList);
        listView.setAdapter(adapter);

        // Load previously uploaded files
        loadFilesFromLocalStorage();

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            // Handle item click (show the content of the file)
            String fileName = fileList.get(i);
            Toast.makeText(this, "Current Item: " + fileName, Toast.LENGTH_SHORT).show();
            showFileContent(fileName);
        });
    }

    private void loadFilesFromLocalStorage() {
        // Get the list of files in the internal storage directory
        String[] files = fileList();

        // Clear the current fileList
        fileList.clear();

        // Add the files to the fileList
        for (String file : files) {
            fileList.add(file);
        }

        // Notify the adapter about the data change
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showFileContent(String fileName) {
        // Get the file path in local storage
        File file = new File(getFilesDir(), fileName);

        // Check if the file exists
        if (file.exists()) {
            // Get the content URI using FileProvider
            Uri fileUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

            // Create an intent to open the file
            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            viewIntent.setDataAndType(fileUri, getMimeType(file));
            viewIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant URI permission

            try {
                startActivity(viewIntent);
            } catch (ActivityNotFoundException e) {
                // No viewer application found
                Toast.makeText(this, "No application available to open this file", Toast.LENGTH_SHORT).show();
            }

        } else {
            // File doesn't exist
            Toast.makeText(this, "File not found: " + fileName, Toast.LENGTH_SHORT).show();
        }
    }


    private String getMimeType(File file) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }


}