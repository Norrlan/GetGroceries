package com.example.getgroceries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListsView extends ViewModel {

    private final MutableLiveData<List<UserList>> listsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> listAlreadyExistsLiveData = new MutableLiveData<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private boolean listenerRegistered = false;

    public LiveData<List<UserList>> getLists()
    {
        return listsLiveData;
    }

    public LiveData<Boolean> getListAlreadyExists()
    {
        return listAlreadyExistsLiveData;
    }

    // Only register listener once
    public void loadLists()
    {
        if (listenerRegistered) return; // ADD THIS CHECK
        listenerRegistered = true;

        db.collection("lists").addSnapshotListener((snapshot, error) ->
        {
            if (error != null || snapshot == null) return;

            List<UserList> loaded = new ArrayList<>();
            for (var doc : snapshot.getDocuments())
            {
                UserList list = doc.toObject(UserList.class);
                if (list != null)
                {
                    list.setId(doc.getId());
                    loaded.add(list);
                }
            }
            listsLiveData.setValue(loaded);
        });
    }

    // Create a new list and save to Firestore
    public void createList(String name, ListItem firstItem)
    {
        db.collection("lists").whereEqualTo("name", name).get().addOnSuccessListener(query -> {
                    if (!query.isEmpty())
                    {
                        listAlreadyExistsLiveData.setValue(true);
                        return;
                    }
                    UserList newList = new UserList(name);
                    newList.addItem(firstItem);
                    db.collection("lists").add(newList);

                });
    }

    // Add item to existing list in Firestore
    public void addItemToList(String listId, ListItem item) {
        DocumentReference ref = db.collection("lists").document(listId);
        ref.get().addOnSuccessListener(doc ->
        {
            UserList list = doc.toObject(UserList.class);
            if (list == null) return;
            list.addItem(item);
            ref.set(list);

        });
    }

    // Remove a specific item from a list in Firestore
    public void removeListItem(String listId, String itemId)
    {
        DocumentReference ref = db.collection("lists").document(listId);
        ref.get().addOnSuccessListener(doc -> {
            UserList list = doc.toObject(UserList.class);
            if (list == null) return;
            list.removeItem(itemId);
            ref.set(list);

        });
    }

    // Delete an entire list from Firestore
    public void deleteList(String listId)
    {
        db.collection("lists").document(listId).delete();

    }

    // Get a list by id from local LiveData
    public UserList getListById(String listId)
    {
        List<UserList> current = listsLiveData.getValue();
        if (current == null) return null;
        for (UserList list : current)
        {
            if (list.getId().equals(listId))
            {
                return list;
            }
        }
        return null;
    }
}
