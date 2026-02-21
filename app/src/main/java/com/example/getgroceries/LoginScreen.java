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
    // method for input validation (email adn password)
    private boolean validateField(EditText field, String type)
    {

        String value = field.getText().toString().trim();

        // Prevent user from leaving emtpy fields
        if (value.isEmpty()) {
            field.setError("This field is required");
            return false;
        }

        // Email validation
        if (type.equals("email"))


            // Password validation
            if (type.equals("password")) {
                if (value.length() < 8) {
                    field.setError("Password must be at least 8 characters");
                    return false;
                }
            }

        return true;
    }

    // method for login.
    public void login(String email, String password)
    {
        if (email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(LoginScreen.this, "Enter email  and password", Toast.LENGTH_SHORT).show();
            return;
        }
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
    public void clickedloginbtn (View view)
    {
        EditText email= findViewById(R.id.Email2);
        EditText password = findViewById(R.id.Password2);
        // validate email and password
        if (!validateField(email, "email"))
            return;
        if (!validateField(email, "password"))
            return;
        // login after validation
        String semail = email.getText().toString().trim();
        String spassword = password.getText().toString().trim();

        login(semail,spassword);


    }





}