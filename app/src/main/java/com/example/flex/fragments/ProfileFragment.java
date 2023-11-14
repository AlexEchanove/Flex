package com.example.flex.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flex.Login;
import com.example.flex.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

    FirebaseAuth uAuth;

    private Button UpdateBtn, DeleteBtn, CameraBtn, LocationBtn;
    private EditText UserEmail, Username, UserPassword;

    private ProgressDialog loadingBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        uAuth = FirebaseAuth.getInstance();

        UserEmail = (EditText) view.findViewById(R.id.editEmail);
        Username = (EditText) view.findViewById(R.id.editUsername);
        UserPassword = (EditText) view.findViewById(R.id.editPassword);
        UpdateBtn = (Button) view.findViewById(R.id.updateButton);
        DeleteBtn = view.findViewById(R.id.deleteButton);

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = UserEmail.getText().toString();
                String username = Username.getText().toString();
                String userPassword = UserPassword.getText().toString();

                if(!TextUtils.isEmpty(email) && TextUtils.isEmpty(username)) {

//                    Toast.makeText(Login.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}