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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ORDSTestsDay4 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReadaer.getProperty("ords.uri");
    }

    @Test
    @DisplayName("Verify that response time is less tha 3 seconds")
    public void test1() {
        given().
                accept(ContentType.JSON).
                when().get("/employees").
                then().assertThat().
                statusCode(200).
                time(lessThan(3l), TimeUnit.SECONDS).log().all(true);
    }

    @Test
    @DisplayName("Verify that country_name from payload is \"United States of America\" ")
    public void test2() {
        given().
                accept("application/json").
                queryParam("q", "{\"country_id\":\"US\"}").
                when().
                get("/countries").
                then().
                assertThat().
                contentType(ContentType.JSON).
                statusCode(200).
                body("items[0].country_name", is("United States of America")).
                log().all(true);

    }

    @Test
    @DisplayName("get all links ")
    public void test3() {
        Response response = given().
                accept("application/json").
                // queryParam("q","{\"country_id\":\"US\"}").
                        when().
                        get("/countries");

        JsonPath jsonPath = response.jsonPath();
        List<?> links = jsonPath.getList("items.links.href");
        for (Object link : links) {
            System.out.println(link);
        }

    }

    @Test
    @DisplayName("Verify that payload contains only 25 countries")
    public void test4() {
        List<?> countries = given().
                accept(ContentType.JSON).
                when().
                get("/countries").prettyPeek().
                thenReturn().jsonPath().getList("items");
        assertEquals(25, countries.size());
    }

    @Test
    @DisplayName("Verify that payload contains following countries")
    public void test5() {

        //to use List.of() set java 9 at least
        List<String> expected = List.of("Argentina", "Brazil", "Canada", "Mexico", "United States of America");
        Response response = given().accept(ContentType.JSON).
                get("countries");
        response.then().assertThat().statusCode(200);
        JsonPath json = response.thenReturn().jsonPath();
        List<String> actual = json.get("items.country_name");
        for (int i = 0; i < expected.size(); i++) {
            assertTrue(actual.contains(expected.get(i)));

        }
    }

    @Test
    @DisplayName("Verify that employees has positive salary")
    public void test6() {
        given().accept(ContentType.JSON).
                when().
                get("/employees").
                then().
                assertThat().
                statusCode(200).
                body("items.salary", everyItem(greaterThan(0)));
    }

    @Test
    @DisplayName("Verify that employee 101 has following phone number :515-12304568")
    public void test7() {
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/employees/{id}", 101);
        assertEquals(200, response.getStatusCode());
        String expected = "515-123-4568";
        String actual = response.jsonPath().get("phone_number");
        expected = expected.replace("-", ".");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify that body returns following salary information after sorting from higher to lower(aster sorting it in decedning order)  ")
    public void test8() {
                     List<Integer> expectedSalaries=List.of(24000,17000,17000,12008,11000,
                             9000,9000,8200,8200,8000,
                             7900,7800,7700,6900,6500,
                             6000,5800,4800,4800,4200,
                             3100,2900,2800,2600,2500);
                        Response response=given().
                                accept(ContentType.JSON).
                                when().
                                get("/employees");
                        assertEquals(200, response.statusCode());
                        List<Integer> actualSalaries=response.jsonPath().getList("items.salary");
                        Collections.sort(actualSalaries, Collections.reverseOrder());
                        System.out.println(actualSalaries);
                        assertEquals(expectedSalaries,actualSalaries);

    }

    @Test
     @DisplayName("Verify that JSON body has following entries")
             public void test9(){
                 given().
                       accept(ContentType.JSON).
                       pathParam("id",2900).
                       when().
                       get("/locations/{id})").
                         then().
                         assertThat().
                         statusCode(200).
                         body("", hasEntry("street_address","20 Rue des Corps-Saints")).
                         body("", hasEntry("city","Geneva")).
                         body("", hasEntry("postal_code","1730")).
                         body("", hasEntry("country_id","CH")).
                         body("", hasEntry("state_province","Geneve")).
                         log().all(true);



             }
     @Test
  @DisplayName("Verify that JSON body has following")
    public void test9_2() {

                     given().
                           accept(ContentType.JSON).
                           pathParam("id",2900).
                           when().
                           get("/locations/{id})").
                             then().
                             assertThat().
                             statusCode(200).
                             body("", hasEntry("street_address",is("20 Rue des Corps-Saints"))).
                             body("", hasEntry("city",is("Geneva"))).
                             body("", hasEntry("postal_code",is("1730"))).
                             body("", hasEntry("country_id",is("CH"))).
                             body("", hasEntry("state_province",is("Geneve"))).
                             log().all(true);





     }
}