package com.cwj.game.oxygen.rocket.core;

import javax.swing.JComboBox;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;
import com.cwj.game.oxygen.rocket.model.Result;
import com.cwj.game.oxygen.rocket.utils.RocketUtil;

/**
 * 计算火箭到达目标高度最佳的燃料质量
 */
public class CalcFuelQuality extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    public CalcFuelQuality() {
        super(true, RocketComponent.ENGINE_IRON);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected String calcResult() {
        rocket.setIronEngineNum(0);
        // 计算火箭最大飞行高度
        Result maxFinalHeightResult = RocketUtil.calcMaxFinalHeight(rocket);
        if (maxFinalHeightResult == null) return null;
        // 获取目标值
        JComboBox<Integer> finalHeightBox = (JComboBox<Integer>) get(FINAL_HEIGHT_BOX);
        int finalHeight = (int) finalHeightBox.getSelectedItem();
        if (maxFinalHeightResult.getFinalHeight() < finalHeight) {
            log.debug("尝试添加固体助推器");
            int ironFinalHeight = Integer.MIN_VALUE;
            Result ironResult = null;
            while (true) {
                rocket.addIronEngine(1);
                log.debug("添加固体助推器{}个", rocket.getIronEngineNum());
                ironResult = RocketUtil.calcMaxFinalHeight(rocket);
                int currIronFinalHeight = ironResult.getFinalHeight();
                log.debug("最大飞行高度{}km", currIronFinalHeight);
                if (currIronFinalHeight < ironFinalHeight || currIronFinalHeight > finalHeight) break;
                ironFinalHeight = currIronFinalHeight;
            }
            if (ironResult.getFinalHeight() < finalHeight) {
                rocket.setIronEngineNum(0);
                showRocket();
                return maxFinalHeightResult.getResult("火箭无法到达" + finalHeight + " km");
            }
            log.debug("添加固体助推器{}个后火箭可达目标高度", rocket.getIronEngineNum());
            maxFinalHeightResult = ironResult;
        }
        // 刷新火箭主体显示, 避免将上次计算添加的固体助推器显示出来
        showRocket();
        // 最大燃料质量
        int maxFuelQuality = maxFinalHeightResult.getFuelQuality();
        // 大于4000kg时最小燃料质量
        int minFuelQuality = Constant.QUALITY_QUNISHMENT_SPLIT_VALUE - rocket.getComponentQuality();
        minFuelQuality = RocketComponent.ENGINE_STEAM.equals(rocket.getEngineType()) ? minFuelQuality : (int) Math.floor(minFuelQuality / 2) + 1;
        if (minFuelQuality > 0) {
         // 计算最小燃料质量的飞行高度, 如果最小也大于目标值, 则考虑小于等于4000kg
            int minTotalQuality = RocketUtil.calcTotalQuality(rocket, minFuelQuality);
            int minQualityPunishment = RocketUtil.calcQualityPunishment(minTotalQuality);
            int minMaxHeight = RocketUtil.calcMaxHeight(rocket, minFuelQuality);
            int minFinalHeight = minMaxHeight - minQualityPunishment;
            if (minFinalHeight == finalHeight) { // 刚好和目标相等
                Result result = new Result();
                result.setComponentQuality(rocket.getComponentQuality());
                result.setFuelQuality(minFuelQuality);
                result.setTotalQuality(minTotalQuality);
                result.setQualityPunishment(minQualityPunishment);
                result.setMaxHeight(minMaxHeight);
                result.setFinalHeight(minFinalHeight);
                result.setRocketLength(rocket.getLength());
                return addIronIfHas(result);
            } else if (minFinalHeight > finalHeight) {
                // 结果落在质量小于等于4000kg
                int fuelQuality;
                if (RocketComponent.ENGINE_STEAM.equals(rocket.getEngineType())) {
                    fuelQuality = (int) Math.ceil((finalHeight + rocket.getComponentQuality()) * 1.0 / (rocket.getFuelType().efficiency() - 1));
                } else {
                    fuelQuality = (int) Math.ceil((finalHeight + rocket.getComponentQuality()) * 1.0 / 
                            (rocket.getFuelType().efficiency() * rocket.getOxidantType().efficiency() - 2));
                }
                int totalQuality = RocketUtil.calcTotalQuality(rocket, fuelQuality);
                int maxHeight = RocketUtil.calcMaxHeight(rocket, fuelQuality);
                
                Result result = new Result();
                result.setFuelQuality(fuelQuality);
                result.setComponentQuality(rocket.getComponentQuality());
                result.setTotalQuality(totalQuality);
                result.setQualityPunishment(totalQuality);
                result.setMaxHeight(maxHeight);
                result.setFinalHeight(maxHeight - totalQuality);
                result.setRocketLength(rocket.getLength());
                return addIronIfHas(result);
            }
        }
        while (true) {
            int middleFuelQuality = (minFuelQuality + maxFuelQuality) / 2;
            int currTotalQuality = RocketUtil.calcTotalQuality(rocket, middleFuelQuality);
            int currQualityPunishment = RocketUtil.calcQualityPunishment(currTotalQuality);
            int currMaxHeight = RocketUtil.calcMaxHeight(rocket, middleFuelQuality);
            int currFinalHeight = currMaxHeight - currQualityPunishment;
            log.debug("min = {}, middle = {}, max = {}, finalHeight = {}", minFuelQuality, middleFuelQuality, maxFuelQuality, currFinalHeight);
            // 1. 计算高度 = 目标高度
            // 2. 边界
            // 3. 计算高度 > 目标高度 && 计算高度 - 目标高度 < 1kg燃料所能提供的推力
            if (currFinalHeight == finalHeight || middleFuelQuality == minFuelQuality || middleFuelQuality == maxFuelQuality
                    || currFinalHeight > finalHeight && currFinalHeight - finalHeight < rocket.getFuelType().efficiency() * rocket.getOxidantType().efficiency()) {
                Result result = new Result();
                result.setComponentQuality(rocket.getComponentQuality());
                result.setFuelQuality(middleFuelQuality);
                result.setTotalQuality(currTotalQuality);
                result.setQualityPunishment(currQualityPunishment);
                result.setMaxHeight(currMaxHeight);
                result.setFinalHeight(currFinalHeight);
                result.setRocketLength(rocket.getLength());
                return addIronIfHas(result);
            } else if (currFinalHeight < finalHeight) minFuelQuality = middleFuelQuality;
            else maxFuelQuality = middleFuelQuality;
        }
    }
    
    private String addIronIfHas(Result result) {
        int ironEngineNum = rocket.getIronEngineNum();
        return result.getResult(ironEngineNum > 0 ? "需要添加固体助推器" + ironEngineNum + "个" + Constant.NEW_LINE : null);
    }
}
