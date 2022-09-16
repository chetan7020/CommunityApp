package com.example.communityapp.logs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityapp.HomeActivity;
import com.example.communityapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private TextView tvForgetPass , tvSignUp ;
    private Button btnSignIn ;
    private TextInputLayout etEmail , etPass ;
    private FirebaseAuth auth ;

    private void initialize() {
        btnSignIn = findViewById(R.id.si_btnSignIn) ;
        tvForgetPass = findViewById(R.id.tvForgetPass) ;
        tvSignUp = findViewById(R.id.tvSignUp) ;

        etEmail = findViewById(R.id.etEmail) ;
        etPass = findViewById(R.id.etPass) ;

        auth = FirebaseAuth.getInstance() ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialize() ;

        btnSignIn.setOnClickListener(view -> signIn());

        tvSignUp.setOnClickListener(view -> signUp());

        tvForgetPass.setOnClickListener(view -> forgetPass());

    }

    private void forgetPass() {
        startActivity(new Intent(SignInActivity.this , ForgetPassActivity.class));
    }


    private void signIn(){
        String email , pass = "" ;

        email = etEmail.getEditText().getText().toString().trim() ;
        pass = etPass.getEditText().getText().toString().trim() ;

        if (email.equals("") || pass.equals("")) {

            if (email.equals("")) {
                etEmail.setError("*Required");
            }else {
                etEmail.setErrorEnabled(false);
                etEmail.setError(null);
            }

            if (pass.equals("")) {
                etPass.setError("*Required");
            }else {
                etPass.setErrorEnabled(false);
                etPass.setError(null);
            }

        } else {

            etPass.setErrorEnabled(false);
            etPass.setError(null);

            etEmail.setErrorEnabled(false);
            etEmail.setError(null);


            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                makeToast("Successfully Login");
                                startActivity(new Intent(SignInActivity.this , HomeActivity.class));
                                finish() ;
                            } else {
                                makeToast("Failed to Login");
                            }
                        }
                    });
            }

        }

    private void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void signUp(){
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
        finish();
    }
}