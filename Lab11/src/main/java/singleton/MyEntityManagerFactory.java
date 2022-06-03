package singleton;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MyEntityManagerFactory {
    private static EntityManager entityManager = null;
    private static EntityManagerFactory emf;
    private MyEntityManagerFactory() {

    }

    public static EntityManager getEntityManager() {
        if (entityManager == null)
            createEntityManager();
        return entityManager;
    }

    private static void createEntityManager() {
        emf = Persistence.createEntityManagerFactory("default");
        entityManager = emf.createEntityManager();
    }

    public static void closeEntities(){
        entityManager.close();
        emf.close();
    }
}
