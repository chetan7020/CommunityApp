package com.example.communityapp.post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.communityapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailedPostFragment extends Fragment {

    private View view ;
    private TextView tvHeader , tvDescription ;
    private String header ;
    private FirebaseFirestore firebaseFirestore ;
    private FirebaseUser firebaseUser ;

    private void initialize() {
        tvHeader = view.findViewById(R.id.tvHeader) ;
        tvDescription = view.findViewById(R.id.tvDescription) ;

        header = "" ;
    }

    public DetailedPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_layout, container, false);

        initialize() ;

        getData() ;

        return view ;

    }

    private void getData() {

        header = getArguments().getString("header") ;

        tvHeader.setText(header) ;


    }

}