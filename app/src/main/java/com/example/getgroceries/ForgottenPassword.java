package com.example.getgroceries;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ForgottenPassword extends AppCompatActivity
{


        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_forgotten_password);

            mAuth = FirebaseAuth.getInstance();

            //bind email and reset buttons to the appropriate xml elements
            EditText emailField = findViewById(R.id.emailField2);
            Button resetButton = findViewById(R.id.resetButton);

            // method for reset button.
            resetButton.setOnClickListener(v ->
            {
                String email = emailField.getText().toString().trim();

                if (email.isEmpty()) // empty  email field validation
                {
                    emailField.setError("Email is required");
                    return;
                }

                // Logic to send reset email to users email address.
     mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task ->
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgottenPassword.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(ForgottenPassword.this,"Reset failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            });
        }
}