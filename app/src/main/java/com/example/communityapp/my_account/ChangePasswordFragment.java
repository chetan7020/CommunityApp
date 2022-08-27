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

public class ChangePasswordFragment extends Fragment {

    private View view ;
    private TextInputLayout etCurrentPass , etNewPass , etConfirmNewPass ;
    private Button btnChangePassword ;
    private FirebaseUser firebaseUser ;

    public ChangePasswordFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_change_password, container, false);

        initialize() ;

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePass() ;
            }
        });

        return view ;

    }

    private void changePass() {
        String  currentPass , confirmNewPass , newPass ;

        currentPass = etCurrentPass.getEditText().getText().toString().trim() ;
        confirmNewPass = etConfirmNewPass.getEditText().getText().toString().trim() ;
        newPass = etNewPass.getEditText().getText().toString().trim() ;

        if ( currentPass.equals("") || confirmNewPass.equals("") || newPass.equals("") ){

            if (currentPass.equals("")) {
                etCurrentPass.setError("*Required");
            }else {
                etCurrentPass.setErrorEnabled(false);
                etCurrentPass.setError(null);
            }

            if (confirmNewPass.equals("")) {
                etConfirmNewPass.setError("*Required");
            }else {
                etConfirmNewPass.setErrorEnabled(false);
                etConfirmNewPass.setError(null);
            }

            if (newPass.equals("")) {
                etNewPass.setError("*Required");
            }else {
                etNewPass.setErrorEnabled(false);
                etNewPass.setError(null);
            }
        } else {

            etNewPass.setErrorEnabled(false);
            etNewPass.setError(null);
            etConfirmNewPass.setErrorEnabled(false);
            etConfirmNewPass.setError(null);
            etCurrentPass.setErrorEnabled(false);
            etCurrentPass.setError(null);

            if (currentPass.equals(newPass)){
                makeToast("New password and current password both are same") ;
            }
            else if (newPass.equals(confirmNewPass)) {
                AuthCredential authCredential = EmailAuthProvider
                        .getCredential(firebaseUser.getEmail() , currentPass) ;

                firebaseUser.reauthenticate(authCredential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    firebaseUser.updatePassword(newPass)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        etCurrentPass.getEditText().setText("") ;
                                                        etNewPass.getEditText().setText("") ;
                                                        etConfirmNewPass.getEditText().setText("") ;
                                                        makeToast("Password updated") ;
                                                    } else {
                                                        makeToast("Failed to update password") ;
                                                    }
                                                }
                                            }) ;
                                } else {
                                    makeToast("Wrong password");
                                }
                            }
                        });
            } else {
                makeToast("Password Mismatched");
            }

        }
    }

    private void makeToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void initialize() {
        etCurrentPass = view.findViewById(R.id.etCurrentPassword) ;
        etNewPass = view.findViewById(R.id.etNewPassword) ;
        etConfirmNewPass = view.findViewById(R.id.etConfirmNewPassword) ;

        btnChangePassword = view.findViewById(R.id.btnChangePassword) ;

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
    }
}