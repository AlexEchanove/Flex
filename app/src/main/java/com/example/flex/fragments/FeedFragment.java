package com.example.flex.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.flex.Adapter;
import com.example.flex.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class FeedFragment extends Fragment {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users"); // Update the reference
    FirebaseUser user;
    RecyclerView recyclerView;
    Adapter adapter;

    private String userJoined = "User Just Joined! :)";
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
                Collections.shuffle(data);
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
                    DataSnapshot workoutsSnapshot = snapshot.child("workouts");
                    for (DataSnapshot nextShot : workoutsSnapshot.getChildren()) {
                        if (!nextShot.hasChild("exercise")) {
                            data.add(username + "\n" + userJoined);
                        } else {
                            List<String> exercise = new ArrayList<>();
                            List<String> reps = new ArrayList<>();
                            List<String> weight = new ArrayList<>();
                            DataSnapshot exerciseSnapshot = nextShot.child("exercise");
                            for (DataSnapshot exerciseShot : exerciseSnapshot.getChildren()) {
                                exercise.add(exerciseShot.getValue(String.class));
                            }
                            DataSnapshot repSnapshot = nextShot.child("reps");
                            for (DataSnapshot repShot : repSnapshot.getChildren()) {
                                reps.add(repShot.getValue(String.class));
                            }
                            DataSnapshot weightSnapshot = nextShot.child("weight");
                            for (DataSnapshot weightShot : weightSnapshot.getChildren()) {
                                weight.add(weightShot.getValue(String.class));
                            }
                            int size = exercise.size();
                            String workout = username + "\nExercise:\t\tReps:\t\tWeight:\n";
                            for(int count = 0; count < size; count++){
                                workout = workout + exercise.get(count) + "\t\t\t\t\t"
                                        + reps.get(count) + "\t\t\t\t\t" + weight.get(count) + "\n";
                            }

                            data.add(workout);

                        }
                    }
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
