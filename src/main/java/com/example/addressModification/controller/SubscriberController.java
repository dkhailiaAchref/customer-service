package com.example.addressModification.controller;

import com.example.addressModification.dto.CustomerDTO;
import com.example.addressModification.service.SubscriberService;
import com.example.addressModification.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
@Api(tags = {Constants.TAG_SUBSCRIBER})
public class SubscriberController {
    private final Logger log = LoggerFactory.getLogger(SubscriberController.class);
    @Autowired
    SubscriberService subscriberService;

    //https://localhost:8443/getAllSubscribers
    //[{"id":null,"name":"jhon","firstName":"jhon","address":"2 rue henri barbuse asnieres sur seine , 92600"}]
    @RequestMapping(value = "/getAllSubscribers", method = RequestMethod.GET)
    @ApiOperation(value = "Obtenir les abonnées")
    public @ResponseBody
    List<CustomerDTO> getAllAbonnees() {
        return subscriberService.getAllAbonnees();
    }

    //https://localhost:8443/getSubscriberById/1
    //{"id":null,"name":"jhon","firstName":"jhon","address":"2 rue henri barbuse asnieres sur seine , 92600"}
    @RequestMapping(value = "/getSubscriberById/{subscriberId}", method = RequestMethod.GET)
    @ApiOperation(value = "Obtenir un abonee par ID ")
    public @ResponseBody
    CustomerDTO getSubscriberById(@PathVariable("subscriberId") Long subscriberId) {

        return subscriberService.getAbonneeById(subscriberId);
    }

    //https://localhost:8443/createOrUpdateSubscriber/
    //tester avec requette 'POST' sur (Postman ou swagger) , avec body json, comme suit:
    //{"id":null,"name":"sina","firstName":"jhon","address":"28 rue henri barbuse asnieres sur seine , 92600"}
    @RequestMapping(value = "/updateSubscriber", method = RequestMethod.POST)
    @ApiOperation(value = "Créer ou Màj les infos d''un abonee ( adrresse, nom,prenom)")
    public @ResponseBody
    CustomerDTO updateAbonnee(@RequestBody CustomerDTO customerDto) {
        return subscriberService.updateAbonnee(customerDto);
    }

}
