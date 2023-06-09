package ru.netology.mycloudstorage.modele;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
}
