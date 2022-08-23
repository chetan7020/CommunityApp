package com.example.communityapp.post;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.communityapp.R;

public class DetailedPostFragment extends Fragment {

    View view ;

    public DetailedPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_layout, container, false);

        return view ;

    }

}