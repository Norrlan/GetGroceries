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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // "Login" hyperlink
        TextView loginLink = findViewById(R.id.loginlink);
        loginLink.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
            startActivity(intent);
        });
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

        register(sEmail, sPassword);
    }
}