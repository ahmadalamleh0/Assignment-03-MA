package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

    public class SecondLoginActivity extends AppCompatActivity {
        private static final String TAG = "SecondLoginActivity"; // Tag for logging purposes
        ImageView imageView; // ImageView to display the profile picture
        TextView name; // TextView to display the user's name
        Button logOutbtn; // Button for logging out

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_second_login);

            // Find views by their IDs
            imageView=findViewById(R.id.imageview);
            name= findViewById(R.id.name);
            logOutbtn=findViewById(R.id.logOutbtn);

            // Get current access token from Facebook
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            // Create a new GraphRequest to fetch user data
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            try {
                                // Extract user's full name
                                String fullName = object.getString("name");
                                // Extract URL of the user's profile picture
                                String url = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                // Set user's full name to the TextView
                                name.setText(fullName);
                                // Load and display the profile picture using Picasso library
                                Picasso.get().load(url).into(imageView);
                            } catch (JSONException e) {
                                // Handle JSONException if occurred
                                throw new RuntimeException(e);

                            }
                        }
                    });

            // Set parameters for the GraphRequest to fetch specific fields
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,picture.type(large)");
            request.setParameters(parameters);
            // Execute the GraphRequest asynchronously
            request.executeAsync();

            // Set OnClickListener for the logout button
            logOutbtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    // Log out the user from Facebook
                    LoginManager.getInstance().logOut();
                    // Redirect the user to the LoginActivity
                    startActivity(new Intent(SecondLoginActivity.this, LoginActivity.class));
                    // Finish the current activity
                    finish();
                    // Log the logout event
                    Log.d(TAG, "onClick: User logged out");
                }
            });
        }
    }