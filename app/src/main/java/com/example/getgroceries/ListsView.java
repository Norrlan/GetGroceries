package com.example.getgroceries;
// this class is for holding and displaying the User Lists that have been created

import  androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.getgroceries.ListItem;
import com.example.getgroceries.UserList;

import java.util.ArrayList;
import java.util.List;
public class ListsView extends ViewModel

{
    private final MutableLiveData<List<UserList>> listsLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<UserList>> getLists()
    {
        return listsLiveData;
    }

    // method for creating Lists -
    public void createList(String name, ListItem firstItem)
    {
        List<UserList> current = listsLiveData.getValue();
        if (current == null) current = new ArrayList<>();

        UserList newList = new UserList(name);
        newList.addItem(firstItem);

        current.add(newList);
        listsLiveData.setValue(current);
    }
    // method for adding an item to the list
    public void addItemToList(String listId, ListItem item)
    {
        List<UserList> current = listsLiveData.getValue();
        if (current == null) return;

        for (UserList list : current)
        {
            if (list.getId().equals(listId))
            {
                list.addItem(item);
                break;
            }
        }

        listsLiveData.setValue(current);
    }

    // method to remove Lists on the List Screen
    public void  removeListItem(String listid, String itemId)
    {
        List<UserList> current = listsLiveData.getValue();
        if (current == null ) return;

        for (UserList list: current)
        {
            if (list.getId().equals(listid))
            {
                list.removeItem(itemId);
                break;
            }
        }
    }

    public UserList getListById(String listId)
    {
        List<UserList> current = listsLiveData.getValue();
        if (current == null) return null;

        for (UserList list: current)
        {
            if (list.getId().equals(listId))
            {
                return list;
            }
        }
        return null;
    }


}
