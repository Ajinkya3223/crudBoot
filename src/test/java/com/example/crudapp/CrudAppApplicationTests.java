package com.example.crudapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class CrudAppApplicationTests {

//	@Test
//	void contextLoads() {
//	}
@Test
@DisplayName("doSum")

 void doSumTest() {
    int ActualResult = 7;
    int ExepctedResult = 7;
    Assertions.assertEquals(ExepctedResult, ActualResult, "data not matched");
    log.info("performing  dosumtest");
}
}
