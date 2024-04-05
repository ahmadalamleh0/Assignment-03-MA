package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import kotlin.text.UStringsKt;

public class MainActivity extends AppCompatActivity {


    @Override

   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Get references to the CardView elements in the layout
        CardView birthdayCard = findViewById(R.id.birthday);
        CardView dinnerCard = findViewById(R.id.dinner);
        CardView gameCard = findViewById(R.id.game);
        CardView costumeCard = findViewById(R.id.costume);
        CardView gradutionCard = findViewById(R.id.graduation);
        CardView karaokeCard = findViewById(R.id.karaoke);


        // Set up click listeners for each CardView
        birthdayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBirthdayActivity();
            }
        });

        dinnerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBirthdayActivity();
            }
        });

        gameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBirthdayActivity();
            }
        });

        costumeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openBirthdayActivity(); }
        });

        gradutionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openBirthdayActivity();}
        });

          karaokeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {openBirthdayActivity();}
        });
        // Log that MainActivity is created
        Log.d("MainActivity", "onCreate: MainActivity created");

    }

    // Method to open the BirthdayActivity
    private void openBirthdayActivity() {
        Intent intent = new Intent(this, birthdayActivity.class);
        startActivity(intent);
    }

}


