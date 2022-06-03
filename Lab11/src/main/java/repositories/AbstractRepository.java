package repositories;

import models.AbstractEntity;
import singleton.MyEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractRepository<T extends AbstractEntity, ID extends Serializable> {
    protected EntityManager em;
    private String className;

    public AbstractRepository(String name) {
        this.em = MyEntityManagerFactory.getEntityManager();
        this.className = name;
    }

    public T findById(ID id) { //for all entities
        String query = this.className + ".findById";
        T obj = null;
        try{
            obj = (T) em.createNamedQuery(query)
                    .setParameter(1, id)
                    .getSingleResult();
            return obj;
        }
        catch(NoResultException exception){
            return null;
        }
    }

    public List<T> findByUsername(String username) { //only for User
        String query = this.className + ".findByUsername";
        return  em.createNamedQuery(query)
                .setParameter(1, username)
                .getResultList();
    }

    public List<T> findAll() { //for all entities
        String query = this.className + ".findAll";
        return  em.createNamedQuery(query)
                .getResultList();
    }

    public void create(T obj) { //for all entities
        System.out.println("Inserting into database ... " +  obj.toString());
        persist(obj);
    }

    public void delete(ID id){ //for all entities
        beginTransaction();
        String query = this.className + ".delete";
        em.createNamedQuery(query)
                .setParameter(1, id).executeUpdate();
        commit();
    }

    public void deleteByUsername(String username){ //for User
        beginTransaction();
        String query = this.className + ".deleteByUsername";
        em.createNamedQuery(query).setParameter(1, username).executeUpdate();
        commit();
    }

    public void updateUsername(int id, String replacedUsername){ //for User
        beginTransaction();
        String query = this.className + ".updateByUsername";
        em.createNamedQuery(query).setParameter(1, id).
                setParameter(2, replacedUsername).
                executeUpdate();
        commit();
    }
    public void persist(T entity) {
        try {
            beginTransaction();
            em.persist(entity);
            commit();
        } catch (Exception e) {
            handleException(e);
            rollback();
        }
    }

    private void rollback() {
        em.getTransaction().rollback();
    }

    protected void commit() {
        em.getTransaction().commit();
    }

    protected void beginTransaction() {
        em = MyEntityManagerFactory.getEntityManager();
        em.getTransaction().begin();
    }

    public String getClassName() {
        return className;
    }

    private void handleException(Exception e) {
        System.err.println(e.getMessage());
    }
}

