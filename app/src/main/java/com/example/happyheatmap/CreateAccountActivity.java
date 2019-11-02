package com.example.happyheatmap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateAccountActivity extends AppCompatActivity {
    Button createAccButton;
    EditText userName;
    EditText password;
    boolean check=true;
    boolean checkRet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        createAccButton = (Button)findViewById(R.id.button2);
        userName = (EditText)findViewById(R.id.nameField);
        password = (EditText)findViewById(R.id.passField);
        checkRet = false;

        createAccButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                writeAccount();
            }
        });
    }

    protected void writeAccount(){
        checkRet = false;

        //RUN CHECKS FOR VALID USER AND PASSWORD
        Editable name = userName.getText();
        Editable pass = password.getText();

        if (name.length() < 6) {
            String value = "Please enter a valid username that is at least 6 characters long";
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
            return;
        } else if(pass.length() < 6){
            String value = "Please enter a valid password that is at least 6 characters long";
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
        }

        //RUN CHECKS FOR NON-TAKEN USERNAME
        dbReadCheck(new dataCallback() {
            @Override
            public void onCallback(boolean value) {
                checkRet = value;
            }
        }, name.toString());

        if (!checkRet) {
            String value = "That username is already taken. Please try another.";
            Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(name.toString());

        myRef.setValue(password.toString());
        // [END write_message]
    }

    public interface dataCallback {
        void onCallback(boolean value);
    }

    protected void dbReadCheck(final dataCallback myCallback, final String nameToCheck){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(nameToCheck);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (!dataSnapshot.exists()) {
                    String value = "The key is null. This is valid";
                    Toast.makeText(getApplicationContext(), value, Toast.LENGTH_LONG).show();
                    //String value = dataSnapshot.getValue(String.class);
                    myCallback.onCallback(true);
                } else {
                    myCallback.onCallback(false);
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
        });


    }
}
