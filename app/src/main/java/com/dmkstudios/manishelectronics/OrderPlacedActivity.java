package com.dmkstudios.manishelectronics;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderPlacedActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    Button saveButton;
    Intent logoutIntent, selectedProductsIntent;
    Bundle productBundle;
    ArrayList<Integer> productPrices, productQuantity;
    ArrayList<String> selectedProducts;
    TextView dateView, totalAmount, discount, netAmount;
    RadioGroup paymentOptions;
    String vendorName = "", vendorLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        totalAmount = (TextView) findViewById(R.id.total_amount);
        netAmount = (TextView) findViewById(R.id.net_amount);
        discount = (TextView) findViewById(R.id.discount);
        paymentOptions = (RadioGroup) findViewById(R.id.payment_group);
        paymentOptions.setOnCheckedChangeListener(this);
        dateView = (TextView) findViewById(R.id.dateView);
        dateView.setText("Date: " + getCurrentDate());
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        selectedProductsIntent = getIntent();
        productBundle = selectedProductsIntent.getExtras();
        productPrices = productBundle.getIntegerArrayList("productsPrices");
        selectedProducts = productBundle.getStringArrayList("selectedProducts");
        productQuantity = productBundle.getIntegerArrayList("productQuantities");
        vendorLocation = productBundle.getString("vendorLocation");
        vendorName = productBundle.getString("vendorName");
    }

    private void writeOrderToFile() {
        String newOrderFileName = vendorName.replace("\\s+", "") + "-" + getCurrentDate().replaceAll("/", "-") + ".txt";
        OrderLocationService orderLocationService = new OrderLocationService(getBaseContext());
        Location myLocation = orderLocationService.getLocation();
        if (myLocation != null) {
            try {
                BufferedWriter orderWriter = new BufferedWriter(new OutputStreamWriter(openFileOutput(newOrderFileName, MODE_PRIVATE)));
                orderWriter.append("Latitude: " + String.valueOf(myLocation.getLatitude())
                        + " Longitude: " + String.valueOf(myLocation.getLongitude()) + "\n");
                orderWriter.append(vendorName + "\n");
                orderWriter.append(vendorLocation + "\n");
                orderWriter.append("Date: " + getCurrentDate() + "\n\n");
                for (int i = 0; i < productQuantity.size(); i++) {
                    orderWriter.append("Product -> " + selectedProducts.get(i) + "\n");
                    orderWriter.append("Quantity -> " + productQuantity.get(i) + "\n");
                    orderWriter.append("Cost -> Rs. " + (productPrices.get(i) * productQuantity.get(i)) + "\n");
                }
                if (paymentOptions.getCheckedRadioButtonId() == R.id.cash_radio) {
                    orderWriter.append("Total Cost -> Rs. " + calculateNetAmount(10) + "\n");
                } else {
                    orderWriter.append("Total Cost -> Rs. " + calculateTotalAmount() + "\n");
                }
                orderWriter.flush();
                orderWriter.close();
                Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Order cannot be placed!", Toast.LENGTH_SHORT).show();
        }

    }

    private double calculateTotalAmount() {
        double totalAmount = 0;
        for (int i = 0; i < productQuantity.size(); i++) {
            totalAmount += productPrices.get(i) * productQuantity.get(i);
        }
        return totalAmount;
    }

    private double calculateNetAmount(double discount) {
        double netAmount = 0;
        double totalAmount = calculateTotalAmount();
        netAmount = totalAmount - (totalAmount * (discount / 100));
        return netAmount;
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                writeOrderToFile();
                logoutIntent = new Intent(OrderPlacedActivity.this, LogoutActivity.class);
                startActivity(logoutIntent);
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.cash_radio:
                totalAmount.setText("Total Amount: " + calculateTotalAmount());
                discount.setText("10% discount on cash");
                netAmount.setText("Net Amount: " + calculateNetAmount(10));
                break;
            case R.id.credit_radio:
                totalAmount.setText("Total Amount: " + calculateTotalAmount());
                discount.setText("No discount on credit");
                netAmount.setText("");
                break;
        }
    }
}
