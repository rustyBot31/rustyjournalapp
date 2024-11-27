package com.rustyleague.rustyjournal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rustyleague.rustyjournal.entity.user;
import com.rustyleague.rustyjournal.service.userSevice;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    public userSevice userservice;
    
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<user> list=userservice.getAll();
        if(list!=null && !list.isEmpty()) {
            return new ResponseEntity<>(list,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
