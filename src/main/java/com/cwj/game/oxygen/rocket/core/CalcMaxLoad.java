package com.cwj.game.oxygen.rocket.core;

import javax.swing.JComboBox;

import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;
import com.cwj.game.oxygen.rocket.model.Result;
import com.cwj.game.oxygen.rocket.utils.RocketUtil;

/**
 * 计算火箭到达目标高度所能携带的最大负载
 */
public class CalcMaxLoad extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    
    public CalcMaxLoad() {
        super(true, RocketComponent.RESEARCH, RocketComponent.WAREHOUSE, RocketComponent.TOURISM);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String calcResult() {
        int researchNum = 0, warehouseNum = 0;
        Result maxFinalHeightResult = RocketUtil.calcMaxFinalHeight(rocket);
        if (maxFinalHeightResult == null) return null;
        // 获取目标值
        JComboBox<Integer> finalHeightBox = (JComboBox<Integer>) get(FINAL_HEIGHT_BOX);
        int finalHeight = (int) finalHeightBox.getSelectedItem();
        if (maxFinalHeightResult.getFinalHeight() < finalHeight) {
            return maxFinalHeightResult.getResult("火箭无法到达" + finalHeight + " km");
        }
        Result result = null;
        while (researchNum < 5) {
            rocket.addResearch(1);
            Result currResult = RocketUtil.calcMaxFinalHeight(rocket);
            if (currResult == null) return result == null ? "火箭无法负载其它组件！" : result.getResult("最多能负载 " + researchNum + "个研究仓");
            result = currResult;
            researchNum++;
        }
        showRocket();
        return result.getResult("最多能负载 " + researchNum + "个研究仓");
    }
}
