package com.example.flex.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flex.Adapter;
import com.example.flex.R;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {

    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_fragmemnt, container, false);

        recyclerView = view.findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<String> data = getData(); // Implement this method to provide data for the RecyclerView
        adapter = new Adapter(data);
        recyclerView.setAdapter(adapter);

        return view;

    }

    private List<String> getData() {
        // Implement this method to provide data for the RecyclerView
        // Example:
        List<String> data = new ArrayList<>();
        data.add("Item 1");
        data.add("Item 2");
        data.add("Item 3");
        data.add("Item 4");
        data.add("Item 5");
        data.add("Item 6");
        data.add("Item 7");
        data.add("Item 8");
        data.add("Item 9");
        data.add("Item 10");
        // Add more items as needed
        return data;
    }
}