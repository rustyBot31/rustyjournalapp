package com.rustyleague.rustyjournal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rustyleague.rustyjournal.entity.user;
import com.rustyleague.rustyjournal.service.seqGenService;
import com.rustyleague.rustyjournal.service.userDetails;
import com.rustyleague.rustyjournal.service.userSevice;
import com.rustyleague.rustyjournal.utils.jwtUtil;

@RestController
@RequestMapping("/public") 
public class publicControl {

    @Autowired
    public userSevice userSevice;

    @Autowired
    public AuthenticationManager authManager;

    @Autowired
    public userDetails userServeDetails;

    @Autowired
    public jwtUtil jwtUtil;

    /*@Autowired
    public seqGenService seqGenService;
    */

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody user myUser) {
        try {
            myUser.setId(seqGenService.generateSequence(user.SEQUENCE_NAME));
            myUser.setNoOfJournals(0);
            if(myUser.getUsername()==null) {
                throw new NullPointerException("No username");
            }
            
            myUser.getRoles().add("USER");
            userSevice.saveNewUser(myUser);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("try a new username!",HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody user myUser) {
        try {
           authManager.authenticate(new UsernamePasswordAuthenticationToken(myUser.getUsername(), myUser.getPassword()));
           UserDetails userDetails=userServeDetails.loadUserByUsername(myUser.getUsername());
           String jwt=jwtUtil.generateToken(userDetails);
           return new ResponseEntity<>(jwt,HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Wrong credentials!",HttpStatus.BAD_REQUEST);
        }
    }
}
