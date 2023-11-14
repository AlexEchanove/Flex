package com.example.flex.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.flex.R;
import com.example.flex.model.UserAccount;
import com.example.flex.model.Workout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class WorkoutFragment extends Fragment implements View.OnClickListener {

    Button addBtn;
    Button saveBtn;
    LinearLayout mLayout;

    private DatabaseReference reference;
    FirebaseUser user;

    private EditText activity, reps, weight;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_workout, container, false);
        addBtn = (Button) v.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(this);
        saveBtn = (Button) v.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        mLayout = (LinearLayout) v.findViewById(R.id.linearLayout);
        activity = (EditText) v.findViewById(R.id.activityText1);
        reps = (EditText) v.findViewById(R.id.repsText1);
        weight = (EditText) v.findViewById(R.id.weightText1);
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");
        user = FirebaseAuth.getInstance().getCurrentUser();
        return v;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.addBtn) {
        /* textCnt++;
        EditText editText = new EditText(getContext());
        editText.setLayoutParams(new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                45));
        editText.setPadding(0,textCnt*60,0,0);
        mLayout.addView(editText); */
        } else if (view.getId() == R.id.saveBtn) {
            Workout curr = new Workout();
            curr.setUser(user.getUid());
            curr.setTimeOfWorkout(new Date());
            ArrayList<String> exercises = new ArrayList<>(); //have to add text boxes to fill the lists
            exercises.add(activity.getText().toString());
            curr.setExercise(exercises);
            ArrayList<String> repsList = new ArrayList<>();
            repsList.add(reps.getText().toString());
            curr.setReps(repsList);
            ArrayList<String> weightList = new ArrayList<>();
            weightList.add(weight.getText().toString());

            curr.setWeight(weightList);
            System.out.println(user);
            reference.child("users").child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child(curr.getTimeOfWorkout().toString()).setValue(curr);
        }
    }
}