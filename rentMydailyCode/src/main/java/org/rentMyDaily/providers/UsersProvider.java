package org.rentMyDaily.providers;
import org.rentMyDaily.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

public class UsersProvider {
    private final EntityManager em;
    public UsersProvider(EntityManager em) {
        this.em = em;
    }
    public Boolean save(User u){
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        return true;
    }
    public boolean remove(User u){
        em.getTransaction().begin();
        em.remove(u);
        em.getTransaction().commit();
        return true;
    }
    public List<User> provide(){
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    public User provide(int id){
        return em.find(User.class, id);
    }
    public User provide(String email){
        return (User) em.createQuery("SELECT u FROM User WHERE u.email = :email")
                .setParameter("email", email)
                .getSingleResult();
    }
    public User isExist(User user) throws NoResultException {
        return (User) em.createQuery("SELECT u FROM User u WHERE u.firstname = :firstname AND u.lastname = :lastname OR u.email = :email", User.class)
                .setParameter("firstname", user.getFirstname())
                .setParameter("lastname", user.getLastname())
                .setParameter("email", user.getEmail())
                .getSingleResult();
    }
}
