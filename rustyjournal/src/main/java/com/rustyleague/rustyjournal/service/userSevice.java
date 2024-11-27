package com.rustyleague.rustyjournal.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.rustyleague.rustyjournal.entity.user;
import com.rustyleague.rustyjournal.repo.userrepo;

@Component
public class userSevice {

    @Autowired
    private userrepo userrepo;

    private static final PasswordEncoder passEncode = new BCryptPasswordEncoder();

    public List<user> getAll() {
        return userrepo.findAll();
    }
    
    public user getByUsername(String name) {
        return userrepo.findByusername(name);
    }

    public boolean saveUser(user myUser) {
        userrepo.save(myUser);
        return true;
    }

    public void saveNewUser(user myUser) {
        myUser.setPassword(passEncode.encode(myUser.getPassword()));
        userrepo.save(myUser);
    }
    
    public boolean deleteUser(String username) {
        userrepo.delete(userrepo.findByusername(username));
        return true;
    }
}
