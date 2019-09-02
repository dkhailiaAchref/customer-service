package com.example.addressModification;

import com.example.addressModification.dto.CustomerDTO;
import com.example.addressModification.utils.JSONUtils;
import org.mockserver.integration.ClientAndServer;

import java.util.List;

import static com.example.addressModification.TestHelpers.*;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * refer to ( http://www.mock-server.com/mock_server/creating_expectations.html#button_match_request_by_body_with_json_exactly )
 */
public class Expectation{
   public static List<CustomerDTO> listAbonneesContrats;

    public static void initDefaultExpectations(ClientAndServer mockServer) {


        // GET
        getSubscriberContracts(mockServer);

        // POST
        updateSubscriber(mockServer);
        //POST
        updateSubscriberContracts(mockServer);

    }

    private static void getSubscriberContracts(ClientAndServer mockServer) {
        CustomerDTO abonnees [] = new CustomerDTO[1];
        listAbonneesContrats=initAllAbonneesContcatsFromFile();
        abonnees[0]= getFirstAbonneeFromFile(listAbonneesContrats);
        String abonneReponse=JSONUtils.convertPojoToJsonString(abonnees);
        mockServer.when(request().withMethod("GET")
                .withPath("/listSubscribers"))
                .respond(response().withStatusCode(200).withBody(abonneReponse));

    }


    private static void updateSubscriber(ClientAndServer mockServer) {
        CustomerDTO abonnees[] = new CustomerDTO[1];
        List<CustomerDTO> listAbonnees=initAllAbonneesFromFile();
        abonnees[0] = getAbonneeToModifyByAddress(listAbonnees,"28 rue henri barbuse asnieres sur seine , 92600");

        String abonneeMockResponse = JSONUtils.convertCompositePOJOToJsonString(abonnees);
        String abonneeMockRequest=JSONUtils.convertCompositePOJOToJsonString(getAbonneeToModifyByAddress(initAllAbonneesFromFile(),"28 rue henri barbuse asnieres sur seine , 92600"));
        mockServer.when(request().withMethod("POST")
                .withHeader("Content-Type", "application/json").withPath("/updateSubscriber")
                .withBody(abonneeMockRequest))
                .respond(response().withStatusCode(200).withBody(abonneeMockResponse));

    }



    private static void updateSubscriberContracts(ClientAndServer mockServer) {
        CustomerDTO abonnees = getAbonneeToModifyByAddress(initAllAbonneesFromFile(),"28 rue henri barbuse asnieres sur seine , 92600");

        String abonneeMockRequest=JSONUtils.convertCompositePOJOToJsonString(abonnees), abonneeMockResponse = JSONUtils.convertCompositePOJOToJsonString(abonnees);

        mockServer.when(request().withMethod("POST")
                .withPath("/updateSubscriberContracts")
                .withBody(abonneeMockRequest))
               .respond(response().withStatusCode(200).withBody(abonneeMockResponse));

    }

}
