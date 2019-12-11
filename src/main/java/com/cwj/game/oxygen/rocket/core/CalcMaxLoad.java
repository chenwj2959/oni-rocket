package com.cwj.game.oxygen.rocket.core;

import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;

/**
 * 计算火箭到达目标高度所能携带的最大负载
 */
public class CalcMaxLoad extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    
    public CalcMaxLoad() {
        super(RocketComponent.RESEARCH, RocketComponent.WAREHOUSE);
    }

    @Override
    protected String calcResult() {
        return null;
    }
}
