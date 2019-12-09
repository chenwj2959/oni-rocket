package com.cwj.game.oxygen.rocket.model;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.FuelType;
import com.cwj.game.oxygen.rocket.constant.OxidantType;
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
    
    // 是否有观光仓
    private boolean hasToursim;
    
    // 氧化剂种类
    private OxidantType oxidantType;
    
    // 燃料种类
    private FuelType fuelType;
    
    // 燃料质量
    private int fuelQuality;
    
    // 氧化剂质量
    private int oxidantQuality;
    
    /**
     * 返回火箭主体
     */
    public String getRocket() {
        StringBuilder rocket = new StringBuilder();
        rocket.append(getText(RocketComponent.COMMANDER, 1))
            .append(getText(RocketComponent.RESEARCH, researchNum))
            .append(getText(RocketComponent.WAREHOUSE, wareHouseNum))
            .append(getText(RocketComponent.TOURISM, hasToursim ? 1 : 0))
            .append(getText(RocketComponent.FUELBIN, fuelBinNum))
            .append(getText(RocketComponent.OXIDANTBIN, oxidantBinNum))
            .append(getText(RocketComponent.ENGINE_IRON, ironEngineNum))
            .append(getText(engineType, 1));
        return rocket.toString();
    }
    
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
        } else if (RocketComponent.TOURISM.componentName().equals(component)) {
            setHasToursim(true);
        } else {
            setEngineType(component);
        }
    }
    
    /**
     * 移除组件
     */
    public void removeComponent(String component) {
        if (RocketComponent.RESEARCH.componentName().equals(component)) {
            addResearch(-1);
        } else if (RocketComponent.WAREHOUSE.componentName().equals(component)) {
            addWareHouse(-1);
        } else if (RocketComponent.FUELBIN.componentName().equals(component)) {
            addFuelBin(-1);
        } else if (RocketComponent.OXIDANTBIN.componentName().equals(component)) {
            addOxidantBin(-1);
        } else if (RocketComponent.TOURISM.componentName().equals(component)) {
            setHasToursim(false);
        } else if (engineType != null && engineType.componentName().equals(component)) {
            engineType = null;
        }
    }
    
    /**
     * 返回火箭组件总质量
     */
    public int getComponentQuality() {
        return RocketComponent.COMMANDER.quality() + (hasToursim ? RocketComponent.TOURISM.quality() : 0)
                + RocketComponent.RESEARCH.quality() * researchNum
                + RocketComponent.WAREHOUSE.quality() * wareHouseNum
                + RocketComponent.FUELBIN.quality() * fuelBinNum
                + RocketComponent.OXIDANTBIN.quality() * oxidantBinNum
                + RocketComponent.ENGINE_IRON.quality() * ironEngineNum
                + engineType.quality();
    }
    
    /**
     * 添加研究仓
     */
    public void addResearch(int num) {
        researchNum = fixLimit(researchNum + num, 0, 5);
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
        if (fuelBinNum == 0) addOxidantBin(1);
        fuelBinNum = fixLimit(fuelBinNum + num, 0, 3);
        fuelQuality = fuelBinNum * Constant.FUEL_BIN_MAX_QUALITY;
        oxidantQuality = fuelQuality;
    }
    
    /**
     * 添加氧化剂仓
     */
    public void addOxidantBin(int num) {
        oxidantBinNum = fixLimit(oxidantBinNum + num, 0, 1);
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
            setFuelType(FuelType.STEAM);
            fuelQuality = 2000;
            oxidantQuality = 0;
        } else if (RocketComponent.ENGINE_PETROLEUM.componentName().equals(engineStr)) {
            this.engineType = RocketComponent.ENGINE_PETROLEUM;
            setFuelType(FuelType.PETROLEUM);
        } else if (RocketComponent.ENGINE_HYDROGEN.componentName().equals(engineStr)) {
            this.engineType = RocketComponent.ENGINE_HYDROGEN;
            setFuelType(FuelType.HYDROGEN);
        } else if (RocketComponent.ENGINE_IRON.componentName().equals(engineStr)) {
            addIronEngine(1);
        }
    }
    
    public void setEngineType(RocketComponent engineType) {
        setEngineType(engineType.componentName());
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
    
    public boolean isHasToursim() {
        return hasToursim;
    }

    public void setHasToursim(boolean hasToursim) {
        this.hasToursim = hasToursim;
    }

    public OxidantType getOxidantType() {
        return oxidantType;
    }

    public void setOxidantType(OxidantType oxidantType) {
        this.oxidantType = oxidantType;
    }
    
    public void setOxidantType(String oxidantType) {
        for (OxidantType type : OxidantType.values()) {
            if (type.chiness().equals(oxidantType)) {
                this.oxidantType = type;
                return;
            }
        }
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public int getFuelQuality() {
        return fuelQuality;
    }

    public void setFuelQuality(int fuelQuality) {
        this.fuelQuality = fuelQuality;
    }

    public int getOxidantQuality() {
        return oxidantQuality;
    }

    public void setOxidantQuality(int oxidantQuality) {
        this.oxidantQuality = oxidantQuality;
    }

    private int fixLimit(int curr, int min, int max) {
        if (curr < min) curr = min;
        else if (curr > max) curr = max;
        return curr;
    }
    
    private String getText(RocketComponent component, int repeatedNum) {
        if (component == null) return "";
        StringBuilder res = new StringBuilder();
        while (repeatedNum-- > 0) {
            res.append(component.componentName());
            res.append(Constant.NEW_LINE);
        }
        return res.toString();
    }
}
