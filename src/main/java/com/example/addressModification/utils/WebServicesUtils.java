package com.example.addressModification.utils;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class WebServicesUtils {


    /**
     **  configure Rest Template Specific =>    paramétrer timeOut Connection dans  Clientdu Rest Template définit par spring-web-mvc,
     ** NB: In restTemplate we have 3 type of timeouts :
     ** 1) ConnectionRequestTimeout  : This is timeout in millis for getting connection from connectionManager
     ** 2) ConnectionTimeout : This is timeout in millis for establishing connection between source and destination
     ** 3) ReadTimeout : This is timeout in millis which expects the response/result should be returned from the destination endpoint.
     **  @Inject
     **  public RestTemplate restTemplateSpec ;)
     ** @param timeOutSpec
     ** @return
     ** @throws Exception
     **/
    //paramétrer timeOut Connection dans  Clientdu Rest Template définit par spring-web-mvc,
    public static RestTemplate buildRestTemplateWithSpecTimeOut(Integer timeOutSpec) throws Exception {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory(timeOutSpec));
        return  restTemplate ;
    }
    public static ClientHttpRequestFactory getClientHttpRequestFactory(Integer timeOutSpec) {
        org.springframework.http.client.HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeOutSpec);
        clientHttpRequestFactory.setConnectionRequestTimeout(timeOutSpec);
        clientHttpRequestFactory.setReadTimeout(timeOutSpec);
        clientHttpRequestFactory.setConnectionRequestTimeout(timeOutSpec);
        return clientHttpRequestFactory;
    }
}
