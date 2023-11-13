package com.example.flex.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.flex.Login;
import com.example.flex.R;
import com.google.android.material.imageview.ShapeableImageView;

public class ProfileFragment extends Fragment {
    private static final int CAMERA_REQUEST_CODE = 1;
    private int CAMERA_ENABLE_FEATURE = 1;
    private ShapeableImageView profileImage;
    private Button photoButton;
    private Button cameraEnableFeature;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        cameraEnableFeature = view.findViewById(R.id.cameraButton);
        profileImage = view.findViewById(R.id.profileImage);
        photoButton  = view.findViewById(R.id.photoButton);
        cameraEnableFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CAMERA_ENABLE_FEATURE == 1) {
                    CAMERA_ENABLE_FEATURE = 0 ;
                    cameraEnableFeature.setText("Enable Camera");
                } else {
                    CAMERA_ENABLE_FEATURE = 1;
                    cameraEnableFeature.setText("Disable Camera");
                }
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CAMERA_ENABLE_FEATURE == CAMERA_REQUEST_CODE) {
                    openCamera();
                } else {
                    Toast.makeText(requireContext(), "Camera is not enabled", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profileImage.setImageBitmap(photo);
        }
    }
}