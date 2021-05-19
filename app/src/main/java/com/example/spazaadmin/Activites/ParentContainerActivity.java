package com.example.spazaadmin.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.spazaadmin.Dialogs.LoadingFragmentDialog;
import com.example.spazaadmin.Fragments.LogoutFragment;
import com.example.spazaadmin.Fragments.MenuFragment;
import com.example.spazaadmin.Fragments.OrderFragment;
import com.example.spazaadmin.Fragments.InfoFragment;
import com.example.spazaadmin.Fragments.ReportFragment;
import com.example.spazaadmin.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ParentContainerActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private ChipNavigationBar navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_container);

        //call methods here
        init();
        setUpNavigation();
    }

    private void init()
    {
        //toolbar = findViewById(R.id.my_toolbar);
        navigationBar = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new OrderFragment()).commit();
        navigationBar.setItemSelected(R.id.bottom_nav_order, true);
        //toolbar.setTitle("Order");
    }

    private void setUpNavigation()
    {
        navigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int i) {

                Fragment fragment = null;

                switch (i)
                {
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
            }
        });
    }

    private void signOutUser()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("LOGOUT");
            builder.setMessage("Are you sure you want to log out?");
            builder.setCancelable(false);
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            }).setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //sign out user
                    FirebaseAuth.getInstance().signOut();

                    LoadingFragmentDialog dialog1 = new LoadingFragmentDialog("Logging out...");
                    dialog1.show(getSupportFragmentManager().beginTransaction(), "Logging out");

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                                finish();

                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
            });
            builder.show();
        }
    }


}