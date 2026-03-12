package com.example.getgroceries;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFragment extends Fragment {
    private FirebaseAuth mAuth;

    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        // in the Profile screen, display the logged in users email
        EditText Emailfield = view.findViewById(R.id.EmailAddressField);
        Button resetPasswordBtn = view.findViewById(R.id.resetPasswordBtn);
        //Logout button logs user out. Takes user to the Login Screen after.
        Button logoutbtn = view.findViewById(R.id.logoutBtn);
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseAuth.getInstance().signOut();// To mkae the logout button logout
        // get logged in users email
        if (user!= null)
        {
            String Email = user.getEmail();
            Emailfield.setText(Email);
        }
        // For loggin out user clicks logout button and toast is displayed
        logoutbtn.setOnClickListener(v ->
        {
            signOut();
            Toast.makeText(getActivity(),"Logout successful",Toast.LENGTH_SHORT).show();

        });
        resetPasswordBtn.setOnClickListener(v ->
        {
            Intent intent = new Intent(getActivity(), ForgottenPassword.class);
            startActivity(intent); // takes user to the forgotten passwrod screen
        });
    }

    public void  signOut ()// method to make user logout.
    {
        mAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        startActivity(intent);
        // Prevent user from going back to the Login Screen
        getActivity().finish();

    }


}