package com.cwj.game.oxygen.rocket.model;

import com.cwj.game.oxygen.rocket.constant.Constant;

public class Result {

    private int finalHeight;
    
    private int totalQuality;
    
    private int componentQuality;
    
    private int fuelQuality;
    
    private int qualityPunishment;
    
    private int maxHeight;
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(Constant.NEW_LINE)
            .append("组件质量 = ").append(componentQuality).append(Constant.NEW_LINE)
            .append("燃料质量 = ").append(fuelQuality).append(Constant.NEW_LINE)
            .append("总质量 = ").append(totalQuality).append(Constant.NEW_LINE)
            .append("质量惩罚距离 = ").append(qualityPunishment).append(Constant.NEW_LINE)
            .append("最大推进距离 = ").append(maxHeight).append(Constant.NEW_LINE)
            .append("最大飞行高度 = ").append(finalHeight);
        return builder.toString();
    }

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
