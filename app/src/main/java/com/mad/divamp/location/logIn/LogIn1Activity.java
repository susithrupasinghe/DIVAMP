package com.mad.divamp.location.logIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.R;

import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.admin.AdminMainActivity;
import com.mad.divamp.location.LocationMainActivity;
import com.mad.divamp.location.registration.Registration1Activity;
import com.mad.divamp.utils.SHA256;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


public class LogIn1Activity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText emailEtd,passwordEtd;
    Button logInBtn,registerBtn;
    SharedPreferences sharedpreferences;
    String email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity_log_in);
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);

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
                    Toasty.error(LogIn1Activity.this, "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }else{
                    Login(emailEtd.getText().toString(), passwordEtd.getText().toString());
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


    private void Login(String email, String password) {

        db.collection("location")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){

                        if (task.isSuccessful()){
                            if(task.getResult().size() == 0){
                                Toasty.error(getApplicationContext(), "Email not found", Toast.LENGTH_SHORT, true).show();
                                emailEtd.setText("");
                                passwordEtd.setText("");
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("password").toString().equals(SHA256.getHash(password))){
                                    Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    //get data from DB


                                    editor.putString("email", email);
                                    editor.putBoolean("loggedin", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent myintent = new Intent(getApplicationContext(), LocationMainActivity.class);
                                    startActivity(myintent);
                                    break;
                                }
                                else {
                                    Toasty.error(getApplicationContext(), "Password Incorrect", Toast.LENGTH_SHORT, true).show();
                                    passwordEtd.setText("");
                                    break;
                                }


                            }
                        }else{
                            Toasty.error(getApplicationContext(), " Data retrieval faild", Toast.LENGTH_SHORT, true).show();
                        }
                    }

                });
    }
}