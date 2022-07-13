package com.example.communityapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.communityapp.logs.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    private Animation animation;
    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLogo = findViewById(R.id.ivLogo);
        rotateAnimation();

    }

    private void rotateAnimation() {

        animation = AnimationUtils.loadAnimation(this, R.anim.rotation_animation);
        ivLogo.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                finish();
            }
        }, 2515);
    }
}