package com.example.movierec;

public class User {
    public String username , email , password;

    public User(){

    }

    public User(String username,String email ,String password){
        this.email=email;
        this.username=username;
        this.password=password;
    }
}
