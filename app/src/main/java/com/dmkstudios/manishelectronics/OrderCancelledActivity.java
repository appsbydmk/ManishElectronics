package com.dmkstudios.manishelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderCancelledActivity extends AppCompatActivity {
    Button saveButton, logoutButton;
    Intent logoutIntent, selectedProductsIntent;
    Bundle productBundle;
    ArrayList<Integer> productPrices, productQuantity;
    ArrayList<String> selectedProducts;
    String vendorName = "", vendorLocation = "";
    TextView orderCancelReason, dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_canceled);
        orderCancelReason = (TextView) findViewById(R.id.cancel_reason);
        dateView = (TextView) this.findViewById(R.id.dateView);
        dateView.setText("Date: " + getCurrentDate());
        selectedProductsIntent = getIntent();
        productBundle = selectedProductsIntent.getExtras();
        productPrices = productBundle.getIntegerArrayList("productsPrices");
        selectedProducts = productBundle.getStringArrayList("selectedProducts");
        productQuantity = productBundle.getIntegerArrayList("productQuantities");
        vendorLocation = productBundle.getString("vendorLocation");
        vendorName = productBundle.getString("vendorName");

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newOrderFileName = vendorName.replace("\\s+", "") + "-" + "OrderCancelled" + ".txt";
                File dataDir = Environment.getExternalStorageDirectory();
                File orderDir = new File(dataDir.getAbsoluteFile() + "/ManishElectronics");
                BufferedWriter orderWriter = null;
                try {
                    File newOrder = new File(orderDir, newOrderFileName);
                    orderWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newOrder)));
                    orderWriter.append(vendorName + "\n");
                    orderWriter.append(vendorLocation + "\n");
                    orderWriter.append("Date: " + getCurrentDate() + "\n\n");
                    for (int i = 0; i < productQuantity.size(); i++) {
                        orderWriter.append("Product -> " + selectedProducts.get(i) + "\n");
                        orderWriter.append("Quantity -> " + productQuantity.get(i) + "\n\n");
                    }
                    orderWriter.append("Reason to cancel the order -> " + orderCancelReason.getText().toString());
                    orderWriter.flush();
                    orderWriter.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (orderWriter != null)
                            orderWriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                orderCancelReason.setText("");
                Toast.makeText(getBaseContext(), "Order Cancelled!", Toast.LENGTH_SHORT).show();
            }
        });
        logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutIntent = new Intent(OrderCancelledActivity.this, LogoutActivity.class);
                startActivity(logoutIntent);
                finish();
            }
        });
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
