package com.example.flex;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flex.model.Workout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {

    EditText exercise;
    EditText weight;
    EditText reps;
    Button exerciseButton;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://flex-f72ad-default-rtdb.firebaseio.com");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        exercise = findViewById(R.id.exercise);
        weight = findViewById(R.id.weight);
        reps = findViewById(R.id.reps);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String exerciseText = exercise.toString();
                String weightText   = weight.toString();
                String repsText     = reps.toString();

                // Add to CRUD database
                ArrayList<String> exercisesList = new ArrayList<String>();
                exercisesList.add(exerciseText);
                ArrayList <String> repsList = new ArrayList<String>();
                repsList.add(repsText);
                ArrayList <String> weightsList = new ArrayList<String>();
                weightsList.add(weightText); //this needs to be looped when multi adds are added
                Workout curr = new Workout(exercisesList,repsList,weightsList);
                //curr.setUser(); needs firebase auth
                reference.child("workouts").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                            reference.child("workouts").child(repsText).child("reps").setValue(repsList);
                            reference.child("workouts").child(exerciseText).child("exercises").setValue(exercisesList);
                            reference.child("workouts").child(weightText).child("weights").setValue(weightsList);
                            finish();

                    }
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
            });
            }
        });
    }
}
