package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import jakarta.persistence.EntityManager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.App.msg;
import static edu.touro.mco152.bm.DiskMark.MarkType.READ;

/**
 * Concrete command to execute read tests
 * Accepts/sets test params via constructor
 */
public class ReadTest implements ICommand{
    UiInterface ui;
    int numMarks;
    int numBlocks;
    int blockSizeKb;
    DiskRun.BlockSequence blockSequence;

    /**
     * Constructor to be called by the client.
     * @param ui the UiInterface to be implemented by the test
     * @param numMarks the number of files to run the test on
     * @param numBlocks the number of blocks to break the test into
     * @param blockSizeKb how many kilobytes each block will contian
     * @param blockSequence a DiskRun enum to determine how the test will be run
     */
    public ReadTest(UiInterface ui, int numMarks, int numBlocks, int blockSizeKb, DiskRun.BlockSequence blockSequence) {
        this.ui = ui;
        this.numMarks = numMarks;
        this.numBlocks = numBlocks;
        this.blockSizeKb = blockSizeKb;
        this.blockSequence = blockSequence;
    }

    /**
     * Execute method to be called by the Executor
     */
    public void execute() {
        // declare local vars formerly in DiskWorker
        int unitsComplete = 0;
        int unitsTotal = numMarks * numBlocks;
        float percentComplete;

        int blockSize = blockSizeKb * 1024;
        byte[] blockArr = new byte[blockSize];
        for (int b = 0; b < blockArr.length; b++) {
            if (b % 2 == 0) {
                blockArr[b] = (byte) 0xFF;
            }
        }

        DiskMark rMark;
        int startFileNum = App.nextMarkNumber;

        DiskRun run = new DiskRun(DiskRun.IOMode.READ, blockSequence);
        run.setNumMarks(numMarks);
        run.setNumBlocks(numBlocks);
        run.setBlockSize(blockSizeKb);
        run.setTxSize((long) blockSizeKb * numBlocks * numMarks);
        run.setDiskInfo(Util.getDiskInfo(dataDir));

        msg("disk info: (" + run.getDiskInfo() + ")");

        Gui.chartPanel.getChart().getTitle().setVisible(true);
        Gui.chartPanel.getChart().getTitle().setText(run.getDiskInfo());

        for (int m = startFileNum; m < startFileNum + numMarks && !ui.uiIsCancelled(); m++) {

            if (App.multiFile) {
                testFile = new File(dataDir.getAbsolutePath()
                        + File.separator + "testdata" + m + ".jdm");
            }
            rMark = new DiskMark(READ);  // starting to keep track of a new benchmark
            rMark.setMarkNum(m);
            long startTime = System.nanoTime();
            long totalBytesReadInMark = 0;

            try {
                try (RandomAccessFile rAccFile = new RandomAccessFile(testFile, "r")) {
                    for (int b = 0; b < numBlocks; b++) {
                        if (blockSequence == DiskRun.BlockSequence.RANDOM) {
                            int rLoc = Util.randInt(0, numBlocks - 1);
                            rAccFile.seek((long) rLoc * blockSize);
                        } else {
                            rAccFile.seek((long) b * blockSize);
                        }
                        rAccFile.readFully(blockArr, 0, blockSize);
                        totalBytesReadInMark += blockSize;
                        unitsComplete++;
                        percentComplete = (float) unitsComplete / (float) unitsTotal * 100f;
                        ui.setUiProgress((int) percentComplete);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            long endTime = System.nanoTime();
            long elapsedTimeNs = endTime - startTime;
            double sec = (double) elapsedTimeNs / (double) 1000000000;
            double mbRead = (double) totalBytesReadInMark / (double) MEGABYTE;
            rMark.setBwMbSec(mbRead / sec);
            msg("m:" + m + " READ IO is " + rMark.getBwMbSec() + " MB/s    "
                    + "(MBread " + mbRead + " in " + sec + " sec)");
            App.updateMetrics(rMark);
            ui.uiPublish(rMark);

            run.setRunMax(rMark.getCumMax());
            run.setRunMin(rMark.getCumMin());
            run.setRunAvg(rMark.getCumAvg());
            run.setEndTime(new Date());
        }

        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();

        Gui.runPanel.addRun(run);
    }
}
