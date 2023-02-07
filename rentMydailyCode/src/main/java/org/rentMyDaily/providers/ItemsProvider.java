package org.rentMyDaily.providers;
import org.rentMyDaily.domain.Item;


import javax.persistence.EntityManager;
import java.util.List;

public class ItemsProvider {
    private final EntityManager em;
    public ItemsProvider(EntityManager em) {
        this.em = em;
    }
    public Boolean save(Item i){
        em.getTransaction().begin();
        em.persist(i);
        em.getTransaction().commit();
        return true;
    }
    public boolean remove(Item i){
        em.getTransaction().begin();
        em.remove(i);
        em.getTransaction().commit();
        return true;
    }
    public List<Item> provide(){
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }
    public Item provide(int id){
        return em.find(Item.class, id);
    }
}
