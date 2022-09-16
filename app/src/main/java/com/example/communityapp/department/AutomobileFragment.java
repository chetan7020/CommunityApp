package com.example.communityapp.department;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.communityapp.R;
import com.example.communityapp.post.DetailedPostFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class AutomobileFragment extends Fragment {

    private LinearLayout linearLayout;
    private View view;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;

    private void initialize() {
        linearLayout = view.findViewById(R.id.linear_layout_automobile_dept);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    public AutomobileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_automobile, container, false);

        initialize();

        getData();

        return view;
    }

    private void getData() {
        firebaseFirestore.collection("post")
                .whereEqualTo("department", "Automobile")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String header = documentChange.getDocument().getData().get("header").toString();
                            String id = documentChange.getDocument().getData().get("id").toString();
                            addPost(header, id);
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

    private void addPost(String header, String id) {
        View highlightPostView = getLayoutInflater().inflate(R.layout.highlight_post_layout, null, false);

        highlightPostView.setId(Integer.parseInt(id));
        LinearLayout llHighlightPost = highlightPostView.findViewById(R.id.linear_layout_highlight_post);

        TextView tvHeader = highlightPostView.findViewById(R.id.tvHeader);

        ImageView ivThumbnail = highlightPostView.findViewById(R.id.ivThumbnail);

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

    private void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


}