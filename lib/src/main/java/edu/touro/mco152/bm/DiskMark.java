package edu.touro.mco152.bm;

import java.text.DecimalFormat;

/**
 *
 */
public class DiskMark {

    static DecimalFormat df = new DecimalFormat("###.###");
    MarkType type;
    private int markNum = 0;       // x-axis
    private double bwMbSec = 0;    // y-axis
    private double cumMin = 0;
    private double cumMax = 0;
    private double cumAvg = 0;
    DiskMark(MarkType type) {
        this.type  = type;
    }

    public DiskMark() {

    }

    @Override
    public String toString() {
        return "Mark(" + type + "): " + getMarkNum() + " bwMbSec: " + getBwMbSecAsString() + " avg: " + getAvgAsString();
    }

    String getBwMbSecAsString() {
        return df.format(getBwMbSec());
    }

    String getMinAsString() {
        return df.format(getCumMin());
    }

    String getMaxAsString() {
        return df.format(getCumMax());
    }

    String getAvgAsString() {
        return df.format(getCumAvg());
    }

    public int getMarkNum() {
        return markNum;
    }

    /*
    I changed this method to only change markNum if the parameter > 0
     */
    public void setMarkNum(int markNum) {
        if(markNum > 0)
            this.markNum = markNum;
    }

    public double getBwMbSec() {
        return bwMbSec;
    }

    public void setBwMbSec(double bwMbSec) {
        this.bwMbSec = bwMbSec;
    }

    public double getCumAvg() {
        return cumAvg;
    }

    public void setCumAvg(double cumAvg) {
        this.cumAvg = cumAvg;
    }

    public double getCumMin() {
        return cumMin;
    }

    public void setCumMin(double cumMin) {
        this.cumMin = cumMin;
    }

    public double getCumMax() {
        return cumMax;
    }

    public void setCumMax(double cumMax) {
        this.cumMax = cumMax;
    }

    public enum MarkType {READ, WRITE}
}
