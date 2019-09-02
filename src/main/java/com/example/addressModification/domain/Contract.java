package com.example.addressModification.domain;

import javax.persistence.*;


@Entity
@Table(name = "ContractDTO")
public class Contract extends BaseEntity {


    private String contractNumber;

    private String contractDescription;

    //FK (subscriber_id) references Subscriber;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subscriber_id", foreignKey = @ForeignKey(name = "FK_CONTRACT_SUBSCRIBER"))
    private Subscriber subscriber;

    public Contract() {
    }

    public Contract( Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    public Subscriber getSubscriber() {
        return subscriber;
    }
    public void setSubscriber(Subscriber subscriber) {
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

}

