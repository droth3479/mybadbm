package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class UiInterfaceTest implements UiInterface{
    DiskWorker worker = new DiskWorker(this);
    Boolean isCancelled = false;
    Notifier notifier;
    static TestObserver testObserver = new TestObserver();

    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    @Test
    void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        //Add observers
        notifier = new Notifier();
        notifier.registerObserver(testObserver);

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference
        App.dataDir = new File(App.locationDir.getAbsolutePath()+File.separator+App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        }
        else
        {
            App.dataDir.mkdirs(); // create data dir if not already present
        }

        setDiskWorker(new DiskWorker(this));

        uiDoInBackground();

        assertTrue(true);
    }

    @Override
    public void uiDoInBackground() {
        try {
            worker.execute();
            notifier.updateObservers(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDiskWorker(DiskWorker worker) {
        this.worker = worker;
        assertNotNull(this.worker);
    }

    @Override
    public Boolean uiIsCancelled() {
        return isCancelled;
    }

    @Override
    public void setUiProgress(int i) {
        assertTrue(i >= 0 && i <= 100);
    }

    @Override
    public void uiPublish(DiskMark dm) {
        assertTrue(dm.getMarkNum() <= App.numOfMarks);
        assertTrue(dm.getAvgAsString().matches("-?\\d+(\\.\\d+)?"));
        assertTrue(dm.getBwMbSecAsString().matches("-?\\d+(\\.\\d+)?"));
        assertTrue(dm.getMaxAsString().matches("-?\\d+(\\.\\d+)?"));
        assertTrue(dm.getMinAsString().matches("-?\\d+(\\.\\d+)?"));
    }

    @Override
    public void uiCancel(Boolean b) {
        isCancelled = b;
    }

    //This method has no purpose in a non-gui context
    @Override
    public void uiAddPropertyChangeListener(PropertyChangeListener pcl) {
    }

    @AfterAll
    @Test
    public static void observerNotified(){
        assertTrue(testObserver.invoked);
    }
}