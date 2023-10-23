package com.example.flex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.flex.model.UserAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        final EditText email = findViewById(R.id.email);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText confPassword = findViewById(R.id.password_confirm);

        final Button createUserBtn = findViewById(R.id.create_button);
        final Button exitBtn = findViewById(R.id.exit_button);

        createUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String emailTxt = email.getText().toString();
                final String usernameTxt = username.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String confPasswordTxt = confPassword.getText().toString();

                if(emailTxt.isEmpty() || usernameTxt.isEmpty() || passwordTxt.isEmpty() || confPasswordTxt.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(!passwordTxt.equals(confPasswordTxt)) {
                    Toast.makeText(SignUp.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else {

                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(usernameTxt)) {
                                Toast.makeText(SignUp.this, "Account with Username Already In Use", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                reference.child("users").child(usernameTxt).child("email").setValue(emailTxt);
                                reference.child("users").child(usernameTxt).child("username").setValue(usernameTxt);
                                reference.child("users").child(usernameTxt).child("password").setValue(passwordTxt);

                                Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }

}
