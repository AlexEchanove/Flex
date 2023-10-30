package com.example.flex;

public class Workout {

    public String exercise;
    public String email;

    public Workout() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Workout(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
