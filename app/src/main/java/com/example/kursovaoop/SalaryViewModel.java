package com.example.kursovaoop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class SalaryViewModel extends ViewModel {
    private MutableLiveData<List<String>> salaryList = new MutableLiveData<>();

    public LiveData<List<String>> getSalaryList() {
        if (salaryList.getValue() == null) {
            salaryList.setValue(new ArrayList<>());
        }
        return salaryList;
    }

    public void addSalary(String salary) {
        List<String> currentList = salaryList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(salary);
        salaryList.setValue(currentList);
    }
}



