package com.dmkstudios.manishelectronics;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class LocationDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Button continueButton;
    Intent orderIntent, userIntent;
    private Spinner regionSpinner, locationSpinner;
    private ArrayAdapter<String> regionAdapter, southMumbaiAdapter, easternSuburbsAdapter, westernSuburbsAdapter;
    Bundle locationDetailsBundle;
    String userName;
    SharedPreferences loginDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        loginDetails = getSharedPreferences("loginDetails", 0);
        userName = loginDetails.getString("userName", null);
        continueButton = (Button) findViewById(R.id.continue_button);
        regionSpinner = (Spinner) findViewById(R.id.region_spinner);
        regionSpinner.setOnItemSelectedListener(this);
        regionAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.mumbai_geography));
        regionSpinner.setAdapter(regionAdapter);
        locationSpinner = (Spinner) findViewById(R.id.location_spinner);
        locationSpinner.setOnItemSelectedListener(this);
        continueButton.setOnClickListener(this);
        Toast.makeText(getBaseContext(), "Welcome, " + userName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                locationDetailsBundle = new Bundle();
                locationDetailsBundle.putString("region", regionSpinner.getSelectedItem().toString());
                locationDetailsBundle.putString("location", locationSpinner.getSelectedItem().toString());
                orderIntent = new Intent(LocationDetailsActivity.this, OrderDetailsActivity.class);
                orderIntent.putExtras(locationDetailsBundle);
                startActivity(orderIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.region_spinner:
                if (position == 0) {
                    southMumbaiAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.south_mumbai));
                    locationSpinner.setAdapter(southMumbaiAdapter);
                } else if (position == 1) {
                    westernSuburbsAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.western_suburbs));
                    locationSpinner.setAdapter(westernSuburbsAdapter);
                } else {
                    easternSuburbsAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.eastern_suburbs));
                    locationSpinner.setAdapter(easternSuburbsAdapter);
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getBaseContext(), "Select the region and location!", Toast.LENGTH_SHORT).show();
    }
}
