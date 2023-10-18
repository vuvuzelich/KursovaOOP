package com.example.kursovaoop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<User> user = new MutableLiveData<>();

    public void setUser(User newUser) {
        user.setValue(newUser);
    }

    public LiveData<User> getUser() {
        return user;
    }
}

