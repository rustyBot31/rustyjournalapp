package com.rustyleague.rustyjournal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rustyleague.rustyjournal.entity.user;
import com.rustyleague.rustyjournal.repo.userrepo;
@Component
public class userDetails implements UserDetailsService {

    @Autowired
    private userrepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user myUser=userRepo.findByusername(username);
        if(myUser!=null) {
            UserDetails userDetails=org.springframework.security.core.userdetails.User.builder()
            .username(myUser.getUsername())
            .password(myUser.getPassword())
            .roles(myUser.getRoles().toArray(new String[0]))
            .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("No user was found with the given username!");
    }
    
}
