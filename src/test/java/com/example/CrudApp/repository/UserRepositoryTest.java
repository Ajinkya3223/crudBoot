package com.example.CrudApp.repository;

import com.example.CrudApp.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
@Autowired
UserRepository userRepository;
    @Test
    void getUserById() {
        User user=new User("2","Krishna","krishan@gmail.com","123456789","male","bvgfcdxsazdr");
        userRepository.findById("2");

    }
}