package com.example.addressModification.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractDTO {

    private Long id;

    private String contractNumber;

    private String contractDescription;

    private CustomerDTO subscriber;

    public ContractDTO() {
    }

    public ContractDTO(CustomerDTO subscriber) {
        this.subscriber = subscriber;
    }
    public CustomerDTO getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(CustomerDTO subscriber) {
        this.subscriber = subscriber;
    }

    public String getContractNumber() {
        return contractNumber;
    }
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }
    public String getContractDescription() {
        return contractDescription;
    }

    public void setContractDescription(String contractDescription) {
        this.contractDescription = contractDescription;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }
}

