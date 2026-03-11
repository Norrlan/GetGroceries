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
        TextView loginLink = findViewById(R.id.loginLink);
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
        });
    }

    // method for input validation  for email and password
    private  boolean validateInfo (EditText emailField, EditText passwordField)
    {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        // Empty
        if (email.isEmpty()) {
            emailField.setError("Email is required");
            return false;
        }

        // Spaces
        if (email.contains(" ")) {
            emailField.setError("Email cannot contain spaces");
            return false;
        }

        // Lowercase only
        if (!email.equals(email.toLowerCase())) {
            emailField.setError("Email must be in lowercase");
            return false;
        }

        // Must contain @
        if (!email.contains("@")) {
            emailField.setError("Email must contain '@'");
            return false;
        }

        // Length 8–16
        if (email.length() < 8 || email.length() > 16) {
            emailField.setError("Email must be 8–16 characters long");
            return false;
        }

         // Empty
        if (password.isEmpty()) {
            passwordField.setError("Password is required");
            return false;
        }

        // Spaces
        if (password.contains(" ")) {
            passwordField.setError("Password cannot contain spaces");
            return false;
        }

        // Length 8–16
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
    /* public void clickedregisterbtn(View view)
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
    }*/
    public void clickedregisterbtn(View view)
    {

        EditText email = findViewById(R.id.emailField);
        EditText password = findViewById(R.id.passwordField);

        if (!validateInfo(email, password))
        {
            return;
        }

        register(email.getText().toString().trim(),
                password.getText().toString().trim());
    }
}