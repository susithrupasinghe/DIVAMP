package com.mad.divamp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import  com.mad.divamp.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mad.divamp.utils.SHA256;

import es.dmoral.toasty.Toasty;


public class AdminLoginActivity extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button loginbtn;
    TextView emailtxt, passwordtxt;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);

        loginbtn = findViewById(R.id.admin_login_btn);
        emailtxt = findViewById(R.id.admin_login_email);
        passwordtxt = findViewById(R.id.admin_login_password);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailtxt.getText().toString().isEmpty()){
                    emailtxt.setError("Please enter your email");
                }
                else if(passwordtxt.getText().toString().isEmpty()){
                    passwordtxt.setError("Please enter your password");
                }
                else {
                    Login(emailtxt.getText().toString(), passwordtxt.getText().toString());
                }

            }
        });

    }

    private void Login(String email, String password){



        db.collection("admin")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if(task.getResult().size() == 0){
                                Toasty.error(getApplicationContext(), "Email not found", Toast.LENGTH_SHORT, true).show();
                                emailtxt.setText("");
                                passwordtxt.setText("");
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("password").toString().equals(SHA256.getHash(password))){
                                    Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString("email", email);
                                    editor.putBoolean("loggedin", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent myintent = new Intent(getApplicationContext(), AdminMainActivity.class);
                                    startActivity(myintent);
                                    break;
                                }
                                else {
                                    Toasty.error(getApplicationContext(), "Password Incorrect", Toast.LENGTH_SHORT, true).show();
                                    passwordtxt.setText("");
                                    break;
                                }


                            }
                        } else {

                            Toasty.error(getApplicationContext(), " Data retrieval faild", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });



    }


}