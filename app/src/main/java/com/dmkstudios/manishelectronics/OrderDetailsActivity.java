package com.dmkstudios.manishelectronics;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrderDetailsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button placeOrderButton, cancelOrderButton, addProductButton;
    Intent placeOrderIntent, cancelOrderIntent, locationIntent;
    Bundle locationBundle, productBundle;
    String region, location;
    TextView locationTextView, productDetailTv;
    ArrayAdapter<String> southMumbaiVendors, easternSuburbsVendors, westernSuburbsVendors, productTypeAdapter, productDetailAdapter;
    Spinner vendorSpinner, productTypeSpinner, productDetailSpinner;
    String product = "";
    EditText productQty;
    int selectedProductCounter;
    ArrayList<Integer> productPrices, productQuantity;
    ArrayList<String> selectedProducts;
    String vendorName = "", vendorLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedProductCounter = 0;
        productPrices = new ArrayList<>();
        productQuantity = new ArrayList<>();
        selectedProducts = new ArrayList<>();
        setContentView(R.layout.activity_order_details);
        placeOrderButton = (Button) findViewById(R.id.order_placed_button);
        placeOrderButton.setOnClickListener(this);
        cancelOrderButton = (Button) findViewById(R.id.order_cancelled_button);
        cancelOrderButton.setOnClickListener(this);
        locationIntent = getIntent();
        locationBundle = locationIntent.getExtras();
        region = locationBundle.getString("region");
        location = locationBundle.getString("location");
        locationTextView = (TextView) findViewById(R.id.location_textview);
        locationTextView.setText("Vendor Location: " + region + ", " + location);
        vendorSpinner = (Spinner) findViewById(R.id.vendor_spinner);
        loadVendors(region, location);
        productTypeSpinner = (Spinner) findViewById(R.id.product_type_spinner);
        productTypeSpinner.setOnItemSelectedListener(this);
        productTypeAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.products));
        productTypeSpinner.setAdapter(productTypeAdapter);
        productDetailSpinner = (Spinner) findViewById(R.id.product_detail_spinner);
        productDetailSpinner.setOnItemSelectedListener(this);
        addProductButton = (Button) findViewById(R.id.add_product_button);
        addProductButton.setOnClickListener(this);
        productDetailTv = (TextView) findViewById(R.id.product_details_tv);
        productQty = (EditText) findViewById(R.id.product_qty);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_placed_button:
                /*System.out.println(productPrices);
                for(int i = 0; i < 5; i++) {
                    System.out.println(productPrices[i]);
                }
                for(int i = 0; i < 5; i++) {
                    System.out.println(productQuantity[i]);
                }*/
                vendorName = vendorSpinner.getSelectedItem().toString();
                vendorLocation = locationTextView.getText().toString();
                productBundle = new Bundle();
                productBundle.putStringArrayList("selectedProducts", selectedProducts);
                productBundle.putIntegerArrayList("productsPrices", productPrices);
                productBundle.putIntegerArrayList("productQuantities", productQuantity);
                productBundle.putString("vendorName", vendorName);
                productBundle.putString("vendorLocation", vendorLocation);
                placeOrderIntent = new Intent(OrderDetailsActivity.this, OrderPlacedActivity.class);
                placeOrderIntent.putExtras(productBundle);
                startActivity(placeOrderIntent);
                finish();
                break;
            case R.id.order_cancelled_button:
                vendorName = vendorSpinner.getSelectedItem().toString();
                vendorLocation = locationTextView.getText().toString();
                productBundle = new Bundle();
                productBundle.putStringArrayList("selectedProducts", selectedProducts);
                productBundle.putIntegerArrayList("productsPrices", productPrices);
                productBundle.putIntegerArrayList("productQuantities", productQuantity);
                productBundle.putString("vendorName", vendorName);
                productBundle.putString("vendorLocation", vendorLocation);
                cancelOrderIntent = new Intent(OrderDetailsActivity.this, OrderCancelledActivity.class);
                cancelOrderIntent.putExtras(productBundle);
                startActivity(cancelOrderIntent);
                finish();
                break;
            case R.id.add_product_button:
                if (selectedProductCounter < 5) {
                    String selectedProduct = productDetailSpinner.getSelectedItem().toString();
                    String SelectedProductQty = productQty.getText().toString();
                    productPrices.add(Integer.parseInt(selectedProduct.substring(selectedProduct.lastIndexOf("(") + 5, selectedProduct.lastIndexOf(")"))));
                    productQuantity.add(Integer.parseInt(SelectedProductQty));
                    selectedProducts.add(selectedProduct);
                    product += productTypeSpinner.getSelectedItem().toString() + " - " + selectedProduct + "\t" + "(Qty: " + SelectedProductQty + ")\n";
                    productDetailTv.setText(product);
                    productQty.setText("");
                    ++selectedProductCounter;
                } else {
                    Toast.makeText(getBaseContext(), "You can add only upto 5 products", Toast.LENGTH_SHORT).show();
                    productQty.setText("");
                }
                break;
        }
    }

    private void loadVendors(String region, String location) {
        if (region.equals("South Mumbai")) {
            if (location.equals("Dadar")) {
                southMumbaiVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.dadar_vendors));
                vendorSpinner.setAdapter(southMumbaiVendors);
            }
            if (location.equals("Colaba")) {
                southMumbaiVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.colaba_vendors));
                vendorSpinner.setAdapter(southMumbaiVendors);
            }
            if (location.equals("Parel")) {
                southMumbaiVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.parel_vendors));
                vendorSpinner.setAdapter(southMumbaiVendors);
            }
        }
        if (region.equals("Eastern Suburbs")) {
            if (location.equals("Thane")) {
                easternSuburbsVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.thane_vendors));
                vendorSpinner.setAdapter(easternSuburbsVendors);
            }
            if (location.equals("Kurla")) {
                easternSuburbsVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.kurla_vendors));
                vendorSpinner.setAdapter(easternSuburbsVendors);
            }
            if (location.equals("Ghatkopar")) {
                easternSuburbsVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.ghatkopar_vendors));
                vendorSpinner.setAdapter(easternSuburbsVendors);
            }
        }
        if (region.equals("Western Suburbs")) {
            if (location.equals("Bandra")) {
                westernSuburbsVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.bandra_vendors));
                vendorSpinner.setAdapter(westernSuburbsVendors);
            }
            if (location.equals("Andheri")) {
                westernSuburbsVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.andheri_vendors));
                vendorSpinner.setAdapter(westernSuburbsVendors);
            }
            if (location.equals("Borivali")) {
                westernSuburbsVendors = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.borivali_vendors));
                vendorSpinner.setAdapter(westernSuburbsVendors);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.product_type_spinner:
                if (position == 0) {
                    productDetailAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.fans));
                    productDetailSpinner.setAdapter(productDetailAdapter);
                }
                if (position == 1) {
                    productDetailAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.tvs));
                    productDetailSpinner.setAdapter(productDetailAdapter);
                }
                if (position == 2) {
                    productDetailAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.mixers));
                    productDetailSpinner.setAdapter(productDetailAdapter);
                }
                if (position == 3) {
                    productDetailAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.irons));
                    productDetailSpinner.setAdapter(productDetailAdapter);
                }
                if (position == 4) {
                    productDetailAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.fridges));
                    productDetailSpinner.setAdapter(productDetailAdapter);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getBaseContext(), "Select the product!", Toast.LENGTH_SHORT).show();
    }
}
