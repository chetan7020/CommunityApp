package com.example.communityapp.department;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.communityapp.R;

public class MechanicalFragment extends Fragment {

    private LinearLayout linearLayout ;

    public MechanicalFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_mechanical, container, false);

        linearLayout = view.findViewById(R.id.linear_layout_mechanical_dept) ;

        addPost("Hello Codiz , Chetan Dagaji Patil\nChetan\nDagaji\nPatil") ;

        return view ;
    }

    private void addPost(String header) {

        View highlightPostView = getLayoutInflater().inflate(R.layout.highlight_post_layout , null , false) ;
        TextView tvHeader = highlightPostView.findViewById(R.id.tvHeader) ;
        ImageView ivThumbnail = highlightPostView.findViewById(R.id.ivThumbnail) ;

        tvHeader.setText(header);

        linearLayout.addView(highlightPostView);
    }


}