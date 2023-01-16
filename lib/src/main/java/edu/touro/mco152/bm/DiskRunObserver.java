package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 *  This class is an interface for observers.
 *  It waits to be notified of a test completion,
 *  and performs some operation on being updated.
 */
public interface DiskRunObserver {
    public void update(DiskRun run);
}
