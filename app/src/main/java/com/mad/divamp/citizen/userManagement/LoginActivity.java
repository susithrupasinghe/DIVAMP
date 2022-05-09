package com.mad.divamp.citizen.userManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.divamp.R;
import com.mad.divamp.citizen.CitizenMainActivity;
import com.mad.divamp.location.LocationMainActivity;
import com.mad.divamp.location.logIn.LogIn1Activity;
import com.mad.divamp.location.registration.Registration1Activity;
import com.mad.divamp.utils.SHA256;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText nicET,passwordET;
    Button register,login;
    String nic,password;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citizen_activity_login);
        sharedpreferences= getSharedPreferences("session", Context.MODE_PRIVATE);

        nicET = findViewById(R.id.nicLogin);
        passwordET = findViewById(R.id.passwordLogin);
        register = findViewById(R.id.registerLogin);
        login = findViewById(R.id.loginLogin);

        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                nic = nicET.getText().toString();
                password = passwordET.getText().toString();

                if(nic.isEmpty()){
                    Toasty.error(LoginActivity.this, "Please enter your nic", Toast.LENGTH_SHORT).show();
                }else if (password.isEmpty()){
                    Toasty.error(LoginActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                }
                else{
                    Login(nic,password);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                registration();
            }
        });

//        nicET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean onFocus) {
//                if (onFocus){
//                    nicET.setText("");
//                }
//            }
//        });


    }

    public void registration(){
        //navigate another page
        startActivity(new Intent(LoginActivity.this, Register1Activity.class));
        finish();
    }



    private void Login(String nic, String password) {

        db.collection("citizen")
                .whereEqualTo("nic", nic)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task){

                        if (task.isSuccessful()){
                            if(task.getResult().size() == 0){
                                Toasty.error(getApplicationContext(), "nic not found", Toast.LENGTH_SHORT, true).show();
                                nicET.setText("");
                                passwordET.setText("");
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if(document.get("hashPassword").toString().equals(SHA256.getHash(password))){
                                    Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString("nic", nic);
                                    editor.putBoolean("loggedin", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent myintent = new Intent(getApplicationContext(), CitizenMainActivity.class);
                                    startActivity(myintent);
                                    break;
                                }
                                else {
                                    Toasty.error(getApplicationContext(), "Password Incorrect", Toast.LENGTH_SHORT, true).show();
                                    passwordET.setText("");
                                    break;
                                }


                            }
                        }else{
                            Toasty.error(getApplicationContext(), " Data retrieval failed", Toast.LENGTH_SHORT, true).show();
                        }
                    }

                });
    }


}