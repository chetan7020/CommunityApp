package com.example.communityapp.post;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.communityapp.R;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailedPostFragment extends Fragment {

    private View view ;
    private TextView tvHeader , tvDescription , tvEmail;
    private FirebaseFirestore firebaseFirestore ;
    private String id ;
    private LinearLayout llPostLayout;

    private void initialize() {
        tvHeader = view.findViewById(R.id.tvHeader) ;
        tvDescription = view.findViewById(R.id.tvDescription) ;
        tvEmail = view.findViewById(R.id.tvEmail);

        firebaseFirestore = FirebaseFirestore.getInstance();

        id = getArguments().getString("id");

        llPostLayout = view.findViewById(R.id.llPostLayout);
    }

    public DetailedPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_layout, container, false);

        initialize() ;

        getData();

        getComments();

        return view ;
    }

    private void getComments() {
        firebaseFirestore.collection(id+"_comment")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (!String.valueOf(value.getDocuments().stream().count()).equals("0")) {
                            View viewCommentHead = getLayoutInflater().inflate(R.layout.comment_head_layout, null, false);

                            llPostLayout.addView(viewCommentHead);

                            for (DocumentChange documentChange : value.getDocumentChanges()) {

                                String email = documentChange.getDocument().getData().get("email").toString() ;
                                String comment = documentChange.getDocument().getData().get("comment").toString() ;

                                createCommentLayout(email, comment);
                            }
                        }


                    }
                });
    }

    private void createCommentLayout(String email, String comment) {
        View viewCommentLayout = getLayoutInflater().inflate(R.layout.comment_layout, null, false);

        TextView tvEmail = viewCommentLayout.findViewById(R.id.tvEmail);
        TextView tvComment = viewCommentLayout.findViewById(R.id.tvComment);

        tvEmail.setText(email+" : ");
        tvComment.setText(comment);

        llPostLayout.addView(viewCommentLayout);
    }

    private void getData() {
        firebaseFirestore.collection("post")
                .whereEqualTo("id" , id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String email = documentChange.getDocument().getData().get("email").toString() ;
                            String header = documentChange.getDocument().getData().get("header").toString() ;
                            String description = documentChange.getDocument().getData().get("description").toString() ;

                            tvEmail.setText(email);
                            tvHeader.setText(header);
                            tvDescription.setText(description);

                        }
                    }
                });

    }

}