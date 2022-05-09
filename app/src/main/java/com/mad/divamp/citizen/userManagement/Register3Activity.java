package com.mad.divamp.citizen.userManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mad.divamp.R;

public class Register3Activity extends AppCompatActivity {
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citizen_activity_register3);

        login = findViewById(R.id.loginNow);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register3Activity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}