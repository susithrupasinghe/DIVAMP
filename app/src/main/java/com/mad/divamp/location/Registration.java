package com.mad.divamp.location;
import com.mad.divamp.R;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

public class Registration extends AppCompatActivity {

    Spinner categoryId;
    String strEmail,strLocation_name,strCategory,strAddress_1,strAddress_2,strPassword,strPasswordReEnter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText email,location_name,Address_1,Address_2,password,passwordReEnter;
    Button btn;
    Button btnPassBundles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email);
        location_name = findViewById(R.id.location_name);
        categoryId = findViewById(R.id.category);
        Address_1 = findViewById(R.id.Address_1);
        Address_2 = findViewById(R.id.Address_2);
        password = findViewById(R.id.password);
        passwordReEnter = findViewById(R.id.passwordReEnter);
        btnPassBundles = findViewById(R.id.reg_next_btn);
//        btn = findViewById(R.id.reg_next_btn);

        String[] category = getResources().getStringArray(R.array.category);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryId.setAdapter(adapter);

        btnPassBundles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                strEmail = email.getText().toString();
                strLocation_name = location_name.getText().toString();
                strCategory = categoryId.toString();
                strAddress_1 = Address_1.getText().toString();
                strAddress_2 = Address_2.getText().toString();
                strPassword = password.getText().toString();
                strPasswordReEnter = passwordReEnter.getText().toString();

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(strEmail)) {
                    email.setError("Please enter Email");
                } else if (TextUtils.isEmpty(strLocation_name)) {
                    location_name.setError("Please enter Location Name");
                } else if (TextUtils.isEmpty(strAddress_1)) {
                    Address_1.setError("Please enter Address line 1");
                }else if (TextUtils.isEmpty(strPassword)) {
                    password.setError("Please enter Password");
                }
//                else if(password != passwordReEnter){
//                    passwordReEnter.setError("Password and Password Re Enter fields are different");
//                }
                else {
                    // calling method to add data to Firebase Firestore.
                    next();
                }
            }
        });
    }

    public void next(){
        Intent intent = new Intent(Registration.this, Registration_2.class);
        // passing the bundle to the intent
        intent.putExtra("email",strEmail);
        intent.putExtra("Location_name",strLocation_name);
        intent.putExtra("Category",strCategory);
        intent.putExtra("Address_1",strAddress_1);
        intent.putExtra("Address_2",strAddress_2);
        intent.putExtra("password",strPassword);
        // starting the activity by passing the intent
        // to it.
        startActivity(intent);
    }

//    @Override
//    public void onItemSelected(AdapterView<?>parent, View view, int i, long l) {
//        String strCategory = parent.getItemAtPosition(i).toString();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}