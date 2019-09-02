package com.example.addressModification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 *
 * {
 *   "id" : 1,
 *   "name" : "sina",
 *   "firstName" : "jhon",
 *   "address" : "28 rue henri barbuse asnieres sur seine , 92600"
 * }
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {


    private Long id=1L;;

    private List<ContractDTO> contracts;

    private String name;

    private String firstName;

    private String address;

    public CustomerDTO() {
          }

    public CustomerDTO(String name, String address) {
        this.name = name;
        this.address=address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String fiestName) { this.firstName = fiestName; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public List<ContractDTO> getContracts() { return contracts; }

    public void setContracts(List<ContractDTO> contracts) { this.contracts = contracts; }

}



