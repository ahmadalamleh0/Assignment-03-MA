package com.example.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.button.MaterialButton;
import android.widget.ImageView;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    ImageView fbBtn;
    CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("LoginActivity", "onCreate() called");

        callbackManager = CallbackManager.Factory.create();
        AccessToken accessToken= AccessToken.getCurrentAccessToken();
        if(accessToken!=null && accessToken.isExpired()==false){
            startActivity(new Intent(LoginActivity.this, SecondLoginActivity.class));
            finish();
        }

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        startActivity(new Intent(LoginActivity.this, SecondLoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        Log.d("LoginActivity", "Facebook login cancelled");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("LoginActivity", "Facebook login error: " + exception.getMessage());
                    }
                });
        Log.d("LoginActivity", "Setting OnClickListener for Facebook button");
        fbBtn = findViewById(R.id.fb_btn);
        fbBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
            }
        });




        Log.d("LoginActivity", "Setting OnClickListener for login button");
        // Assuming you have a MaterialButton with the ID btnLogin in your XML
        MaterialButton loginButton = findViewById(R.id.btnLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform login validation, for example, check hardcoded credentials
                if (validateLogin()) {
                    // If login is successful, navigate to MainActivity
                    openMainActivity();
                } else {
                    // Handle unsuccessful login, show a message, etc.
                    showErrorMessage();
                }
            }
        });
    }


    // Method to perform login validation
    private boolean validateLogin() {
        // Hardcoded credentials for demo, replace with your actual validation logic
        String hardcodedEmail = "test@example.com";
        String hardcodedPassword = "password";

        // Get user-entered email and password
        EditText emailEditText = findViewById(R.id.editTextEmail);
        EditText passwordEditText = findViewById(R.id.editTextPassword);
        String enteredEmail = emailEditText.getText().toString();
        String enteredPassword = passwordEditText.getText().toString();

        // Compare with hardcoded credentials (replace with your authentication logic)
        return enteredEmail.equals(hardcodedEmail) && enteredPassword.equals(hardcodedPassword);
    }

    // Method to open MainActivity on successful login
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish LoginActivity to prevent going back to it from MainActivity
    }

    // Method to show an error message for unsuccessful login
    private void showErrorMessage() {
        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
        Log.d("LoginActivity", "Incorrect email or password entered");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
