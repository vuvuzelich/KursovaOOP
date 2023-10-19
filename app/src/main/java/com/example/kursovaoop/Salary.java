package com.example.kursovaoop;

public class Salary {
    private String amount;
    private String firstName;
    private String lastName;

    public Salary(String amount, String firstName, String lastName) {
        this.amount = amount;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAmount() {
        return amount;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

