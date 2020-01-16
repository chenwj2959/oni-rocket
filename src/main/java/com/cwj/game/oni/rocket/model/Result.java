package com.cwj.game.oni.rocket.model;

import com.cwj.game.oni.rocket.constant.Constant;

public class Result {

    private int finalHeight;
    
    private int totalQuality;
    
    private int componentQuality;
    
    private int fuelQuality;
    
    private int qualityPunishment;
    
    private int maxHeight;
    
    private int rocketLength;
    
    private int maxLoadQuality;
    
    public String getResult(String result) {
        StringBuilder builder = new StringBuilder();
        if (finalHeight < 10000) builder.append("火箭无法起飞！").append(Constant.NEW_LINE);
        else if (result != null) builder.append(result).append(Constant.NEW_LINE);
        builder.append("火箭长度 = ").append(rocketLength).append("节").append(Constant.NEW_LINE)
            .append("组件质量 = ").append(componentQuality).append(" kg").append(Constant.NEW_LINE)
            .append("燃料质量 = ").append(fuelQuality).append(" kg").append(Constant.NEW_LINE)
            .append("总质量 = ").append(totalQuality).append(" kg").append(Constant.NEW_LINE)
            .append("质量惩罚距离 = ").append(qualityPunishment).append(" km").append(Constant.NEW_LINE)
            .append("最大推进距离 = ").append(maxHeight).append(" km").append(Constant.NEW_LINE)
            .append("最大飞行高度 = ").append(finalHeight).append(" km");
        return builder.toString();
    }
    
    public String getMaxLoadResult(String result) {
        StringBuilder builder = new StringBuilder();
        if (finalHeight < 10000) builder.append("火箭无法起飞！").append(Constant.NEW_LINE);
        else if (result != null) builder.append(result).append(Constant.NEW_LINE);
        builder.append("火箭长度 = ").append(rocketLength).append("节").append(Constant.NEW_LINE)
            .append("组件质量 = ").append(componentQuality).append(" kg").append(Constant.NEW_LINE)
            .append("最大可负载质量 = ").append(maxLoadQuality).append(" kg").append(Constant.NEW_LINE)
            .append("最佳燃料质量 = ").append(fuelQuality).append(" kg").append(Constant.NEW_LINE)
            .append("总质量 = ").append(totalQuality).append(" kg").append(Constant.NEW_LINE)
            .append("质量惩罚距离 = ").append(qualityPunishment).append(" km").append(Constant.NEW_LINE)
            .append("最大推进距离 = ").append(maxHeight).append(" km").append(Constant.NEW_LINE)
            .append("最大飞行高度 = ").append(finalHeight).append(" km");
        return builder.toString();
    }

    public int getFinalHeight() {
        return finalHeight;
    }

    public void setFinalHeight(int finalHeight) {
        this.finalHeight = finalHeight;
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

    public void setQualityPunishment(int qualityPunishment) {
        this.qualityPunishment = qualityPunishment;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }

    public int getRocketLength() {
        return rocketLength;
    }

    public void setRocketLength(int rocketLength) {
        this.rocketLength = rocketLength;
    }

    public void setMaxLoadQuality(int maxLoadQuality) {
        this.maxLoadQuality = maxLoadQuality;
    }
}
