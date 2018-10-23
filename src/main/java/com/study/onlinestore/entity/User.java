package com.study.onlinestore.entity;

public class User {
    private String name;
    private String password;
    private UserType userType;

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userType=" + userType +
                '}';
    }
}
