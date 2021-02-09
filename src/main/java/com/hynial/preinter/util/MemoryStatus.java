package com.hynial.preinter.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * report memory usage
 * MemoryStatus.create(new MemoryStatus.MemoryStatusBuilder(1)).report();
 * MemoryStatus.create(new MemoryStatus.MemoryStatusBuilder()).report();
 */
public class MemoryStatus {
    private static final long MEGABYTE_FACTOR = 1024L * 1024L;
    private static final DecimalFormat ROUNDED_DOUBLE_DECIMALFORMAT;
    private static final String MIB = "MiB";
    private boolean outputConsole = true;
    private static long preFree;

    static {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        ROUNDED_DOUBLE_DECIMALFORMAT = new DecimalFormat("####0.00", otherSymbols);
        ROUNDED_DOUBLE_DECIMALFORMAT.setGroupingUsed(false);
        preFree = Runtime.getRuntime().freeMemory();
    }

    public MemoryStatus(MemoryStatusBuilder builder){

    }

    public static MemoryStatus create(MemoryStatusBuilder builder){
        return builder.build();
    }

    public long getMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    public long getUsedMemory() {
        return getMaxMemory() - getFreeMemory();
    }

    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public long getFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public long getDeltaFree(){
        return getFreeMemory() - this.preFree;
    }

    public String getDeltaFreeInMiB(){
        return String.format("%s%s", ROUNDED_DOUBLE_DECIMALFORMAT.format(bytesToMiB(getDeltaFree())), "(" + MIB + ")");
    }

    public String getTotalMemoryInMiB() {
        double totalMiB = bytesToMiB(getTotalMemory());
        return String.format("%s%s", ROUNDED_DOUBLE_DECIMALFORMAT.format(totalMiB), "(" + MIB + ")");
    }

    public String getFreeMemoryInMiB() {
        double freeMiB = bytesToMiB(getFreeMemory());
        return String.format("%s%s", ROUNDED_DOUBLE_DECIMALFORMAT.format(freeMiB), "(" + MIB + ")");
    }

    public String getUsedMemoryInMiB() {
        double usedMiB = bytesToMiB(getUsedMemory());
        return String.format("%s%s", ROUNDED_DOUBLE_DECIMALFORMAT.format(usedMiB), "(" + MIB + ")");
    }

    public String getMaxMemoryInMiB() {
        double maxMiB = bytesToMiB(getMaxMemory());
        return String.format("%s%s", ROUNDED_DOUBLE_DECIMALFORMAT.format(maxMiB), "(" + MIB + ")");
    }

    public double getPercentageUsed() {
        return ((double) getUsedMemory() / getMaxMemory()) * 100;
    }

    public String getPercentageUsedFormatted() {
        double usedPercentage = getPercentageUsed();
        return ROUNDED_DOUBLE_DECIMALFORMAT.format(usedPercentage) + "%";
    }

    public double bytesToMiB(long bytes){
        return bytes / MEGABYTE_FACTOR;
    }

    private StringBuilder stringBuilder = new StringBuilder();
    public String report(){
        if(this.stringBuilder.toString().length() == 0){
            this.stringBuilder.append("\t\t:");
        }

        this.stringBuilder.append("<Memory Status> ")
                .append("Total:").append(getTotalMemory()).append("#").append(getTotalMemoryInMiB())
                .append(", Free:").append(getFreeMemory()).append("#").append(getFreeMemoryInMiB())
                .append(", Used:").append(getUsedMemory()).append("#").append(getUsedMemoryInMiB())
                .append(", Max:").append(getMaxMemory()).append("#").append(getMaxMemoryInMiB())
                .append(", PercentUsed:").append(getPercentageUsed()).append("#").append(getPercentageUsedFormatted());

        if(Math.abs(getDeltaFree()) > 0) {
            this.stringBuilder.append("\n\t\t , ∆Free:").append(getDeltaFree()).append("#").append(getDeltaFreeInMiB());
        }

        if(outputConsole) {
            System.out.println(stringBuilder.toString());
        }
        return stringBuilder.toString();
    }

    public String report(String flag){
        return this.stringBuilder.append(flag).append(":").append(report()).toString();
    }

    public static class MemoryStatusBuilder{
        public MemoryStatusBuilder(){
        }
        public MemoryStatusBuilder(boolean o){
            this.memoryStatus.outputConsole = o;
        }
        public MemoryStatusBuilder(int mark){
            if(mark == 1){
                saveCurrentFreeForDelta();
            }
        }

        private MemoryStatus memoryStatus = new MemoryStatus(this);

        public MemoryStatusBuilder outputConsole(boolean o){
            this.memoryStatus.outputConsole = o;
            return this;
        }

        public MemoryStatusBuilder preFree(long preFree){
            if(this.memoryStatus.preFree > 0){
                System.out.println("before change preFree:" + this.memoryStatus.preFree + ", changed:" + preFree);
            }

            this.memoryStatus.preFree = preFree;
            return this;
        }

        public MemoryStatusBuilder saveCurrentFreeForDelta(){
            if(this.memoryStatus.preFree > 0){
                System.out.println("before change preFree:" + this.memoryStatus.preFree + ", now:" + this.memoryStatus.getFreeMemory());
            }
            this.memoryStatus.preFree = this.memoryStatus.getFreeMemory();
            return this;
        }

        public MemoryStatus build(){
            return memoryStatus;
        }
    }
}
