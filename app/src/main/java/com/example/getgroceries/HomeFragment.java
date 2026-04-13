package com.example.getgroceries;

import android.media.Image;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment
{

    private FirebaseAuth mAuth;
    //initializing firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance(); // Firestore database instance

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<GroceryProducts> productsList = new ArrayList<>();

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

        // assign the recycler view to the ID in xml
       recyclerView = view.findViewById(R.id.HomeProducts);
       // fetch the products data into the layout of the recycler view on the Home screen.
       recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       adapter = new ProductAdapter(productsList, getActivity());
       recyclerView.setAdapter(adapter);

        fetchProducts();
    }
    // method to Fetch the products data entered in manually  from Firestore
     private void fetchProducts()
     {

         db.collection("products").get().addOnSuccessListener(queryDocumentSnapshots ->
         {
             productsList.clear();

             for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
             {
                 GroceryProducts products = doc.toObject(GroceryProducts.class);
                 productsList.add(products);
             }
             adapter.notifyDataSetChanged();
         })
         .addOnFailureListener(e -> android.util.Log.e("FIREBASE", "Error loading products", e));
     }




}