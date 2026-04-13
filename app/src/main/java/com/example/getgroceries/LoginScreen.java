package com.example.getgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginScreen extends AppCompatActivity
{
    private FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//rgsiter tetx hyperlink
        TextView registerLink = findViewById(R.id.registerlink);
        registerLink.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);

            }
        });
        // intent logic to make my Forgotten password text behave like a hyperlink.
        TextView forgottenpwd = findViewById(R.id.forgottenpwd);
        forgottenpwd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginScreen.this, ForgottenPassword.class);
                startActivity(intent);
            }
        });

        //Logic to make the user move to Home Screen after successful login.
        mAuth = FirebaseAuth.getInstance();

        // Auto Login after user is verified
        FirebaseUser currentUser =mAuth.getCurrentUser();
        if (currentUser != null)
        {
            if (currentUser.isEmailVerified())
            {
                // intent to move user to MainActivity screen
                startActivity(new Intent(LoginScreen.this, MainActivity.class));
                finish();
                return;
            }
            else {
                mAuth.signOut();
                Toast.makeText(this, "Unverified email", Toast.LENGTH_LONG).show();
            }


        }


    }

    // validateInfo function takes email and password as parameters
    // set valid format for the email and pssword field
    //reference: https://www.youtube.com/watch?v=3-lQy24Xjtk&list=PLm1nH2wfylLiBDVQKbUnEyoWxKzQxYzUd&index=4&t=191s


// email validation
    private boolean validateInfo(EditText emailField, EditText passwordField) {

        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Validation for empty field
        if (email.isEmpty()) {
            emailField.setError("Email is required");
            return false;
        }

        // Validation for empty spaces
        if (email.contains(" ")) {
            emailField.setError("Email cannot contain spaces");
            return false;
        }

        // Validation for lowercase only email
        if (!email.equals(email.toLowerCase())) {
            emailField.setError("Email must be in lowercase");
            return false;
        }

        //  Email must contain @
        if (!email.contains("@")) {
            emailField.setError("Email must contain '@'");
            return false;
        }


        // Password Validation

        // Empty Password
        if (password.isEmpty()) {
            passwordField.setError("Password is required");
            return false;
        }

        // Spaces in between password
        if (password.contains(" ")) {
            passwordField.setError("Password cannot contain spaces");
            return false;
        }

        // Password Character Length  is 8–16
        if (password.length() < 8 || password.length() > 16) {
            passwordField.setError("Password must be 8–16 characters long");
            return false;
        }

        // At least 1 uppercase
        if (!password.matches(".*[A-Z].*")) {
            passwordField.setError("Password must contain at least one uppercase letter");
            return false;
        }

        // At least 1 special character
        if (!password.matches(".*[!@#$%^&*()_+=<>?/{}~|].*")) {
            passwordField.setError("Password must contain at least one special character");
            return false;
        }

        return true;
    }

    // method for login.
    public void login(String email, String password) // Login Function
    {
                        // Logic for users to  Log in with email + password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(authTask ->
                                {
                                    if (authTask.isSuccessful())
                                    {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        if (user != null && user.isEmailVerified())
                                        {
                                            startActivity(new Intent(this, MainActivity.class));
                                            finish();
                                        }
                                         // prevent unverified users from authenticating
                                        else {
                                            mAuth.signOut();
                                            Toast.makeText(LoginScreen.this,
                                                    "Verify your email!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginScreen.this,
                                                "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }

                                });



    }
    //method for the Login Button.
    public void clickedloginbtn(View view)
    {

        EditText email = findViewById(R.id.Email2);
        EditText password = findViewById(R.id.Password2);

        if (!validateInfo(email, password)) {
            return;
        }

        login(email.getText().toString().trim(),
                password.getText().toString().trim());
    }

}



