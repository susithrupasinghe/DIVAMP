package com.mad.divamp.citizen.userManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;
import com.mad.divamp.citizen.models.Citizen;
import com.mad.divamp.location.registration.Registration2Activity;
import com.mad.divamp.location.registration.Registration3Activity;

import java.util.Arrays;

import es.dmoral.toasty.Toasty;

public class Register2Activity extends AppCompatActivity {

    String email,contactNo,province,district,address1,address2,firstName,lastName,nic,birthday,gender,hashPassword;
    EditText emailEt,contactNoEt,address1Et,address2Et;
    Spinner provinceSp,districtSp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Button backBtn,finishBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citizen_activity_register2);

        emailEt = findViewById(R.id.Email);
        contactNoEt = findViewById(R.id.contactNo);
        address1Et = findViewById(R.id.address1);
        address2Et = findViewById(R.id.address2);
        provinceSp = findViewById(R.id.Province);
        districtSp = findViewById(R.id.district);
        backBtn = findViewById(R.id.reg2_back_btn);
        finishBtn = findViewById(R.id.reg2_finish_btn);

        Intent intent =getIntent();

        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        nic = intent.getStringExtra("nic");
        birthday = intent.getStringExtra("birthday");
        gender = intent.getStringExtra("gender");
        hashPassword = intent.getStringExtra("password");

        String[] provinceArray = getResources().getStringArray(R.array.province);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,provinceArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSp.setAdapter(adapter);

        provinceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected = adapterView.getItemAtPosition(i).toString();

                switch (selected){
                    case "Western Province":{
                        String[] districtArray = getResources().getStringArray(R.array.western);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "Eastern Province":{
                        String[] districtArray = getResources().getStringArray(R.array.eastern);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "Southern Province":{
                        String[] districtArray = getResources().getStringArray(R.array.southern);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "Northern Province":{
                        String[] districtArray = getResources().getStringArray(R.array.northern);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "North Central Province":{
                        String[] districtArray = getResources().getStringArray(R.array.northCentral);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "North Western Province":{
                        String[] districtArray = getResources().getStringArray(R.array.northWestern);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "Uva Province":{
                        String[]  districtArray = getResources().getStringArray(R.array.uva);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "Sabaragamuwa Province":{
                        String[]  districtArray = getResources().getStringArray(R.array.sabaragamuwa);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                    case "Central Province":{
                        String[] districtArray = getResources().getStringArray(R.array.central);
                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,districtArray);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        districtSp.setAdapter(adapter);
                        break;
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEt.getText().toString();
                contactNo = contactNoEt.getText().toString();
                address1 = address1Et.getText().toString();
                address2 = address2Et.getText().toString();
                province = provinceSp.getSelectedItem().toString();
                district = districtSp.getSelectedItem().toString();
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if (TextUtils.isEmpty(email)){
                    emailEt.setError("please enter email");
                }else if(!email.matches(Expn) && email.length() > 0){
                    emailEt.setError("please enter valid email");
                }else if (TextUtils.isEmpty(contactNo)){
                    contactNoEt.setError("please enter contact number");
                }else if (TextUtils.isEmpty(address1)){
                    address1Et.setError("please enter Address");
                }else if (TextUtils.isEmpty(address2)){
                    address2Et.setError("please enter Address");
                }else if (TextUtils.isEmpty(province)){
                    Toasty.success(Register2Activity.this, "Please Select Province", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(district)){
                    Toasty.success(Register2Activity.this, "Please Select District", Toast.LENGTH_SHORT).show();
                }else {
                    addData(email,contactNo,province,district,address1,address2,firstName,lastName,nic,birthday,gender,hashPassword);
                }



            }
        });


    }

    private void addData(String email, String contactNo, String province, String district,
                         String address1, String address2, String firstName, String lastName,
                         String nic,String birthday,String gender,String hashPassword
                         ){
        CollectionReference dbCitizen = db.collection("citizen");
        //create a citizen object
        Citizen citizen = new Citizen();
        citizen.setEmail(email);
        citizen.setContactNo(contactNo);
        citizen.setProvince(province);
        citizen.setDistrict(district);
        citizen.setAddress1(address1);
        citizen.setAddress2(address2);
        citizen.setFirstName(firstName);
        citizen.setLastName(lastName);
        citizen.setNic(nic);
        citizen.setBirthday(birthday);
        citizen.setGender(gender);
        citizen.setHashPassword(hashPassword);
        citizen.setImgurl("https://i.pravatar.cc/150?u=a042581f4e29026704d");
        citizen.setStatus("Healthy");


        dbCitizen.add(citizen).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // after the data addition is successful
                // we are displaying a success toast message.
                Toasty.success(Register2Activity.this, "Your details has been added", Toast.LENGTH_SHORT).show();
                //navigate another page
                startActivity(new Intent(Register2Activity.this, Register3Activity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toasty.success(Register2Activity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }
}