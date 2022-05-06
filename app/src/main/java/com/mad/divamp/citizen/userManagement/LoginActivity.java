package com.mad.divamp.citizen.userManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mad.divamp.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citizen_activity_login);
    }
}