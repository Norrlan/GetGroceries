package com.example.getgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterScreen extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Login hyperlink
        TextView loginLink = findViewById(R.id.loginlink);
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
        });
    }

    // method for input validation (email adn password)
    private  boolean validateInfo (String email, String password)
    {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
        {
            // Email regex:a username, an "@" symbol, a domain name, and a top-level domain, a digit, and is about 9 characters
            return  false;
        } else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,16}$"))
        {
            // Password regex :at least one digit, one lowercase letter, one uppercase letter, one special character, and is 8-16 characters long.
            return false;
        }
        return true;
    }


    // Register user with email + password
    public void register(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful())
                    {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user == null)
                        {
                            Toast.makeText(RegisterScreen.this,
                                    "Unexpected error occurred", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String uid = user.getUid();

                        // Save email + uid to Firestore
                        UserProfile profile = new UserProfile(email, uid);

                        db.collection("users").document(uid).set(profile)
                                .addOnSuccessListener(aVoid -> {})
                                .addOnFailureListener(e -> {});

                        Toast.makeText(RegisterScreen.this,
                                "Registration Success", Toast.LENGTH_SHORT).show();

                        // Move to HomeScreen
                        startActivity(new Intent(this, LoginScreen.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(RegisterScreen.this,
                                "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Register button handler
    public void clickedregisterbtn(View view)
    {
        EditText email = findViewById(R.id.emailField);
        EditText password = findViewById(R.id.passwordField);

        String sEmail = email.getText().toString().trim();
        String sPassword = password.getText().toString().trim();

        // Call validateInfor function on this button
        if (!validateInfo(sEmail,sPassword))
        {
            email.setError("Invalid email");
            password.setError("Invalid password ");
            return;
        }

        register(sEmail, sPassword);
    }
}