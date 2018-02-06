package com.example.application.logindemo;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText name, password;
    private TextView info, register;
    private Button login;
    private int counter = 5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.etName);
        password = (EditText) findViewById(R.id.etUserPasswordCreate);
        info = (TextView) findViewById(R.id.tvAttempsRemaining);
        login = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        // Check to see if the user has already logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // If the user has logged in, we want to direct them to the second activity
        //  rather than having them log in again
        if (user != null){
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        // OnClickListener for the Login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name.getText().toString(), password.getText().toString());
            }
        });

        // OnClickListener for the Register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void validate(String userName, String userPassword){

        progressDialog.setMessage("Please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    Toast.makeText(MainActivity.this, "Logged in as " + firebaseAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();

                    counter--;
                    info.setText("Number of attempts remaining: " + counter);
                    if(counter == 0){
                        login.setEnabled(false);
                    }
                }
            }
        });
    }
}
