package com.example.kursovaoop;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class SalaryViewModel extends ViewModel {

    private MutableLiveData<List<Salary>> salaryList;

    public MutableLiveData<List<Salary>> getSalaryList() {
        if (salaryList == null) {
            salaryList = new MutableLiveData<>();
        }
        return salaryList;
    }

    public void loadSalaries(DatabaseHelper databaseHelper) {
        if (salaryList == null) {
            salaryList = new MutableLiveData<>();
        }
        List<Salary> salaries = databaseHelper.getAllSalaries();
        salaryList.setValue(salaries);
    }

    public void addSalary(String amount, DatabaseHelper databaseHelper, String firstName, String lastName,
                          String cardNumber, String bankAccount, double insuranceContributions, double socialBenefits) {
        databaseHelper.addSalary(amount, firstName, lastName, cardNumber, bankAccount, insuranceContributions, socialBenefits);
        loadSalaries(databaseHelper);
    }

}



