package app.implementation;

import app.dbObjects.Movie;
import app.interfaces.IDBService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DBMovieService implements IDBService<Movie> {

    EntityManagerFactory factory = Persistence.createEntityManagerFactory("DB4REST");
    EntityManager em = factory.createEntityManager();

    public DBMovieService(){}

    public void INSERT(Movie object){

        em.getTransaction().begin();
        em.persist(object);
        em.getTransaction().commit();
    }

    public void DELETE(long id){
        Movie movie = getByID(id);
        em.getTransaction().begin();
        em.remove(movie);
        em.getTransaction().commit();
    }

    public void UPDATE(Movie object){
        em.getTransaction().begin();
        em.merge(object);
        em.getTransaction().commit();
    }

    public List<Movie> getAll(){
        Query q = em.createQuery("FROM Movie ", Movie.class);
        return (List<Movie>) q.getResultList();
    }

    public Movie getByID(long id){
        return em.find(Movie.class,id);
    }
}
