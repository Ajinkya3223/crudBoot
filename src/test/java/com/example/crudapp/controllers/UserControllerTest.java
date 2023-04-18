package com.example.crudapp.controllers;

import com.example.crudapp.dtos.UserDto;
import com.example.crudapp.entities.User;
import com.example.crudapp.repository.UserRepository;
import com.example.crudapp.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
 class UserControllerTest {


    @MockBean
    private UserService userService;


    @MockBean
    private UserRepository userRepository;

    private User user;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
     void init() {


        user = User.builder()
                .userName("Krushna")
                .Email("krushnaRam@gmail.com").about("This is testing create method")
                .gender("Male")
                .Password("1234567899")
                .build();
    }
    @Test
     void createUserTest() throws Exception {

//        /users +POST+ user data as json
        //data as json+status created

        UserDto dto = mapper.map(user, UserDto.class);

        when(userService.createUser(Mockito.any())).thenReturn(dto);

        //actual request for url

        this.mockMvc.perform(MockMvcRequestBuilders.post("/userdata/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").exists());
    }


    @Test
     void updateUserTest() throws Exception {

        // /users/{userId} + PUT request+ json

        String userId = "123";
        UserDto dto = this.mapper.map(user, UserDto.class);

        when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/userdata/" + userId)

                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").exists());

    }
    @Test
     void getAllUsersTest() throws Exception {
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
        when(userService.getAllUser()).thenReturn(userdt);



            this.mockMvc.perform(MockMvcRequestBuilders.get("/userdata/getall")
                            .contentType(MediaType.APPLICATION_JSON)

                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());


//        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/userdata/getall"); // use to create request
//        ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder); // use to send the request
//        MvcResult mvcResult = perform.andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        int status = response.getStatus();
//        Assertions.assertEquals(200, status);


    }


    @Test
     void deleteUserTest() throws Exception {
        String userId = "123dfgy67";
        userService.deleteUser(userId);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete("/userdata/" + userId); //  create request
        ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder); // send the request
        MvcResult mvcResult = perform.andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200, status);

    }

    @Test
     void getUserTest() throws Exception {
        String userId = "abc12345678";
        User user1 = User.builder().userId(userId)
                .userName("Krushna")
                .Email("krushnaRam@gmail.com").about("This is testing create method")
                .gender("Male")
                .Password("1234567899")
                .build();

        UserDto userDto = mapper.map(user1, UserDto.class);
        Mockito.when(userService.getUserById(userId)).thenReturn(userDto);

//        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/userdata/" + userId); // use to create request
//        ResultActions perform = mockMvc.perform(mockHttpServletRequestBuilder); // use to send the request
//        MvcResult mvcResult = perform.andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        int status = response.getStatus();
//        Assertions.assertEquals(200, status);


        // make the request to the controller using MockMvc
        mockMvc.perform(MockMvcRequestBuilders.get("/userdata/"+userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("abc12345678"));
               // .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("krushna"));



    }
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

        WireMockServer  wireMockServer = new WireMockServer( 8080);
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
    @AfterAll
    public static void cleanup() {
        log.info("After all test cases ");

    }
}
