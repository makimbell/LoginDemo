package com.example.application.logindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        signOut = (TextView) findViewById(R.id.tvSignOut);
        firebaseAuth = FirebaseAuth.getInstance();

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // When a menu item is clicked, get the ID, see which item they clicked, and do the appropriate thing
        switch(item.getItemId()){
            case R.id.logoutMenu: {
                Logout();
            }
        }

        return true;
    }

    private void Logout() {
        // Log out
        firebaseAuth.signOut();
        Toast.makeText(SecondActivity.this, "You have successfully signed out", Toast.LENGTH_SHORT).show();

        // Stop this activity, start main activity, and delete stack (for back button functionality)
        Intent intent = new Intent(SecondActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
}
