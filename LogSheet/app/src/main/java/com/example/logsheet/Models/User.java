package com.example.logsheet.Models;

public class User {
    private int id;
    private String username;
    private String password;
    private String gender;
    private int age;
    private float height;
    private float weight;

    // Constructor
    public User(int id, String username, String password, String gender, int age, float height, float weight) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    // Getters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public float getHeight() { return height; }
    public float getWeight() { return weight; }
}
