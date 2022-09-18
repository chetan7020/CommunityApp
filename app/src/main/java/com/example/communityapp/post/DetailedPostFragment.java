package com.example.communityapp.post;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.communityapp.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailedPostFragment extends Fragment {

    private View view ;
    private TextView tvHeader , tvDescription ;
    private FirebaseFirestore firebaseFirestore ;

    private void initialize() {
        tvHeader = view.findViewById(R.id.tvHeader) ;
        tvDescription = view.findViewById(R.id.tvDescription) ;

        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public DetailedPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.post_layout, container, false);

        initialize() ;

        getData();

        return view ;
    }

    private void getData() {
        String id ;

        id = getArguments().getString("id");

        Log.d("id" , id);

        firebaseFirestore.collection("post")
                .whereEqualTo("id" , id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String header = documentChange.getDocument().getData().get("header").toString() ;
                            String description = documentChange.getDocument().getData().get("description").toString() ;

                            tvHeader.setText(header);
                            tvDescription.setText(description);

                        }
                    }
                });

    }

}