package com.example.sarthak.todo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
public class Launch extends AppCompatActivity {
    private LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        animationView = findViewById(R.id.splash_logo);
        animationView.setAnimation("progress_bar.json");
//        animationView.loop(true);
//        animationView.setRepeatMode();
        animationView.playAnimation();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),home.class));
                finish();
            }
        },4000);
    }
}
