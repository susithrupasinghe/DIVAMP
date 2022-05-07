package com.mad.divamp.citizen.userManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.location.registration.Registration1Activity;
import com.mad.divamp.location.registration.Registration2Activity;
import com.mad.divamp.utils.SHA256;

import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class Register1Activity extends AppCompatActivity {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 6 characters
                    "$");

    EditText firstNameEt,lastNameEt,nicEt,birthdayEt,passwordEt,passwordRetypeEt;
    Spinner genderSp;
    Button nextBtn;
    String firstName,lastName,nic,birthday,password,passwordRetype,gender,hashPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citizen_activity_register1);

        firstNameEt = findViewById(R.id.firstName);
        lastNameEt  = findViewById(R.id.lastName);
        nicEt  = findViewById(R.id.nic);
        birthdayEt  = findViewById(R.id.birthday);
        passwordEt  = findViewById(R.id.password);
        passwordRetypeEt  = findViewById(R.id.passwordRetype);
        genderSp = findViewById(R.id.gender);
        nextBtn = findViewById(R.id.reg_next_btn);

        String[] genderArray = getResources().getStringArray(R.array.gender);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,genderArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSp.setAdapter(adapter);

        nextBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                firstName = firstNameEt.getText().toString();
                lastName = lastNameEt.getText().toString();
                birthday = birthdayEt.getText().toString();
                gender = genderSp.getSelectedItem().toString();
                nic = nicEt.getText().toString();
                password = passwordEt.getText().toString();
                passwordRetype = passwordRetypeEt.getText().toString();

                hashPassword = SHA256.getHash(password);

                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(firstName)) {
                    firstNameEt.setError("Please enter First Name");
                } else if (TextUtils.isEmpty(lastName)) {
                    lastNameEt.setError("Please enter Last Name");
                }else if(TextUtils.isEmpty(birthday)){
                    birthdayEt.setError("Please Enter Birthday");
                }else if(TextUtils.isEmpty(nic)){
                    nicEt.setError("Please Enter Birthday");
                }else if (gender.equals("Gender")) {
                    //error for spinner
                    Toasty.success(Register1Activity.this, "Please select Gender", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    passwordEt.setError("Please enter Password");
                }else if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    passwordEt.setError("Password should have  at least 1 special character,no white spaces,at least 6 characters");
                }else if(!password.equals(passwordRetype)){
                    passwordRetypeEt.setError("Password and Confirm Password do not match");
                }
                else {
                    // calling method to add data to Firebase Firestore.
                    next();
                }
            }

        });

    }

    public void next(){
        Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
        // passing the bundle to the intent

        intent.putExtra("firstName",firstName);
        intent.putExtra("lastName",lastName);
        intent.putExtra("nic",nic);
        intent.putExtra("birthday",birthday);
        intent.putExtra("password",hashPassword);
        intent.putExtra("gender",gender);
        // starting the activity by passing the intent
        // to it.
        startActivity(intent);
    }
}