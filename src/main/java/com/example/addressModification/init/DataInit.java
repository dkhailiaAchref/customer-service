package com.example.addressModification.init;

import com.example.addressModification.dto.CustomerDTO;
import com.example.addressModification.utils.JSONUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInit  {


    static String TARGET_FILE_SUBSCRIBERS_CONTRACTS= "src/test/resources" + File.separator + "abonnesContracts.json";


    public static List<CustomerDTO> initAbonnesContratsFromFile() {
        List<CustomerDTO> abonnees = new ArrayList<>();
        try {
            abonnees.add(JSONUtils.convertJsonFileToPojo(TARGET_FILE_SUBSCRIBERS_CONTRACTS, CustomerDTO.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return abonnees;
    }
}
