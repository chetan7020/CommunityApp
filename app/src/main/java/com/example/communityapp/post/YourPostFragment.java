package com.example.communityapp.post;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityapp.R;
import com.example.communityapp.post.DetailedPostFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class YourPostFragment extends Fragment {

    private LinearLayout linearLayout ;
    private View view ;
    private FirebaseFirestore firebaseFirestore ;
    private FirebaseUser firebaseUser ;
    private SwipeRefreshLayout swipeRefreshLayout ;

    private void initialize() {
        linearLayout = view.findViewById(R.id.linear_layout_your_post) ;
        firebaseFirestore = FirebaseFirestore.getInstance() ;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    }

    public YourPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_your_post, container, false);

        initialize() ;

        getData() ;

        return view ;
    }

    private void getData() {
        firebaseFirestore.collection("post")
                .whereEqualTo("email" , firebaseUser.getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                for (DocumentChange documentChange : value.getDocumentChanges()) {
                    String header = documentChange.getDocument().getData().get("header").toString() ;
                    addPost(header);
                }
            }
        });
    }

    private void loadFrag(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
        fragmentTransaction.replace(R.id.container, fragment) ;
        fragmentTransaction.addToBackStack(null) ;
        fragmentTransaction.commit() ;

    }

    private void addPost(String header) {

        View highlightPostView = getLayoutInflater().inflate(R.layout.highlight_post_layout , null , false) ;

        View delete = getLayoutInflater().inflate(R.layout.delete , null , false) ;

        LinearLayout llHighlightPost = highlightPostView.findViewById(R.id.linear_layout_highlight_post) ;

        TextView tvHeader = highlightPostView.findViewById(R.id.tvHeader) ;
        ImageView ivThumbnail = highlightPostView.findViewById(R.id.ivThumbnail) ;
        ImageView ivDelete = delete.findViewById(R.id.ivDelete) ;

        tvHeader.setText(header);

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                deletePost(header) ;
                makeToast("Yet to build") ;
            }
        });

        llHighlightPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String header = tvHeader.getText().toString() ;
                Bundle bundle = new Bundle() ;
                bundle.putString("header" , header);
                DetailedPostFragment fragment = new DetailedPostFragment() ;
                fragment.setArguments(bundle);
                loadFrag(fragment) ;
            }
        });

        linearLayout.addView(highlightPostView);
        linearLayout.addView(delete);
    }

//    private void deletePost(String header) {
//        firebaseFirestore
//                .collection("post")
//                .whereEqualTo("header" , header)
//                .whereEqualTo("email" , firebaseUser.getEmail())
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if ( task.isSuccessful() && !task.getResult().isEmpty() ){
//                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
//                            String documentID = documentSnapshot.getId();
//
//                            firebaseFirestore
//                                    .collection("post")
//                                    .document(documentID)
//                                    .delete()
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            makeToast("Post Deleted");
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            makeToast("Failed to Delete");
//                                        }
//                                    });
//                        }
//                    }
//                });
//    }

    private void makeToast(String msg) {
        Toast.makeText(getContext(), msg , Toast.LENGTH_SHORT).show();
    }


}