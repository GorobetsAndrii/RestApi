package app.implementation;

import app.dbObjects.User;
import app.interfaces.IDBService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DBUserService implements IDBService<User> {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("DB4REST");
    EntityManager em = factory.createEntityManager();

    public DBUserService(){}

    public void INSERT(User object){
        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    public void DELETE(long id){
        User user = getByID(id);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }

    public void UPDATE(User object){
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }

    public List<User> getAll(){
        Query q = em.createQuery("FROM User ", User.class);
        return (List<User>) q.getResultList();
    }

    public User getByID(long id){
        return em.find(User.class,id);
    }
}