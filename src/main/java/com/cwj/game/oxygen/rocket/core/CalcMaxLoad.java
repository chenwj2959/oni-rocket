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
        Result maxFinalHeightResult = RocketUtil.calcMaxFinalHeight(rocket);
        if (maxFinalHeightResult == null) return null;
        // 获取目标值
        JComboBox<Integer> finalHeightBox = (JComboBox<Integer>) get(FINAL_HEIGHT_BOX);
        int finalHeight = (int) finalHeightBox.getSelectedItem();
        if (maxFinalHeightResult.getFinalHeight() < finalHeight) {
            return maxFinalHeightResult.getResult("火箭无法到达" + finalHeight + " km");
        }
        return null;
    }
}
