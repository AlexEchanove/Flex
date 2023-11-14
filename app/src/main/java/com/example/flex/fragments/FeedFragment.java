package com.example.flex.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.flex.Adapter;
import com.example.flex.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users"); // Update the reference
    FirebaseUser user;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_fragmemnt, container, false);

        recyclerView = view.findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        user = FirebaseAuth.getInstance().getCurrentUser();

        getData(reference, new OnGetDataListener() {
            @Override
            public void onSuccess(List<String> data) {
                adapter = new Adapter(data);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }

    private void getData(DatabaseReference reference, final OnGetDataListener listener) {
        final List<String> data = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String username = snapshot.child("username").getValue(String.class);
                    data.add(username);
                }
                listener.onSuccess(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public interface OnGetDataListener {
        void onSuccess(List<String> data);
    }
}
