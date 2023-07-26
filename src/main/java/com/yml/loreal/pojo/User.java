package com.yml.loreal.pojo;

public class User {

    private String username;
    private String password;
    private String firtName;
    private String lastName;

    public User() {

    }

    public User(String firstName, String lastName,String username,String password) {
        this.username = username;
        this.password = password;
    }


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getFirtName() {
        return firtName;
    }

    public void setFirtName(String firtName) {
        this.firtName = firtName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
