package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

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
