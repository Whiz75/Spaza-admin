package com.example.spazaadmin.activites;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import com.example.spazaadmin.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //call methods here
        //init();
        startSplashScreen();
    }

    private void startSplashScreen() {

        //ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(4000);
                //open new activity
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}