package edu.touro.mco152.bm;

import edu.touro.mco152.bm.DiskRunObserver;
import edu.touro.mco152.bm.persist.DiskRun;

import java.util.ArrayList;

public class Notifier {
    ArrayList<DiskRunObserver> observers = new ArrayList<DiskRunObserver>();

    public void registerObserver(DiskRunObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(DiskRunObserver observer) {
        observers.remove(observer);
    }

    public void updateObservers(DiskRun run) {
        for (DiskRunObserver observer : observers) {
            observer.update(run);
        }
    }
}
