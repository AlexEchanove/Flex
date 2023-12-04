package com.example.flex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.widget.EditText;

import com.example.flex.fragments.WorkoutFragment;
import com.example.flex.model.UserAccount;
import com.example.flex.model.Workout;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class TestWorkoutFormat {
    @Test
    public void testWorkout() {
        Workout curr = new Workout();
        ArrayList<String> test = new ArrayList<String>();
        test.add("123");
        curr.setWeight(test);
        curr.setReps(test);
        curr.setExercise(test);
        curr.setTimeOfWorkout(new Date());
        Workout curr2 = new Workout();
        curr2.setWeight(test);
        curr2.setReps(test);
        curr2.setExercise(test);
        curr.setTimeOfWorkout(new Date());
        assertNotEquals(curr,curr2);
        test.add("1234");
        curr.setReps(test);
        assertNotEquals(curr.getReps(),curr2.getReps());
        curr2.setReps(test);
        assertTrue(curr.getReps() == curr2.getReps());
    }

}
