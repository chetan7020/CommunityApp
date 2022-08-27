package com.example.communityapp.my_account;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.communityapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class YourInformationFragment extends Fragment {

    private View view ;
    private TextView tvName , tvMobileNumber , tvEmail ;
    private FirebaseUser firebaseUser ;
    private FirebaseFirestore firebaseFirestore ;


    private void initialize() {
        tvName = view.findViewById(R.id.tvName) ;
        tvMobileNumber = view.findViewById(R.id.tvMobileNumber) ;
        tvEmail = view.findViewById(R.id.tvEmail) ;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        firebaseFirestore = FirebaseFirestore.getInstance() ;

    }

    public YourInformationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_your_information, container, false);

        initialize() ;

        getData() ;

        return view ;
    }

    private void getData() {
        firebaseFirestore.collection("user")
                .whereEqualTo("email" , firebaseUser.getEmail())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange documentChange : value.getDocumentChanges()) {
                            String name = documentChange.getDocument().getData().get("name").toString() ;
                            String email = documentChange.getDocument().getData().get("email").toString() ;
                            String number = documentChange.getDocument().getData().get("phoneNumber").toString() ;

                            tvName.setText("Name : " + name);
                            tvEmail.setText("Email : " + email);
                            tvMobileNumber.setText("Mobile No. : " + number);

                        }
                    }
                });
    }


}