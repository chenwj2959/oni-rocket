package com.cwj.game.oxygen.rocket.core;

import com.cwj.game.oxygen.rocket.constant.RocketComponent;

public class Rules {

    /**
     * 计算 最终飞行高度 = 燃料推力 - 火箭质量惩罚
     * @param thrustHeight 燃料推力
     * @param rocketMassPenalty 火箭质量惩罚(km)
     */
    public static double flightHeight(double thrustHeight, double rocketMassPenalty) {
        return thrustHeight - rocketMassPenalty;
    }
    
    /**
     * 燃料和氧化剂的消耗比  = 1 : 1
     * <br/>
     * 计算 燃料推力 = Math.min(燃料质量, 氧化剂质量) * 燃料效率 * 氧化剂效率
     * <br/>
     * 燃料质量 : 蒸汽 = 20, 精炼铁(固体推进) = 30, 石油 = 40, 液氢 = 60
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
     * 火箭质量惩罚 = 指挥舱(默认) + 研究仓 + 货仓 + 燃料仓 + 氧化剂仓 + 推进器 + 燃料 + 氧化剂
     * <br/>
     * 当火箭总质量 < 4000kg, 质量惩罚(km) = 总质量(kg)
     * <br/>
     * 火箭总质量 >= 4000kg, 质量惩罚(km) = (总质量(kg) / 300) * 3.2
     * @param researchNum 研究仓数量
     * @param warehouseNum 货仓数量
     * @param fuelBinNum 燃料仓数量
     * @param oxidantBinNum 氧化剂仓数量
     * @param thrusterQuality 推进器总质量
     * @param fuelQuality 燃料质量
     * @param oxidantQuality 氧化剂质量
     * @param hasToursim 是否有观光仓
     */
    public static double rocketMassPenalty(int researchNum, int warehouseNum, int fuelBinNum, int oxidantBinNum, 
            int thrusterQuality, double fuelQuality, double oxidantQuality, boolean hasToursim) {
        double totalQuality = RocketComponent.COMMANDER.quality() + (hasToursim ? RocketComponent.TOURISM.quality() : 0);
        totalQuality += researchNum * RocketComponent.RESEARCH.quality();
        totalQuality += warehouseNum * RocketComponent.WAREHOUSE.quality();
        totalQuality += fuelBinNum * RocketComponent.FUELBIN.quality();
        totalQuality += oxidantBinNum * RocketComponent.OXIDANTBIN.quality();
        totalQuality += thrusterQuality + fuelQuality + oxidantQuality;
        return totalQuality < 4000 ? totalQuality :  (totalQuality / 300) * 3.2;
    }
    
    /**
     * 计算 火箭推进器质量 = 单个推机器质量 * 推进器数量
     * @param engineType 火箭推进器种类
     * @param engineNum 推进器数量
     */
    public static int engineQuality(String engineType, int engineNum) {
        for (RocketComponent component : RocketComponent.values()) {
            if (engineType == component.componentName()) {
                return component.quality() * engineNum;
            }
        }
        return 0;
    }
}
