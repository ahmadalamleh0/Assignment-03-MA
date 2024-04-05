package com.example.assignment1;

// Import necessary packages and classes
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

// Define the ConfirmationActivity class, extending AppCompatActivity
public class ConfirmationActivity extends AppCompatActivity {

    // Override the onCreate method to set up the activity when it is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the XML layout file (activity_confirmation.xml)
        setContentView(R.layout.activity_confirmation);
        Log.d(TAG, "onCreate: Confirmation Activity created"); // Log onCreate event
    }

    // Define the onBackToHomeClick method to handle the "Back to Home" button click
    public void onBackToHomeClick(View view) {
        // Create an Intent to navigate from ConfirmationActivity to MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        // Start the MainActivity using the created Intent
        startActivity(intent);

        // Optional: Finish the current activity if you don't want it to remain in the back stack
        finish();
    }
}
