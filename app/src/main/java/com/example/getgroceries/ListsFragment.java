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
import android.widget.Toast;

import com.example.getgroceries.UserList;
import com.example.getgroceries.ListsView;

import java.util.List;

public class ListsFragment extends Fragment
{

    private ListsView listsView;
    private RecyclerView recyclerView;
    private ListsAdapter adapter;

    public ListsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.lists_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listsView = new ViewModelProvider(requireActivity()).get(ListsView.class);

        listsView.loadLists();

        listsView.getListAlreadyExists().observe(getViewLifecycleOwner(), exists ->
        {
            if (exists != null && exists)
            {
                Toast.makeText(getContext(),
                        "List already exists", Toast.LENGTH_SHORT).show();
            }
        });

        listsView.getLists().observe(getViewLifecycleOwner(), userLists ->
        {
            if (adapter == null)
            {
                adapter = new ListsAdapter(
                        userLists,
                        list -> openListDetails(list),
                        list -> listsView.deleteList(list.getId())
                );
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateLists(userLists);
            }
        });
    }

    private void openListDetails(UserList list)
    {
        ListDetailsFragment fragment = new ListDetailsFragment();

        Bundle args = new Bundle();
        args.putString("listId", list.getId());
        fragment.setArguments(args);

        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}