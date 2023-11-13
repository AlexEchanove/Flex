package com.example.flex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.flex.model.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");

    private FirebaseAuth uAuth;

    private ProgressDialog loadingBar;

    private EditText UserEmail, UserPassword, UserConfirmPassword;
    private Button CreateAccBtn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_registration);

        uAuth = FirebaseAuth.getInstance();

        UserEmail = (EditText) findViewById(R.id.email);
//        final EditText username = findViewById(R.id.username);
        UserPassword = (EditText) findViewById(R.id.password);
        UserConfirmPassword = (EditText) findViewById(R.id.password_confirm);

        CreateAccBtn = (Button) findViewById(R.id.create_button);
        final Button exitBtn = findViewById(R.id.exit_button);
        loadingBar = new ProgressDialog(this);

        CreateAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = UserEmail.getText().toString();
//              final String usernameTxt = username.getText().toString();
                String password = UserPassword.getText().toString();
                String confPassword = UserConfirmPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confPassword)) {
                    Toast.makeText(SignUp.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else if(!password.equals(confPassword)) {
                    Toast.makeText(SignUp.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else {

                    loadingBar.setTitle("Creating new User");
                    loadingBar.setMessage("Please wait while we create your account");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);

                    uAuth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                String username = getUsername(email);
                                                reference.child("users").child(username).child("email").setValue(email);
                                                reference.child("users").child(username).child("username").setValue(username);
                                                reference.child("users").child(username).child("password").setValue(password);

                                                sendUserToLogin();

                                                Toast.makeText(SignUp.this, "User Authenticated Successfully", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            else {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(SignUp.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                        }
                                    });

//                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.hasChild(usernameTxt)) {
//                                Toast.makeText(SignUp.this, "Account with Username Already In Use", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//
//                                reference.child("users").child(usernameTxt).child("email").setValue(emailTxt);
//                                reference.child("users").child(usernameTxt).child("username").setValue(usernameTxt);
//                                reference.child("users").child(usernameTxt).child("password").setValue(passwordTxt);
//
//                                Toast.makeText(SignUp.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
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

    private void sendUserToLogin() {
        Intent setupIntent = new Intent(SignUp.this, Login.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private String getUsername(String email) {

        return (email.substring(0, email.indexOf("@")));

    }

}
