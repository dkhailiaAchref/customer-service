package com.example.addressModification.controller;

import com.example.addressModification.BaseTests;
import com.example.addressModification.Expectation;
import com.example.addressModification.TestHelpers;
import com.example.addressModification.dto.CustomerDTO;
import com.example.addressModification.utils.JSONUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.addressModification.TestHelpers.initAllAbonneesFromFile;
import static com.example.addressModification.utils.JSONUtils.convertCompositeJsonStringToPOJO;

public class SubscriberControllerTest extends BaseTests {

    private static List<CustomerDTO> abonneesContacts=new ArrayList<>();
    private static  List<CustomerDTO> abonnees=new ArrayList<>();
    private static CustomerDTO abonneContratsToModify;

    @Before
    public void setup() {
        super.setup();
        abonneesContacts= TestHelpers.initAllAbonneesContcatsFromFile();
        abonneContratsToModify = TestHelpers.getAbonneeToModifyByAddress(abonneesContacts,"28 rue henri barbuse asnieres sur seine , 92600");
        abonnees=initAllAbonneesFromFile();
    }

    @Test
    public void shouldUpdateAbonneeContrats() {
        Object response = subscriberController.updateAbonnee(abonneContratsToModify);
        String responseJsonStr = JSONUtils.convertPojoToJsonString(response);
        CustomerDTO returnedAbonnee = convertCompositeJsonStringToPOJO(responseJsonStr, CustomerDTO.class);
       TestHelpers.assertionUpdateAbonnee(returnedAbonnee,abonneContratsToModify);
    }


    @Test
    public void shouldGetAllSubscribers() {

        Object response = subscriberController.getAllAbonnees();
        String responseJsonStr = JSONUtils.convertPojoToJsonString(response);
        CustomerDTO[] abonneeResponse = convertCompositeJsonStringToPOJO(responseJsonStr, CustomerDTO[].class);
       assertionGetAllSubscribers(abonneeResponse);
    }

    public void assertionGetAllSubscribers(CustomerDTO[] abonneeResponse) {
       CustomerDTO firstAbonneExpectation = Expectation.listAbonneesContrats.get(0);
        if (abonneeResponse!=null) {
            Assert.assertEquals(abonneeResponse[0].getName(), firstAbonneExpectation.getName());
            Assert.assertEquals(abonneeResponse[0].getFirstName(), firstAbonneExpectation.getFirstName());
            Assert.assertEquals(abonneeResponse[0].getAddress(), firstAbonneExpectation.getAddress());
            //verif , toutes les adresses abonnée dans les contrats sont egaux , à l'adresse abonée du response REST api
            firstAbonneExpectation.getContracts().forEach(contactAbonnee -> Assert.assertEquals(contactAbonnee.getSubscriber().getAddress(), abonneeResponse[0].getAddress()));
        }
    }
}
