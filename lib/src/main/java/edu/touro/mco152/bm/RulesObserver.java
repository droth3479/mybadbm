package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

public class RulesObserver implements DiskRunObserver {
    final double MAX_TOO_HIGH = 1.03;
    SlackManager slackmgr = new SlackManager("droth_BMTest");

    @Override
    public void update(DiskRun run) {
        if(run != null){
            if(run.getIoMode() == DiskRun.IOMode.READ){
                if (Integer.parseInt(run.getMax()) > Integer.parseInt(run.getAvg()) * MAX_TOO_HIGH) {
                    sendSlack(run);
                }
            }
        }
    }

    private void sendSlack(DiskRun run) {
        Boolean worked = slackmgr.postMsg2OurChannel("Benchmark max exceeded 3% of average.");
    }
}
