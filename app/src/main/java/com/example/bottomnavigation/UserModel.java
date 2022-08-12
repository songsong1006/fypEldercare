package com.example.bottomnavigation;

public class UserModel {

    public String email, username, age, gender, children;

    public UserModel(){

    }

    public UserModel(String email, String username, String age, String gender, String children){
        this.email = email;
        this.username = username;
        this.age = age;
        this.gender = gender;
        this.children = children;
    }

}
