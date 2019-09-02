package com.example.addressModification.service;

import com.example.addressModification.BaseTests;
import com.example.addressModification.TestHelpers;
import com.example.addressModification.dto.CustomerDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.example.addressModification.TestHelpers.initAllAbonneesFromFile;
import static org.mockito.Mockito.when;


public class MockServerSubscriberServiceMockitoTest extends BaseTests {
    @Mock
    MockServerSubscriberService mockServerSubscriberService;
    private static List<CustomerDTO> abonneesContacts=new ArrayList<>();
    private static List<CustomerDTO> abonnees=new ArrayList<>();
    private static CustomerDTO firstAbonneeFromFile ;
    private static CustomerDTO abonneContrats ;
    private static CustomerDTO abonneToModify;
    @Before
    public void setup() {
        super.setup();
        abonneesContacts= TestHelpers.initAllAbonneesContcatsFromFile();
        abonnees=initAllAbonneesFromFile();
        abonneToModify=TestHelpers.getAbonneeToModifyByAddress(abonnees,"28 rue henri barbuse asnieres sur seine , 92600");
        abonneContrats = TestHelpers.getAbonneeToModifyByAddress(abonneesContacts,"28 rue henri barbuse asnieres sur seine , 92600");

    }

    @Test
    public void shouldUpdateAbonneeContrats() throws Exception {
        when(mockServerSubscriberService.updateAllContractsForAbonnee(abonneToModify)).thenReturn(abonneToModify);

        Object response = mockServerSubscriberService.updateAllContractsForAbonnee(abonneToModify);
        CustomerDTO returnedAbonnee =(CustomerDTO) response;
        TestHelpers.assertionUpdateAbonnee(returnedAbonnee,abonneToModify);
    }

    @Test
    public void shouldUpdateAbonnee() throws Exception {
        when(this.mockServerSubscriberService.updateAbonnee(abonneToModify)).thenReturn(abonneToModify);

        Object response = mockServerSubscriberService.updateAbonnee(abonneToModify);
        CustomerDTO returnedAbonnee =(CustomerDTO) response;
        TestHelpers.assertionUpdateAbonnee(returnedAbonnee,abonneToModify);
    }

    @Test
    public void shouldGetAllSubscribers() throws Exception {
        when(this.mockServerSubscriberService.getAllAbonnees()).thenReturn(abonneContrats);

        Object response = mockServerSubscriberService.getAllAbonnees();
        CustomerDTO returnedAbonnees =(CustomerDTO) response;
        assertionGetAllSubscribersContracts(returnedAbonnees);
    }

    public void assertionGetAllSubscribersContracts(CustomerDTO abonneeResponse) {
        firstAbonneeFromFile=TestHelpers.getFirstAbonneeFromFile(abonneesContacts);
        if (abonneeResponse!=null) {
            Assert.assertEquals(abonneeResponse.getName(), firstAbonneeFromFile.getName());
            Assert.assertEquals(abonneeResponse.getFirstName(), firstAbonneeFromFile.getFirstName());
            Assert.assertEquals(abonneeResponse.getAddress(), firstAbonneeFromFile.getAddress());
            //verif , toutes les adresses abonnée dans les contrats sont egaux , à l'adresse abonée du response REST api
            firstAbonneeFromFile.getContracts().forEach(contactAbonnee -> Assert.assertEquals(contactAbonnee.getSubscriber().getAddress(), abonneeResponse.getAddress()));
        }
    }

}
