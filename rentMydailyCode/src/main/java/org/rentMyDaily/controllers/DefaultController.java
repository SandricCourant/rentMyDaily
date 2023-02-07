package org.rentMyDaily.controllers;

import org.rentMyDaily.services.UsersService;
import org.rentMyDaily.domain.*;

public class DefaultController {
    private final UsersService usersService;
    public DefaultController(UsersService usersService) {
        this.usersService = usersService;
    }
    public boolean createAccount(String email, String firstname, String lastname, String password, String username, String phoneNumber){
        return usersService.add(email, firstname, lastname, password, username, phoneNumber);
    }
    public boolean removeAccount(int id){
        return usersService.remove(id);
    }
    public String getAccountInfo(int id){
        User user = usersService.getUser(id);
        return user.toString();
    }
}
