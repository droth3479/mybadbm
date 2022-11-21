package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.List;

import static edu.touro.mco152.bm.App.dataDir;

/**
 * This class is an implementation of the UiInterface, and extends SwingWorker.
 * It leverages SwingWorker methods to provide a swing implementation of UiInterface.
 */
public class SwingUiImplementation extends SwingWorker<Boolean, DiskMark> implements UiInterface {
    DiskWorker worker = null;

    @Override
    public void setDiskWorker(DiskWorker worker) {
        this.worker = worker;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        return worker.execute();
    }

    @Override
    protected void process(List<DiskMark> markList) {
        markList.stream().forEach((dm) -> {
            if (dm.type == DiskMark.MarkType.WRITE) {
                Gui.addWriteMark(dm);
            } else {
                Gui.addReadMark(dm);
            }
        });
    }

    @Override
    protected void done() {
        if (App.autoRemoveData) {
            Util.deleteDirectory(dataDir);
        }
        App.state = App.State.IDLE_STATE;
        Gui.mainFrame.adjustSensitivity();
    }

    @Override
    public Boolean uiIsCancelled() {
        return super.isCancelled();
    }

    @Override
    public void setUiProgress(int i) {
        super.setProgress(i);
    }

    @Override
    public void uiPublish(DiskMark dm) {
        super.publish(dm);
    }

    @Override
    public void uiCancel(Boolean b) {
        super.cancel(b);
    }

    @Override
    public void uiAddPropertyChangeListener(PropertyChangeListener pcl) {
        super.addPropertyChangeListener(pcl);
    }


    //These methods need not be overridden, as Swing will call its own implementation of them.
    @Override
    public void uiDoInBackground(){
        super.execute();
    }
}
