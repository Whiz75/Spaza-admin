package com.example.spazaadmin.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.spazaadmin.Dialogs.LoadingFragmentDialog;
import com.example.spazaadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    private MaterialButton signUpButton, signInButton;
    private TextInputLayout txt_password, txt_email;

    //firebase
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //instatiate firebase auth
        auth = FirebaseAuth.getInstance();

        //call methods here
        init();
        GotoSignUp();
        GotoMainPage();
    }

    private void init()
    {
        //initialize components
        txt_email = findViewById(R.id.signIn_username);
        txt_password = findViewById(R.id.signIn_password);
        signUpButton = findViewById(R.id.signup_button);
        signInButton = findViewById(R.id.signIn_button);
    }

    private void GotoSignUp()
    {
        //go to sign up activity
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                    finish();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private void GotoMainPage()
    {
        //sign in user
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String email = txt_email.getEditText().getText().toString().trim();
                    String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    String password = txt_password.getEditText().getText().toString().trim();

                    //check email
                    if (TextUtils.isEmpty(email))
                    {
                        txt_email.setError("Field can not be empty");

                    } else if (!email.matches(checkEmail))
                    {
                        txt_email.setError("Invalid Email!");

                    }else {
                        txt_email.setError(null);
                        txt_email.setErrorEnabled(false);
                    }
                    //validate password
                    if(TextUtils.isEmpty(password))
                    {
                        txt_password.setError("Field can not be empty");

                    }else
                    {
                        txt_password.setError(null);
                        txt_password.setErrorEnabled(false);

                    }

                    //validate both email and password
                    if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password))
                    {

                        auth.signInWithEmailAndPassword(email,password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {

                                        startActivity(new Intent(getApplicationContext(),ParentContainerActivity.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            LoadingFragmentDialog loadingDialogFragment = new LoadingFragmentDialog("Authenticating...please wait");

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            loadingDialogFragment.setCancelable(false);
            loadingDialogFragment.show(ft, "Loading");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run()
                {
                    try
                    {
                        startActivity(new Intent(getApplicationContext(), ParentContainerActivity.class));
                        finish();

                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        }
    }
}