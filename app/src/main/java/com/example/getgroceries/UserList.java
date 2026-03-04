// this class holds the list name,id and a list item
package com.example.getgroceries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class UserList
{
    private String id;
    private String name;
    private List<ListItem> items;

    public  UserList(String name)
    {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.items = new ArrayList<>();
    }

    public  String getId()
    {
        return id;
    }

    public  String getName()
    {
        return  name;
    }

    public List<ListItem> getItems()
    {
        return items;
    }

    public void addItem(ListItem item)
    {
        items.add(item);
    }

    public void removeItem(String itemId) // for removing items in a list
    {
        items.removeIf(item -> getId().equals(itemId));
    }

}
