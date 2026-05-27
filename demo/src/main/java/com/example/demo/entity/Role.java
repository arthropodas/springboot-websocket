package com.example.demo.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    SUBSCRIBER(1,"subscriber"),
    ADMIN(2,"admin");


    private final  int id;
    private  String role;

    Role(int id, String role) {

        this.id = id;
        this.role = role;

    }
}
