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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");

    FirebaseAuth uAuth;

    private Button LoginBtn;
    private EditText UserEmail, UserPassword;

    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        UserEmail = (EditText) findViewById(R.id.email);
        UserPassword = (EditText) findViewById(R.id.password);
        LoginBtn = (Button) findViewById(R.id.loginButton);
        final Button registerBtn = findViewById(R.id.signupBtn);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = UserEmail.getText().toString();
                String password = UserPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
                else {

                    loadingBar.setTitle("Authenticating User");
                    loadingBar.setMessage("Please wait while we log you in");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);

                    uAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                Intent mainIntent = new Intent(Login.this, MainActivity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(mainIntent);
                                                finish();

                                                Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }
                                            else {
                                                String message = task.getException().getMessage();
                                                Toast.makeText(Login.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();

                                            }
                                        }
                                    });
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
    }
}