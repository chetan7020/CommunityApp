package com.example.communityapp.department;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.communityapp.R;
import com.example.communityapp.post.DetailedPostFragment;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DefaultFragment extends Fragment {

    private LinearLayout linearLayout;
    private View view;
    private FirebaseFirestore firebaseFirestore;

    public DefaultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_default, container, false);

        initialize();

        getData();

        return view;
    }

    private void initialize() {
        linearLayout = view.findViewById(R.id.linear_layout_default);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void getData() {
        firebaseFirestore.collection("post")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String header = documentChange.getDocument().getData().get("header").toString();
                            addPost(header);
                        }
                    }
                });
    }

    private void loadFrag(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    private void addPost(String header) {

        View highlightPostView = getLayoutInflater().inflate(R.layout.highlight_post_layout, null, false);

        LinearLayout llHighlightPost = highlightPostView.findViewById(R.id.linear_layout_highlight_post);

        TextView tvHeader = highlightPostView.findViewById(R.id.tvHeader);
        ImageView ivThumbnail = highlightPostView.findViewById(R.id.ivThumbnail);

        tvHeader.setText(header);

        llHighlightPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String header = tvHeader.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("header", header);
                DetailedPostFragment fragment = new DetailedPostFragment();
                fragment.setArguments(bundle);
                loadFrag(fragment);
            }
        });

        linearLayout.addView(highlightPostView);
    }

}