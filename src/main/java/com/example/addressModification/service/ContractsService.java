package com.example.addressModification.service;

import com.example.addressModification.dto.CustomerDTO;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.addressModification.init.DataInit.initAbonnesContratsFromFile;
import static com.example.addressModification.utils.JSONUtils.writeJsonFileFromPojo;

@Service
public class ContractsService {


    @Value("${file.abonnes.contrats.path}")
    private String fileAbonnesContratsPath;

    private List<CustomerDTO> initData() {
        return initAbonnesContratsFromFile();
    }

    CustomerDTO updateContractsForAbonne(CustomerDTO abonneeToModify) {
        try {


            //récuperer  les abonnées depuis la source de donnée locale ( fichier json(abonnesContracts.json)), et leurs contrats .
            List<CustomerDTO> abonneeWithContrats = initData();

            //recuperer depuis le fichier l'abonnée ayant l'ID de celui recu en param , avec la liste des contrats qui lui sont associées
            Optional<CustomerDTO> abonneeToModifyOpt = abonneeWithContrats.stream()
                    .filter(abonnee -> abonnee.getId().equals(abonneeToModify.getId()))
                    .findFirst();
            if (abonneeToModifyOpt.isPresent()) {
                //changement d'adresse pour tous les contrats de l'abonné en question ,
                abonneeToModifyOpt.get().getContracts().forEach(contactAbonnee -> contactAbonnee.getSubscriber().setAddress(abonneeToModify.getAddress()));
                //retourner l'abonnee à modifier recu en paramettre , Màj, avec ses contrats , qui sont à leur tour Màj par l'adresse .
                abonneeToModify.setContracts(abonneeToModifyOpt.get().getContracts());
                //aprés Màj de l'abonnee à modifier , on ecrit le nouveau contenu dans le fichier d'infos json (abonnesContracts.json)
                if (abonneeToModify.getId() != null && !Strings.isNullOrEmpty(abonneeToModify.getAddress())) {
                    writeJsonFileFromPojo(fileAbonnesContratsPath, abonneeToModify);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonneeToModify;
    }

}
