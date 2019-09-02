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

public class MockServerSubscriberServiceWithoutContractsTest extends BaseTests {
    private static  List<CustomerDTO> abonnees=new ArrayList<>();
    private static CustomerDTO firstAbonneeFromFile ;
    private static CustomerDTO abonneToModify;
    @Before
    public void setup() {
        super.setup();
        abonnees=initAllAbonneesFromFile();
        firstAbonneeFromFile= TestHelpers.getFirstAbonneeFromFile(abonnees);
        abonneToModify = TestHelpers.getAbonneeToModifyByAddress(abonnees,"28 rue henri barbuse asnieres sur seine , 92600");
    }



    @Test
    public void shouldUpdateAbonneeWithMockServer() {
        Object response = mockServerSubscriberController.updateAbonnee(abonneToModify);
        CustomerDTO[] returnedAbonnees = convertCompositeJsonStringToPOJO((String)response, CustomerDTO[].class);
        CustomerDTO abonneModified=TestHelpers.getAbonneeById(Arrays.asList(returnedAbonnees),abonneToModify.getId());
        TestHelpers.assertionUpdateAbonnee(abonneModified,abonneToModify);
    }



    @Test
    public void shouldGetAllSubscribersWithMockServer() {

        Object response = mockServerSubscriberController.getAllAbonnees();
        CustomerDTO[] abonneeResponse = convertCompositeJsonStringToPOJO((String)response, CustomerDTO[].class);
        assertionGetAllSubscribers(abonneeResponse);

    }

    public void assertionGetAllSubscribers(CustomerDTO[] abonneeResponse) {
        if (abonneeResponse!=null) {
            Assert.assertEquals(abonneeResponse[0].getName(), firstAbonneeFromFile.getName());
            Assert.assertEquals(abonneeResponse[0].getFirstName(), firstAbonneeFromFile.getFirstName());
            Assert.assertEquals(abonneeResponse[0].getAddress(), firstAbonneeFromFile.getAddress());
        }
    }


}
