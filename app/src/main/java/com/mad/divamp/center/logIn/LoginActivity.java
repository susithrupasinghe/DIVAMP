package com.mad.divamp.center.logIn;
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

import com.mad.divamp.admin.AdminMainActivity;
import com.mad.divamp.center.CenterMainActivity;
import com.mad.divamp.utils.SHA256;

import es.dmoral.toasty.Toasty;

import android.os.Bundle;

import com.mad.divamp.R;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button loginbtn;
    TextView textcenterid, textpassword;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_activity_login);
        sharedpreferences = getSharedPreferences("session", Context.MODE_PRIVATE);

        loginbtn = findViewById(R.id.loginbtn);
        textcenterid = findViewById(R.id.textcenterid);
        textpassword = findViewById(R.id.textpassword);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login(textcenterid.getText().toString(), textpassword.getText().toString());
                // do something when the corky2 is clicked
            }
        });
    }

    private void Login(String centerid, String password) {


        db.collection("center")
                .whereEqualTo("email", centerid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            if (task.getResult().size() == 0) {
                                Toasty.error(getApplicationContext(), "invalid centerid or password", Toast.LENGTH_SHORT, true).show();
                                textcenterid.setText("");
                                textpassword.setText("");
                            }

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if (document.get("password").toString().equals(SHA256.getHash(password))) {
                                    Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
                                    SharedPreferences.Editor editor = sharedpreferences.edit();

                                    editor.putString("email", centerid);
                                    editor.putBoolean("loggedin", true);
                                    editor.commit();
                                    editor.apply();
                                    Intent myintent = new Intent(getApplicationContext(), CenterMainActivity.class);
                                    startActivity(myintent);
                                    break;
                                } else {
                                    Toasty.error(getApplicationContext(), "Password Incorrect", Toast.LENGTH_SHORT, true).show();
                                    textpassword.setText("");
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
