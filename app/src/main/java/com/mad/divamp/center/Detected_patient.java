package com.mad.divamp.center;
import com.mad.divamp.MainActivity;
import com.mad.divamp.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.location.Registration_2;
import com.mad.divamp.location.location;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Detected_patient extends AppCompatActivity {
    String BatchId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText BatchIDetc;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected_patient);

        BatchIDetc = findViewById(R.id.batchID);

        btnFinish = (Button) findViewById(R.id.finishbtn);

        btnFinish.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                BatchId = BatchIDetc.getText().toString();
                // validating the text fields if empty or not.
                if (TextUtils.isEmpty(BatchId)) {
                    BatchIDetc.setError("Please enter Batch Id");
                } else{
                    // calling method to add data to Firebase Firestore.
                   // Toast.makeText(Detected_patient.this, "Your Batch ID has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                    saveData(BatchId);
                }
            }

        });
    }
    private void saveData(String batchId){

        //CollectionReference dbCenter = db.collection("center");

        //center center1 = new center(batchId);
        Map<String, Object> center1 = new HashMap<>();
        center1.put("BatchId", batchId);

        db.collection("center").add(center1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(Detected_patient.this, "Your Batch ID has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(Detected_patient.this, "Fail to add Batch ID \n" + e, Toast.LENGTH_SHORT).show();
            }
        });


        Map<String, Object> location = new HashMap<>();
        location.put("NIC", "99087");
        location.put("last", "Lovelace");
        location.put("born", 1815);

//        Intent intent = new Intent(Registration_2.this, Registration_3.class);

// Add a new document with a generated ID
//        db.collection("center")
//                .add(center)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("Succuss", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("Fail", "Error adding document", e);
//                    }
//                });
    }
}