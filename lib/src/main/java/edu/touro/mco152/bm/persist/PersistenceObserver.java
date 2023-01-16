package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.DiskRunObserver;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import jakarta.persistence.EntityManager;

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
