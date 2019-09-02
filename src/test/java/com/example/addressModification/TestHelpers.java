package com.example.addressModification;

import com.example.addressModification.dto.CustomerDTO;
import com.example.addressModification.utils.JSONUtils;
import org.junit.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TestHelpers {

    static String TARGET_FILE_SUBSCRIBERS = "src/test/resources" + File.separator + "abonnees.json";
    static String TARGET_FILE_SUBSCRIBERS_CONTRACTS = "src/test/resources" + File.separator + "abonnesContracts.json";


    public static List<CustomerDTO> initAllAbonneesFromFile() {
        List<CustomerDTO> abonnees = new ArrayList<>();

        try {
            abonnees.add(JSONUtils.convertJsonFileToPojo(TARGET_FILE_SUBSCRIBERS, CustomerDTO.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonnees;
    }

    public static List<CustomerDTO> initAllAbonneesContcatsFromFile() {
        List<CustomerDTO> abonnees = new ArrayList<>();

        try {
            CustomerDTO abonnesFromFile = JSONUtils.convertJsonFileToPojo(TARGET_FILE_SUBSCRIBERS_CONTRACTS, CustomerDTO.class);
            abonnees.add(abonnesFromFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonnees;
    }

    public static CustomerDTO getAbonneeById(List<CustomerDTO> abonnees, Long subscriberId) {
        CustomerDTO abonneeFound = new CustomerDTO();
        try {

            Optional<CustomerDTO> abonneesOpt = abonnees.stream().filter(ab -> ab.getId().equals(subscriberId)).findFirst();
            if (abonneesOpt.isPresent()) {
                abonneeFound = abonneesOpt.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonneeFound;
    }

    public static CustomerDTO getFirstAbonneeFromFile(List<CustomerDTO> abonnees) {
        CustomerDTO abonneeFound = new CustomerDTO();
        Optional<CustomerDTO> abonneesOpt = abonnees.stream().findFirst();
        if (abonneesOpt.isPresent()) {
            abonneeFound = abonneesOpt.get();
        }
        return abonneeFound;
    }

    public static CustomerDTO getAbonneeToModifyByAddress(List<CustomerDTO> abonnees, String newAddress) {

        CustomerDTO abonneeToModify = getFirstAbonneeFromFile(abonnees);
        //changement de l'adresse principale de l'abonné en question
        abonneeToModify.setAddress(newAddress);
        //changement d'adresse pour tous les contrats de l'abonné en question
        if (abonneeToModify.getContracts() != null && !abonneeToModify.getContracts().isEmpty()) {

            abonneeToModify.getContracts().forEach(contactAbonnee -> contactAbonnee.getSubscriber().setAddress(newAddress));
        }
        return abonneeToModify;
    }

    public static void assertionUpdateAbonneeWithContracts(CustomerDTO abonneeResponse, CustomerDTO abonneContratsToModify) {
        Assert.assertEquals(abonneeResponse.getName(), abonneContratsToModify.getName());
        Assert.assertEquals(abonneeResponse.getFirstName(), abonneContratsToModify.getFirstName());
        Assert.assertEquals(abonneeResponse.getAddress(), abonneContratsToModify.getAddress());
        //verif , toutes les adresses abonnée dans les contrats sont egaux , à l'adresse abonée du response REST api
        abonneContratsToModify.getContracts().forEach(contactAbonnee -> Assert.assertEquals(contactAbonnee.getSubscriber().getAddress(), abonneeResponse.getAddress()));

    }

    public static void assertionUpdateAbonnee(CustomerDTO abonneeResponse, CustomerDTO abonneContratsToModify) {
        Assert.assertEquals(abonneeResponse.getName(), abonneContratsToModify.getName());
        Assert.assertEquals(abonneeResponse.getFirstName(), abonneContratsToModify.getFirstName());
        Assert.assertEquals(abonneeResponse.getAddress(), abonneContratsToModify.getAddress());

    }
}
