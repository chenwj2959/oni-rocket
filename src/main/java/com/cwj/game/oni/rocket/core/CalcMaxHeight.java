package com.cwj.game.oni.rocket.core;

import com.cwj.game.oni.rocket.framework.AbstractCalculate;
import com.cwj.game.oni.rocket.model.Result;
import com.cwj.game.oni.rocket.utils.RocketUtil;

/**
 * 根据火箭组成计算能到达的最大高度
 * <br/>
 * 火箭组成包括 - 研究仓, 货仓(固体/液体/生物), 观光仓, 燃料仓, 氧化剂仓, 蒸汽/固体/石油/液氢推进器
 * 
 */
public class CalcMaxHeight extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    
    public CalcMaxHeight() {
        super(false);
    }

    @Override
    protected String calcResult() {
        Result result = RocketUtil.calcMaxFinalHeight(rocket);
        if (result == null) return null;
        log.debug(result.getResult(null));
        return result.getResult(null);
    }
}
