package com.example.womensafety;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmergencyContact extends AppCompatActivity {
    Button addBtn, deleteBtn, updateBtn;
    static EditText nameEditText, phoneEditText;
    RecyclerView linearLayout;
    DatabaseHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        addBtn = findViewById(R.id.add);
        deleteBtn = findViewById(R.id.delete);
        updateBtn = findViewById(R.id.update);
        nameEditText = findViewById(R.id.name);
        phoneEditText = findViewById(R.id.phone);
        linearLayout = findViewById(R.id.list);
        linearLayout.setLayoutManager(new LinearLayoutManager(this));
        myDB = new DatabaseHandler(this);


        loadData();
        {
        // Set InputFilter to restrict to alphanumeric characters and a maximum length of 20
        InputFilter[] nameFilters = new InputFilter[2];
        nameFilters[0] = new InputFilter.LengthFilter(20); // Set the maximum length to 20
        nameFilters[1] = (source, start, end, dest, dstart, dend) -> {
            for (int i = start; i < end; i++) {
                if (!Character.isLetterOrDigit(source.charAt(i)) && !Character.isWhitespace(source.charAt(i))) {
                    return "";
                }
            }
            return null;
        };

        nameEditText.setFilters(nameFilters);

        nameEditText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS); // Set input type to text

        // Add a TextWatcher to listen for changes in the EditText
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Check if the entered text starts with a space
                if (charSequence.length() > 0 && charSequence.charAt(0) == ' ') {
                    // Remove the leading space
                    Editable editable = nameEditText.getText();
                    editable.delete(0, 1);
                    nameEditText.setError("Name never start with space");
                    // You can also show an error message or handle it in your desired way
                    // For example: nameEditText.setError("First character cannot be a space");
                }
            }


            @Override
            public void afterTextChanged(Editable editable) {
                // Check if the entered name has a maximum length of 20 characters
                if (editable.length() >= 20) {
                    // Name exceeds the maximum length, show an error message
                    nameEditText.setError("Maximum 20 characters allowed");
                }
            }
        });
    }
        {
            // Set InputFilter to restrict to digits only and exactly 10 digits
            InputFilter[] filters = new InputFilter[1];
            filters[0] = new InputFilter.LengthFilter(10); // Set the exact length to 10
            phoneEditText.setFilters(filters);
            phoneEditText.setInputType(InputType.TYPE_CLASS_NUMBER); // Set input type to numeric

            // Add a TextWatcher to listen for changes in the EditText
            phoneEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Not needed for this example
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Not needed for this example
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Check if the entered phone number has exactly 10 digits
                    if (editable.length() == 10) {
                        // Valid phone number
                        // You can add further actions or validation here if needed
                    } else {
                        // Invalid phone number, you may want to show an error message
                        phoneEditText.setError("Enter a valid 10-digit phone number");
                    }
                }
            });
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String number = phoneEditText.getText().toString();

                if(name.length()!=0){
                    if(number.length()==10){
                        Cursor data = myDB.getNumber(number);
                        if (data.getCount() == 0) {
                            addData(name, number);
                            Toast.makeText(EmergencyContact.this, "Data added", Toast.LENGTH_SHORT).show();
                            nameEditText.setText("");
                            phoneEditText.setText("");
                            loadData();
                        } else {
                            phoneEditText.setError("This Number is already exist in contact");
                        }
                    }
                    else {
                        phoneEditText.setError("Please Enter valid 10 Digit Number");
                    }
                }else {
                    nameEditText.setError("Please Enter valid Name");
                }



            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberToDelete = phoneEditText.getText().toString();
                if(numberToDelete.length()==10){
                    Cursor data = myDB.getNumber(numberToDelete);
                    if (data.getCount() == 0) {
                        phoneEditText.setError("This Number is not exist in contact");
                    } else {
                        deleteData(numberToDelete);
                        nameEditText.setText("");
                        phoneEditText.setText("");
                        loadData();
                    }
                }
                else {
                    phoneEditText.setError("Please Enter valid 10 Digit Number");
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String number = phoneEditText.getText().toString();

                if(name.length()!=0){
                    if(number.length()==10){
                        Cursor data = myDB.getNumber(number);
                        if (data.getCount() == 0) {
                            phoneEditText.setError("This Number is not exist in contact");
                        } else {
                            updateData(name, number);
                            nameEditText.setText("");
                            phoneEditText.setText("");
                            loadData();
                        }
                    }
                    else {
                        phoneEditText.setError("Please Enter valid 10 Digit Number");
                    }
                }else {
                    nameEditText.setError("Please Enter valid Name");
                }

            }
        });
    }

    private void addData(String name, String number) {
        boolean insertData = myDB.addData(name, number);
        if (insertData) {
            Toast.makeText(EmergencyContact.this, "Data added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EmergencyContact.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData(String numberToDelete) {
        int rowsDeleted = myDB.deleteData(numberToDelete);
        if (rowsDeleted > 0) {
            Toast.makeText(EmergencyContact.this, "Data deleted successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EmergencyContact.this, "No data deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData(String name, String number) {
        int rowsUpdated = myDB.updateData(name, number);
        if (rowsUpdated > 0) {
            Toast.makeText(EmergencyContact.this, "Data updated successfully.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(EmergencyContact.this, "No data updated.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadData() {
        Cursor data = myDB.getListContent();
        if (data.getCount() == 0) {
            Toast.makeText(EmergencyContact.this, "There is no content", Toast.LENGTH_SHORT).show();
        } else {
            ArrayList<EmergencyContactItem> theList = new ArrayList<>();

            while (data.moveToNext()) {
                String name = data.getString(1);
                String number = data.getString(2);
                theList.add(new EmergencyContactItem(name, number));
            }

            EmergencyContactAdapter adapter = new EmergencyContactAdapter(theList);
            linearLayout.setAdapter(adapter);
        }
    }

}

class EmergencyContactAdapter extends RecyclerView.Adapter<EmergencyContactAdapter.HelplineViewHolder> {
    private ArrayList<EmergencyContactItem> helplineList;

    EmergencyContactAdapter(ArrayList<EmergencyContactItem> helplineList) {
        this.helplineList = helplineList;
    }


    @NonNull
    @Override
    public HelplineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new HelplineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HelplineViewHolder holder, int position) {
        final EmergencyContactItem currentItem = helplineList.get(position);
        holder.nameTextView.setText(currentItem.getName());
        holder.numberTextView.setText(currentItem.getNumber());

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the call icon is clicked, you can get the number from the item
                String contactName = currentItem.getName();
                String phoneNumber = currentItem.getNumber();
                // Handle the action to call the number (e.g., using an Intent)
                EmergencyContact.nameEditText.setText(contactName);
                EmergencyContact.phoneEditText.setText(phoneNumber);

            }
        });
        holder.numberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // When the call icon is clicked, you can get the number from the item
                String contactName = currentItem.getName();
                String phoneNumber = currentItem.getNumber();
                // Handle the action to call the number (e.g., using an Intent)
                EmergencyContact.nameEditText.setText(contactName);
                EmergencyContact.phoneEditText.setText(phoneNumber);

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

        HelplineViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            numberTextView = itemView.findViewById(R.id.numberTextView);
        }
    }

}
