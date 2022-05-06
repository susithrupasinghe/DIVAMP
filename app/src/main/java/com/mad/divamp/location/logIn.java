package com.mad.divamp.location;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class logIn extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText emailEtd,passwordEtd;
    Button logInBtn,register;

    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailEtd = findViewById(R.id.logInemail);
        passwordEtd = findViewById(R.id.logInpassword);
        logInBtn = findViewById(R.id.LogIn);
        register = findViewById(R.id.register);

        logInBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                email = emailEtd.getText().toString();
                password = passwordEtd.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(logIn.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }else{
//                    db.collection('')
                }
            }
        });

    }
}