package com.example.addressModification.service;

import com.example.addressModification.dao.BaseDAO;
import com.example.addressModification.dto.CustomerDTO;
import com.example.addressModification.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.addressModification.init.DataInit.initAbonnesContratsFromFile;
import static com.example.addressModification.utils.JSONUtils.convertCompositePOJOToJsonString;


@Service
public class MockServerSubscriberService {

    private Logger logger = LoggerFactory.getLogger(MockServerSubscriberService.class);
    @Autowired
    BaseDAO baseDAO;
    @Autowired
    ContractsService contractsService;

    List<CustomerDTO> abonnees = new ArrayList<>();

    private void initData() {
        abonnees = initAbonnesContratsFromFile();

    }

    // connect to url GET (http://localhost:8000/listSubscribers), when the MockServer is started from from scope test, on port (8000),
    // (voir initialize server in BaseTest) + creation des mock ( request/response ) in class from scope test (Expectation )
    public Object getAllAbonnees() {

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Accept", "application/json");
        HttpEntity<?> httpEntity = new HttpEntity<Object>(requestHeaders);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost").port(8000).path("/listSubscribers");
        String url = builder.toUriString();

        ResponseEntity response = new ResponseEntity(HttpStatus.OK);
        Object abonneeResponse = null;
        try {

            logger.info("send Rest GET Request URL={}", builder.toUriString());

            // response=restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
            response = baseDAO.getForObjectWithStringResponse(url);

        } catch (Exception e) {
            logger.error(" an error occured when get a subscribers", e.getMessage());
            e.printStackTrace();
        }
        return response.getBody();
        // return  abonneeResponse;


    }


    public CustomerDTO getAbonneeById(Long subscriberId) {
        CustomerDTO subscriber = new CustomerDTO();
        try {
            initData();
            Optional<CustomerDTO> abonneesOpt = abonnees.stream().filter(ab -> ab.getId().equals(subscriberId)).findFirst();
            if (abonneesOpt.isPresent()) {
                subscriber = abonneesOpt.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscriber;
    }

    // connect to url POST (http://localhost:8000/updateSubscriber), when the MockServer is started from from scope test, on port (8000),
    // (voir initialize server in BaseTest) + creation des mock ( request/response ) in class from scope test (Expectation )
    public Object updateAbonnee(CustomerDTO subscriberToModify) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost").port(8000).path("/updateSubscriber");
            String url = builder.toUriString();
            HttpEntity<String> entityRequestBody = new HttpEntity<String>(convertCompositePOJOToJsonString(subscriberToModify), headers);

            logger.info("send Rest POST Request URL={}", builder.toUriString());

            response = baseDAO.postForObjectWithStringResponse(entityRequestBody, url);

        } catch (Exception e) {
            logger.error("Encountered an error when trying to Modify a Subscriber ", e.getMessage());

        }
        //retourner la liste de tous les abonnées , pour y vérifier , l'abonné modifié en question
        return response.getBody();
    }


    public Object updateAllContractsForAbonnee(CustomerDTO subscriberToModify) {
        ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.OK);
        CustomerDTO abonneeResponse = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);


            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost").port(8000).path("/updateSubscriberContracts");
            String url = builder.toUriString();

            //the request body is a string json
            String reqStr = convertCompositePOJOToJsonString(subscriberToModify);
            HttpEntity<String> entityRequestBody = new HttpEntity<String>(reqStr, headers);

            logger.info("send Rest POST Request URL={}", builder.toUriString());
            //the response is an String
            response = baseDAO.postForObjectWithStringResponse(entityRequestBody, url);

            //changement d'adresse pour tous les contrats de l'abonné en question
            CustomerDTO returnedAbonnee = JSONUtils.convertCompositeJsonStringToPOJO((String) response.getBody(), CustomerDTO.class);
            abonneeResponse = contractsService.updateContractsForAbonne(returnedAbonnee);

        } catch (Exception e) {
            logger.error("an error occured when when trying to Modify all contracts for Subscriber ", e.getMessage());
            e.printStackTrace();

        }
        //retourner la liste de tous les abonnées , pour en vérifier , l'abonné modifié en question
        return abonneeResponse;
    }


}
