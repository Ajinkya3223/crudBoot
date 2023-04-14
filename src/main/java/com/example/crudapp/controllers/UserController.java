package com.example.crudapp.controllers;

import com.example.crudapp.dtos.ApiResponseMsg;
import com.example.crudapp.dtos.UserDto;
import com.example.crudapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserController {
    @Autowired
    private UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping("/save")
    public ResponseEntity<UserDto> createUser(@RequestBody  UserDto userDto)
    {
        UserDto userDto1 = userService.createUser(userDto);

        return  new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") String userId,@RequestBody  UserDto userDto)
    {
        UserDto userDto1 = userService.updateUser(userDto,userId);
        return  new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMsg> deleteUser(@PathVariable String userId)
    {
        userService.deleteUser(userId);
        ApiResponseMsg message =  ApiResponseMsg.builder().message("user deleted succesfully").success(true).status(HttpStatus.OK).build();
        logger.info("user deleted {}",message);
        return  new ResponseEntity<>(message,HttpStatus.OK);
    }
    //get all
    @GetMapping("/getall")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }
    //get single
    @GetMapping("/{userid}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userid)
    {
        logger.info("user of given id {} ",userService.getUserById(userid));
        return new ResponseEntity<>(userService.getUserById(userid),HttpStatus.OK);
    }
}
