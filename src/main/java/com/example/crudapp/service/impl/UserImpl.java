package com.example.crudapp.service.impl;

import com.example.crudapp.dtos.UserDto;
import com.example.crudapp.entities.User;
import com.example.crudapp.exception.ResourceNotFoundException;
import com.example.crudapp.repository.UserRepository;
import com.example.crudapp.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class UserImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        User user = dtoToEntity(userDto);
        User saveUser = userRepository.save(user);
        UserDto newDto = entityToDto(saveUser);
        return newDto;
    }




    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("userNot found"));
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updateUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updateUser);

        return updatedDto;
    }

    @Override
    public void deleteUser(String userId)
    {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user not found"));
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
       /**   List<UserDto> dtolist =   users.stream().map(user ->entityToDto(user)).collect(Collectors.toList()); **/
        List<UserDto> dtolist = users.stream()
                .map(user->entityToDto(user))
                .toList();

        return dtolist;
    }

    @Override
    public UserDto getUserById(String userid) {
        User user   =   userRepository.findById(userid).orElseThrow(()->new ResourceNotFoundException("user not found"));
        return  entityToDto(user);

    }

    public UserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User dtoToEntity(UserDto userDto) {


        return mapper.map(userDto,User.class);

    }
    private UserDto entityToDto(User saveUser) {


        return mapper.map(saveUser,UserDto.class);
    }
}

