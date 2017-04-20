package com.dmkstudios.manishelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LogoutActivity extends AppCompatActivity {
    Button logoutButton, newOrderButton;
    Intent newOrderIntent, loginIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginIntent = new Intent(getApplicationContext(), MainActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginIntent);
                finish();
            }
        });
        newOrderButton = (Button) findViewById(R.id.new_order_button);
        newOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newOrderIntent = new Intent(LogoutActivity.this, LocationDetailsActivity.class);
                startActivity(newOrderIntent);
                finish();
            }
        });
    }
}
