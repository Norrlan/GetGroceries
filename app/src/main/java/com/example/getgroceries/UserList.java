package com.example.getgroceries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserList
{

    private String id;
    private String name;
    private List<ListItem> items;


    public UserList()
    {
        this.items = new ArrayList<>();
    }

    public UserList(String name)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getId()
    {
        return id;
    }

    // Firestore needs this setter
    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<ListItem> getItems()
    {
        return items;
    }

    public void setItems(List<ListItem> items)
    {
        this.items = items;
    }

    public void addItem(ListItem item)
    {
        items.add(item);
    }

    public void removeItem(String itemId)
    {
        items.removeIf(item -> item.getId().equals(itemId));
    }
}