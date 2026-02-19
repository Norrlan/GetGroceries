package com.example.getgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EbayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EbayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EbayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EbayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EbayFragment newInstance(String param1, String param2) {
        EbayFragment fragment = new EbayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ebay, container, false);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.ebay_list);

        ArrayList<String> aisles = CategoryList2.getInstance().getEbayAisles(); // Get the List from CategoryList singleton

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (
                        getContext(),
                        android.R.layout.simple_list_item_1, aisles
                );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedAisle = aisles.get(position);
            Toast.makeText(getContext(), "Clicked: " + selectedAisle, Toast.LENGTH_SHORT).show();

            // The Category Fragements
            Fragment nextFragment = null;

            // replace with fragments for ebay later
            switch (selectedAisle) {
                case "Fruit":
                    nextFragment = new AsdaFruitFragment();
                    break;
                case "Bakery":
                    nextFragment = new AsdaBakeryFragment();
                    break;
                case "Frozen Food":
                    nextFragment = new AsdaFFoodFragment();
                    break;
                case "Laundry":
                    nextFragment = new AsdaLaundryFragment();
                    break;
            }

            if (nextFragment != null) {
                ((MainActivity) getActivity()).openFragment(nextFragment);
            }
        });
    }

}