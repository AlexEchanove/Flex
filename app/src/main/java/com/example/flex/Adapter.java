package com.example.flex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    List<String> data;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth uAuth = FirebaseAuth.getInstance();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");

    public Adapter(List<String> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        uAuth.getCurrentUser().getEmail();
//        holder.imageView.setImageURI(storageReference.child("images/" + ));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
