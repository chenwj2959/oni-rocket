package com.cwj.game.oxygen.rocket.core;

import javax.swing.JComboBox;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;
import com.cwj.game.oxygen.rocket.model.Result;
import com.cwj.game.oxygen.rocket.utils.RocketUtil;

/**
 * 计算火箭到达目标高度所能携带的最大负载
 */
public class CalcMaxLoad extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    
    private static final String TAB = "##";
    
    public CalcMaxLoad() {
        super(true, RocketComponent.RESEARCH, RocketComponent.WAREHOUSE, RocketComponent.TOURISM);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String calcResult() {
        rocket.addResearch(Integer.MIN_VALUE);
        rocket.addWareHouse(Integer.MIN_VALUE);
        int researchNum = 0, warehouseNum = 0;
        Result result = RocketUtil.calcMaxFinalHeight(rocket);
        if (result == null) return null;
        // 获取目标值
        JComboBox<Integer> finalHeightBox = (JComboBox<Integer>) get(FINAL_HEIGHT_BOX);
        int finalHeight = (int) finalHeightBox.getSelectedItem();
        if (result.getFinalHeight() < finalHeight) {
            return result.getResult("火箭无法到达" + finalHeight + " km");
        }
        while (warehouseNum < 4) {
            rocket.addWareHouse(1);
            Result currResult = RocketUtil.calcMaxFinalHeight(rocket);
            if (cannotFly(currResult, finalHeight)) {
                rocket.addWareHouse(-1);
                break;
            }
            result = currResult;
            warehouseNum++;
        }
        log.debug("火箭最大负载 {} 个货仓", warehouseNum);
        
        while (researchNum < 5) {
            rocket.addResearch(1);
            Result currResult = RocketUtil.calcMaxFinalHeight(rocket);
            if (cannotFly(currResult, finalHeight)) {
                rocket.addResearch(-1);
                break;
            }
            result = currResult;
            researchNum++;
        }
        if (researchNum == 0 && warehouseNum == 0) return result.getResult("火箭无法负载其它组件！");
        showRocket();
        StringBuilder builder = new StringBuilder();
        builder.append("最多能负载:").append(Constant.NEW_LINE);
        int warehourseQuality = warehouseNum * RocketComponent.WAREHOUSE.quality();
        if (warehouseNum != 0) builder.append(TAB).append(warehouseNum).append("个货仓(").append(warehourseQuality).append("kg)").append(Constant.NEW_LINE);
        int researchQuality = researchNum * RocketComponent.RESEARCH.quality();
        if (researchNum != 0) builder.append(TAB).append(researchNum).append("个研究仓(").append(researchQuality).append("kg)").append(Constant.NEW_LINE);
        builder.append(TAB).append("总计:").append(warehourseQuality + researchQuality).append("kg");
        return result.getResult(builder.toString());
    }
    
    private boolean cannotFly(Result result, int finalHeight) {
        return result == null || result.getFinalHeight() < 10000 || result.getFinalHeight() < finalHeight;
    }
}
