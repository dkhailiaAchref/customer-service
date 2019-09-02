package com.example.addressModification.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Subscriber")
public class Subscriber extends BaseEntity {

    public static final int NAME_LENGTH = 50;
    public static final int ADDRESS_LENGTH = 250;

    @OneToMany(mappedBy = "subscriber", fetch = FetchType.EAGER)
    private List<Contract> contracts;

    @Size(max = NAME_LENGTH)
    @Column(length = NAME_LENGTH)
    private String name;

    @Size(max = NAME_LENGTH)
    @Column(length = NAME_LENGTH)
    private String firstName;

    @Size(max = ADDRESS_LENGTH)
    @Column(length = ADDRESS_LENGTH)
    private String address;

    public Subscriber() {
    }

    public Subscriber(String name,String address) {
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

}



