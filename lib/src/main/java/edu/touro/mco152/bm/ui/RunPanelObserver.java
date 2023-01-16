package edu.touro.mco152.bm.ui;

import edu.touro.mco152.bm.DiskRunObserver;
import edu.touro.mco152.bm.persist.DiskRun;

public class RunPanelObserver implements DiskRunObserver {
    @Override
    public void update(DiskRun run) {
        if(run != null){
            Gui.runPanel.addRun(run);
        }
    }
}
