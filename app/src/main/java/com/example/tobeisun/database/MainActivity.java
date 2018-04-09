package com.example.tobeisun.database;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText a ;
    Button b;
    DatabaseReference dataa ;
    List <Messages> storedmessage; //the list will store the messages entered


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataa= FirebaseDatabase.getInstance().getReference("Messages") ;


        a = (EditText) findViewById(R.id.editTextMessage);
        b = (Button) findViewById(R.id.buttonsave);
        storedmessage= new ArrayList<>(); //saves the message in an array

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savename();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();




        dataa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {  //ondatachange will be executed anytime you change something in the database
           // also reads the values in the specified "database" , which is "data" .. kind of takes a snapshot of them

                  storedmessage.clear(); //clear it if something was previously there as the datasnapshot would contain all messages
                for(DataSnapshot d:dataSnapshot.getChildren())  //an enhanced forloop
                {
                            Messages m= d.getValue(Messages.class) ;

                            storedmessage.add(m) ;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {  //oncancelled will be used when there is an error in the database

            }
        }) ;
    }

    private void savename()
        {
            String message = a.getText().toString().trim();


            if(!TextUtils.isEmpty(message))
            {

                Messages d =new Messages(message) ;
                dataa.child(message).setValue(d) ;
                Toast.makeText(this,"Message saved",Toast.LENGTH_LONG).show();


            }

            else
            {
                Toast.makeText(this,"Empty field, please enter a name", Toast.LENGTH_LONG).show();
            }

        }



}
