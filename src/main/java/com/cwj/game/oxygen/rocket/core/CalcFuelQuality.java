package com.cwj.game.oxygen.rocket.core;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;
import com.cwj.game.oxygen.rocket.model.Result;

public class CalcFuelQuality extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    
    public CalcFuelQuality() {
        super(RocketComponent.ENGINE_IRON);
    }
    
    @Override
    protected Dimension createLeftButton() {
        Dimension dimension = super.createLeftButton();
        int buttonTop = BUTTON_MARGIN_TOP + DEFAULT_HEIGHT + dimension.height;
        JLabel finalHeightLabel = new JLabel(Constant.LABEL_FINAL_HEIGHT);
        finalHeightLabel.setBounds(MARGIN_LEFT, buttonTop, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.add(finalHeightLabel);
        Integer[] heightArray = new Integer[19];
        for (int i = 0; i < 19; i++) {
            heightArray[i] = 10000 * (i + 1);
        }
        JComboBox<Integer> finalHeightBox = new JComboBox<Integer>(heightArray);
        finalHeightBox.setBounds(MARGIN_LEFT + DEFAULT_WIDTH, buttonTop, dimension.width - MARGIN_LEFT - DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.add(finalHeightBox);
        dimension.height = buttonTop;
        return dimension;
    }

    @Override
    protected Result calcResult() {
        return null;
    }
}
