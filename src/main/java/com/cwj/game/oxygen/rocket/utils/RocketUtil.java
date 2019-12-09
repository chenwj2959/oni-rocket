package com.cwj.game.oxygen.rocket.utils;

import java.awt.Dimension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.FuelType;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.core.Rules;
import com.cwj.game.oxygen.rocket.model.Rocket;

public class RocketUtil {

    private static final Logger log = LoggerFactory.getLogger(RocketUtil.class);

    // TODO 检查火箭是否完整
    public static String checkRocket(Rocket rocket) {
        return null;
    }

    /** 计算火箭最大飞行高度 */
    public static int calcHeight(Rocket rocket) {
        // 计算推进器质量
        RocketComponent engineType = rocket.getEngineType();
        if (engineType == null)
            return 0;
        int engineQuality = Rules.engineQuality(engineType, 1);
        engineQuality += Rules.engineQuality(RocketComponent.ENGINE_IRON, rocket.getIronEngineNum());
        log.debug("推进器总质量为{}kg", engineQuality);
        // 计算燃料质量
        int fuelQuality = rocket.getFuelBinNum() * Constant.FUEL_BIN_MAX_QUALITY
                + rocket.getIronEngineNum() * Constant.ENGINE_IRON_MAX_FUEL_QUALITY
                + (rocket.getEngineType().equals(RocketComponent.ENGINE_STEAM) ? Constant.FUEL_BIN_MAX_QUALITY : 0);
        log.debug("燃料总质量为{}kg", fuelQuality);
        int oxidantQuality = rocket.getOxidantQuality() + rocket.getIronEngineNum() * Constant.ENGINE_IRON_MAX_OXIDANT_QUALITY;
        log.debug("氧化剂总质量为{}kg", oxidantQuality);
        // 计算质量
        double totalQuality = Rules.totalQuality(rocket.getResearchNum(), rocket.getWareHouseNum(),
                rocket.getFuelBinNum(), rocket.getOxidantBinNum(), engineQuality, fuelQuality, oxidantQuality,
                rocket.isHasToursim());
        log.debug("火箭总质量为{}kg", totalQuality);
        // 质量惩罚
        double rocketMassPenalty = Rules.rocketMassPenalty(totalQuality);
        log.debug("火箭质量惩罚为{}km", rocketMassPenalty);
        // 计算总推力
        double maxHeight = Rules.thrustHeight(fuelQuality, rocket.getFuelType().efficiency(), oxidantQuality,
                rocket.getOxidantType().efficiency());
        maxHeight += rocket.getIronEngineNum() * Constant.ENGINE_IRON_MAX_FUEL_QUALITY * FuelType.IRON.efficiency();
        maxHeight += rocket.getEngineType().equals(RocketComponent.ENGINE_STEAM)
                ? Constant.FUEL_BIN_MAX_QUALITY * FuelType.STEAM.efficiency() : 0;
        log.debug("火箭总推力为{}km", maxHeight);
        int finalHeight = (int) Rules.flightHeight(maxHeight, rocketMassPenalty);
        log.debug("火箭最大飞行高度为{}km", finalHeight);
        return finalHeight;
    }

    /**
     * 计算最大飞行高度和燃料质量
     * @param fuelEfficiency 燃料效率
     * @param oxidantEfficiency 氧化剂效率
     * @param componentQuality 组件质量
     * @param maxfuelQuality 燃料质量
     */
    public static Dimension getBestFuelQuality(int fuelEfficiency, double oxidantEfficiency, int componentQuality,
            int maxfuelQuality) {
        if (componentQuality < 4000) {
            double finalHeight = (fuelEfficiency * oxidantEfficiency - 2) * maxfuelQuality - componentQuality;
            return new Dimension(maxfuelQuality, (int) finalHeight);
        }
        int bestfuelQuality = 1;
        double lastFinalHeight = bestfuelQuality * fuelEfficiency * oxidantEfficiency
                - Math.pow((componentQuality + 2 * bestfuelQuality) * 1.0 / 300, 3.2), maxFinalHeight = 0;
        for (int fuelQuality = 2; fuelQuality <= maxfuelQuality; fuelQuality++) {
            double finalHeight = fuelQuality * fuelEfficiency * oxidantEfficiency
                    - Math.pow((componentQuality + 2 * fuelQuality) * 1.0 / 300, 3.2);
            if (finalHeight > maxFinalHeight) {
                maxFinalHeight = finalHeight;
                bestfuelQuality = fuelQuality;
            }
            if (finalHeight <= lastFinalHeight)
                break;
            lastFinalHeight = finalHeight;
        }
        return new Dimension(bestfuelQuality, (int) Math.floor(maxFinalHeight));
    }
}
