package org.rentMyDaily;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.rentMyDaily.domain.*;
import org.rentMyDaily.providers.*;
import org.rentMyDaily.services.*;
import org.rentMyDaily.controllers.*;


import javax.persistence.EntityManager;
import java.util.Scanner;

public class Main {
    private static UsersProvider usersProvider;
    private static UsersService usersService;
    private static DefaultController defaultController;
    private static Scanner scanner;
    public static void main(String[] args) {
        bootstrapContainer();
        launch();
    }

    private static void bootstrapContainer() {
// Chargement du fichier de configuration Hibernate
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

// Ajout des classes à traiter comme entité
        configuration.addAnnotatedClass(Comment.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Post.class);
        configuration.addAnnotatedClass(Message.class);

// Création d'un EntityManager
        SessionFactory factory = configuration.buildSessionFactory();
        EntityManager em = factory.createEntityManager();

// Instanciation des providers
        usersProvider = new UsersProvider(em);

// Instanciation des services
        usersService = new UsersService(usersProvider);

// Instanciation des controllers
        defaultController = new DefaultController(usersService);

    }
    private static void createAccount(){
        String email = "sandric.courant@gmail.com", firstname = "Sandric", lastname = "Courant", password = "azerty", username = "kiloutou", phoneNumber = "0606060606";
        if(defaultController.createAccount(email, firstname, lastname, password, username, phoneNumber)) showAccounts();
        else System.out.println("Erreur");
    }
    private static void showAccounts(){
        System.out.println(defaultController.getAccountInfo(1));
    }
    private static void launch(){
        createAccount();

    }
}