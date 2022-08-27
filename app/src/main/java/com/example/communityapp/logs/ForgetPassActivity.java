package com.example.communityapp.logs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.communityapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button btnReset;
    private TextInputLayout etEmail;
    private TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        initialize();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgetPassActivity.this, SignInActivity.class));
                finish() ;
            }
        });

    }

    private void reset() {

        String email;

        email = etEmail.getEditText().getText().toString().trim();

        if (email.equals("")) {
            if (email.equals("")) {
                etEmail.setError("*Required");
            } else {
                etEmail.setErrorEnabled(false);
                etEmail.setError(null);
            }
        } else {

            firebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                etEmail.getEditText().setText("");
                                Toast.makeText(ForgetPassActivity.this, "Reset email sent to registered email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgetPassActivity.this, "Failed to send email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        btnReset = findViewById(R.id.btnReset);
        etEmail = findViewById(R.id.etEmail);
        tvSignIn = findViewById(R.id.tvSignIn) ;
    }
}