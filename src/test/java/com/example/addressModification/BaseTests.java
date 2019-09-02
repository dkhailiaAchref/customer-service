package com.example.addressModification;


import com.example.addressModification.controller.MockServerSubscriberController;
import com.example.addressModification.controller.SubscriberController;
import com.example.addressModification.dao.BaseDAO;
import com.example.addressModification.service.ContractsService;
import com.example.addressModification.service.MockServerSubscriberService;
import com.example.addressModification.service.SubscriberService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockserver.integration.ClientAndServer;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

/**
 * spring-aop (Aspect Oriented Programming ) + Génericité
 */
@TestPropertySource("classpath:application.properties")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AddressModificationApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BaseTests {


    @InjectMocks
    public SubscriberController subscriberController;

    @InjectMocks
    SubscriberService subscriberService;


    @InjectMocks
    public MockServerSubscriberController mockServerSubscriberController;

    @InjectMocks
    MockServerSubscriberService mockServerSubscriberService;

    @Autowired
    RestTemplate restTemplate;

    @InjectMocks
    BaseDAO baseDAO ;

    @InjectMocks
    ContractsService contractsService;

    public static ClientAndServer mockServer;


    //Initializing Mock Server in the scope TEST , on the port (8000) + creation des expectations par defaut (GET(/listSubscribers),POST(/updateSubscriber))
    @BeforeClass
    public static void setupMockServer() throws IOException, JAXBException {
        mockServer = startClientAndServer(8000);

        Expectation.initDefaultExpectations(mockServer);
    }

    @AfterClass
    public static void after() {
        mockServer.stop();
    }


    //injection des dependances ( arborecence des beans et properties à injecter dans les classes appellées dans le scope test)
    @Before
    @Test
    public void setup() {
        MockitoAnnotations.initMocks(this);

        SubscriberController vSubscriberController = this.unwrapProxy(subscriberController);
        ReflectionTestUtils.setField(vSubscriberController, "subscriberService", this.subscriberService);

        SubscriberService vSubscriberService = this.unwrapProxy(subscriberService);
        ReflectionTestUtils.setField(vSubscriberService, "fileAbonnesContratsPath", "src/test/resources/abonnesContracts.json");

        MockServerSubscriberController vMockServerSubscriberController= this.unwrapProxy(mockServerSubscriberController);
        ReflectionTestUtils.setField(vMockServerSubscriberController, "subscriberService", this.mockServerSubscriberService);

        MockServerSubscriberService vMockServerSubscriberService= this.unwrapProxy(mockServerSubscriberService);;
        ReflectionTestUtils.setField(vMockServerSubscriberService, "baseDAO", this.baseDAO);
        ReflectionTestUtils.setField(vMockServerSubscriberService, "contractsService", this.contractsService);

        ContractsService vContractsService= this.unwrapProxy(contractsService);;
        ReflectionTestUtils.setField(vContractsService, "fileAbonnesContratsPath", "src/test/resources/abonnesContracts.json");

        BaseDAO vSpecificDAO= this.unwrapProxy(baseDAO);;
        ReflectionTestUtils.setField(vSpecificDAO, "restTemplateSpec", this.restTemplate);


    }
    //spring-aop (Aspect Oriented Programming )
    @SuppressWarnings("unchecked")
    public <T> T unwrapProxy(Object bean) {
        /*
         * If the given object is a proxy, set the return value as the object
         * being proxied, otherwise return the given object.
         */
        T vResult = null;
        try {
            if (AopUtils.isAopProxy(bean) && bean instanceof Advised) {
                Advised advised = (Advised) bean;
                vResult = (T) advised.getTargetSource().getTarget();
            } else {
                vResult = (T) bean;
            }
        } catch (Exception pEx) {
            // Test failed
            vResult = (T) bean;
        }
        assertNotNull(vResult);
        return vResult;
    }


}
