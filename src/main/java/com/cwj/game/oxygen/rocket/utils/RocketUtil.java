package com.cwj.game.oxygen.rocket.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.model.Result;
import com.cwj.game.oxygen.rocket.model.Rocket;

public class RocketUtil {

    private static final Logger log = LoggerFactory.getLogger(RocketUtil.class);

    // TODO 检查火箭是否完整
    public static String checkRocket(Rocket rocket) {
        return null;
    }
    
    /** 
     * 计算火箭最大飞行高度和燃料质量
     */
    public static Result calcHeight2(Rocket rocket) {
        RocketComponent engineType = rocket.getEngineType();
        if (engineType == null) return null;
        Result result = new Result();
        int componentQuality = rocket.getComponentQuality();
        log.debug("组件质量 {} kg", componentQuality);
        result.setComponentQuality(componentQuality);
        if (RocketComponent.ENGINE_STEAM.equals(engineType)) {
            log.debug("蒸汽推进器");
            int fuelQuality = rocket.getFuelQuality();
            result.setFuelQuality(fuelQuality);
            log.debug("燃料质量 {} kg", fuelQuality);
            if (componentQuality + fuelQuality < Constant.QUALITY_QUNISHMENT_SPLIT_VALUE) {
                int totalQuality = componentQuality + fuelQuality;
                result.setTotalQuality(totalQuality);
                result.setQualityPunishment(totalQuality);
                log.debug("质量惩罚 = 总质量 {} km/kg", Constant.QUALITY_QUNISHMENT_SPLIT_VALUE);
                int maxHeight = rocket.getFuelQuality() * rocket.getFuelType().efficiency();
                result.setMaxHeight(maxHeight);
                log.debug("最大推力 {} km", maxHeight);
                int finalHeight = maxHeight - totalQuality;
                log.debug("最终高度 {} km", finalHeight);
                result.setFinalHeight(finalHeight);
                return result;
            }
            
        }
    }
    
    /** 
     * 计算火箭最大飞行高度和燃料质量
     */
    public static Result calcHeight(Rocket rocket) {
        RocketComponent engineType = rocket.getEngineType();
        if (engineType == null) return null;
        Result less = getResultLess(rocket);
        Result more = getResultMore(rocket);
        Result result = less.getFinalHeight() > more.getFinalHeight() ? less : more;
        log.debug("Best fuel quality = {}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 计算总质量小于4000时的结果
     */
    private static Result getResultLess(Rocket rocket) {
        Result result = new Result();
        result.setComponentQuality(rocket.getComponentQuality());
        int fuelQuality;
        double maxHeight;
        int qualityPunishment;
        if (rocket.getEngineType().equals(RocketComponent.ENGINE_STEAM)) {
            fuelQuality = Math.min(Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - rocket.getComponentQuality(), rocket.getFuelQuality());
            maxHeight = rocket.getFuelType().efficiency() * fuelQuality;
            qualityPunishment = fuelQuality + rocket.getComponentQuality();
        } else {
            fuelQuality = (int) Math.floor((Math.min(Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - rocket.getComponentQuality(), rocket.getFuelQuality())) / 2);
            maxHeight = rocket.getFuelType().efficiency() * rocket.getOxidantType().efficiency() * fuelQuality;
            qualityPunishment = 2 * fuelQuality + rocket.getComponentQuality();
        }
        double finalHeight = maxHeight - qualityPunishment;
        result.setFinalHeight(finalHeight);
        result.setFuelQuality(fuelQuality);
        result.setMaxHeight(maxHeight);
        result.setQualityPunishment(qualityPunishment);
        result.setTotalQuality(qualityPunishment);
        return result;
    }
    
    /**
     * 计算总质量大于4000时的结果
     */
    private static Result getResultMore(Rocket rocket) {
        Result result = new Result();
        result.setComponentQuality(rocket.getComponentQuality());
        int minfuelQuality;
        double maxHeight;
        int totalQuality;
        if (rocket.getEngineType().equals(RocketComponent.ENGINE_STEAM)) {
            minfuelQuality = Math.min(Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - rocket.getComponentQuality(), rocket.getFuelQuality() - 1) + 1;
            maxHeight = rocket.getFuelType().efficiency() * minfuelQuality;
            totalQuality = rocket.getComponentQuality() + minfuelQuality;
        } else {
            minfuelQuality = (int) Math.floor((Math.min(Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - rocket.getComponentQuality(), rocket.getFuelQuality())) / 2) + 1;
            totalQuality = rocket.getComponentQuality() + 2 * minfuelQuality;
            maxHeight = rocket.getFuelType().efficiency() * rocket.getOxidantType().efficiency() * minfuelQuality;
        }
        double qualityPunishment = Math.pow((totalQuality * 1.0 / 300), 3.2);
        double lastFinalHeight = maxHeight - qualityPunishment;
        double maxFinalHeight = lastFinalHeight;
        
        result.setMaxHeight(maxHeight);
        result.setTotalQuality(totalQuality);
        result.setFuelQuality(minfuelQuality);
        result.setQualityPunishment(qualityPunishment);
        result.setFinalHeight(lastFinalHeight);
        for (int fuelQuality = rocket.getFuelQuality(); fuelQuality > minfuelQuality; fuelQuality--) {
            if (rocket.getEngineType().equals(RocketComponent.ENGINE_STEAM)) {
                totalQuality = rocket.getComponentQuality() + fuelQuality;
                maxHeight = rocket.getFuelType().efficiency() * fuelQuality;
            } else {
                totalQuality = rocket.getComponentQuality() + 2 * fuelQuality;
                maxHeight = rocket.getFuelType().efficiency() * rocket.getOxidantType().efficiency() * fuelQuality;
            }
            qualityPunishment = Math.pow((totalQuality * 1.0 / 300), 3.2);
            double finalHeight = maxHeight - qualityPunishment;
            if (finalHeight > maxFinalHeight) {
                maxFinalHeight = finalHeight;
                result.setMaxHeight(maxHeight);
                result.setTotalQuality(totalQuality);
                result.setQualityPunishment(qualityPunishment);
                result.setFinalHeight(maxFinalHeight);
                result.setFuelQuality(fuelQuality);
            }
            if (finalHeight > lastFinalHeight)
                break;
            lastFinalHeight = finalHeight;
        }
        return result;
    }
}
