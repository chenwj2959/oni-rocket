package com.cwj.game.oxygen.rocket.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.FuelType;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.model.Rocket;

public class RocketUtil {

    private static final Logger log = LoggerFactory.getLogger(RocketUtil.class);

    // TODO 检查火箭是否完整
    public static String checkRocket(Rocket rocket) {
        return null;
    }
    
    /**
     * 计算火箭总质量
     */
    public static int calcTotalQuality(Rocket rocket, int fuelQuality) {
     // 组件质量
        int componentQuality = rocket.getComponentQuality();
        log.debug("火箭组件质量 = {} kg", componentQuality);
        fuelQuality *= RocketComponent.ENGINE_STEAM.equals(rocket.getEngineType()) ? 1 : 2;
        log.debug("火箭液体燃料质量 = {} kg", fuelQuality);
        int ironFuelQuality = rocket.getIronEngineNum() * Constant.ENGINE_IRON_MAX_FUEL_QUALITY;
        if (ironFuelQuality != 0) log.debug("火箭固体燃料质量 = {} kg", ironFuelQuality);
        int totalQuality = componentQuality + fuelQuality + ironFuelQuality;
        log.debug("火箭总质量 = {} kg", totalQuality);
        return totalQuality;
    }
    
    /**
     * 计算火箭质量惩罚
     */
    public static int calcQualityPunishment(int totalQuality) {
        return totalQuality > Constant.QUALITY_QUNISHMENT_SPLIT_VALUE ? (int) Math.round(Math.pow((totalQuality * 1.0 / 300), 3.2)) : totalQuality;
    }
    
    /**
     * 计算火箭的最大推进高度
     */
    public static int calcMaxHeight(Rocket rocket, int fuelQuality) {
        int maxHeight = 0;
        RocketComponent engineType = rocket.getEngineType();
        if (RocketComponent.ENGINE_STEAM.equals(engineType)) {
            maxHeight += fuelQuality * rocket.getFuelType().efficiency();
        } else {
            maxHeight += fuelQuality * rocket.getFuelType().efficiency() * rocket.getOxidantType().efficiency();
        }
        log.debug("{}推进器总推力 = {} km", engineType.componentName(), maxHeight);
        int ironEngineNum = rocket.getIronEngineNum();
        int ironMaxHeight = ironEngineNum * FuelType.IRON.efficiency() * Constant.ENGINE_IRON_MAX_FUEL_QUALITY;
        if (ironMaxHeight != 0) { 
            log.debug("固体推进器总推力 = {} km", ironMaxHeight);
            maxHeight += ironMaxHeight;
            log.debug("推进器总推力 = {} km", maxHeight);
        }
        return maxHeight;
    }
}
