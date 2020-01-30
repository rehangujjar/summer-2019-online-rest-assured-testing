package com.automation.test.day4;
import com.automation.utilities.ConfigurationReadaer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
public class MetaWeatherJsonPathTests {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReadaer.getProperty("meta.weather.uri");
    }

    @Test
    @DisplayName("Verify that are 5 cities that are matching 'New'")
    public void test1(){
        given().accept(ContentType.JSON).
                queryParam("query","New").
        when().
            get("/search").
                then().assertThat().statusCode(200).
                body("",hasSize(5)).log().body(true);
    }

    @Test
    @DisplayName("Verify that 1ist city is has folloing info:Newyok,city,2456115,40.71455,-74.0077118")
    public void test2(){
        given().accept(ContentType.JSON).
                queryParam("query","New").
                when().
                get("/search").
                then().assertThat().statusCode(200).
                body("title[0]",is("New York"))
               .body("woreid[0]",is(2459115))
                . body("latt_long[0]",is("40.71455,-74.0077118"))

                .log().body(true);

    }
    @Test
    @DisplayName(" other way of same problem Verify that 1ist city is has folloing info:Newyok,city,2456115,40.71455,-74.0077118")
    public void test2_2(){

        Map<String,String> expected=new HashMap<>();
        expected.put("title","New York");
        expected.put("location_type","City");
        expected.put("woeid","2459115");
        expected.put("latt_long","40.71455,-74.007118");
       Response response= given().accept(ContentType.JSON).
                queryParam("query","New").
                when().
                get("/search");
       JsonPath jsonPath=response.jsonPath();
       assertEquals(expected,jsonPath.getMap("[0]", String.class,String.class));
       List<Map<String,?>> values=jsonPath.get();
       for(Map<String,?>value:values) {
           System.out.println(values);
       }
    }
    @Test
    @DisplayName("Verify that 1ist city is has folloing info:Newyok,city,2456115,40.71455,-74.0077118")
    public void test3() {
        given().accept(ContentType.JSON).
                queryParam("query", "Las").
                when().
                get("/search").
                then().assertThat().statusCode(200).
                body("title", contains("Glassgow", "Dallas", "Las Vegas"));


    }
    @Test
    @DisplayName("Vednin g following that pauload contains weather forecast sources")
    public void test5(){
        List<String> expected = Arrays.asList("BBC","Forecast.io","HAMweather","Met Office",
                "OpenWeatherMap","Weather Underground",
                "World Weather Online");
        Response response=given().
                            accept(ContentType.JSON).pathParam("woeid",44418).when().get("/location/{woeid}");
        List<String> actual=response.jsonPath().getList("sources.title");
        assertEquals(expected,actual);

    }


}
