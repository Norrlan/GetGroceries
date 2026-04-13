package com.example.getgroceries;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListDetailsFragment extends Fragment
{

    private ListsView listsView;
    private RecyclerView recyclerView;
    private ListDetailsAdapter adapter;
    private String listId;

    public ListDetailsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            listId = getArguments().getString("listId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //inflate layout
        return inflater.inflate(R.layout.fragment_list_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // bind recycler view to the xml element
        recyclerView = view.findViewById(R.id.list_details_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // set the recycler views layout
        listsView = new ViewModelProvider(requireActivity()).get(ListsView.class); // open ListView activity that displays the Lists that have been created

        listsView.getLists().observe(getViewLifecycleOwner(), userLists ->
        {
            UserList selectedList = listsView.getListById(listId);

            if (selectedList != null)
            {
                adapter = new ListDetailsAdapter(
                        selectedList.getItems(),
                        item -> deleteItem(item.getId())
                );
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void deleteItem(String itemId)
    {
        listsView.removeListItem(listId, itemId);
    }
}