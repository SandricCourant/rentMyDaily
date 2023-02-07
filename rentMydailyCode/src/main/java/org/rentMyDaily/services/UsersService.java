package org.rentMyDaily.services;

import org.rentMyDaily.domain.User;
import org.rentMyDaily.providers.UsersProvider;

import javax.persistence.NoResultException;
import java.util.List;


public class UsersService {
    private UsersProvider usersProvider;

    public UsersService(UsersProvider usersProvider) {
        this.usersProvider = usersProvider;
    }
    public boolean add(String email, String firstname, String lastname, String password, String username, String phoneNumber){
        User user = new User();
        user.setEmail(email);
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setPassword(password);
        user.setUsername(username);
        user.setPhoneN(phoneNumber);
        try{
            usersProvider.isExist(user);
            return false;
        } catch (NoResultException e){
            return usersProvider.save(user);
        }
    }
    public boolean remove(int id){
        return usersProvider.remove(usersProvider.provide(id));
    }
    public List<User> getAll(){
        return usersProvider.provide();
    }
    public User getUser(int id){
        return usersProvider.provide(id);
    }
    public User findByMail(String email){
        return usersProvider.provide(email);
    }
}
