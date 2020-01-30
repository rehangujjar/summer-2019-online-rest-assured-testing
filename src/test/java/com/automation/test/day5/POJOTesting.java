package com.automation.test.day5;

import com.automation.pojos.Job;
import com.automation.pojos.Location;
import com.automation.utilities.ConfigurationReadaer;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;

public class POJOTesting {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReadaer.getProperty("ords.uri");
    }
    @Test
    @DisplayName("Get job info from json and convert it into POJO")
    public void test1(){
   Response response= given().accept(ContentType.JSON).
           when().get("/jobs");
        JsonPath jsonPath=response.jsonPath();
       Job job= jsonPath.getObject("items[0]", Job.class);
        System.out.println(job);


    }
    @Test
    @DisplayName("Convert from json to pojo ")
    public void test4(){
         Response response=given()
                 .accept(ContentType.JSON).
                         when().
                         get("/locations/{location_id}",2500);
         List<Location> locations=response.jsonPath().getList("items",Location.class);
       //  System.out.println(location);

      for(Location l:locations){
          System.out.println(locations);
      }

}




}
