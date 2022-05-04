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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;

public class Registration extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button btn;
    Button btnPassBundles, btnNoPassBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        btn = findViewById(R.id.reg_next_btn);
        btnPassBundles = findViewById(R.id.reg_next_btn);

        btnPassBundles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                next();
            }
        });
    }

    public void next(){
        Bundle bundle = new Bundle();
        bundle.putString(
                "key1",
                "Passing Bundle From Main Activity to 2nd Activity");
        Intent intent = new Intent(Registration.this, Registration_2.class);
        // passing the bundle to the intent
        intent.putExtras(bundle);
        // starting the activity by passing the intent
        // to it.
        startActivity(intent);
    }
}