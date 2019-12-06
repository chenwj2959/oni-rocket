package com.cwj.game.oxygen.rocket.core;

import com.cwj.game.oxygen.rocket.constant.RocketComponent;

public class Rules {

    /**
     * 计算 最终飞行高度
     * <br/>
     * 当总质量小于4000时, 高度 = 燃料推力 - 火箭质量惩罚
     * <br/>
     * 当总质量大于4000时, 高度 = 300 * 总推力高度 / (300 + 3.2 * 总质量)
     * @param thrustHeight 燃料推力
     * @param rocketMassPenalty 火箭质量惩罚(km)
     */
    public static double flightHeight(double thrustHeight, double totalQuality) {
        return totalQuality < 4000 ? thrustHeight - totalQuality : (300 * thrustHeight) / (300 + 3.2 * totalQuality);
    }
    
    /**
     * 燃料和氧化剂的消耗比  = 1 : 1
     * <br/>
     * 计算 燃料推力 = Math.min(燃料质量, 氧化剂质量) * 燃料效率 * 氧化剂效率
     * <br/>
     * 燃料质量 : 石油 = 40, 液氢 = 60
     * <br/>
     * 氧化剂效率 : 氧石 = 1, 液氧 = 1.33
     * @param fuelQuality 燃料质量
     * @param fuelEfficiency 燃料效率
     * @param oxidantQuality 氧化剂质量
     * @param oxidantEfficiency 氧化剂效率
     */
    public static double thrustHeight(double fuelQuality, int fuelEfficiency, double oxidantQuality, double oxidantEfficiency) {
        return Math.min(fuelQuality, oxidantQuality) * fuelEfficiency * oxidantEfficiency;
    }
    
    /**
     * 火箭质量 = 指挥舱(默认) + 研究仓 + 货仓 + 燃料仓 + 氧化剂仓 + 推进器 + 燃料 + 氧化剂
     * @param researchNum 研究仓数量
     * @param warehouseNum 货仓数量
     * @param fuelBinNum 燃料仓数量
     * @param oxidantBinNum 氧化剂仓数量
     * @param thrusterQuality 推进器总质量
     * @param fuelQuality 燃料质量
     * @param oxidantQuality 氧化剂质量
     * @param hasToursim 是否有观光仓
     */
    public static double totalQuality(int researchNum, int warehouseNum, int fuelBinNum, int oxidantBinNum, 
            int thrusterQuality, double fuelQuality, double oxidantQuality, boolean hasToursim) {
        double totalQuality = RocketComponent.COMMANDER.quality() + (hasToursim ? RocketComponent.TOURISM.quality() : 0);
        totalQuality += researchNum * RocketComponent.RESEARCH.quality();
        totalQuality += warehouseNum * RocketComponent.WAREHOUSE.quality();
        totalQuality += fuelBinNum * RocketComponent.FUELBIN.quality();
        totalQuality += oxidantBinNum * RocketComponent.OXIDANTBIN.quality();
        return totalQuality + thrusterQuality + Math.min(fuelQuality, oxidantQuality) * 2;
    }
    
    /**
     * 计算 火箭推进器质量 = 单个推机器质量 * 推进器数量
     * @param engineType 火箭推进器种类
     * @param engineNum 推进器数量
     */
    public static int engineQuality(RocketComponent engineType, int engineNum) {
        for (RocketComponent component : RocketComponent.values()) {
            if (engineType.equals(component)) {
                return component.quality() * engineNum;
            }
        }
        return 0;
    }
}
