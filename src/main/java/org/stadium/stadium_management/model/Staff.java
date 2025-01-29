package org.stadium.stadium_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String position;
    private String phone;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User userAccount;

    public Staff(){}

    public Staff(String name, String position, String phone)
    {
        this.name = name;
        this.position = position;
        this.phone = phone;
    }

}
