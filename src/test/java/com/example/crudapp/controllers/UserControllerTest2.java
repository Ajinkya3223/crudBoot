package com.example.crudapp.controllers;

import com.example.crudapp.dtos.UserDto;
import com.example.crudapp.entities.User;
import com.example.crudapp.repository.UserRepository;
import com.example.crudapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
 class UserControllerTest2 {

    @MockBean
    private UserService userService;


    @MockBean
    private UserRepository userRepository;

    private User user;
    @Autowired
    private ModelMapper mapper;


    @Test
    void testGetUserById() throws Exception {

        TestRestTemplate restTemplate = new TestRestTemplate();

        String userId = "123";
        UserDto userDto = new UserDto();
        userDto.setUserId(userId);
        userDto.setUserName("abhishek");
        userDto.setEmail("abhishek@gmail.com");
        userDto.setGender("male");
        userDto.setAbout("this is abhishek");
        userDto.setPassword("23451678");

        WireMockServer wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        // Stubbing the response of the userService.getUserById() method
        stubFor(get(urlEqualTo("/users/" + userId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(convertObjectToJsonString(userDto))));

        // When
        ResponseEntity<UserDto> response = restTemplate.getForEntity("http://localhost:8080/users/" + userId, UserDto.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto.toString(), response.getBody().toString());


        wireMockServer.stop();

    }


    @Test
    void getAllUserstest() throws Exception {

        TestRestTemplate restTemplate = new TestRestTemplate();


        WireMockServer  wireMockServer = new WireMockServer( 8080);
        wireMockServer.start();

        UserDto user1= UserDto.builder()
                .userName("Krushna")
                .Email("krushnaRam@gmail.com").about("This is testing create method")
                .gender("Male")
                .Password("1234567899")
                .build();
        UserDto user2= UserDto.builder()
                .userName("Rama")
                .Email("RamRam@gmail.com").about("This is testing create method")
                .gender("Male")
                .Password("123456784599")
                .build();
        List<UserDto> userdt= List.of(user1,user2);
        UserDto userDto = mapper.map(user1, UserDto.class);



        // Stubbing the response of the userService.getUserById() method
        stubFor(get(urlEqualTo("/userdata/getall"))
                .willReturn(aResponse()
                        .withStatus(200).withFixedDelay(2000)
                        .withHeader("Content-Type", "application/json")
                        .withBody(convertObjectToJsonString(userDto))));

        // When
        ResponseEntity<UserDto> response = restTemplate.getForEntity("http://localhost:8080/userdata/getall" , UserDto.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto.toString(), response.getBody().toString());
        wireMockServer.stop();

    }



    @Test
    void postcreateUser() throws Exception {

        WireMockServer  wireMockServer = new WireMockServer( 8080);
        wireMockServer.start();
        TestRestTemplate restTemplate = new TestRestTemplate();

        UserDto user1= UserDto.builder()
                .userName("Krushna")
                .Email("krushnaRam@gmail.com").about("This is testing create method")
                .gender("Male")
                .Password("1234567899")
                .build();

        // UserDto userDto = mapper.map(user1, UserDto.class);



        // Stubbing the response of the userService.getUserById() method
        stubFor(post(urlEqualTo("/userdata/save"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(convertObjectToJsonString(user1))));

        // When
        ResponseEntity<UserDto> response = restTemplate.postForEntity("http://localhost:8080/userdata/save",user1,UserDto.class);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user1.toString(), response.getBody().toString());
        wireMockServer.stop();

    }

    @Test
    void deleteUserTestwire() throws Exception {

        log.info("started deleteuserwiremocktest");
        WireMockServer  wireMockServer = new WireMockServer( 8080);
        wireMockServer.start();
        TestRestTemplate restTemplate = new TestRestTemplate();
        String userId="123";
        UserDto user1= UserDto.builder()
                .userName("Krushna")
                .Email("krushnaRam@gmail.com").about("This is testing delete method")
                .gender("Male")
                .Password("1234567899")
                .build();

        UserDto userDto = mapper.map(user1, UserDto.class);



        // Stubbing the response of the userService.getUserById() method
        stubFor(delete(urlEqualTo("/userdata/"+userId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));
        // .withBody(convertObjectToJsonString(userDto))));

        // When
        restTemplate.delete("http://localhost:8080/userdata/"+userId);


        // Then
        // assertEquals(HttpStatus.OK, response.getStatusCode());
        // assertEquals(userDto.toString(), response.getBody().toString());
        verify(exactly(1), deleteRequestedFor(urlPathEqualTo("/userdata/" + userId)));
        log.info("completed the first verifiaction");

        // Define the endpoint path and ID of the mapping to delete
        String endpointPath = "/mapping";
        int mappingId = 123;

        // Set up the mock response for the DELETE request
        WireMock.stubFor(WireMock.delete(WireMock.urlEqualTo(endpointPath + "/" + mappingId))
                .willReturn(WireMock.noContent()));

        // Send the DELETE request to the mock server
        restTemplate.delete("http://localhost:8080/mapping/"+mappingId);

        // Verify that the mock server received the expected DELETE request
        WireMock.verify(WireMock.deleteRequestedFor(WireMock.urlEqualTo(endpointPath + "/" + mappingId)));
        log.info("completed the second verification");

        wireMockServer.stop();





    }




    private String convertObjectToJsonString(Object user) {

        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
