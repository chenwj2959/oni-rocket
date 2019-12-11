package com.cwj.game.oxygen.rocket.core;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;
import com.cwj.game.oxygen.rocket.model.Result;
import com.cwj.game.oxygen.rocket.utils.RocketUtil;

/**
 * 根据火箭组成计算能到达的最大高度
 * <br/>
 * 火箭组成包括 - 研究仓, 货仓(固体/液体/生物), 观光仓, 燃料仓, 氧化剂仓, 蒸汽/固体/石油/液氢推进器
 * 
 */
public class CalcMaxHeight extends AbstractCalculate {

    private static final long serialVersionUID = 1L;

    @Override
    protected Result calcResult() {
        RocketComponent engineType = rocket.getEngineType();
        if (engineType == null) return null;
        Result result = new Result();
        int componentQuality = rocket.getComponentQuality();
        result.setComponentQuality(componentQuality);
        result.setRocketLength(rocket.getLength());
        int maxFuelQuality = rocket.getFuelQuality();
        int totalQuality = RocketUtil.calcTotalQuality(rocket, maxFuelQuality);
        if (totalQuality > Constant.QUALITY_QUNISHMENT_SPLIT_VALUE) {
            log.debug("=== 最大总质量大于{}kg ===", Constant.QUALITY_QUNISHMENT_SPLIT_VALUE);
            int minFuelQuality = Math.max(Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - componentQuality, 0);
            minFuelQuality = Math.min(minFuelQuality, maxFuelQuality);
            int qualityPunishment = 0, maxHeight = 0, fuelQuality = 0, finalHeight = Integer.MIN_VALUE;
            for (int currFuelQuality = maxFuelQuality; currFuelQuality >= minFuelQuality; currFuelQuality--) {
                int currTotalQuality = RocketUtil.calcTotalQuality(rocket, currFuelQuality);
                int currQualityPunishment = RocketUtil.calcQualityPunishment(currTotalQuality);
                int currMaxHeight = RocketUtil.calcMaxHeight(rocket, currFuelQuality);
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
                int remindMaxHeight = RocketUtil.calcMaxHeight(rocket, remindFuelQuality);
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
            int maxHeight = RocketUtil.calcMaxHeight(rocket, maxFuelQuality);
            int qualityPunishment = RocketUtil.calcQualityPunishment(totalQuality);
            result.setFuelQuality(rocket.getFuelQuality());
            result.setMaxHeight(maxHeight);
            result.setQualityPunishment(qualityPunishment);
            result.setFinalHeight(maxHeight - qualityPunishment);
        }
        log.debug(result.toString());
        return result;
    }
}
