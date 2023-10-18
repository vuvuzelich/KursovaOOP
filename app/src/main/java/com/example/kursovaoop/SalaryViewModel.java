package com.example.kursovaoop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class SalaryViewModel extends ViewModel {

    private MutableLiveData<List<String>> salaryList;

    public LiveData<List<String>> getSalaryList() {
        if (salaryList == null) {
            salaryList = new MutableLiveData<>();
        }
        return salaryList;
    }

    public void loadSalaries(DatabaseHelper databaseHelper) {
        if (salaryList == null) {
            salaryList = new MutableLiveData<>();
        }
        List<String> salaries = databaseHelper.getAllSalaries();
        salaryList.setValue(salaries);
    }

    public void addSalary(String amount, DatabaseHelper databaseHelper) {
        // Додаємо розрахунок до бази даних
        databaseHelper.addSalary(amount);
        // Оновлюємо список розрахунків
        loadSalaries(databaseHelper);
    }
}



