package com.example.flex;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flex.model.UserAccount;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");

    final Button deleteBut = findViewById(R.id.deleteButt);
    final Button updateBut = findViewById(R.id.updateBut);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameToDelete = "mk27";
                DatabaseReference userReference = reference.child("users").child(usernameToDelete);
                userReference.removeValue().addOnCompleteListener(task -> {


                });
            }
        });


        updateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEmail = "ir20";
                String usernameToUpdate = "mk27"; // Replace with the appropriate username

                DatabaseReference userReference = reference.child("users").child(usernameToUpdate);
                userReference.child("email").setValue(newEmail).addOnCompleteListener(task -> {


                });
            }
        });

    }
}