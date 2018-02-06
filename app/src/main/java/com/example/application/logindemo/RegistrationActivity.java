package com.example.application.logindemo;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPassword;
    private Button regButton;
    private TextView alreadyRegistered;

    //Create an instance of FireBase Authenticator
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        // Setup FireBase Authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        // OnClickListener for Register button
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate user details entered
                if (validate()){
                    // Register to the database
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                // If successful, display a message and return the user to the main screen to log in
                                Toast.makeText(RegistrationActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                                // Start main activity and delete stack for back button functionality
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                // If not successful, tell the user
                                Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        // OnClickListener for Already Registered button
        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the user clicks on AlreadyRegistered, return to MainActivity screen
                //  Extra flags are for clearing the stack (don't want to come back here if the user clicks Back)
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setupUIViews(){
        userName = (EditText) findViewById(R.id.etUserNameCreate);
        userEmail = (EditText) findViewById(R.id.etUserEmailCreate);
        userPassword = (EditText) findViewById(R.id.etUserPasswordCreate);
        regButton = (Button) findViewById(R.id.btnRegister);
        alreadyRegistered = (TextView) findViewById(R.id.tvAlreadySignedUp);
    }

    private Boolean validate() {
        // Returns true if user has entered values into all EditTexts (username, email, password)
        Boolean result = false;

        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Please enter login details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;
    }
}
