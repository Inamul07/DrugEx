package com.application.drugex;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.posts.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class ReportActivity extends AppCompatActivity {

    EditText incDesc, incDate, traffickingType, transportMethod, city, address, gender, approxAge, appearance, otherInfo, numOfPersons;
    Button btnEvidence, btnSubmit;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        incDesc = findViewById(R.id.inc_desc);
        incDate = findViewById(R.id.inc_date);
        traffickingType = findViewById(R.id.trafficking_type);
        transportMethod = findViewById(R.id.transport_method);
        city = findViewById(R.id.city);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        approxAge = findViewById(R.id.approx_age);
        appearance = findViewById(R.id.appearance);
        otherInfo = findViewById(R.id.other_info);
        numOfPersons = findViewById(R.id.num_of_persons);
        btnEvidence = findViewById(R.id.btn_evidence);
        btnSubmit = findViewById(R.id.btn_submit);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtDesc, txtDate, txtTraffickingType, txtTransportMethod, txtCity, txtAddress, txtGender, txtAge, txtAppearance, txtOtherInfo, txtNumOfPersons;
                txtDesc = incDesc.getText().toString();
                txtDate = incDate.getText().toString();
                txtTraffickingType = traffickingType.getText().toString();
                txtTransportMethod = transportMethod.getText().toString();
                txtCity = city.getText().toString();
                txtAddress = address.getText().toString();
                txtGender = gender.getText().toString();
                txtAge = approxAge.getText().toString();
                txtAppearance = appearance.getText().toString();
                txtOtherInfo = otherInfo.getText().toString();
                txtNumOfPersons = numOfPersons.getText().toString();

                if(txtDesc.isEmpty()) {
                    incDesc.setError("This field is required");
                    incDesc.requestFocus();
                } else if(txtDate.isEmpty()) {
                    incDate.setError("This field is required");
                    incDate.requestFocus();
                } else if(txtCity.isEmpty()) {
                    city.setError("This field is required");
                    city.requestFocus();
                } else if(txtAddress.isEmpty()) {
                    address.setError("This field is required");
                    address.requestFocus();
                } else if(txtGender.isEmpty()) {
                    gender.setError("This field is required");
                    gender.requestFocus();
                } else if(txtAppearance.isEmpty()) {
                    appearance.setError("This field is required");
                    appearance.requestFocus();
                } else {
                    if(txtTraffickingType.isEmpty()) {
                        txtTraffickingType = null;
                    } if(txtTransportMethod.isEmpty()) {
                        txtTransportMethod = null;
                    } if(txtAge.isEmpty()) {
                        txtAge = null;
                    } if(txtOtherInfo.isEmpty()) {
                        txtOtherInfo = null;
                    } if(txtNumOfPersons.isEmpty()) {
                        txtNumOfPersons = null;
                    }

                    Post post = new Post(txtDesc, txtDate, txtTraffickingType, txtTransportMethod, txtCity, txtAddress, txtGender, txtAge, txtAppearance, txtOtherInfo, txtNumOfPersons);
                    String randomKey = UUID.randomUUID().toString();
                    databaseReference.child("posts").child(randomKey).setValue(post);
                    Toast.makeText(ReportActivity.this, "Your message is received in our servers", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReportActivity.this, AppreciationActivity.class));

                }
            }
        });

    }
}