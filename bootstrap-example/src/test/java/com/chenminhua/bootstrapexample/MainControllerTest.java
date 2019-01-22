package com.chenminhua.bootstrapexample;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { App.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MainControllerTest {

    @Test
    public void helloTest() {
        Response response = RestAssured.get("http://localhost:8080/greeting/hello");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }
}
