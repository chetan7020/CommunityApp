package com.example.communityapp.department;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.communityapp.R;
import com.example.communityapp.post.DetailedPostFragment;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class CivilFragment extends Fragment {

    private LinearLayout linearLayout ;
    private FirebaseFirestore firebaseFirestore ;
    private View view ;

    public CivilFragment() {
    }

    private void initialize() {
        linearLayout = view.findViewById(R.id.linear_layout_civil_dept) ;
        firebaseFirestore = FirebaseFirestore.getInstance() ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_civil, container, false);

        initialize() ;

        getData() ;

        return view ;
    }

    private void addPost(String header , String id) {

        View highlightPostView = getLayoutInflater().inflate(R.layout.highlight_post_layout , null , false) ;
        highlightPostView.setId(Integer.parseInt(id));

        LinearLayout llHighlightPost = highlightPostView.findViewById(R.id.linear_layout_highlight_post) ;

        TextView tvHeader = highlightPostView.findViewById(R.id.tvHeader) ;
        ImageView ivThumbnail = highlightPostView.findViewById(R.id.ivThumbnail) ;

        tvHeader.setText(header);

        llHighlightPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(highlightPostView.getId());
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                DetailedPostFragment fragment = new DetailedPostFragment();
                fragment.setArguments(bundle);
                loadFrag(fragment);
            }
        });

        linearLayout.addView(highlightPostView);
    }

    private void loadFrag(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
        fragmentTransaction.replace(R.id.container, fragment) ;
        fragmentTransaction.addToBackStack(null) ;
        fragmentTransaction.commit() ;
    }

    private void getData() {
        firebaseFirestore.collection("post")
                .whereEqualTo("department" , "Civil")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String header = documentChange.getDocument().getData().get("header").toString() ;
                            String id = documentChange.getDocument().getData().get("id").toString();
                            addPost(header, id);
                        }
                    }
                });
    }


}