package com.example.getgroceries;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity
{

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNav);

        // default fragment on start
        loadFragment(new HomeFragment());

        // Handle bottom navigation item selection.
        bottomNav.setOnItemSelectedListener(item ->
        {

            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.navigation_home)
            {
                selectedFragment = new HomeFragment();
            }
            else if (item.getItemId() == R.id.navigation_search)
            {
                selectedFragment = new SearchFragment();
            }
            else if (item.getItemId() == R.id.navigation_lists)
            {
                selectedFragment = new ListsFragment();
            }
            else if (item.getItemId() == R.id.navigation_recipes)
            {
                selectedFragment = new RecipesFragment();
            }
            // Replace the current fragment with the selected one.

            return loadFragment(selectedFragment);
        });
    }
// mthod to load  by replacing the fragment in the main container.
    private boolean loadFragment(Fragment fragment)
    {
        if (fragment == null) return false;

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment)
                .commit();

        return true;
    }


    public void openFragment (Fragment fragment)
    {
       // open new fragments and add them to the back stack.
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).addToBackStack(null)
                .commit();


    }




}
