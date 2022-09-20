package com.example.communityapp.department;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.communityapp.R;
import com.example.communityapp.post.DetailedPostFragment;
import com.example.communityapp.post.WritePostFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RecentPostFragment extends Fragment {

    private LinearLayout linearLayout;
    private View view;
    private FirebaseFirestore firebaseFirestore;
    private TextView tvRecentPost;
    private FirebaseUser firebaseUser;

    private void initialize() {
        linearLayout = view.findViewById(R.id.linear_layout_default);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        tvRecentPost = view.findViewById(R.id.tvRecent);
    }

    public RecentPostFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Log.d("tag" , "onCreate");

        view = inflater.inflate(R.layout.fragment_recent_post, container, false);

        initialize();

        tvRecentPost.setVisibility(View.GONE);

        getData();

        return view;
    }

    private void getData() {
        firebaseFirestore.collection("post")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (String.valueOf(value.getDocuments().stream().count()).equals("0")) {
                            addNoPostView();
                        } else {
                            tvRecentPost.setVisibility(View.VISIBLE);
                            for (DocumentChange documentChange : value.getDocumentChanges()) {
                                String header = documentChange.getDocument().getData().get("header").toString();
                                String description = documentChange.getDocument().getData().get("description").toString();
                                String id = documentChange.getDocument().getData().get("id").toString();
                                addPost(header, id, description);
                            }
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


    private void addPost(String header, String id, String description) {

        View highlightPostView = getLayoutInflater().inflate(R.layout.highlight_post_layout, null, false);

        highlightPostView.setId(Integer.parseInt(id));

        LinearLayout llComment = highlightPostView.findViewById(R.id.llComment);

        llComment.setVisibility(View.GONE);

        ImageView ivShare, ivComment;

        ivShare = highlightPostView.findViewById(R.id.ivShare);
        ivComment = highlightPostView.findViewById(R.id.ivComment);

        ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareText(header, description);
            }
        });

        ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llComment.setVisibility(View.VISIBLE);

                TextInputLayout etComment = highlightPostView.findViewById(R.id.etComment);

                ImageView ivSend = highlightPostView.findViewById(R.id.ivSend);

                ivSend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String comment = etComment.getEditText().getText().toString().trim();

                        if (comment.equals("")) {
                            etComment.setError("*Required");

                        } else {
                            etComment.setErrorEnabled(false);
                            etComment.setError(null);

                            Map<String, Object> data = new HashMap<>();

                            data.put("email", firebaseUser.getEmail());
                            data.put("comment", comment);

                            firebaseFirestore.collection(id + "_comment")
                                    .add(data)
                                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            makeToast("Commented");
                                            Log.d("tag", "Commented");
                                            etComment.getEditText().setText("");
                                            llComment.setVisibility(View.GONE);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("tag", e.toString());
                                        }
                                    });

                        }

                    }
                });
            }
        });


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

    private void addNoPostView() {
        View noPostView = getLayoutInflater().inflate(R.layout.write_post, null, false);

        Button btnWritePost = noPostView.findViewById(R.id.btnWritePost);

        btnWritePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFrag(new WritePostFragment());
            }
        });

        linearLayout.addView(noPostView);

    }

    private void shareText(String header, String description) {
        Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
        txtIntent .setType("text/plain");
        txtIntent .putExtra(android.content.Intent.EXTRA_TEXT, header+"\n\n\n"+description);
        startActivity(Intent.createChooser(txtIntent ,"Share"));
    }

    private void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}