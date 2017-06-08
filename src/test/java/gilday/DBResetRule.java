package gilday;

import org.junit.After;
import org.junit.Before;
import org.junit.rules.ExternalResource;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class DBResetRule extends ExternalResource {

    private EntityManagerFactory factory;
    private EntityManager em;

    @Before
    public void before() {
        factory = Persistence.createEntityManagerFactory("test-pu");
        em = factory.createEntityManager();
        em.getTransaction().begin();
        if (em.getTransaction().getRollbackOnly()) {
            em.getTransaction().rollback();
        } else {
            em.getTransaction().commit();
        }
    }

    @After
    public void after() {
        try {
            if (!em.getTransaction().isActive()) {
                em.getTransaction().begin();
                em.getTransaction().commit();
            } else if (!em.getTransaction().getRollbackOnly()) {
                em.getTransaction().commit();
            } else {
                em.getTransaction().rollback();
            }
            em.close();
        } catch (final RuntimeException ex) {
            throw ex;
        }
        factory.close();
    }

    public EntityManager getEntityManager() {
        return em;
    }
}