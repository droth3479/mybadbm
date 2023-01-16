package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.DiskRunObserver;
import jakarta.persistence.EntityManager;

/**
 *  This class is an implementation of the observer pattern.
 *  It waits to be notified of a test completion,
 *  and handles persistence
 */
public class PersistenceObserver implements DiskRunObserver {
    @Override
    public void update(DiskRun run) {
        if(run != null){
            EntityManager em = EM.getEntityManager();
            em.getTransaction().begin();
            em.persist(run);
            em.getTransaction().commit();
        }
    }
}
