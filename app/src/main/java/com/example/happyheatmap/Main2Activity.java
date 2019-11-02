package com.example.happyheatmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Main2Activity extends AppCompatActivity {
    Button buttontest;
    Button buttontest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        buttontest2 = (Button)findViewById(R.id.button3);
        buttontest = (Button)findViewById(R.id.dbTest);
        buttontest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                dbWrite();
            }
        });
        buttontest2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                dbRead();
            }
        });
    }

    public void dbWrite(){
        // [START write_message]
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Big");

        myRef.setValue("Bitches");
        // [END write_message]
    }

    public void dbRead(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Bad");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
    });
    }

}
