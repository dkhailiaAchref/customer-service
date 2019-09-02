package com.example.addressModification.controller;

import com.example.addressModification.dto.CustomerDTO;
import com.example.addressModification.service.MockServerSubscriberService;
import com.example.addressModification.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/")
@Api(tags = {Constants.TAG_SUBSCRIBER})
public class MockServerSubscriberController {
    private final Logger log = LoggerFactory.getLogger(MockServerSubscriberController.class);
    @Autowired
    MockServerSubscriberService subscriberService;

    //https://localhost:8443/getAllAbonnes => ( test sur navigateur , Postman , ou , swagger )
    //[{"id":null,"name":"jhon","firstName":"jhon","address":"2 rue henri barbuse asnieres sur seine , 92600"}]
    @RequestMapping(value = "/getAllAbonnes", method = RequestMethod.GET)
    @ApiOperation(value = "Obtenir les abonnées,(à tester dans le scope TEST , requiert le MockServer started on the port(8000)")
    public @ResponseBody
    Object getAllAbonnees() {
        return subscriberService.getAllAbonnees();
    }

    //https://localhost:8443/getSubscriberById/1
    //{"id":null,"name":"jhon","firstName":"jhon","address":"2 rue henri barbuse asnieres sur seine , 92600"}
    @RequestMapping(value = "/getAbonneeById/{subscriberId}", method = RequestMethod.GET)
    @ApiOperation(value = "Obtenir un abonee par ID ")
    public @ResponseBody
    CustomerDTO getAbonneeById(@PathVariable("subscriberId") Long subscriberId) {

        return subscriberService.getAbonneeById(subscriberId);
    }

    //https://localhost:8443/updateAbonnee/
    //tester avec requette 'POST' sur (Postman ou swagger) , avec le body json, ecrit dans le fichier (abonnes.json)
    //{"id":null,"name":"sina","firstName":"jhon","address":"28 rue henri barbuse asnieres sur seine , 92600"}
    @RequestMapping(value = "/updateAbonnee", method = RequestMethod.POST)
    @ApiOperation(value = "Créer ou Màj les infos d''un abonee ( adrresse, nom,prenom),(à tester dans le scope TEST , requiert le MockServer started on the port(8000)")
    public @ResponseBody
    Object updateAbonnee(@RequestBody CustomerDTO customerDto) {
        return subscriberService.updateAbonnee(customerDto);
    }
    //https://localhost:8443/updateAllContractsForAbonnee/
    //tester avec requette 'POST' sur (Postman ou swagger) , avec le body json, ecrit dans le fichier (abonnesContracts.json)
    @RequestMapping(value = "/updateAllContractsForAbonnee", method = RequestMethod.POST)
    @ApiOperation(value = "Créer ou Màj les infos d''un abonee ( adrresse, nom,prenom),(à tester dans le scope TEST , requiert le MockServer started on the port(8000)")
    public @ResponseBody
    Object updateAllContractsForAbonnee(@RequestBody CustomerDTO customerDto) {
        return subscriberService.updateAllContractsForAbonnee(customerDto);
    }


}
