package com.example.addressModification.service;

import com.example.addressModification.BaseTests;
import com.example.addressModification.TestHelpers;
import com.example.addressModification.dto.CustomerDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.addressModification.TestHelpers.initAllAbonneesFromFile;
import static com.example.addressModification.utils.JSONUtils.convertCompositeJsonStringToPOJO;

/**
 * vérifier sur les logs du demarage du serveur : les mocks préparées sur le mockServer dans l'etape de  ( creating expectation), voir le body du httpResponse crée
 * <p>
 * 2019-08-30 22:46:00.519  INFO 8012 --- [           main] org.mockserver.mock.HttpStateHandler     : started on port: 8000
 * org.mockserver.mock.HttpStateHandler     : creating expectation:
 * "httpResponse" : {
 * "statusCode" : 200,
 * "body" : "[ {\r\n  \"name\" : \"jhon\",\r\n  \"firstName\" : \"jhon\",\r\n  \"address\" : \"28 rue henri barbuse asnieres sur seine , 92600\"\r\n} ]"
 * }
 */

public class MockServerSubscriberServiceWithContractsTest extends BaseTests {
    private static  List<CustomerDTO> abonneesContacts=new ArrayList<>();
    private static  List<CustomerDTO> abonnees=new ArrayList<>();
    private static CustomerDTO abonneContratsToModify;
    private static CustomerDTO abonneToModify;
    private static CustomerDTO firstActualAbonneeFromFile ;

    @Before
    public void setup() {
        super.setup();
        abonneesContacts= TestHelpers.initAllAbonneesContcatsFromFile();
        abonneContratsToModify = TestHelpers.getAbonneeToModifyByAddress(abonneesContacts,"28 rue henri barbuse asnieres sur seine , 92600");
        abonnees=initAllAbonneesFromFile();
        abonneToModify=TestHelpers.getAbonneeToModifyByAddress(abonnees,"28 rue henri barbuse asnieres sur seine , 92600");
    }



    @Test
    public void shouldUpdateAbonneeContratsWithMockServer() {
        try {
            Object response = mockServerSubscriberController.updateAllContractsForAbonnee(abonneToModify);
            CustomerDTO returnedAbonnee =(CustomerDTO) response;
            CustomerDTO abonneModified=TestHelpers.getAbonneeById(Arrays.asList(returnedAbonnee),abonneContratsToModify.getId());
            TestHelpers.assertionUpdateAbonneeWithContracts(abonneModified,abonneContratsToModify);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void shouldGetAllSubscribersWithMockServer() {

        Object response = mockServerSubscriberController.getAllAbonnees();
        CustomerDTO[] abonneeResponse = convertCompositeJsonStringToPOJO((String)response, CustomerDTO[].class);
        assertionGetAllSubscribersContacts(abonneeResponse);

    }

    public void assertionGetAllSubscribersContacts(CustomerDTO[] abonneeResponse) {
        firstActualAbonneeFromFile=TestHelpers.getFirstAbonneeFromFile(abonneesContacts);
        if (abonneeResponse!=null) {
            Assert.assertEquals(abonneeResponse[0].getName(), firstActualAbonneeFromFile.getName());
            Assert.assertEquals(abonneeResponse[0].getFirstName(), firstActualAbonneeFromFile.getFirstName());
            Assert.assertEquals(abonneeResponse[0].getAddress(), firstActualAbonneeFromFile.getAddress());
            //verif , toutes les adresses abonnée dans les contrats sont egaux , à l'adresse abonée du response REST api
            abonneContratsToModify.getContracts().forEach(contactAbonnee -> Assert.assertEquals(contactAbonnee.getSubscriber().getAddress(), abonneeResponse[0].getAddress()));
        }
    }


}
