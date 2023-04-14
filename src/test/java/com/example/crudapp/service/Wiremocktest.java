//package com.example.CrudApp.service;
//
//import com.example.CrudApp.dtos.UserDto;
//import com.github.tomakehurst.wiremock.WireMockServer;
//import com.github.tomakehurst.wiremock.junit5.WireMockTest;
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.*;
//import com.github.tomakehurst.wiremock.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.ResponseEntity;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Scanner;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WireMockTest(httpsEnabled = true, httpsPort = 8081)
//public class Wiremocktest {
//
//
//
//
//
//    private static WireMockServer wireMockServer;
//
//    @BeforeClass
//    public static void setup() {
//        wireMockServer = new WireMockServer(8081);
//        wireMockServer.start();
//    }
//
//    @AfterClass
//    public static void tearDown() {
//        wireMockServer.stop();
//    }
//@Test
//
//
// void stubbing() throws IOException {
//
//    stubFor(get(urlPathEqualTo("/user"))
//
//            .willReturn(aResponse()
//                    .withStatus(200)
//                    .withHeader("Content-Type", "text/html")
//                    .withBody("!!! Service available !!!")));
//
//    CloseableHttpClient httpClient = HttpClients.createDefault();
//    HttpGet request = new HttpGet("http://localhost:8081/baeldung/wiremock");
//    request.addHeader("Accept", "text/html");
//    HttpResponse httpResponse = httpClient.execute(request);
//    String stringResponse = convertHttpResponseToString(httpResponse);
//
//
//
//    verify(getRequestedFor(urlEqualTo("/user")));
//    assertEquals(503, httpResponse.getStatusLine().getStatusCode());
//
//    assertEquals("!!! Service available !!!", stringResponse);
// }
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Test
// void stub2(){
//     stubFor(get(urlEqualTo("/my-api"))
//             .willReturn(aResponse()
//                     .withStatus(200)
//                     .withHeader("Content-Type", "application/json")
//                     .withBody("{\"message\": \"Hello World!\"}")));
//
//
//     // Make a request to your Spring Boot application, which should trigger the stubbed response
//     ResponseEntity<String> response = restTemplate.getForEntity("/my-api", String.class);
//
//     // Assert that the response matches the expected output
//     assertThat(response.getStatusCodeValue()).isEqualTo(200);
//     assertThat(response.getBody()).isEqualTo("{\"message\": \"Hello World!\"}");
// }
//
//
// }
//
//    private String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
//        InputStream inputStream = httpResponse.getEntity().getContent();
//        return convertInputStreamToString(inputStream);
//    }
//
//    private String convertInputStreamToString(InputStream inputStream) {
//        Scanner scanner = new Scanner(inputStream, "UTF-8");
//        String string = scanner.useDelimiter("\\Z").next();
//        scanner.close();
//        return string;
//    }
//
//
//
//
//    public void basicStub() throws Exception {
//
////        WireMockServer wireMockServer = new WireMockServer();
////        wireMockServer.start();
//
//
//
//        // Stubbing the response of the userService.getUserById() method
//        stubFor(get(urlEqualTo("/users" ))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("this is basic stub")));
//
//
//    }
//
//    @Test
//    public void verifyStub() throws Exception {
//        basicStub();
//        RestAssured.baseURI="http://localhost:8080";
//      Response res = RestAssured.given()
//                .log()
//                .all()
//                .when()
//                .get("/users");
//       int statuscode= res.getStatusCode();
//
//    }
//    @Test
//    public void testGetUserById() throws Exception {
//
//        TestRestTemplate restTemplate = new TestRestTemplate();
//
//        String userId = "123";
//        UserDto userDto = new UserDto();
//        userDto.setUserId(userId);
//        userDto.setUserName("Ram");
//        userDto.setEmail("nvkiyc@gmail.com");
//        userDto.setGender("male");
//        userDto.setAbout("this is ");
//        userDto.setPassword("23451678");
//
//
//
//        // Stubbing the response of the userService.getUserById() method
//        stubFor(get(urlEqualTo("/users/" + userId))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody(convertObjectToJsonString(userDto))));
//
//        // When
//        ResponseEntity<UserDto> response = restTemplate.getForEntity("/users/" + userId, UserDto.class);
//
//        // Then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(userDto, response.getBody());
//    }
//
//
//
//
//}
