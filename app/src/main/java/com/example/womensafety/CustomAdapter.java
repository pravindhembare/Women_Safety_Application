package com.example.womensafety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> fileList;

    public CustomAdapter(Context context, ArrayList<String> fileList) {
        super(context, R.layout.list_item2, R.id.textFileName, fileList);
        this.context = context;
        this.fileList = fileList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item2, parent, false);

        TextView textFileName = rowView.findViewById(R.id.textFileName);
        ImageButton btnDelete = rowView.findViewById(R.id.btnDelete);
        ImageButton btnInfo = rowView.findViewById(R.id.btnInfo);

        textFileName.setText(fileList.get(position));

        btnDelete.setOnClickListener(view -> {
            // Handle delete button click
            String deletedFileName = fileList.get(position);
            deleteFileFromLocalStorage(deletedFileName);
            fileList.remove(position);
            notifyDataSetChanged();
        });

        btnInfo.setOnClickListener(view -> {
            String fileName = fileList.get(position);
            showFileInfo(fileName);
        });


        return rowView;
    }

    private void deleteFileFromLocalStorage(String fileName) {
        // Get the file path in local storage

        File fileToDelete = new File(getContext().getFilesDir(), fileName);

        // Check if the file exists
        if (fileToDelete.exists()) {
            // Attempt to delete the file
            if (fileToDelete.delete()) {
                // File deleted successfully
                Toast.makeText(context, "File deleted: " + fileName, Toast.LENGTH_SHORT).show();
            } else {
                // Failed to delete the file
                Toast.makeText(context, "Failed to delete file: " + fileName, Toast.LENGTH_SHORT).show();
            }
        } else {
            // File doesn't exist
            Toast.makeText(context, "File not found: " + fileName, Toast.LENGTH_SHORT).show();
        }
    }

    private void showFileInfo(String fileName) {
        File file = new File(context.getFilesDir(), fileName);

        if (file.exists()) {
            Toast.makeText(context, "File Path: " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "File not found: " + fileName, Toast.LENGTH_SHORT).show();
        }
    }
}
