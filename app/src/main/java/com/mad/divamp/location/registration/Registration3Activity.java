package com.mad.divamp.location.registration;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.anton46.stepsview.StepsView;
import com.mad.divamp.R;
import com.mad.divamp.location.logIn.LogIn1Activity;


public class Registration3Activity extends AppCompatActivity {

    private StepsView stepsView;
    Button logInNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_registration3);

        stepsView = findViewById(R.id.stepsViewLocation3);
        logInNow = (Button) findViewById(R.id.logInNow);

        stepsView
                .setLabels(new String[] {"Location Details", "Contact Details", "Finish"})
                .setBarColorIndicator(stepsView.getContext().getResources().getColor(R.color.inactive))
                .setProgressColorIndicator(stepsView.getContext().getResources().getColor(R.color.light_green))
                .setLabelColorIndicator(stepsView.getContext().getResources().getColor(R.color.inactive))
                .setCompletedPosition(2)
                .drawView();

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