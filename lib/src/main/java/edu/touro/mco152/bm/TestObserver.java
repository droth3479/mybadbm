package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 *  This class is an implementation of the observer pattern for testing purposes.
 *  It waits to be notified of a test completion,
 *  and tracks that it has been invoked, to aid in unit testing.
 */
public class TestObserver implements DiskRunObserver {
    boolean invoked;

    @Override
    public void update(DiskRun run) {
        invoked = true;
    }

    public boolean invoked(){
        return invoked;
    }
}
