package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * create an instance of this fragment.
 * Reference for Search View: https://developer.android.com/develop/ui/views/search/search-dialog accessed: 24/02/2026
 */
public class SearchFragment extends Fragment
{
// Static category list for demo
    private List <String> category = Arrays.asList(
            "Fresh Food",
        "Bakery",
        "Drinks",
        "Frozen",
        "Household",
        "Snacks",
        "Fruit & Veg",
        "Meat & Fish"
);


    private void openCategory(int position)
    {
        Fragment fragment;
        switch (position)
        {
            case 0:
                fragment = new DrinksFragment();
                break;
            case 1:
                fragment = new  SnacksFragment();
                break;
            case 2:
                fragment = new BakeryFragment();
                break;
            case 3:
                fragment = new FrozenFragment();
                break;
            case 4:
                fragment = new CannedFoodFragment();
                break;
            case 5:
                fragment = new CondimentsFragment();
                break;
            default:
                return;
        }
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null).commit();
    }




    public SearchFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_search,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.category_recycler);
        List<Integer> categoryImages = Arrays.asList(
                // add more catergory cards
                R.drawable.drinks_card,
                R.drawable.snacks,
                R.drawable.bakery,
                R.drawable.canfood,
                R.drawable.frozenfood,
                R.drawable.condiment
        );

        //Searchadapter searchadapter = new Searchadapter(categoryImages);
       // recyclerView.setAdapter(searchadapter);
        //recyclerView.setLayoutManager( new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        Searchadapter adapter = new Searchadapter(categoryImages, position ->
        {
            openCategory(position);// why eerro here?
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return view;


        
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_search, container, false);
    }

   

   
    
}