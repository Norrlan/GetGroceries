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

        // Email Character Length  is 8–20
        /*
        * if (email.length() < 8 || email.length() > 20) {
            emailField.setError("Email must be 8–20 characters long");
            return false;
        } it is flagging every email I put into it even legit ones so we dodge this
        * */



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

        // Password Character Length  is 8–20
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
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(authTask ->
                                {
                                    if (authTask.isSuccessful())
                                    {
                                        startActivity(new Intent(this,MainActivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(LoginScreen.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
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



    /*
        private  boolean validateInfo (String email, String password)
    {
        if (!email.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
        {
            return  false;
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,16}$"))
        {
             return false;
        }
        return true;
    }

        // Didnt need it cause the validation field handles it all:
          // Empty field prevention
        if (semail.isEmpty())
        {
            email.setError("This field is required!");
        }

        if (spassword.isEmpty())
        {
            password.setError("This field is required!");
        }
           private boolean validateField(EditText field, String type)
    {

        String value = field.getText().toString().trim();

        // Prevent user from leaving emtpy fields
        if (value.isEmpty())
        {
            if (type.equals("email"))
            {
                field.setError("Email is required");
            } else if (type.equals("password"))
            {
                field.setError("Password is required");
            } else
            {
                field.setError("This field is required");
            }
            return false;
        }
            // Email validation
            if (type.equals("email"))
            {
                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                if (!value.matches(emailRegex))
                {
                    field.setError("Enter a valid email address");
                    return false;
                }
            }

            // Password validation
            if (type.equals("password"))
            {
                if (value.length() < 8)
                {
                    field.setError("Password must be at least 8 characters");
                    return false;
                }
            }

        return true;
    }

     public void clickedloginbtn (View view)
    {
        EditText email= findViewById(R.id.Email2);
        EditText password = findViewById(R.id.Password2);

        // login after validation

        String semail = email.getText().toString().trim();
        String spassword = password.getText().toString().trim();


        // Call validateInfor function on this button
        if (!validateInfo(semail,spassword))
        {
            email.setError("Invalid email");
            password.setError("Invalid password ");
            return;
        }


        login(semail,spassword);

    }

        */