package com.example.crudapp.service;

import com.example.crudapp.dtos.UserDto;
import com.example.crudapp.entities.User;
import com.example.crudapp.exception.ResourceNotFoundException;
import com.example.crudapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.mockito.Mockito.*;
@Slf4j
@SpringBootTest
 class UserServiceTest {
   @MockBean    // this make dummmy repo available for the spring context
   private UserRepository userRepository;

    @Autowired
    private  UserService userService;

    User user;

//    @Autowired
//    private MockMvc mockMvc;


    @Autowired
    private ModelMapper mapper;
    @BeforeEach
    public void init() {



        user = User.builder()
                .userName("Ram")
                .Email("Ramkrishna@gmail.com").about("This is testing create method")
                .gender("Male")
                .Password("1234567899")
                .build();



    }
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }


    @Test
     void createUserTest(){
        when(userRepository.save(any())).thenReturn(user);
        UserDto user1=userService.createUser(mapper.map(user, UserDto.class));
        assertNotNull(user1);
        Assertions.assertEquals("Ram", user1.getUserName());
    }
    @Test
     void updateUserTest(){
        String userId = "hosdhfosdhvo";
        UserDto userDto = UserDto.builder()
                .userName("Shriram")
                .about("This is updated user about details")
                .gender("Male")

                .build();

        when(userRepository.findById(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto, userId);
//        UserDto updatedUser=mapper.map(user,UserDto.class);
        System.out.println(updatedUser.getUserName());
        log.info("updated user {}",updatedUser.getUserName());
        assertNotNull(userDto);
        Assertions.assertEquals(userDto.getUserName(), updatedUser.getUserName(), "Name is not validated !!");
        //multiple assertion are valid..

    }

    @Test
     void deleteUserTest() {
        String userid = "abcde";
        when(userRepository.findById("abcde")).thenReturn(Optional.of(user));
        userService.deleteUser(userid);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
       // this method denotes that how many times delete method was invoked on userRepository Object how many times
        // delete moethod executes

    }
    @Test
     void getAllUsersTest2() {
        User user1 = User.builder()
                .userName("Ganesh")
                .Email("Ganesh@gmail.com").about("This is  method")
                .gender("Male")
                .Password("123456789999")
                .build();
        User user2 = User.builder()
                .userName("Hanuman")
                .Email("Hanuman@gmail.com").about("This is  method")
                .gender("Male")
                .Password("123456789jjj999")
                .build();
        List<User> userList = Arrays.asList(user, user1, user2);
//        Page<User> page = new PageImpl<>(userList);
//        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
//        PageableResponse<UserDto> allUser = (PageableResponse<UserDto>) userService.getAllUser();
//        Assertions.assertEquals(3, allUser.getContent().size());

        when(userRepository.findAll()).thenReturn(userList);
        List<UserDto> userDtos = userService.getAllUser();
        log.info("list of users {}", userDtos);
        Assertions.assertEquals(3, userDtos.size(), "something went wrong");


    }


    @Test
     void getUserByIdTest() {

        String userId = "userIdTest";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //actual call of service method

        UserDto userDto = userService.getUserById(userId);

        assertNotNull(userDto);
        Assertions.assertEquals(user.getUserName(), userDto.getUserName(), "Name not matched !!");


    }


    @Test
     void testGetUserById_UserNotFound() {
        // Arrange
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

        // Act
        try {
            userService.getUserById("user1");
            Assertions.fail("Expected ResourceNotFoundException to be thrown");
        } catch (ResourceNotFoundException e) {
            // Assert
            Assertions.assertEquals("user not found", e.getMessage());
        }
    }
    @Test
    void testUpdateUserWhenUserNotFound() {
        // given
        String userId = "1";
        UserDto userDto = UserDto.builder()
                .userName("Ram")
                .Email("Ramkrishna@gmail.com").about("This is testing create method")
                .gender("Male")
                .Password("1234567899")
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // when + then
        Throwable exception=Assertions.assertThrows(RuntimeException.class, () -> userService.updateUser(userDto, userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).save(any());
      assertNotNull(exception);
        when(userRepository.findById(userId)).thenThrow(new RuntimeException());
        verify(userRepository,never()).save(any());
    }

    @Test
    void testGetUserByIdThrowsResourceNotFoundException() {
        String userId = "1";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
       Throwable exception= assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(userId);
        });
        assertNotNull(exception);
    }

    @Test
     void deleteUserTest_userNotFound() {
        String userId = "ac656789";
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());

       Throwable exception= assertThrows(RuntimeException.class, () -> userService.deleteUser(userId));
       assertNotNull(exception);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any());

    }
     @Test
    void deleteUSer_notfound(){
        String id="fdj4578h";
                when(userRepository.findById(anyString())).thenReturn(Optional.empty());// this will throw exception because this will return empty object
            Throwable exception=    assertThrows(RuntimeException.class,()->userService.deleteUser(id));
         // Verify that the expected exception was thrown
         assertNotNull(exception);
         verify (userRepository,never()).delete(any());

    }


}

