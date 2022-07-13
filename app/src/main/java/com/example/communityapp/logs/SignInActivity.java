package com.example.communityapp.logs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.communityapp.HomeActivity;
import com.example.communityapp.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn , btnSignUp ;
    private TextInputEditText etPhoneMail , etPass ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialize() ;

        addValidation() ;

        btnSignIn.setOnClickListener(view -> signIn());
        btnSignUp.setOnClickListener(view -> signUp());

    }

    private void addValidation() {
    }

    private void initialize() {
        //widgets
        btnSignIn = findViewById(R.id.si_btnSignIn) ;
        btnSignUp = findViewById(R.id.si_btnSignUp) ;
        etPhoneMail = findViewById(R.id.si_etPhoneMail) ;
        etPass = findViewById(R.id.si_etPass) ;

        //validation
    }

    private void signIn(){
        startActivity(new Intent(getApplicationContext() , HomeActivity.class));
    }

    private void signUp(){
        startActivity(new Intent(getApplicationContext() , SignUpActivity.class));
    }
}