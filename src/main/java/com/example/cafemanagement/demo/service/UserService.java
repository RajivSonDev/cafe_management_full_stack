package com.example.cafemanagement.demo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface UserService {

    public ResponseEntity<String> signUp(Map<String, String> requestMap);
}
