package com.example.flex.fragments;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.content.Intent;
import android.graphics.Bitmap;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import android.Manifest;

import com.bumptech.glide.Glide;
import com.example.flex.Login;
import com.example.flex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.ProgressDialog;
import java.util.UUID;


public class ProfileFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private FirebaseStorage storage;
    private static final int CAMERA_REQUEST_CODE = 103;

    String currentPhotoPath;

    private ImageView imageView;

    private Button galleryBtn, cameraBtn;

    private StorageReference sReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://flex-f72ad.appspot.com");

    private FirebaseAuth uAuth = FirebaseAuth.getInstance();
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");

    private String username = getUsername(uAuth.getCurrentUser().getEmail());



    private Button UpdateBtn, DeleteBtn;
    private EditText UserEmail, Username, UserPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        storage = FirebaseStorage.getInstance();

        cameraBtn = view.findViewById(R.id.cameraButton);
        imageView = view.findViewById(R.id.imageView);
        galleryBtn  = view.findViewById(R.id.galleryButton);
        imageView = view.findViewById(R.id.imageView);
        UpdateBtn = view.findViewById(R.id.updateButton);
        DeleteBtn = view.findViewById(R.id.deleteButton);
        UserEmail = view.findViewById(R.id.editEmail);
        Username = view.findViewById(R.id.editUsername);
        UserPassword = view.findViewById(R.id.editPassword);


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = UserEmail.getText().toString();
                String userUsername = Username.getText().toString();
                String userPassword = UserPassword.getText().toString();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(userUsername) || TextUtils.isEmpty(userPassword)) {
                    Toast.makeText(getActivity(), "Please fill out all fields!", Toast.LENGTH_SHORT).show();

                } else {

                    reference.child("users").child(username).child("username").setValue(userUsername);
                    reference.child("users").child(username).child("password").setValue(userPassword);
                    uAuth.getCurrentUser().updatePassword(userPassword);
                    Toast.makeText(getActivity(), "Updated the user", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("users").child(username).removeValue();
                uAuth.getCurrentUser().delete();
                Intent setupIntent = new Intent(getContext(), Login.class);
                setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(setupIntent);
                Toast.makeText(getActivity(), "Deleted user " + username, Toast.LENGTH_SHORT).show();
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 105);
                if (hasPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 103);
                }
            }
        });

        return view;
    }

    private void askCameraPermissions() {

        if(checkCameraPermissions() && isCameraEnabled()) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {

            }
        } else {
            requestCameraPermission();
        }

    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(
                getActivity(),
                new String[]{Manifest.permission.CAMERA},
                1
        );
    }

    private boolean checkCameraPermissions() {
        if (checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private boolean isCameraEnabled() {
        String permission = Manifest.permission.CAMERA;
        int result = ContextCompat.checkSelfPermission(getContext(), permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
            String fileName = "image_" + System.currentTimeMillis() + ".jpg";
            StorageReference storageRef = storage.getReference().child("images").child(fileName);
            // Convert the Bitmap to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageData = baos.toByteArray();
            // Upload the image to Firebase Storage
            UploadTask uploadTask = storageRef.putBytes(imageData);
            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();
                                Log.d("ProfileFragment", "Download URL: " + downloadUrl);
                                Glide.with(ProfileFragment.this)
                                        .load(downloadUrl)
                                        .into(imageView);
                                Toast.makeText(getActivity(), "Image uploaded successfully", Toast.LENGTH_SHORT)
                                        .show();
                                reference.child("users").child(username).child("profilePic").setValue(downloadUrl);

                            }
                        });
                    } else {
                        Exception exception = task.getException();
                    }
                }
            });
        } else if (requestCode == 105 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);

                ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference ref = storageRef.child("images/" + UUID.randomUUID().toString());

                Uri filePath = data.getData();
                ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Image Uploaded!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }

    }

    private String getUsername(String email) {

        return (email.substring(0, email.indexOf("@")));

    }

//
    private boolean hasPermission(Context context, String permission) {
        return checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
