package com.mad.divamp.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mad.divamp.R;

import android.os.Bundle;

import java.util.ArrayList;

public class CustomerHistory extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
//    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Customer> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_history);

        recyclerView = findViewById(R.id.customerList);
        CollectionReference dbCustomer = db.collection("customer");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
//        MyAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);
//        dbCustomer

    }
}