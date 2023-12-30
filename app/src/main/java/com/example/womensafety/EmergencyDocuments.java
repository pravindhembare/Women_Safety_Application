package com.example.womensafety;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class EmergencyDocuments extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 1;
    private ArrayList<String> fileList = new ArrayList<>();
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_documents);

        ImageButton btnUpload = findViewById(R.id.btnUpload);

        // Declare listView here to make it accessible throughout the class
        ListView listView = findViewById(R.id.fileList);

        // Initialize the adapter
        adapter = new CustomAdapter(this, fileList);
        listView.setAdapter(adapter);

        // Load previously uploaded files
        loadFilesFromLocalStorage();

        btnUpload.setOnClickListener(view -> {
            // Open file picker
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, PICK_FILE_REQUEST);
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Handle item click (show the content of the file)
                String fileName = fileList.get(i);
                Toast.makeText(EmergencyDocuments.this, "Current Item: " + fileName, Toast.LENGTH_SHORT).show();
                returnFilePath(fileName);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected file's URI
            Uri uri = data.getData();
            String fileName = getFileName(uri);

            // Save the file to local storage
            saveFileToLocalStorage(uri, fileName);

            // Add the file name to the list
            fileList.add(fileName);
            adapter.notifyDataSetChanged();
        }
    }

    private String getFileName(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            cursor.moveToFirst();
            String fileName = cursor.getString(nameIndex);
            cursor.close();
            return fileName;
        }
        return null;
    }

    private void saveFileToLocalStorage(Uri uri, String fileName) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void returnFilePath(String fileName) {
        // Get the file path in local storage
        File file = new File(getFilesDir(), fileName);

        // Check if the file exists
        if (file.exists()) {
            // Return the full file path
            Toast.makeText(this, "File Path: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } else {
            // File doesn't exist
            Toast.makeText(this, "File not found: " + fileName, Toast.LENGTH_SHORT).show();

        }
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
        adapter.notifyDataSetChanged();
    }
}
