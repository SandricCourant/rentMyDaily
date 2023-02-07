package org.rentMyDaily.providers;
import org.rentMyDaily.domain.Message;


import javax.persistence.EntityManager;
import java.util.List;

public class MessagesProvider {
    private final EntityManager em;
    public MessagesProvider(EntityManager em) {
        this.em = em;
    }
    public boolean save(Message m){
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
        return true;
    }
    public boolean remove(Message m){
        em.getTransaction().begin();
        em.remove(m);
        em.getTransaction().commit();
        return true;
    }
    public List<Message> provide(){
        return em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
    }
    public Message provide(int id){
        return em.find(Message.class, id);
    }
}
