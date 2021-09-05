package com.example.spazaadmin.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.spazaadmin.frags.InfoFragment;
import com.example.spazaadmin.frags.LogoutFragment;
import com.example.spazaadmin.frags.MenuFragment;
import com.example.spazaadmin.frags.OrderFragment;
import com.example.spazaadmin.frags.ReportFragment;
import com.example.spazaadmin.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;

public class ParentContainerActivity extends AppCompatActivity {

    private ChipNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_container);

        //call methods here
        init();
        setUpNavigation();
        FirebaseMessaging();
    }

    private void FirebaseMessaging(){
        FirebaseMessaging
                .getInstance()
                .subscribeToTopic(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
    }

    private void init() {
        navigationBar = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new OrderFragment()).commit();
        navigationBar.setItemSelected(R.id.bottom_nav_order, true);
    }

    @SuppressLint("NonConstantResourceId")
    private void setUpNavigation() {
        navigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = new Fragment();

            switch (i) {
                case R.id.bottom_nav_order:
                    fragment = new OrderFragment();
                    //toolbar.setTitle("Order");
                    break;
                case R.id.bottom_nav_menu:
                    fragment = new MenuFragment();
                    //toolbar.setTitle("Menu");
                    break;
                case R.id.bottom_nav_info:
                    fragment = new InfoFragment();
                    //toolbar.setTitle("Info");
                    break;
                case R.id.bottom_nav_report:
                    fragment = new ReportFragment();
                    //toolbar.setTitle("Report");
                    break;
                case R.id.bottom_nav_logout:
                    fragment = new LogoutFragment();
                    //toolbar.setTitle("Profile");
                    signOutUser();
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
        });
    }

    private void signOutUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("LOGOUT");
            builder.setMessage("Are you sure you want to log out?");
            builder.setCancelable(false);
            builder.setNegativeButton("NO",
                    (dialog, which) -> dialog.dismiss())
                    .setPositiveButton("YES", (dialog, which) -> {
                        //sign out user
                        FirebaseAuth.getInstance().signOut();
                        /*LoadingFragmentDialog dialog1 = new LoadingFragmentDialog("Logging out...");
                        dialog1.show(getSupportFragmentManager().beginTransaction(), "Logging out");*/

                        Thread thread = new Thread(() -> {
                            try {
                                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                                finish();

                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        });
                        thread.start();
                    });
            builder.show();
        }
    }


}