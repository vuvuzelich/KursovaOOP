package com.example.kursovaoop;

public class User {
    private String fullName;

    private String position;
    private String grade;

    private String email;


    public User(String fullName, String position, String grade, String email ) {
        this.fullName = fullName;
        this.position = position;
        this.grade = grade;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPosition() {
        return position;
    }

    public String getGrade() {
        return grade;
    }

    public String getEmail() {
        return email;
    }


}


