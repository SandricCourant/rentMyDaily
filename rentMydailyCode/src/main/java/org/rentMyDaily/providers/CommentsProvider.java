package org.rentMyDaily.providers;
import org.rentMyDaily.domain.Comment;


import javax.persistence.EntityManager;
import java.util.List;

public class CommentsProvider {
    private final EntityManager em;
    public CommentsProvider(EntityManager em) {
        this.em = em;
    }
    public Boolean save(Comment c){
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        return true;
    }
    public boolean remove(Comment c){
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
        return true;
    }
    public List<Comment> provide(){
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }
    public Comment provide(int id){
        return em.find(Comment.class, id);
    }
}
