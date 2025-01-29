package org.stadium.stadium_management.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table( name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column( unique = true)
    private String Username;


    @Column( unique = true)
    private String email;

    private String password;






    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
   
    private Set<Role> roles = new HashSet<>();

    public User(){}
    

    public User(String username, String password)
    {
        this.Username = username;
        this.password = password;
    }
    
    public User(String username,String email, String password, Set<Role> role) {
        this.Username = username;
        this.password = password;
        this.roles = role;
    }

    public void addRole(Role role)
    {
        this.roles.add(role);
    }

}

