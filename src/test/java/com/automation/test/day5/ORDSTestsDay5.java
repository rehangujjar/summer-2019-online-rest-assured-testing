package com.automation.test.day5;
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
public class ORDSTestsDay5 {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReadaer.getProperty("ords.uri");
    }
    @Test
    @DisplayName("Verify the average salary is greater than $5000")
    public void test1(){

       Response response= given().accept(ContentType.JSON).when().
                get("employees");

                JsonPath jsonPath=response.jsonPath();

                List<Integer> salaries= jsonPath.getList("items.salary");
                int sum=0;
                for(int salary:salaries){
                    sum+=salary;

                }
                int avg=sum/salaries.size();

                assertTrue(avg>5000,"Error: actually average salary is lower tha 5000"+avg);


    }




}
