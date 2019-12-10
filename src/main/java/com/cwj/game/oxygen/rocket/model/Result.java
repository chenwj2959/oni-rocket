package com.cwj.game.oxygen.rocket.model;

public class Result {

    private int finalHeight;
    
    private int totalQuality;
    
    private int componentQuality;
    
    private int fuelQuality;
    
    private int qualityPunishment;
    
    private int maxHeight;

    public int getFinalHeight() {
        return finalHeight;
    }

    public void setFinalHeight(double finalHeight) {
        this.finalHeight = (int) Math.round(finalHeight);
    }

    public int getTotalQuality() {
        return totalQuality;
    }

    public void setTotalQuality(int totalQuality) {
        this.totalQuality = totalQuality;
    }

    public int getComponentQuality() {
        return componentQuality;
    }

    public void setComponentQuality(int componentQuality) {
        this.componentQuality = componentQuality;
    }

    public int getFuelQuality() {
        return fuelQuality;
    }

    public void setFuelQuality(int fuelQuality) {
        this.fuelQuality = fuelQuality;
    }

    public int getQualityPunishment() {
        return qualityPunishment;
    }

    public void setQualityPunishment(double qualityPunishment) {
        this.qualityPunishment = (int) Math.round(qualityPunishment);
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = (int) Math.round(maxHeight);
    }
}
