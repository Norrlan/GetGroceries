package com.example.getgroceries;

import android.media.Image;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeFragment extends Fragment {

    private FirebaseAuth mAuth;
    //initializing firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Firestore database instance

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // Initialise Firebase Authentication and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    //Call the method in MainAtivity to open new fragments
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //User profile Icon - top right corer
        ImageView profilefragment = view.findViewById(R.id.profileicon);
        profilefragment.setOnClickListener(v ->
                {
                    ((MainActivity) getActivity()).openFragment(new ProfileFragment());
                });


        // Testing Grocery Products Model
        db.collection("products").document("Milk").get().addOnSuccessListener(snapshot ->
                {

                    if (snapshot.exists())
                    {
                        android.util.Log.d("FIREBASE_TEST", "Snapshot exists: " + snapshot.exists());
                        GroceryProducts product = snapshot.toObject(GroceryProducts.class);

                        if (product != null)
                        {
                            // Log one field to test mapping
                            android.util.Log.d("FIREBASE_TEST", "HomeFragment started");
                            android.util.Log.d("FIREBASE_TEST", "Product name: " + product.getNamefield());
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    android.util.Log.e("FIREBASE_TEST", "Error fetching document", e);
                    android.util.Log.e("FIREBASE_TEST", "Error: ", e);
                });


    }


}