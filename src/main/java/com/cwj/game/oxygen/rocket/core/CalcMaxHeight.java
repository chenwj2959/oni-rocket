package com.cwj.game.oxygen.rocket.core;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextArea;

import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;

/**
 * 根据火箭组成计算能到达的最大高度
 * <br/>
 * 火箭组成包括 - 研究仓, 货仓(固体/液体/生物), 观光仓, 燃料仓, 氧化剂仓, 蒸汽/固体/石油/液氢推进器
 * 
 */
public class CalcMaxHeight extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    
    public static final String ROCKET_TEXT = "RocketText";
    
    public CalcMaxHeight() {
        int height = createLeftButton();
        
        // 显示火箭主体
        JTextArea rocketText = new JTextArea("指挥舱");
        rocketText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rocketText.setBounds(MARGIN_LEFT * 2 + TEXT_WIDTH, MARGIN_TOP, TEXT_WIDTH, height);
        rocketText.setEnabled(false);
        this.add(rocketText);
        put(ROCKET_TEXT, rocketText);
    }
    
    /**
     * 生成左边的按钮
     */
    private int createLeftButton() {
        int buttonTop = MARGIN_TOP;
        RocketComponent[] components = RocketComponent.values();
        for (int i = 0; i < components.length; i++) {
            JButton button = new JButton(components[i].componentName());
            button.setBounds(MARGIN_LEFT, buttonTop, TEXT_WIDTH, TEXT_HEIGHT);
            button.addActionListener(setAddToTextAreaListener());
            this.add(button);
            if (i != components.length - 1) buttonTop += BUTTON_MARGIN_TOP + TEXT_HEIGHT;
        }
        return buttonTop;
    }
    
    /**
     * 按钮的点击事件, 点击时将按钮上的文字添加到右边显示火箭主体的多行文本
     */
//    JOptionPane.showMessageDialog(null, issue, "Error", JOptionPane.WARNING_MESSAGE);
    private ActionListener setAddToTextAreaListener() {
        return (ActionEvent e) -> {
            JButton button = (JButton) e.getSource();
            String text = button.getText();
            rocket.addComponent(text);
            flushTextArea();
        };
    }
    
    /**
     * 根据rocket生成多行文本
     * 
     */
    private void flushTextArea() {
        JTextArea rocketText = (JTextArea) get(ROCKET_TEXT);
        rocketText.setText("");
    }
}
