package com.example.flex;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flex.model.UserAccount;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText regEmail, regUsername, regPassword;
    Button createBtn, exitBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        regEmail = findViewById(R.id.email);
        regUsername = findViewById(R.id.username);
        regPassword = findViewById(R.id.password);
        createBtn = findViewById(R.id.create_button);
        exitBtn = findViewById(R.id.exit_button);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String email = regEmail.getText().toString();
                String username = regUsername.getText().toString();
                String password = regPassword.getText().toString();

                UserAccount userAccount = new UserAccount(email, username, password);

                reference.setValue("Changed");
            }
        });

    }

}
