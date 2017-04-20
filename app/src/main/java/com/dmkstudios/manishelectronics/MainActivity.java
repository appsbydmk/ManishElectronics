package com.dmkstudios.manishelectronics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userName, password;
    Button loginButton;
    Intent locationIntent;
    SharedPreferences loginDetails;
    SharedPreferences.Editor loginDetailsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (userName.getText().toString().equals("darshan") && password.getText().toString().equals("darshan123")) {
                    locationIntent = new Intent(MainActivity.this, LocationDetailsActivity.class);
                    loginDetails = getSharedPreferences("loginDetails", 0);
                    loginDetailsEditor = loginDetails.edit();
                    loginDetailsEditor.putString("userName", userName.getText().toString());
                    loginDetailsEditor.putString("password", password.getText().toString());
                    loginDetailsEditor.commit();
                    startActivity(locationIntent);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Wrong Login Credentials", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
