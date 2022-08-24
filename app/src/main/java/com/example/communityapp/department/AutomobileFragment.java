package com.example.communityapp.department;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.communityapp.R;
import com.example.communityapp.post.DetailedPostFragment;

public class AutomobileFragment extends Fragment {

    private LinearLayout linearLayout ;

    public AutomobileFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String headers[] = {"Chetan Dagaji Patil" , "Chetan Dagaji Patil" , "Chetan Dagaji Patil"} ;

        View view =  inflater.inflate(R.layout.fragment_automobile, container, false);

        linearLayout = view.findViewById(R.id.linear_layout_automobile_dept) ;

        for (String header : headers){
            addPost(header) ;
        }

        return view ;
    }

    private void loadFrag(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addPost(String header) {

        View highlightPostView = getLayoutInflater().inflate(R.layout.highlight_post_layout , null , false) ;

        LinearLayout llHighlightPost = highlightPostView.findViewById(R.id.linear_layout_highlight_post) ;

        TextView tvHeader = highlightPostView.findViewById(R.id.tvHeader) ;
        ImageView ivThumbnail = highlightPostView.findViewById(R.id.ivThumbnail) ;

        llHighlightPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFrag(new DetailedPostFragment()) ;
            }
        });

        tvHeader.setText(header);

        linearLayout.addView(highlightPostView);
    }


}