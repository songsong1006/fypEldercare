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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }
}
