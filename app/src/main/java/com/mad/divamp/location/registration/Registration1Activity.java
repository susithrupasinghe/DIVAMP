package com.mad.divamp.location.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.utils.SHA256;

import java.util.regex.Pattern;

public class Registration1Activity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 6 characters
                    "$");

    Spinner categoryId;
    String strLocation_name,strCategory,strAddress_1,strAddress_2,strPassword,strPasswordReEnter,hashPassword;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText location_name,Address_1,Address_2,password,passwordReEnter;
    Button btn;
    Button btnPassBundles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_registration1);

        location_name = findViewById(R.id.location_name);
        categoryId = findViewById(R.id.category);
        Address_1 = findViewById(R.id.Address_1);
        Address_2 = findViewById(R.id.Address_2);
        password = findViewById(R.id.password);
        passwordReEnter = findViewById(R.id.passwordReEnter);
        btnPassBundles = findViewById(R.id.reg_next_btn);


        String[] category = getResources().getStringArray(R.array.category);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryId.setAdapter(adapter);

        btnPassBundles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                strLocation_name = location_name.getText().toString();
                strCategory = categoryId.getSelectedItem().toString();
                strAddress_1 = Address_1.getText().toString();
                strAddress_2 = Address_2.getText().toString();
                strPassword = password.getText().toString();
                strPasswordReEnter = passwordReEnter.getText().toString();

                hashPassword = SHA256.getHash(strPassword);

                // validating the text fields if empty or not.
               if (TextUtils.isEmpty(strLocation_name)) {
                    location_name.setError("Please enter Location Name");
                } else if (TextUtils.isEmpty(strAddress_1)) {
                    Address_1.setError("Please enter Address line 1");
                }else if (TextUtils.isEmpty(strPassword)) {
                    password.setError("Please enter Password");
                }else if (!PASSWORD_PATTERN.matcher(strPassword).matches()) {
                   password.setError("Password is too weak");
               }else if(!strPassword.equals(strPasswordReEnter)){
                    passwordReEnter.setError("Password and Confirm Password do not match");
                }
                else {
                    // calling method to add data to Firebase Firestore.
                    next();
                }
            }
        });
    }

    public void next(){
        Intent intent = new Intent(Registration1Activity.this, Registration2Activity.class);
        // passing the bundle to the intent
        intent.putExtra("location_name",strLocation_name);
        intent.putExtra("category",strCategory);
        intent.putExtra("address_1",strAddress_1);
        intent.putExtra("address_2",strAddress_2);
        intent.putExtra("password",hashPassword);
        // starting the activity by passing the intent
        // to it.
        startActivity(intent);
    }
}

