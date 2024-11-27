package com.rustyleague.rustyjournal.controller;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.rustyleague.rustyjournal.entity.journal;
import com.rustyleague.rustyjournal.entity.user;
import com.rustyleague.rustyjournal.service.journalService;
import com.rustyleague.rustyjournal.service.userSevice;

@RestController
@RequestMapping("/journal")
public class journalentry {


    @Autowired
    private journalService journalService;

    @Autowired
    private userSevice userService;

    @PostMapping
    public ResponseEntity<?> saveEntry(@RequestBody journal myEntry) {
        try {
            org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username=auth.getName();
            user myUser=userService.getByUsername(username);
            myEntry.setDate(LocalDateTime.now());
            long count=myUser.getNoOfJournals();
            
            if(myEntry.getTitle()==null) {
                throw new NullPointerException("no title");
            }
            myUser.setNoOfJournals(count+1);
            String id=myUser.getId()+"_"+count;
            myEntry.setId(id);
            myEntry.setAuthor(myUser);
            journalService.saveEntry(myEntry);
            myUser.getList().add(myEntry.getId());
            userService.saveUser(myUser);
            //userService.saveUser(myUser);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getById(@PathVariable String myId) {
        try {
            org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username=auth.getName();
            user myUser=userService.getByUsername(username);
            HashSet<String> res=myUser.getList();
            if(res.contains(myId)) {
                return new ResponseEntity<>(journalService.get(myId),HttpStatus.OK);
            }
            throw new Exception();
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
    }


    @PutMapping("id/{myId}")
    public ResponseEntity<?> editJournal(@RequestBody journal myEntry, @PathVariable String myId) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();
        user myUser=userService.getByUsername(username);
        HashSet<String> res=myUser.getList();
        if (res.contains(myId)) {
            Optional<journal> oldEntry=journalService.get(myId);
            journal oldJournal=oldEntry.get();
            if(myEntry.getTitle()!=null) {
                oldJournal.setTitle(myEntry.getTitle());
            }
            if(myEntry.getContent()!=null) {
                oldJournal.setContent(myEntry.getContent());
            }
            journalService.saveEntry(oldJournal);
            return new ResponseEntity<>(oldJournal,HttpStatus.OK);
        }
            
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable String id) {
        org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username=auth.getName();
        user myUser=userService.getByUsername(username);
        HashSet<String> res=myUser.getList();
        if(res.contains(id)) {
            Optional<journal> journal=journalService.get(id);
        if(journal.isPresent()) {
            ResponseEntity<?> result=new ResponseEntity<>(journal.get(),HttpStatus.OK);
            journal journal1=journal.get();
            user user=journal1.getAuthor();
            user.getList().remove(journal1.getId());
            userService.saveUser(user);
            journalService.deleteJournal(id);
            return result;
        }
        }
        
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
