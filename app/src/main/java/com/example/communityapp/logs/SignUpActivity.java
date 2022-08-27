package com.example.communityapp.logs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.communityapp.HomeActivity;
import com.example.communityapp.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private TextView tvSignIn ;
    private Button btnSignUp ;
    private TextInputLayout etFullName, etEmail, etPhoneNumber, etCreatePass, etConfirmPass;
    private FirebaseAuth auth ;

    private void initialized() {
        tvSignIn = findViewById(R.id.su_tvSignIn) ;
        btnSignUp = findViewById(R.id.su_btnSignUp) ;

        etFullName = findViewById(R.id.etFullName) ;
        etEmail = findViewById(R.id.etEmail) ;
        etPhoneNumber = findViewById(R.id.etPhoneNumber) ;
        etCreatePass = findViewById(R.id.etCreatePass) ;
        etConfirmPass = findViewById(R.id.etConfirmPass) ;

        auth = FirebaseAuth.getInstance() ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initialized() ;

        tvSignIn.setOnClickListener(view -> signIn());

        btnSignUp.setOnClickListener(view -> signUp());

    }

    private void signUp() {

        String email , createPass , confirmPass , phoneNumber , fullName ;

        fullName = etFullName.getEditText().getText().toString().trim() ;
        email = etEmail.getEditText().getText().toString().trim() ;
        createPass = etCreatePass.getEditText().getText().toString().trim() ;
        confirmPass = etConfirmPass.getEditText().getText().toString().trim() ;
        phoneNumber = etPhoneNumber.getEditText().getText().toString().trim() ;

        if (fullName.equals("") ||
                email.equals("") ||
                createPass.equals("") ||
                confirmPass.equals("") ||
                phoneNumber.equals("") ) {

            if (fullName.equals("")) {
                etFullName.setError("*Required");
            }else {
                etFullName.setErrorEnabled(false);
                etFullName.setError(null);
            }

            if (email.equals("")) {
                etEmail.setError("*Required");
            }else {
                etEmail.setErrorEnabled(false);
                etEmail.setError(null);
            }

            if (createPass.equals("")) {
                etCreatePass.setError("*Required");
            }else {
                etCreatePass.setErrorEnabled(false);
                etCreatePass.setError(null);
            }

            if (confirmPass.equals("")) {
                etConfirmPass.setError("*Required");
            }else {
                etConfirmPass.setErrorEnabled(false);
                etConfirmPass.setError(null);
            }

            if (phoneNumber.equals("")) {
                etPhoneNumber.setError("*Required");
            }else {
                etPhoneNumber.setErrorEnabled(false);
                etPhoneNumber.setError(null);
            }

        }else {
            if (createPass.equals(confirmPass)) {

                auth.createUserWithEmailAndPassword(email, createPass)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                makeToast("Successfully Registered");
                                startActivity(new Intent(SignUpActivity.this , HomeActivity.class));
                            } else {
                                auth.fetchSignInMethodsForEmail(email)
                                        .addOnCompleteListener(task1 -> {
                                            boolean isNewUser = task1.getResult().getSignInMethods().isEmpty();
                                            if (!isNewUser) {
                                                makeToast("Email id already taken");
                                            }
                                        });
                            }

                        });

            } else {

                makeToast("Password Mismatched");
            }
        }
    }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void signIn() {
        startActivity(new Intent(getApplicationContext() , SignInActivity.class));
        finish();
    }
}