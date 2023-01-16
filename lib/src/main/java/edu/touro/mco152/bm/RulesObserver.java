package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 *  This class is an implementation of the observer pattern.
 *  It waits to be notified of a test completion,
 *  and checks if it passed the rules. If not, it handles
 *  the violation accordingly.
 */
public class RulesObserver implements DiskRunObserver {
    final double MAX_TOO_HIGH = 1.03;
    SlackManager slackmgr = new SlackManager("droth_BMTest");
    boolean slackSent;

    @Override
    public void update(DiskRun run) {
        if(run != null){
            if(run.getIoMode() == DiskRun.IOMode.READ){
                if (Double.parseDouble(run.getMax()) > Double.parseDouble(run.getAvg()) * MAX_TOO_HIGH) {
                    sendSlack(run);
                }
            }
        }
    }

    /**
     * Send a slack message indicating run max was too high.
     * @param run A DiskRun
     */
    private void sendSlack(DiskRun run) {
        slackSent = slackmgr.postMsg2OurChannel("Benchmark max exceeded 3% of average.");
    }

    public boolean slackSent() {
        return slackSent;
    }
}
