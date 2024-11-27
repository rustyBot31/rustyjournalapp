package com.rustyleague.rustyjournal.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rustyleague.rustyjournal.repo.userrepo;

@SpringBootTest
public class userServiceTests {

    @Autowired
    private userrepo userRepo;

    @Test
    public void testFindByUserName() {
        assertNotNull(userRepo.findByusername("RadhaKrishna"));
    }
}
