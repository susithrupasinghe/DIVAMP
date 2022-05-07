package com.mad.divamp.location.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.location.Registration_2;
import com.mad.divamp.location.Registration_3;
import com.mad.divamp.location.location;

import es.dmoral.toasty.Toasty;

public class Registration2Activity extends AppCompatActivity {

    String NIC,fullName,contactNo,email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Spinner categoryId;
    EditText emailEtd,location_name,NICEtd,fullNameEtd,contactNoEtd,Address_1,Address_2,password;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_registration2);

        location_name = findViewById(R.id.location_name);
        categoryId = findViewById(R.id.category);
        Address_1 = findViewById(R.id.Address_1);
        Address_2 = findViewById(R.id.Address_2);
        password = findViewById(R.id.password);

        NICEtd = (EditText)findViewById(R.id.NIC);
        fullNameEtd = (EditText)findViewById(R.id.fullName);
        contactNoEtd = (EditText)findViewById(R.id.contactNumber);
        emailEtd = (EditText)findViewById(R.id.RegEmail);

        btnFinish = (Button) findViewById(R.id.finish_btn);

        Intent intent = getIntent();
//        String email = intent.getStringExtra("email");
        String Location_name = intent.getStringExtra("Location_name");
        String category = intent.getStringExtra("category");
        String Address_1 = intent.getStringExtra("Address_1");
        String Address_2 = intent.getStringExtra("Address_2");
        String password = intent.getStringExtra("password");

        btnFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                NIC = NICEtd.getText().toString();
                fullName = fullNameEtd.getText().toString();
                contactNo = contactNoEtd.getText().toString();
                email = emailEtd.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(NIC)) {
                    NICEtd.setError("Please enter NIC");
                } else if (TextUtils.isEmpty(fullName)) {
                    fullNameEtd.setError("Please enter full name");
                } else if (TextUtils.isEmpty(email)) {
                    emailEtd.setError("Please enter E-mail");
                } else if (TextUtils.isEmpty(contactNo)) {
                    contactNoEtd.setError("Please enter contact number");
                } else {
                    // calling method to add data to Firebase Firestore.
                    addData(email,Location_name,category,Address_1,Address_2,NIC, fullName, contactNo,password);
                }
            }
        });
    }

    private void addData(String category,String Address_1,String Address_2, String email,String Location_name,String NIC,String fullName,String contactNo,String password){

        CollectionReference dbLocation = db.collection("location");

        location location = new location(email,Location_name,category,Address_1,Address_2,NIC, fullName, contactNo,password);

        dbLocation.add(location).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toasty.success(Registration2Activity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
//navigate another page
                startActivity(new Intent(Registration2Activity.this, Registration3Activity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toasty.success(Registration2Activity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });
    }
}