package com.example.crudapp.service;

import com.example.crudapp.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {




        //create

     public   UserDto createUser(UserDto userDto);

        //update

      public  UserDto updateUser(UserDto userDto, String userId);

        //delete
       public void deleteUser(String userId);
        //get all users
      public  List<UserDto> getAllUser();
        //get single user by email

        // get single user by id
      public  UserDto getUserById(String userid);





}
