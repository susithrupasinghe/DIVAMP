package com.mad.divamp.location.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mad.divamp.R;
import com.mad.divamp.location.Registration_3;
import com.mad.divamp.location.logIn.LogIn1Activity;


public class Registration3Activity extends AppCompatActivity {

    Button logInNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_registration3);

        logInNow = (Button) findViewById(R.id.logInNow);

        logInNow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
    }

    public void logIn(){
        //navigate another page
        startActivity(new Intent(Registration3Activity.this,LogIn1Activity.class));
        finish();
    }
}