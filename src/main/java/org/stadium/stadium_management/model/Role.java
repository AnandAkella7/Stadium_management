package org.stadium.stadium_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String Role_name;

    public Role(){}

    public Role(String role_name) {
        this.Role_name= role_name;
    }

}
