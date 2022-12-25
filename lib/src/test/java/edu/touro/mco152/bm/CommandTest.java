package edu.touro.mco152.bm;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest implements UiInterface{
    Boolean isCancelled = false;
    Executor executor = new Executor();

    /**
     * Bruteforce setup of static classes/fields to allow DiskWorker to run.
     *
     * @author lcmcohen
     */
    @BeforeAll
    static void setupDefaultAsPerProperties()
    {
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

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
    }

    /*
    num files: 25, num blks: 128, blk size (kb): 2048, blockSequence: SEQUENTIAL
     */
    @Test
    public void readTest(){
        ReadTest rt = new ReadTest(this, 25, 128, 2048, DiskRun.BlockSequence.SEQUENTIAL);
        executor.setCommand(rt);
        executor.execute();
    }

    @Test
    public void writeTest(){
        WriteTest wt = new WriteTest(this, 25, 128, 2048, DiskRun.BlockSequence.SEQUENTIAL);
        executor.setCommand(wt);
        executor.execute();
    }

    @Override
    public void uiDoInBackground() {
    }

    @Override
    public void setDiskWorker(DiskWorker worker) {
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
        assertTrue(dm.getMarkNum() <= 25);
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
}
