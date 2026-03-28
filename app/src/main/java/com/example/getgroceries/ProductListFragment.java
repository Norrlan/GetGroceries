package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ProductListFragment extends Fragment
{
private static final  String ARG_INDEX = "subcategory_index";
private static final String ARG_TITLE = "subcategory_title";

private int subcategoryIndex;
private String subcategoryTitle;

 public ProductListFragment()
    {
        // Required empty public constructor
    }

    public static ProductListFragment newInstance(int index, String title)
    {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INDEX, index);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            subcategoryIndex = getArguments().getInt(ARG_INDEX);
            subcategoryTitle = getArguments().getString(ARG_TITLE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        TextView titleView = view.findViewById(R.id.product_title);
        titleView.setText(subcategoryTitle);

        RecyclerView recyclerView = view.findViewById(R.id.product_recycler);

        // Load the correct products for this subcategory
      //  List<ProductModel> productModels = loadSubcategoryProduct(subcategoryIndex);

        // Pass requireActivity() so ProductAdapter can use ViewModel safely
      //  ProductAdapter productAdapter = new ProductAdapter(productModels, requireActivity());

       // recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
// placeholder lists

    /**
     *  private List<ProductModel> loadSubcategoryProduct(int subcategoryIndex)
     *     {
     *         switch (subcategoryIndex)
     *         {
     *             case 0: // Energy Drinks Subcategory Products List
     *                 return Arrays.asList
     *                         (new ProductModel("Red Bull 250ml", "£1.35", R.drawable.redbull),
     *                         new ProductModel("Monster Energy 500ml", "£1.50", R.drawable.monster),
     *                         new ProductModel("Prime Original 500ml", "£1.45", R.drawable.primedrink)
     *                 );
     *             case 1: // Tea, Coffee & Beverages Subcategory Products List
     *                 return Arrays.asList(
     *                         new ProductModel("Starbucks Caramel Iced Coffee", "£2.75", R.drawable.starbucks1),
     *                         new ProductModel("Costa Iced Latte", "£1.80", R.drawable.costa)
     *                 );
     *             case 2: // Beer, Wine and Spirits Subcategory Products List
     *                 return Arrays.asList(
     *                         new ProductModel("Heineken 6-pack", "£5.00", R.drawable.heineken),
     *                         new ProductModel("Smirnoff Vodka 70cl", "£14.00", R.drawable.smirnoff)
     *                 );
     *             case 3: // Soft Drinks Subcategory Products List
     *                 return Arrays.asList(
     *                         new ProductModel("Coca-Cola 1.5L", "£1.90", R.drawable.cokeltr),
     *                         new ProductModel("Sprite 500ml", "£1.20", R.drawable.sprite)
     *                 );
     *             case 4: // Fruit Juice Subcategory Products List
     *                 return Arrays.asList(
     *                         new ProductModel("Tropicana Orange Juice 1L", "£2.50", R.drawable.tropicana),
     *                         new ProductModel("Mango Juice 1L", "£2.00", R.drawable.mangojuice)
     *                 );
     *
     *             case 5: // Squash Subcategory Products List
     *                 return Arrays.asList(
     *                         new ProductModel("Vimto Squash 1L", "£1.50", R.drawable.vimto),
     *                         new ProductModel("Ribena Blackcurrant 1L", "£2.00", R.drawable.ribena)
     *                 );
     *             default:
     *                 return new ArrayList<>();
     *         }
     *     }
     *
     */
    



}