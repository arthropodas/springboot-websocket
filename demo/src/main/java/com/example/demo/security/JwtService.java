package com.example.demo.security;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;


    public  JwtService(SecretKey key, long expirationMs, SecretKey key1, long expirationMs1){

        this.key = key1;
        this.expirationMs = expirationMs1;
    }

}
