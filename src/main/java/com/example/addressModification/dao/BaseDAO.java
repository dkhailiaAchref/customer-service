package com.example.addressModification.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

/**
 * Created by Achref.D
*/
@Component
public class BaseDAO {


    @Autowired
    public RestTemplate restTemplateSpec ;


    /************* GET Apis*******************************/

    private  <T, R> ResponseEntity<T> send(Supplier<ResponseEntity<T>> supplier, R pRequest, Class<T> pResponseType) {
        return (ResponseEntity)supplier.get();
    }

    //GET with String UrlRest without request + with response String
    public   ResponseEntity getForObjectWithStringResponse(String  url) throws Exception{

        return this.send(() -> {
            return this.restTemplateSpec.getForEntity(url, String.class);
        }, url,  String.class);
    }


    //with String Urlrest without request + with response Object
    public    <T> ResponseEntity<T> getForObject(String  url, Class<T> responseType) throws Exception{

        return this.send(() -> {
            return this.restTemplateSpec.getForEntity(url, responseType);
        }, url, responseType);
    }

    /***************POST Apis***********************/

    //POST with String UrlRest with request + with response String
    public<R extends HttpEntity> ResponseEntity postForObjectWithStringResponse(R request, String  url) throws Exception {

        return restTemplateSpec.exchange(url, HttpMethod.POST, request, String.class);

    }

    //POST with String UrlRest with Object (request +  response )
    public<T,R > T postForObjectWithObjectResponse( String  url,R request, Class<T> responsetype) throws Exception {

         T ret= restTemplateSpec.postForObject(url, request, responsetype);

         return ret;

    }

}
