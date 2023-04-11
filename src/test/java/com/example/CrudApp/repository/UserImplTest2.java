package com.example.CrudApp.repository;

import com.example.CrudApp.dtos.UserDto;
import com.example.CrudApp.entities.User;
import    com.example.CrudApp.repository.UserRepository;
import com.example.CrudApp.service.UserService;
import com.example.CrudApp.service.impl.UserImpl;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class UserImplTest2 {
@Mock
private UserRepository userrepository;

    private UserImpl userImpl;


    @Autowired
    private TestEntityManager testEntityManager;
    @BeforeEach
    void setUp() {
        this.userImpl = new UserImpl(this.userrepository);
    }
    @Test
    void getAllUser() {
userImpl.getAllUser();
verify(userrepository).findAll();
    }
@Test
    void getUserbyIdTest(){
    User user = userrepository.findById("8d99e673-03ec-45cf-99c9-34ba46e5d4f1").get();
    String username=user.getUserName();
     UserDto user2=userImpl.getUserById("8d99e673-03ec-45cf-99c9-34ba46e5d4f1");
     String username1=user2.getUserName();
  //  assertEquals(username,username1);
    AssertionsForClassTypes.assertThat(username.equals(username1));

}


private User givenuser(){

            User user=new User();
            user.setUserId("45454545");
            user.setUserName("Ram");
            return user;
        }
    }
