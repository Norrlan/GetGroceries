package com.example.getgroceries;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2)
    {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
        // Asda Fragment
        ImageView asdaCard = view.findViewById(R.id.imageView9);

        asdaCard.setOnClickListener(v ->
        {
            ((MainActivity) getActivity()).openFragment(new AsdaFragment());
        });

        // Ebay Fragment
        ImageView ebayCard  = view.findViewById(R.id.imageView6);
        ebayCard.setOnClickListener(V->
        {
            ((MainActivity) getActivity()).openFragment(new EbayFragment());
        });

        // Ice land Fragment
        ImageView iceCard  = view.findViewById(R.id.imageView7);
        iceCard.setOnClickListener(V->
        {
            ((MainActivity) getActivity()).openFragment(new IcelandFragment());
        });

        //User profile Icon - top right corer
        ImageView profilefragment = view.findViewById(R.id.profileicon);
        profilefragment.setOnClickListener(v ->
                {
                    ((MainActivity) getActivity()).openFragment(new ProfileFragment());
                });


    }





}