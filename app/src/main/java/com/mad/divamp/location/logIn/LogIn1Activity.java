package com.mad.divamp.location.logIn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.mad.divamp.R;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.location.registration.Registration1Activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LogIn1Activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText emailEtd,passwordEtd;
    Button logInBtn,registerBtn;

    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_log_in);

        emailEtd = findViewById(R.id.logInemail);
        passwordEtd = findViewById(R.id.logInpassword);
        logInBtn = findViewById(R.id.LogIn);
        registerBtn = findViewById(R.id.register);

        logInBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                email = emailEtd.getText().toString();
                password = passwordEtd.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LogIn1Activity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }else{

                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                registration();
            }
        });
    }

    public void registration(){
        //navigate another page
        startActivity(new Intent(LogIn1Activity.this, Registration1Activity.class));
        finish();
    }
}