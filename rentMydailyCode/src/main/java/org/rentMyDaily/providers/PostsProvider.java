package org.rentMyDaily.providers;
import org.rentMyDaily.domain.Post;


import javax.persistence.EntityManager;
import java.util.List;

public class PostsProvider {
    private final EntityManager em;
    public PostsProvider(EntityManager em) {
        this.em = em;
    }
    public boolean save(Post p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return true;
    }
    public boolean remove(Post p){
        em.getTransaction().begin();
        em.remove(p);
        em.getTransaction().commit();
        return true;
    }
    public List<Post> provide(){
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }
    public Post provide(int id){
        return em.find(Post.class, id);
    }
}
