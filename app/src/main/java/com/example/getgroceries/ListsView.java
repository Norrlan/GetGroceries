package com.example.getgroceries;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

// ViewModel responsible for managing all list-related data.
// Handles Firestore reads and writes
// Get LiveData so the UI updates automatically.



public class ListsView extends ViewModel
{
    // LiveData holding all user-created lists.
    // Starts with an empty ArrayList to avoid null checks in the UI.

    private final MutableLiveData<List<UserList>> listsLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> listAlreadyExistsLiveData = new MutableLiveData<>();

    // Firestore instance used for all database operations.
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

    // method to load  all lists from Firestore using a real-time snapshot listener.
    // The listener is only registered once to avoid duplicate updates.
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

    // Method to create a new list and save to Firestore
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

    // Method to add item to existing list in Firestore
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

    // Method to remove a specific item from a list in Firestore
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

    // Method to delete an entire list from Firestore
    public void deleteList(String listId)
    {
        db.collection("lists").document(listId).delete();

    }

// Retrieves a list from the current LiveData cache using its ID.

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
