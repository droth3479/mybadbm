package edu.touro.mco152.bm;

import java.beans.PropertyChangeListener;
import java.util.List;

public interface UiInterface {
    Boolean uiIsCancelled();

    void setUiProgress(int i);

    void uiPublish(DiskMark dm);

    void uiCancel(Boolean b);

    void uiAddPropertyChangeListener(PropertyChangeListener pcl);

    void uiDoInBackground();

    void setDiskWorker(DiskWorker worker);
}
