package com.example.communityapp.logs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.communityapp.HomeActivity;
import com.example.communityapp.R;

public class SignUpActivity extends AppCompatActivity {

    private Button btnSignUp, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btnSignIn = findViewById(R.id.su_btnSignIn);
        btnSignUp = findViewById(R.id.su_btnSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp() {
        startActivity(new Intent(getApplicationContext() , HomeActivity.class));
    }

    private void signIn() {
        startActivity(new Intent(getApplicationContext() , SignInActivity.class));
    }
}