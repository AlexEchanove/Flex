package com.example.flex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.flex.fragments.WorkoutFragment;
import com.example.flex.model.UserAccount;
import com.example.flex.model.Workout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class TestWorkoutFormat {
    private DatabaseReference reference;

    @Test
    public void testWorkout() {
        Workout curr = new Workout();
        ArrayList<String> test = new ArrayList<String>();
        ArrayList<String> test2 = new ArrayList<String>();
        test.add("123");
        test.add("1234");
        curr.setWeight(test);
        curr.setReps(test);
        curr.setExercise(test);
        curr.setTimeOfWorkout(new Date());
        Workout curr2 = new Workout();
        curr2.setWeight(test);
        curr2.setReps(test);
        curr2.setExercise(test);
        curr.setTimeOfWorkout(new Date());
        assertNotEquals(curr,curr2); //fail cause of date
        curr2 = new Workout();
        curr2.setWeight(test2);
        curr2.setReps(test2);
        curr2.setExercise(test2);
        assertNotEquals(curr.getReps(),curr2.getReps());
        curr.setReps(test2);
        assertTrue(curr.getReps() == curr2.getReps());
    }

    @Test
    public void testValidity() {
        WorkoutFragment test = new WorkoutFragment();
        assertTrue(test.checkInputValidity("bench","1","1"));
        assertTrue(test.checkInputValidity("squat","100","525.5"));
        assertFalse(test.checkInputValidity("10","bench","525.5"));
        assertFalse(test.checkInputValidity("10","123","bench"));
    }

}
