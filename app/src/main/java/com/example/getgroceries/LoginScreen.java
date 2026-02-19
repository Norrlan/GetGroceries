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

//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null)
//        {
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
//    }

    //Method for users to sign in with their email and password
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

        String semail = email.getText().toString().trim();
        String spassword = password.getText().toString().trim();

        login(semail,spassword);


    }





}