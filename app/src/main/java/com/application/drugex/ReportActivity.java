package com.application.drugex;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.application.posts.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;
import java.util.UUID;

public class ReportActivity extends AppCompatActivity {

    EditText incDesc, incDate, traffickingType, transportMethod, city, address, gender, approxAge, appearance, otherInfo, numOfPersons;
    TextView btnEvidenceTxt;
    Button btnEvidence, btnSubmit;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK  && result.getData() != null && result.getData().getData() != null) {
                            imageUri = result.getData().getData();
                            btnEvidence.setVisibility(View.GONE);
                            btnEvidenceTxt.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

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
        btnEvidenceTxt = findViewById(R.id.btn_evidence_txt);
        btnSubmit = findViewById(R.id.btn_submit);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        btnEvidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });

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

                    StorageReference picRef = storageReference.child("posts/" + randomKey);
                    ProgressDialog pd = new ProgressDialog(ReportActivity.this);
                    pd.setTitle("Uploading Image....");
                    pd.show();

                    picRef.putFile(imageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Snackbar.make(findViewById(android.R.id.content), "Image Uploaded Successfully", Snackbar.LENGTH_LONG).show();
                                    pd.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    pd.dismiss();
                                    Toast.makeText(getApplicationContext(), "Error uploading pic", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    double percent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                                    pd.setMessage("Progress: " + (int) percent + "%");
                                }
                            });

                    Toast.makeText(ReportActivity.this, "Your message is received in our servers", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(ReportActivity.this, AppreciationActivity.class));

                }
            }
        });

    }
}