package com.example.communityapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.communityapp.logs.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private Animation animation;
    private FirebaseAuth auth ;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo = findViewById(R.id.ivLogo);
        auth = FirebaseAuth.getInstance() ;
        rotateAnimation();
    }

    private void rotateAnimation() {

        animation = AnimationUtils.loadAnimation(this, R.anim.rotation_animation);
        ivLogo.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                FirebaseUser user = auth.getCurrentUser() ;
                if (user != null){
                    startActivity(new Intent(SplashActivity.this , HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }
                finish() ;
            }
        }, 2515);


    }
}