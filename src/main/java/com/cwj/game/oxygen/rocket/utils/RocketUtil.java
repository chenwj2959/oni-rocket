package com.cwj.game.oxygen.rocket.utils;

import java.awt.Dimension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.model.Rocket;

public class RocketUtil {

    private static final Logger log = LoggerFactory.getLogger(RocketUtil.class);

    // TODO 检查火箭是否完整
    public static String checkRocket(Rocket rocket) {
        return null;
    }
    
    /** 
     * 计算火箭最大飞行高度 
     * @return Dimension(fuelQuality, maxHeight)
     */
    public static Dimension calcHeight(Rocket rocket) {
        // 计算推进器质量
        RocketComponent engineType = rocket.getEngineType();
        if (engineType == null) return null;
        Dimension result = getBestFuelQuality(rocket.getFuelType().efficiency(), 
                rocket.getOxidantType().efficiency(), rocket.getComponentQuality(),
                rocket.getFuelQuality());
        log.debug("Best fuel quality = {}kg, max height = {}", result.getWidth(), result.getHeight());
        return result;
    }

    /**
     * 计算最大飞行高度和燃料质量
     * @param fuelEfficiency 燃料效率
     * @param oxidantEfficiency 氧化剂效率
     * @param componentQuality 组件质量
     * @param maxfuelQuality 燃料质量
     */
    private static Dimension getBestFuelQuality(int fuelEfficiency, double oxidantEfficiency, int componentQuality,
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
        return new Dimension(bestfuelQuality, (int) Math.round(maxFinalHeight));
    }
}
