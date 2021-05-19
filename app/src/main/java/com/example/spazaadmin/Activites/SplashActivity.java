package com.example.spazaadmin.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.spazaadmin.Dialogs.LoadingFragmentDialog;
import com.example.spazaadmin.R;

public class SplashActivity extends AppCompatActivity {

    LoadingFragmentDialog dialog = new LoadingFragmentDialog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //call methods here
        //init();
        startSplashScreen();
    }

    private void startSplashScreen()
    {
        LoadingFragmentDialog loadingDialogFragment = new LoadingFragmentDialog("Loading...Please wait");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        loadingDialogFragment.setCancelable(false);
        loadingDialogFragment.show(ft, "Loading");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(4000);
                    //open new activity
                    startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}