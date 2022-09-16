package com.example.communityapp.my_account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.communityapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MyAccountFragment extends Fragment {

    private View view;
    private CardView cvYourInformation, cvChangeEmail, cvVerifyEmail, cvChangePassword;
    private FirebaseUser firebaseUser ;
    private FirebaseFirestore firebaseFirestore ;
    private String status ;

    private void initialize() {
        status = "" ;

        cvYourInformation = view.findViewById(R.id.cvYourInformation);
        cvChangeEmail = view.findViewById(R.id.cvChangeEmail);
        cvVerifyEmail = view.findViewById(R.id.cvVerifyEmail);
        cvChangePassword = view.findViewById(R.id.cvChangePassword);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        firebaseFirestore = FirebaseFirestore.getInstance() ;

    }

    public MyAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_account, container, false);

        initialize();

        cvYourInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFrag(new YourInformationFragment());
            }
        });

        cvChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFrag(new ChangeEmailFragment());
            }
        });

        cvVerifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkVerification()) {
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            makeToast("Verification email sent on registered email");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            makeToast("Failed to verify email" + e.getMessage());
                        }
                    });
                } else {
                    makeToast("Already Verified");
                }
            }
        });

        cvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser.isEmailVerified()){
                    loadFrag(new ChangePasswordFragment());
                } else {
                    makeToast("Verify first");
                }
            }
        });

        return view;
    }

    private void updateVerifyStatus() {
        Map<String, Object> data = new HashMap<>();
        data.put("verify", "true");

        firebaseFirestore.collection("user")
                .whereEqualTo("email", firebaseUser.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String documentID = documentSnapshot.getId();
                            firebaseFirestore.collection("user")
                                    .document(documentID)
                                    .update(data)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            loadFrag(new MyAccountFragment());
                                        }
                                    });
                        }
                    }
                });
    }

    private boolean checkVerification() {

        if(firebaseUser.isEmailVerified()) {
            updateVerifyStatus() ;
            return true;
        } else {
            return false;
        }

    }

    private void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void loadFrag(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}