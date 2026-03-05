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

public class ListDetailsFragment extends Fragment {

    private ListsView listsView;
    private RecyclerView recyclerView;
    private ListDetailsAdapter adapter;
    private String listId;

    public ListDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            listId = getArguments().getString("listId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.list_details_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listsView = new ViewModelProvider(requireActivity()).get(ListsView.class);

        listsView.getLists().observe(getViewLifecycleOwner(), userLists -> {
            UserList selectedList = listsView.getListById(listId);

            if (selectedList != null) {
                adapter = new ListDetailsAdapter(
                        selectedList.getItems(),
                        item -> deleteItem(item.getId())//Cannot resolve method 'getId()'
                );
                recyclerView.setAdapter(adapter);// 'setAdapter(androidx.recyclerview.widget.RecyclerView.Adapter)' in 'androidx.recyclerview.widget.RecyclerView' cannot be applied to '(com.example.getgroceries.ListDetailsAdapter)'
            }
        });
    }

    private void deleteItem(String itemId) {
        listsView.removeListItem(listId, itemId);
    }
}