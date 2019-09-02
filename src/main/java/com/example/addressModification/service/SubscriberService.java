package com.example.addressModification.service;

import com.example.addressModification.dto.CustomerDTO;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.addressModification.init.DataInit.initAbonnesContratsFromFile;
import static com.example.addressModification.utils.JSONUtils.writeJsonFileFromPojo;

@Service
public class SubscriberService {


    @Value("${file.abonnes.contrats.path}")
    private String fileAbonnesContratsPath;


    private List<CustomerDTO> initData() {
        return initAbonnesContratsFromFile();
    }

    public List<CustomerDTO> getAllAbonnees() {
        return initData();
    }

    public CustomerDTO getAbonneeById(Long subscriberId) {
        CustomerDTO subscriber = new CustomerDTO();
        try {

            List<CustomerDTO> abonnees = initData();
            Optional<CustomerDTO> abonneesOpt = abonnees.stream().filter(ab -> ab.getId().equals(subscriberId)).findFirst();
            if (abonneesOpt.isPresent()) {
                subscriber = abonneesOpt.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscriber;
    }

    public CustomerDTO updateAbonnee(CustomerDTO subscriberToModify) {
        AtomicReference<CustomerDTO> subscriberModified = new AtomicReference<>(new CustomerDTO());
        try {
            List<CustomerDTO> abonnees = initData();
            //on recherche l'abonnée en question en se basant sur la clé (ID) , pour modifier son adresse
            abonnees.stream().filter(abonnee -> abonnee.getId() != null && abonnee.getId().equals(subscriberToModify.getId()))
                    .forEach(
                            abonneeToModify ->
                            {
                                abonneeToModify.setAddress(subscriberToModify.getAddress());
                                abonneeToModify.setFirstName(subscriberToModify.getFirstName());
                                abonneeToModify.setName(subscriberToModify.getName());

                                //changement d'adresse pour tous les contrats de l'abonné en question
                                if (abonneeToModify.getContracts() != null && !abonneeToModify.getContracts().isEmpty()) {

                                    abonneeToModify.getContracts().forEach(contactAbonnee -> contactAbonnee.getSubscriber().setAddress(subscriberToModify.getAddress()));
                                }

                                subscriberModified.set(abonneeToModify);
                            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        //si abonnee à modifier trouvé dans le fichier , on ecrit le nouveau contenu dedans (abonnesContracts.json)
        if (subscriberModified.get().getId() != null && !Strings.isNullOrEmpty(subscriberModified.get().getAddress())) {
            writeJsonFileFromPojo(fileAbonnesContratsPath, subscriberModified.get());
        }
        return subscriberModified.get();
    }

}
