package com.example.spazaadmin.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.spazaadmin.Dialogs.LoadingFragmentDialog;
import com.example.spazaadmin.Models.UserModel;
import com.example.spazaadmin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    //declaring components
    private MaterialToolbar toolbar;

    private TextInputLayout reg_phoneNumber;
    private TextInputLayout reg_fullName;
    private TextInputLayout reg_email;
    private TextInputLayout reg_password;
    private TextInputLayout reg_confirmPassword;
    private MaterialButton signUpButton;

    //declare firebase objects here
    FirebaseAuth auth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //instatiate firebase auth
        auth = FirebaseAuth.getInstance();
        //call all methods here
        init();
        setUpToolbar();
        RegisterUser();
    }

    //method to initialize
    private void init()
    {
        toolbar = findViewById(R.id.sign_up_toobar);

        reg_fullName = findViewById(R.id.reg_full_name);
        reg_email = findViewById(R.id.reg_email);
        reg_phoneNumber = findViewById(R.id.reg_phone);
        reg_password = findViewById(R.id.reg_password);
        reg_confirmPassword = findViewById(R.id.reg_confirm_password);

        signUpButton = findViewById(R.id.signup_btn);
    }

    //method to set up tool bar
    private void setUpToolbar()
    {
        //set up tool bar properties
        toolbar.setTitle("Sign up");
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    //open sign in activity
                    startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //method to register user
    private void RegisterUser()
    {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //initialize components
                String fullName = Objects.requireNonNull(reg_fullName.getEditText()).getText().toString().trim();
                String email = Objects.requireNonNull(reg_email.getEditText()).getText().toString().trim();
                String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String phoneNo = Objects.requireNonNull(reg_phoneNumber.getEditText()).getText().toString().trim();
                String confirmPass = Objects.requireNonNull(reg_confirmPassword.getEditText()).getText().toString().trim();
                String password = Objects.requireNonNull(reg_password.getEditText()).getText().toString().trim();

                //validate full name
                if (TextUtils.isEmpty(fullName))
                {
                    reg_fullName.setError("Field cannot be empty");
                }else
                {
                    reg_fullName.setError(null);
                    reg_fullName.setErrorEnabled(false);
                }

                //validate email
                if (email.isEmpty())
                {
                    reg_email.setError("Field cannot be empty");

                }else if (!email.matches(checkEmail))
                {
                    reg_email.setError("Invalid Email!");

                }else {
                    reg_email.setError(null);
                    reg_email.setErrorEnabled(false);
                }

                //validate phone no
                if (TextUtils.isEmpty(phoneNo))
                {
                    reg_phoneNumber.setError("Field cannot be empty");
                }
                else if(!(phoneNo.charAt(0)=='0'))
                {
                    if (!((phoneNo.charAt(1) == '6') || (phoneNo.charAt(1) == '7') || (phoneNo.charAt(1) == '8')))
                    {
                        if (!(phoneNo.length()<=10))
                        {
                            reg_phoneNumber.setError("Invalid phone number!");
                        }
                    }
                }else
                {
                    reg_phoneNumber.setError(null);
                    reg_phoneNumber.setErrorEnabled(false);
                }

                //validate password
                if (TextUtils.isEmpty(password))
                {
                    reg_password.setError("Field cannot be empty");
                }else if (!TextUtils.isEmpty(password))
                {
                    reg_password.setError(null);
                    reg_password.setErrorEnabled(false);
                }

                //validate confirm password
                if (TextUtils.isEmpty(confirmPass))
                {
                    reg_confirmPassword.setError("Field cannot be empty");
                }
                else
                {
                    reg_confirmPassword.setError(null);
                    reg_confirmPassword.setErrorEnabled(false);
                }

                //check if both password fields are not empty and match
                if (!password.isEmpty() | !confirmPass.isEmpty())
                {
                    if (password.matches(confirmPass))
                    {
                        reg_confirmPassword.setError(null);
                        reg_confirmPassword.setErrorEnabled(false);

                        //loading fragment
                        LoadingFragmentDialog loading = new LoadingFragmentDialog("Registering new user");
                        loading.show(getSupportFragmentManager().beginTransaction(), "Reg");

                        //open home page
                       auth.createUserWithEmailAndPassword(email,password)
                               .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                   @Override
                                   public void onSuccess(AuthResult authResult) {

                                       //call reg method here
                                       UserModel user = new UserModel();
                                       user.setFullName(fullName);
                                       user.setEmail(email);
                                       user.setPhoneNumber(phoneNo);

                                       FirebaseFirestore
                                               .getInstance()
                                               .collection("Admins")
                                               .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                               .set(user);

                                       Toast.makeText(getApplicationContext(), "It works bruh!", Toast.LENGTH_LONG).show();

                                       startActivity(new Intent(getApplicationContext(), ParentContainerActivity.class));
                                       finish();
                                   }
                               }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {

                               Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                           }
                       });

                    }
                    else
                    {
                        reg_confirmPassword.setError("password does not match");
                        reg_password.setError("password does not match");
                    }
                }
                else if (password.isEmpty() | confirmPass.isEmpty())
                {
                    reg_confirmPassword.setError("ensure to fill both password fields");
                    Toast.makeText(getApplicationContext(),"ensure to fill both password fields", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //method to validate user's entered names
    private boolean validateFullName()
    {
        String fullName = Objects.requireNonNull(reg_fullName.getEditText()).getText().toString().trim();

        if (TextUtils.isEmpty(fullName))
        {
            reg_fullName.getEditText().setError("Field cannot be empty");
        }
        return true;
    }

    //method to validate user's entered email
    private boolean  validateEmail()
    {
        String email = Objects.requireNonNull(reg_email.getEditText()).getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty())
        {
            reg_email.setError("Field cannot be empty");

            return false;
        }else if (!email.matches(checkEmail))
        {
            reg_email.setError("Invalid Email!");

            return false;
        }else {
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);

            return true;
        }
    }

    //method to validate user's entered phone no
    private boolean validatePhoneNo()
    {
        String phoneNo = Objects.requireNonNull(reg_phoneNumber.getEditText()).getText().toString().trim();

        if (TextUtils.isEmpty(phoneNo))
        {
            reg_phoneNumber.getEditText().setError("Field cannot be empty");
            return false;
        }

        else if(!(phoneNo.charAt(0)=='+') && !(phoneNo.charAt(1)=='2') && !(phoneNo.charAt(2)=='7'))
        {
            reg_phoneNumber.getEditText().setError("Invalid phone number!");
            return false;
        }else
        {
            return true;
        }
    }

    //method to validate password
    private boolean validatePassword()
    {
        String confirmPass = Objects.requireNonNull(reg_confirmPassword.getEditText()).getText().toString().trim();
        String password = Objects.requireNonNull(reg_password.getEditText()).getText().toString().trim();

        if (!password.isEmpty() | !confirmPass.isEmpty())
        {
            if (password.matches(confirmPass))
            {
                reg_confirmPassword.getEditText().setError(null);
                reg_confirmPassword.setErrorEnabled(false);
                return true;
            } else {
                reg_confirmPassword.getEditText().setError("password does not match");
                return false;
            }
        }else{
            reg_confirmPassword.getEditText().setError("ensure to fill both password fields");
            return false;
        }
    }

}