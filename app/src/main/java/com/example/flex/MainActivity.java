package com.example.flex;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flex.model.UserAccount;
import com.google.android.material.tabs.TabLayout;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name    = findViewById(R.id.nameText);
        final Button deleteBut = findViewById(R.id.deleteButt);
        final Button updateBut = findViewById(R.id.updateBut);


        TabLayout tabLayout;

        deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameToDelete = name.getText().toString();
                reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(usernameToDelete)) {
                            DatabaseReference userReference = reference.child("users").child(usernameToDelete);
                            userReference.removeValue().addOnCompleteListener(task -> {
                                Toast.makeText(MainActivity.this, "Deleted the user", Toast.LENGTH_SHORT).show();

                            });
                        } else {
                            Toast.makeText(MainActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        updateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String usernameToUpdate = name.getText().toString();
                reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(usernameToUpdate)) {
                            String newEmail = "temp@email.com";
                            DatabaseReference userReference = reference.child("users").child(usernameToUpdate);
                            userReference.child("email").setValue(newEmail).addOnCompleteListener(task -> {
                                Toast.makeText(MainActivity.this, "Updated the user", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            Toast.makeText(MainActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}