package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlackObserverTest {
    @Test
    void maxTooHigh(){
        Notifier notifier = new Notifier();
        RulesObserver ro = new RulesObserver();
        notifier.registerObserver(ro);
        DiskRun run = new DiskRun();

        run.setRunAvg(1.0);
        run.setMax(2.0);
        notifier.updateObservers(run);

        assertTrue(ro.slackSent());
    }
}
