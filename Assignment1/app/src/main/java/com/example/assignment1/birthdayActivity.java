package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.util.Calendar;

public class birthdayActivity extends AppCompatActivity {


    private static final String TAG = "birthdayActivity";// Tag for logging purposes
    private EditText mDisplayDate;  // EditText to display and select the event date
    private DatePickerDialog.OnDateSetListener mDateSetListener;// Listener for date picker dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
        Log.d(TAG, "onCreate: Birthday Activity created"); // Log onCreate event

        // Setting up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the back button in the ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize the date EditText
        mDisplayDate = findViewById(R.id.editEventDate);

        // Set up onClickListener for the date EditT
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date
                Calendar cal= Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog to pick a date
                DatePickerDialog dialog = new DatePickerDialog(
                        birthdayActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,mDateSetListener,year,month,day);
                dialog.show();
            }
        });

        // Set up OnDateSetListener for the DatePickerDialog
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Increment month as it is 0-indexed
                month = month + 1;
                // Log the selected date
                Log.d(TAG,"onDateSet: mm/dd/yyy: "+ month +"/" + day + "/" + year );
                // Format the date and set it to the EditText
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
            }
        };

        // Set up onClickListener for the Submit button
        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the ConfirmationActivity with event details
                openConfirmationActivity();
            }
        });

        // Locate the "Add Guest" button by its ID
        Button btnAddGuest = findViewById(R.id.AddGuest);
        // Set an onClickListener for the "Add Guest" button
        btnAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to the GuestListActivity
                Intent intent = new Intent(birthdayActivity.this, GuestListActivity.class);
                openGuestListActivity();
                startActivity(intent);
            }
        });
    }


    // Method to open ConfirmationActivity and pass event details
    private void openConfirmationActivity() {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("eventName", ((EditText) findViewById(R.id.editEventName)).getText().toString());
        intent.putExtra("eventDate", ((EditText) findViewById(R.id.editEventDate)).getText().toString());
        intent.putExtra("eventTime", ((EditText) findViewById(R.id.editEventTime)).getText().toString());
        intent.putExtra("attendees", ((EditText) findViewById(R.id.editAttendees)).getText().toString());
        intent.putExtra("eventLocation", ((EditText) findViewById(R.id.editEventLocation)).getText().toString());

        startActivity(intent);
    }
    private void openGuestListActivity() {
        Intent intent = new Intent(this, GuestListActivity.class);
        intent.putExtra("eventName", ((EditText) findViewById(R.id.editEventName)).getText().toString());
        intent.putExtra("eventDate", ((EditText) findViewById(R.id.editEventDate)).getText().toString());
        intent.putExtra("eventTime", ((EditText) findViewById(R.id.editEventTime)).getText().toString());
        intent.putExtra("eventLocation", ((EditText) findViewById(R.id.editEventLocation)).getText().toString());
        startActivity(intent);
    }
}
