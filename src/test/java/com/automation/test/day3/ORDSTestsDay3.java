package com.automation.test.day3;

import com.automation.utilities.ConfigurationReadaer;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.swing.plaf.synth.SynthEditorPaneUI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ORDSTestsDay3 {
    @BeforeAll
    public static void setup(){

       baseURI= ConfigurationReadaer.getProperty("ords.uri");
    }
    @Test
    public void test1(){
               given().
                     accept("application/json")
                     .get("/employees").
               then().
                     assertThat().statusCode(200).
                       and().assertThat().contentType("application/json").
                       log().all(true);
              // instead of all (true) we can use if err() which will pass the result if there is no error

    }



    @Test
    public void test2() {

        given().
                accept("application/json").
                pathParam("id", 100).
                when().get("/employees/{id}").
                then().assertThat().statusCode(200).
                and().assertThat().body("employee_id", is(100),
                "department_id",is(90),
                "last_name",is("King")).
                log().all(true);
        //body ("phone_number") --> 515.123.4567
        //is is coming from ---> import static org.hamcrest.Matchers.*;
        //log().all  Logs everything in the response, including e.g. headers,
        // cookies, body with the option to pretty-print the body if the content-type is
    }
    @Test
    public void test3() {
        given().
                accept("application/json").
                pathParam("id", 1).
                when().
                get("/regions/{id}").
                then().
                assertThat().statusCode(200).
                assertThat().body("region_name", is("Europe")).
                time(lessThan(10L), TimeUnit.SECONDS).
                log().body(true);//log body in pretty format. all = header + body + status code
        //verify that response time is less than 10 seconds
    }
    @Test
    public void test4() {

         JsonPath json= given()
         .accept("application/json").
        //pathParam()
       when(). get("/employees").
                  thenReturn().jsonPath();
      String nameOfFirstEmployee=json.getString("items[0].first_name");
        String nameOflastEmployee=json.getString("items[0].last_name");
    System.out.println(nameOfFirstEmployee);
        System.out.println( nameOflastEmployee);

        Map<String, ?> firstname=json.get("items[0]");
        System.out.println(firstname);
        for(Map.Entry entry:firstname.entrySet()){
            System.out.println("key:"+entry.getKey()+"value: "+entry.getValue());
        }
        List<String> lastNames=json.get("items.last_name");
        for(String str:lastNames){
            System.out.println("last name: "+str);
        }
    }
    @Test
    public void test5() {
                 JsonPath json=given()
                         .accept("application/json").
                         when().get("/countries").prettyPeek().jsonPath();

               //  System.out.println(json);
         List<HashMap<String,?>> allCountries=json.get("items");
         System.out.println(allCountries);
         for(HashMap<String,?> map:allCountries){
             System.out.println(map);
         }

    }
    @Test
    public void test6() {
    List<Integer> salaries=given().
            accept("application/json").
            when().
            get("employees").
            thenReturn().jsonPath().get("items.salary");
    // Collections.reverse(salaries);Collections.sort(salaries);
     System.out.println(salaries);

    }

    @Test
    public void test7() {
        List<String> phonenumber=given().
                accept("application/json").
                when().
                get("employees").
                thenReturn().jsonPath().get("items.phone_number");
        //System.out.println(phonenumber);

      //ak tarika
     /*  String a="";
        for (Object s: phonenumber){
            a=s.toString().replace(".","-");
            System.out.print(a+",");
        }
       // System.out.println(a);
 */
         phonenumber.replaceAll(phone->phone.replace(".","-"));
         System.out.println(phonenumber);


    }




    @Test
    public void test8() {
        Response response=given().
                accept(ContentType.JSON).
                pathParam("id",1700).
              when().get("locations/{id}");
        response.then().
                assertThat().body("location_id",is(1700)).
                assertThat().body("postal_code",is("98199")).
                assertThat().body("state_province",is("Washington")).log().body();


    }



}
