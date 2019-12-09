package com.cwj.game.oxygen.rocket.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.OxidantType;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.framework.AbstractCalculate;
import com.cwj.game.oxygen.rocket.utils.RocketUtil;

/**
 * 根据火箭组成计算能到达的最大高度
 * <br/>
 * 火箭组成包括 - 研究仓, 货仓(固体/液体/生物), 观光仓, 燃料仓, 氧化剂仓, 蒸汽/固体/石油/液氢推进器
 * 
 */
public class CalcMaxHeight extends AbstractCalculate {

    private static final long serialVersionUID = 1L;
    
    // 被存储的组件KEY
    public static final String ROCKET_TEXT = "RocketText";
    public static final String RESULT = "Result";
    
    public CalcMaxHeight() {
        Dimension dimension = createLeftButton();
        
        // 显示火箭主体
        JTextArea textArea = new JTextArea(RocketComponent.COMMANDER.componentName());
        textArea.setEnabled(false);
        JScrollPane rocketText = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rocketText.setBounds(MARGIN_LEFT + dimension.width, MARGIN_TOP, DEFAULT_WIDTH, dimension.height);
        rocketText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(rocketText);
        put(ROCKET_TEXT, textArea);
        
        // 显示结果
        JTextArea result = new JTextArea();
        result.setLineWrap(true);
        result.setBackground(new Color(238, 238, 238));
        result.setEnabled(false);
        result.setBounds(MARGIN_LEFT * 2 + dimension.width + DEFAULT_WIDTH, MARGIN_TOP, DEFAULT_WIDTH, dimension.height);
        this.add(result);
        put(RESULT, result);
    }
    
    /**
     * 生成左边的按钮
     * @return 左边区域占的大小
     */
    private Dimension createLeftButton() {
        int buttonTop = MARGIN_TOP;
        RocketComponent[] components = RocketComponent.values();
        for (int i = 0; i < components.length; i++) {
            if (components[i].equals(RocketComponent.COMMANDER)) continue;
            String componentName = components[i].componentName();
            // label
            JLabel label = new JLabel(componentName);
            label.setBounds(MARGIN_LEFT, buttonTop, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            this.add(label);
            // "+" button
            JButton addButton = new JButton("+");
            addButton.setName(componentName);
            addButton.setBounds(MARGIN_LEFT + DEFAULT_WIDTH, buttonTop, BUTTON_WIDTH, DEFAULT_HEIGHT);
            addButton.addActionListener(addToTextAreaListener());
            this.add(addButton);
            // "-" button
            JButton removeButton = new JButton("-");
            removeButton.setName(componentName);
            removeButton.setBounds(MARGIN_LEFT * 2 + DEFAULT_WIDTH + BUTTON_WIDTH, buttonTop, BUTTON_WIDTH, DEFAULT_HEIGHT);
            removeButton.addActionListener(removeTextAreaLastListener());
            this.add(removeButton);
            
            buttonTop += BUTTON_MARGIN_TOP + DEFAULT_HEIGHT;
        }
        // 氧化剂类型选择
        JLabel oxidantTypeLabel = new JLabel(Constant.LABEL_OXIDANT_TYPE);
        oxidantTypeLabel.setBounds(MARGIN_LEFT, buttonTop, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.add(oxidantTypeLabel);
        String[] oxidantType = OxidantType.allValue();
        JComboBox<String> oxidantBox = new JComboBox<>(oxidantType);
        oxidantBox.setBounds(MARGIN_LEFT + DEFAULT_WIDTH, buttonTop, BUTTON_WIDTH * 2 + MARGIN_LEFT, DEFAULT_HEIGHT);
        oxidantBox.addActionListener(oxidantTypeListener());
        this.add(oxidantBox);
        rocket.setOxidantType(oxidantType[0]);
        return new Dimension(MARGIN_LEFT * 2 + DEFAULT_WIDTH + BUTTON_WIDTH * 2, buttonTop);
    }
    
    /**
     * 按钮的点击事件, 点击时将按钮上的文字添加到右边显示火箭主体的多行文本
     */
    private ActionListener addToTextAreaListener() {
        return (ActionEvent e) -> {
            JButton button = (JButton) e.getSource();
            String text = button.getName();
            rocket.addComponent(text);
            JTextArea rocketText = (JTextArea) get(ROCKET_TEXT);
            rocketText.setText(rocket.getRocket());
            showResult();
        };
    }
    
    /**
     * 按钮点击事件, 点击时从右边显示火箭主体的多行文本中突出组件
     */
    private ActionListener removeTextAreaLastListener() {
        return (ActionEvent e) -> {
            JButton button = (JButton) e.getSource();
            String text = button.getName();
            rocket.removeComponent(text);
            JTextArea rocketText = (JTextArea) get(ROCKET_TEXT);
            rocketText.setText(rocket.getRocket());
            showResult();
        };
    }
    
    /**
     * 氧化剂选择框监听事件
     */
    @SuppressWarnings("unchecked")
    private ActionListener oxidantTypeListener() {
        return (ActionEvent e) -> {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            rocket.setOxidantType(String.valueOf(comboBox.getSelectedItem()));
            showResult();
        };
    }
    
    /**
     * 显示结果
     */
    private void showResult() {
        JTextArea result = (JTextArea) get(RESULT);
        String issues = RocketUtil.checkRocket(rocket);
        if (issues != null) {
            result.setText(issues);
            return;
        }
        Dimension dimension = RocketUtil.calcHeight(rocket);
        if (dimension == null || dimension.getHeight() < 10000) result.setText("火箭无法起飞！");
        else result.setText("火箭最大的飞行高度为" + dimension.getHeight() + "km" + ", 所需燃料" + dimension.getWidth() + "kg");
    }
}
