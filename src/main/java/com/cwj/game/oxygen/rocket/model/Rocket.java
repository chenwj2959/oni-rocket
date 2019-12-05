package com.cwj.game.oxygen.rocket.model;

import com.cwj.game.oxygen.rocket.constant.RocketComponent;

public class Rocket {

    // 研究仓数量
    private int researchNum;
    
    // 货仓数量
    private int wareHouseNum;
    
    // 燃料仓数量
    private int fuelBinNum;
    
    // 氧化剂仓数量
    private int oxidantBinNum;
    
    // 推进器类型
    private RocketComponent engineType;
    
    // 固体推进器数量
    private int ironEngineNum;
    
    /**
     * 添加组件
     */
    public void addComponent(String component) {
        if (RocketComponent.RESEARCH.componentName().equals(component)) {
            addResearch(1);
        } else if (RocketComponent.WAREHOUSE.componentName().equals(component)) {
            addWareHouse(1);
        } else if (RocketComponent.FUELBIN.componentName().equals(component)) {
            addFuelBin(1);
        } else if (RocketComponent.OXIDANTBIN.componentName().equals(component)) {
            addOxidantBin(1);
        } else {
            setEngineType(component);
        }
    }
    
    /**
     * 添加研究仓
     */
    public void addResearch(int num) {
        researchNum += num;
        fixLimit(researchNum, 0, 5);
    }
    
    /**
     * 添加货仓
     */
    public void addWareHouse(int num) {
        wareHouseNum += num;
        if (wareHouseNum < 0) wareHouseNum = 0;
    }
    
    /**
     * 添加燃料仓
     */
    public void addFuelBin(int num) {
        fuelBinNum += num;
        fixLimit(fuelBinNum, 0, 3);
    }
    
    /**
     * 添加氧化剂仓
     */
    public void addOxidantBin(int num) {
        oxidantBinNum += num;
        if (oxidantBinNum < 0) oxidantBinNum = 0;
    }
    
    /**
     * 添加固体推进器
     */
    public void addIronEngine(int num) {
        ironEngineNum += num;
        if (ironEngineNum < 0) ironEngineNum = 0;
    }
    
    public void setEngineType(String engineStr) {
        if (RocketComponent.ENGINE_STEAM.componentName().equals(engineStr)) {
            this.engineType = RocketComponent.ENGINE_STEAM;
        } else if (RocketComponent.ENGINE_PETROLEUM.componentName().equals(engineStr)) {
            this.engineType = RocketComponent.ENGINE_STEAM;
        } else if (RocketComponent.ENGINE_HYDROGEN.componentName().equals(engineStr)) {
            this.engineType = RocketComponent.ENGINE_STEAM;
        } else if (RocketComponent.ENGINE_IRON.componentName().equals(engineStr)) {
            addIronEngine(1);
        }
    }
    
    public void setEngineType(RocketComponent engineType) {
        this.engineType = engineType;
    }

    public RocketComponent getEngineType() {
        return engineType;
    }

    public int getResearchNum() {
        return researchNum;
    }

    public int getWareHouseNum() {
        return wareHouseNum;
    }

    public int getFuelBinNum() {
        return fuelBinNum;
    }

    public int getOxidantBinNum() {
        return oxidantBinNum;
    }

    public int getIronEngineNum() {
        return ironEngineNum;
    }
    
    private void fixLimit(int curr, int min, int max) {
        if (curr < min) curr = min;
        else if (curr > max) curr = max;
    }
}
