package com.automation.Homework1;

import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReadaer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;


public class HomeWork1 {


    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReadaer.getProperty("ui.name");
    }

    @Test
    @DisplayName("No params test ")
    public void test1(){
           Response response= given().accept(ContentType.JSON).when().get("");
        JsonPath jsonPath = response.jsonPath();


    }

    @Test
    @DisplayName("Gender test")
    public void test2(){   }


    @Test
    @DisplayName("2 params test ")
    public void test3(){   }

    @Test
    @DisplayName("Invalid gender test  ")
    public void test4(){   }

    @Test
    @DisplayName("Invalid region test")
    public void test5(){   }
    @Test
    @DisplayName("Amount and regions test")
    public void test6(){   }
    @Test
    @DisplayName("3 params test ")
    public void test7(){   }
    @Test
    @DisplayName("Amount count test  ")
    public void test8(){   }




}
