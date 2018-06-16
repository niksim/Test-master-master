package com.wealth.test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.wealth.test.R;
import com.wealth.test.models.Result;

public class DetailScreen extends BaseActivity {

    TextView txtName;
    TextView txtHeight;
    TextView txtMass;
    TextView txtDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        Result result = i.getParcelableExtra("Result");
        if (result != null) {
            txtName.setText(result.getName());
            txtHeight.setText(result.getHeight());
            txtMass.setText(result.getMass());
            txtDateTime.setText(result.getCreated());
        }
    }

    @Override
    public void initSetContentView() {
        setContentView(R.layout.activity_detail_screen);
    }

    @Override
    public void initComponents() {
        txtName = findViewById(R.id.txt_name);
        txtHeight = findViewById(R.id.txt_height);
        txtMass = findViewById(R.id.txt_mass);
        txtDateTime = findViewById(R.id.txt_date_time);
    }

    @Override
    public void initListeners() {
        //Provide listeners here
    }

}
