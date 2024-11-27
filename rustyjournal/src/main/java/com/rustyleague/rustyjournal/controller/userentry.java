package com.rustyleague.rustyjournal.controller;

import java.beans.Expression;
import java.net.http.HttpClient;
import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rustyleague.rustyjournal.entity.journal;
import com.rustyleague.rustyjournal.entity.quote;
import com.rustyleague.rustyjournal.entity.user;
import com.rustyleague.rustyjournal.service.journalService;
import com.rustyleague.rustyjournal.service.quotesService;
import com.rustyleague.rustyjournal.service.seqGenService;
import com.rustyleague.rustyjournal.service.userSevice;


@RestController
@RequestMapping("/users")
public class userentry {

    @Autowired
    private userSevice userSevice;

    @Autowired
    private journalService journalService;

    @Autowired
    private quotesService quotesService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody user myUser) {
        try {
            org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username=auth.getName();
            user oldUser=userSevice.getByUsername(username);
            if(myUser.getUsername()!=null) {
                oldUser.setUsername(myUser.getUsername());
            }
            if(myUser.getPassword()!=null) {
                oldUser.setPassword(myUser.getPassword());
                userSevice.saveNewUser(oldUser);
            }
            userSevice.saveUser(oldUser);
            return new ResponseEntity<>(oldUser,HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        try {
            org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username=auth.getName();
            user myUser=userSevice.getByUsername(username);
                HashSet<String> set=myUser.getList();
                for(String s:set) {
                    journalService.deleteJournal(s);
                } 
                userSevice.deleteUser(username);
                return new ResponseEntity<>(myUser,HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> greeting() throws JsonMappingException, JsonProcessingException {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();
        quote[] quote=quotesService.getQuote();
        String greeting="";
        greeting="\""+quote[0].getQuote()+"\""+"\n\n"+"- "+quote[0].getAuthor();
        return new ResponseEntity<>("Hi, "+username+"\n\n"+greeting,HttpStatus.OK);
    }
}
