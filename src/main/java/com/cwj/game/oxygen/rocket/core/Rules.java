package com.cwj.game.oxygen.rocket.core;

public class Rules {

    /**
     * 计算 最终飞行高度 = 燃料推力 - 重量惩罚
     * @param thrust 燃料推力
     * @param quality 重量惩罚
     */
    public double flightHeight(double thrust, int quality) {
        return thrust - quality;
    }
    
    /**
     * 燃料和氧化剂的消耗比  = 1 : 1
     * <br/>
     * 计算 燃料推力 = Math.min(燃料质量, 氧化剂质量) * 燃料效率 * 氧化剂效率
     * <br/>
     * 燃料质量 : 蒸汽 = 20, 精炼铁(固体推进) = 30, 石油 = 40, 液氢 = 60
     * <br/>
     * 氧化剂效率 : 氧石 = 1, 液氧 = 4 / 3
     * @param fuelQuality 燃料质量
     * @param fuelEfficiency 燃料效率
     * @param oxidantQuality 氧化剂质量
     * @param oxidantEfficiency 氧化剂效率
     */
    public double thrustHeight(int fuelQuality, int fuelEfficiency, int oxidantQuality, double oxidantEfficiency) {
        return Math.min(fuelQuality, oxidantQuality) * fuelEfficiency * oxidantEfficiency;
    }
}
