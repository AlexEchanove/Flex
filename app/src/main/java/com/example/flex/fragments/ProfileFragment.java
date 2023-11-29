package com.example.flex.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.text.TextUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

//import com.example.flex.Manifest;
import android.Manifest;
import com.example.flex.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileFragment extends Fragment {

    private static final int CAMERA_PERM_CODE = 102;
    private static final int CAMERA_REQUEST_CODE = 103;

    String currentPhotoPath;

    private ImageView imageView;

    private Button galleryBtn, cameraBtn;

    private StorageReference sReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://flex-f72ad.appspot.com");

    FirebaseAuth uAuth;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");


    private Button UpdateBtn, DeleteBtn;
    private EditText UserEmail, Username, UserPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        cameraBtn = view.findViewById(R.id.cameraButton);
        imageView = view.findViewById(R.id.imageView);
        galleryBtn  = view.findViewById(R.id.galleryButton);

        cameraBtn.setOnClickListener((v) -> {askCameraPermissions();});


        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 105);
//                if (hasPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                } else {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 103);
//                }
            }
        });
        return view;
    }

    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        }
        else {
            dispatchTakePictureIntent();
        }
    }

    private boolean hasPermission(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
            else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 103) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && requestCode == Activity.RESULT_OK) {

//            Bitmap image = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(image);

            File f = new File(currentPhotoPath);
            imageView.setImageURI(Uri.fromFile(f));
            Log.d("tag", "Absolute Url of Image is " + Uri.fromFile(f));

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            getActivity().sendBroadcast(mediaScanIntent);

            uploadImageToFirebase(f.getName(), contentUri);
        }

        if (requestCode == 105 && requestCode == Activity.RESULT_OK) {
            Uri contentUri = data.getData();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "." + getFileExt(contentUri);
            Log.d("tag", "onActivityResult: Gallery Image Uri: " + imageFileName);
            imageView.setImageURI(contentUri);

            uploadImageToFirebase(imageFileName, contentUri);
        }
    }

    private void uploadImageToFirebase(String name, Uri contentUri) {
        final StorageReference image = sReference.child("images/" + name);
        image.putFile(contentUri).addOnSuccessListener((OnSuccessListener) (taskSnapshot) -> {
            image.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                Log.d("tag", "onSuccess: Upload Image URL is " + uri.toString());
                    }
                });

                Toast.makeText(getActivity(), "Image is Uploaded", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener((e) -> {
                Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
        });
    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
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

                if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(username) && TextUtils.isEmpty(userPassword)) {
                    uAuth.getCurrentUser().updateEmail(email);
                    Toast.makeText(getActivity(), "Successfully updated email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) && TextUtils.isEmpty(userPassword)) {
//                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.hasChild(usernameToUpdate)) {
//                                String newEmail = "temp@email.com";
//                                DatabaseReference userReference = reference.child("users").child(usernameToUpdate);
//                                userReference.child("email").setValue(newEmail).addOnCompleteListener(task -> {
//                                    Toast.makeText(getActivity(), "Updated the user", Toast.LENGTH_SHORT).show();
//                                });
//                            } else {
//                                Toast.makeText(getActivity(), "User doesn't exist", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                });



                }
            }
        });
    }
}