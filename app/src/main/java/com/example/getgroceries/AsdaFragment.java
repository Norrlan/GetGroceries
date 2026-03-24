package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class AsdaFragment extends Fragment
{

    public AsdaFragment()
    {
        // Required empty public constructor
    }

    public static AsdaFragment newInstance(String param1, String param2)
    {
        AsdaFragment fragment = new AsdaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // default fragment on start
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asda, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.asda_list);

        ArrayList<String> aisles = CategoryList.getInstance().asdaAisles; // Get the List from CategoryList singleton

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (
                getContext(),
                android.R.layout.simple_list_item_1, aisles
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) ->
        {

            String selectedAisle = aisles.get(position);
            Toast.makeText(getContext(), "Clicked: " + selectedAisle, Toast.LENGTH_SHORT).show();

            Fragment nextFragment = null;

            switch (selectedAisle)  // The Categories that will be used for the Asda Store
                    // API will fetch data into these Categories
            {
                case "Drinks":
                    nextFragment = new DrinksSubcategoryFragment();
                    break;
                case "Bakery":
                    nextFragment = new BakerySubcategoryFragment();
                    break;
                case "Frozen Food":
                    nextFragment = new FrozenSubcategoryFragment();
                    break;
                case "Snacks":
                    nextFragment = new SnacksSubcategoryFragment();
                    break;
                case "Canned Food":
                    nextFragment = new CannedSubcategoryFragment();
                    break;
                case "Condiments":
                    nextFragment = new CondimentsSubcategoryFragment();
                    break;
            }

            if (nextFragment != null)
            {
                ((MainActivity) getActivity()).openFragment(nextFragment);

            }
        });




        }


}