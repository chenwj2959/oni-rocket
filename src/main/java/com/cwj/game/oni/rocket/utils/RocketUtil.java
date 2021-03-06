package com.cwj.game.oni.rocket.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwj.game.oni.rocket.constant.Constant;
import com.cwj.game.oni.rocket.constant.FuelType;
import com.cwj.game.oni.rocket.constant.RocketComponent;
import com.cwj.game.oni.rocket.model.Result;
import com.cwj.game.oni.rocket.model.Rocket;

public class RocketUtil {

    private static final Logger log = LoggerFactory.getLogger(RocketUtil.class);
    
    /**
     * 计算火箭最大的飞行高度
     */
    public static Result calcMaxFinalHeight(Rocket rocket) {
        RocketComponent engineType = rocket.getEngineType();
        if (engineType == null) return null;
        Result result = new Result();
        int componentQuality = rocket.getComponentQuality();
        result.setComponentQuality(componentQuality);
        result.setRocketLength(rocket.getLength());
        int maxFuelQuality = rocket.getFuelQuality();
        int totalQuality = calcTotalQuality(rocket, maxFuelQuality);
        if (totalQuality > Constant.QUALITY_QUNISHMENT_SPLIT_VALUE) {
            log.debug("=== 最大总质量大于{}kg ===", Constant.QUALITY_QUNISHMENT_SPLIT_VALUE);
            int minFuelQuality = Math.max(Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - componentQuality, 0);
            minFuelQuality = Math.min(minFuelQuality, maxFuelQuality);
            int qualityPunishment = 0, maxHeight = 0, fuelQuality = 0, finalHeight = Integer.MIN_VALUE;
            for (int currFuelQuality = maxFuelQuality; currFuelQuality >= minFuelQuality; currFuelQuality--) {
                int currTotalQuality = calcTotalQuality(rocket, currFuelQuality);
                int currQualityPunishment = calcQualityPunishment(currTotalQuality);
                int currMaxHeight = calcMaxHeight(rocket, currFuelQuality);
                int currFinalHeight = currMaxHeight - currQualityPunishment;
                log.debug("总质量{}kg燃料质量{}kg时最大飞行高度为{}km, 上次飞行高度为{}km", currTotalQuality, currFuelQuality, currFinalHeight, finalHeight);
                if (currFinalHeight < finalHeight) break;
                finalHeight = currFinalHeight;
                maxHeight = currMaxHeight;
                qualityPunishment = currQualityPunishment;
                totalQuality = currTotalQuality;
                fuelQuality = currFuelQuality;
            }
            log.debug("总质量大于{}kg时最终飞行高度为{}km", Constant.QUALITY_QUNISHMENT_SPLIT_VALUE, finalHeight);
            // 当组件质量 < 4000, 计算燃料质量 = 4000 - 组件质量时的飞行高度
            if (componentQuality < Constant.QUALITY_QUNISHMENT_SPLIT_VALUE) {
                log.debug("=== 组件质量小于{}kg时 ===", Constant.QUALITY_QUNISHMENT_SPLIT_VALUE);
                int remindFuelQuality = Math.min(Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - componentQuality, Constant.FUEL_BIN_MAX_QUALITY);
                int remindTotalQuality = remindFuelQuality + componentQuality;
                remindFuelQuality = RocketComponent.ENGINE_STEAM.equals(rocket.getEngineType()) ? remindFuelQuality : remindFuelQuality / 2;
                int remindMaxHeight = calcMaxHeight(rocket, remindFuelQuality);
                int remindFinalHeight = remindMaxHeight - remindTotalQuality;
                log.debug("总质量等于{}kg时最大飞行高度为{}km", Constant.QUALITY_QUNISHMENT_SPLIT_VALUE, remindFinalHeight);
                if (remindFinalHeight > finalHeight) {
                    finalHeight = remindFinalHeight;
                    maxHeight = remindMaxHeight;
                    qualityPunishment = remindTotalQuality;
                    totalQuality = remindTotalQuality;
                    fuelQuality = remindTotalQuality - componentQuality;
                }
            }
            result.setFinalHeight(finalHeight);
            result.setMaxHeight(maxHeight);
            result.setQualityPunishment(qualityPunishment);
            result.setTotalQuality(totalQuality);
            result.setFuelQuality(fuelQuality);
        } else {
            // 总质量 < 4000时
            result.setTotalQuality(totalQuality);
            int maxHeight = calcMaxHeight(rocket, maxFuelQuality);
            int qualityPunishment = calcQualityPunishment(totalQuality);
            result.setFuelQuality(rocket.getFuelQuality());
            result.setMaxHeight(maxHeight);
            result.setQualityPunishment(qualityPunishment);
            result.setFinalHeight(maxHeight - qualityPunishment);
        }
        return result;
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
        int totalQuality = componentQuality + fuelQuality;
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
        log.debug("{}液体推进器总推力 = {} km", engineType.componentName(), maxHeight);
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
