package com.example.flex.fragments;

import android.graphics.Color;
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
    LinearLayout mLayout,mLayout2,mLayout3;

    private DatabaseReference reference;
    FirebaseUser user;

    private ArrayList<EditText>  activity, reps, weight;

    int cnt;
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
        mLayout2 = (LinearLayout) v.findViewById(R.id.linearLayout2);
        mLayout3 = (LinearLayout) v.findViewById(R.id.linearLayout3);
        activity = new ArrayList<EditText>();
        reps = new ArrayList<EditText>();
        weight = new ArrayList<EditText>();
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");
        user = FirebaseAuth.getInstance().getCurrentUser();
        cnt = 0;
        return v;
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.addBtn) {
            generateTextFields();

        } else if (view.getId() == R.id.saveBtn) {
            saveWorkout(getTexts());
        }
    }
    public void generateTextFields(){
        cnt++;
        if(cnt < 10) {
            EditText editText  = new EditText(getContext());
            editText = setFormatting(editText, "Activity");
            editText.setId((cnt*3) - 2);
            activity.add(editText);
            mLayout.addView(editText);
            editText  = new EditText(getContext());
            editText = setFormatting(editText, "Reps");
            editText.setId((cnt*3) - 1);
            reps.add(editText);
            mLayout2.addView(editText);
            editText  = new EditText(getContext());
            editText = setFormatting(editText, "Weight");
            editText.setId(cnt*3);
            weight.add(editText);
            mLayout3.addView(editText);
        }
    }
    public EditText setFormatting(EditText currText, String hint){
        currText.setLayoutParams(new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                150));
        currText.setTextColor(Color.WHITE);
        currText.setHintTextColor(Color.WHITE);
        currText.setEms(10);
        currText.setHint(hint);
        return currText;
    }
    public Workout getTexts(){
        Workout curr = new Workout();
        curr.setUser(user.getUid());
        curr.setTimeOfWorkout(new Date());
        ArrayList<String> exercises = new ArrayList<>(); //have to add text boxes to fill the lists
        ArrayList<String> repsList = new ArrayList<>();
        ArrayList<String> weightList = new ArrayList<>();
        int i = 0;

        while(i< activity.size()){
            EditText currAct = activity.get(i);
            EditText currWeight = weight.get(i);
            EditText currReps = reps.get(i);
            exercises.add(currAct.getText().toString());
            repsList.add(currReps.getText().toString());
            weightList.add(currWeight.getText().toString());
            i++;
        }

        curr.setExercise(exercises);
        curr.setReps(repsList);
        curr.setWeight(weightList);
        return curr;
    }
    public void saveWorkout(Workout curr){
        reference.child("users").child(user.getEmail().substring(0, user.getEmail().indexOf("@"))).child("workouts").child(curr.getTimeOfWorkout().toString()).setValue(curr);
    }
}