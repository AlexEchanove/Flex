package com.example.flex;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WorkoutActivity extends AppCompatActivity {

    EditText exercise;
    EditText weight;
    EditText reps;
    Button exerciseButton;

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

            }
        });
    }
}
