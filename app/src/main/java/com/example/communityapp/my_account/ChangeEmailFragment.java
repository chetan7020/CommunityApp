package com.example.communityapp.my_account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.communityapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangeEmailFragment extends Fragment {

    private View view ;
    private Button btnChangeEmail ;
    private TextInputLayout etCurrentEmail , etNewEmail , etConfirmNewEmail , etPassword ;
    private FirebaseUser firebaseUser ;

    private void initialize() {
        btnChangeEmail = view.findViewById(R.id.btnChangeEmail) ;
        etCurrentEmail = view.findViewById(R.id.etCurrentEmail) ;
        etConfirmNewEmail = view.findViewById(R.id.etConfirmNewEmail) ;
        etNewEmail = view.findViewById(R.id.etNewEmail) ;
        etPassword = view.findViewById(R.id.etPassword) ;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        etCurrentEmail.getEditText().setText(firebaseUser.getEmail() ) ;
    }

    public ChangeEmailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_change_email, container, false);

        initialize() ;

        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeEmail() ;
            }
        });

        return view ;
    }

    private void changeEmail() {
        String  newEmail , confirmNewEmail , pass ;

        newEmail = etNewEmail.getEditText().getText().toString().trim() ;
        confirmNewEmail = etConfirmNewEmail.getEditText().getText().toString().trim() ;
        pass = etPassword.getEditText().getText().toString().trim() ;

        if ( newEmail.equals("") || confirmNewEmail.equals("") || pass.equals("") ){

            if (newEmail.equals("")) {
                etNewEmail.setError("*Required");
            }else {
                etNewEmail.setErrorEnabled(false);
                etNewEmail.setError(null);
            }

            if (confirmNewEmail.equals("")) {
                etConfirmNewEmail.setError("*Required");
            }else {
                etConfirmNewEmail.setErrorEnabled(false);
                etConfirmNewEmail.setError(null);
            }

            if (pass.equals("")) {
                etPassword.setError("*Required");
            }else {
                etPassword.setErrorEnabled(false);
                etPassword.setError(null);
            }

        }else {

            etNewEmail.setErrorEnabled(false);
            etNewEmail.setError(null);
            etConfirmNewEmail.setErrorEnabled(false);
            etConfirmNewEmail.setError(null);
            etPassword.setErrorEnabled(false);
            etPassword.setError(null);

            if (newEmail.equals(firebaseUser.getEmail())){
                makeToast("New email and current email both are same");
            }
            else if (newEmail.equals(confirmNewEmail)){

                AuthCredential authCredential = EmailAuthProvider
                        .getCredential(firebaseUser.getEmail() , pass) ;

                firebaseUser.reauthenticate(authCredential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                                firebaseUser.updateEmail(newEmail)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    etConfirmNewEmail.getEditText().setText("") ;
                                                    etNewEmail.getEditText().setText("") ;
                                                    etCurrentEmail.getEditText().setText(firebaseUser.getEmail()) ;
                                                    etPassword.getEditText().setText("") ;
                                                    makeToast("Email Updated");
                                                } else {
                                                    makeToast("Failed to update email");
                                                }
                                            }
                                        });
                            }
                        });
            }else {
                makeToast("Email Mismatched") ;
            }
        }




    }

    private void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

}