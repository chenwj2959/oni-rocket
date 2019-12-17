package com.cwj.game.oxygen.rocket.framework;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwj.game.oxygen.rocket.constant.Constant;
import com.cwj.game.oxygen.rocket.constant.OxidantType;
import com.cwj.game.oxygen.rocket.constant.RocketComponent;
import com.cwj.game.oxygen.rocket.model.Rocket;
import com.cwj.game.oxygen.rocket.utils.RocketUtil;

public abstract class AbstractCalculate extends JPanel {

    private static final long serialVersionUID = 1L;
    protected static final Logger log = LoggerFactory.getLogger(AbstractCalculate.class);
    
    // 默认间隔大小
    public static final int MARGIN_TOP = 25;
    public static final int MARGIN_LEFT = 20;
    
    public static final int DEFAULT_WIDTH = 125;
    public static final int DEFAULT_HEIGHT = 25;
    
    public static final int BUTTON_MARGIN = 10;
    public static final int BUTTON_WIDTH = 50;
    
    // 显示火箭主体文本宽度
    public static final int ROCKET_TEXT_WIDTH = 140;
    // 显示结果文本宽度
    public static final int RESULT_TEXT_WIDTH = 150;
    
    // 被存储的组件KEY
    public static final String ROCKET_TEXT = "RocketText";
    public static final String RESULT = "Result";
    public static final String FINAL_HEIGHT_BOX = "finalHeightBox";
    public static final String OXIDANT_TYPE = "oxidantType";
    
    private HashMap<String, Component> componentMap;
    protected Rocket rocket;
    private List<RocketComponent> leftIgnoreList;
    
    public AbstractCalculate(boolean addFinalHeight, RocketComponent... ignoreComponents) {
        this.setLayout(null);
        componentMap = new HashMap<>();
        rocket = new Rocket();
        leftIgnoreList = new ArrayList<>();
        leftIgnoreList.add(RocketComponent.COMMANDER);
        leftIgnoreList.add(RocketComponent.OXIDANTBIN);
        if (ignoreComponents.length > 0) leftIgnoreList.addAll(Arrays.asList(ignoreComponents));
        
        Dimension dimension = createLeftButton(addFinalHeight);
        
        // 显示火箭主体
        JTextArea textArea = new JTextArea(RocketComponent.COMMANDER.componentName());
        textArea.setEnabled(false);
        JScrollPane rocketText = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        rocketText.setBounds(MARGIN_LEFT + dimension.width, MARGIN_TOP, ROCKET_TEXT_WIDTH, dimension.height);
        rocketText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.add(rocketText);
        put(ROCKET_TEXT, textArea);
        // 复制/粘贴按钮
        int btnWidth = (ROCKET_TEXT_WIDTH - BUTTON_MARGIN) / 2;
        JButton copyBtn = new JButton("复制");
        copyBtn.setBounds(MARGIN_LEFT + dimension.width, MARGIN_TOP + BUTTON_MARGIN + dimension.height, btnWidth, DEFAULT_HEIGHT);
        copyBtn.addActionListener(copyListener());
        this.add(copyBtn);
        
        JButton pasteBtn = new JButton("粘贴");
        pasteBtn.setBounds(MARGIN_LEFT + BUTTON_MARGIN + dimension.width + btnWidth, MARGIN_TOP + BUTTON_MARGIN + dimension.height, btnWidth, DEFAULT_HEIGHT);
        pasteBtn.addActionListener(pasteListener());
        this.add(pasteBtn);
        
        // 显示结果
        JTextArea result = new JTextArea();
        result.setLineWrap(true);
        result.setBackground(new Color(238, 238, 238));
        result.setEditable(false);
        result.setBounds(MARGIN_LEFT + BUTTON_MARGIN + dimension.width + ROCKET_TEXT_WIDTH, MARGIN_TOP, RESULT_TEXT_WIDTH, dimension.height);
        this.add(result);
        put(RESULT, result);
    }
    
    /**
     * 生成左边的按钮
     * @return 左边区域占的大小
     */
    protected Dimension createLeftButton(boolean addFinalHeight) {
        int buttonTop = MARGIN_TOP;
        RocketComponent[] components = RocketComponent.values();
        for (int i = 0; i < components.length; i++) {
            // 不显示指挥舱和氧化剂仓
            if (isIgnore(components[i])) continue;
            String componentName = components[i].componentName();
            // label
            String labelStr = componentName + "(" + components[i].quality() + "kg)";
            JLabel label = new JLabel(labelStr);
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
            
            buttonTop += BUTTON_MARGIN + DEFAULT_HEIGHT;
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
        put(OXIDANT_TYPE, oxidantBox);
        rocket.setOxidantType(oxidantType[0]);
        // 目标高度
        if (addFinalHeight) {
            buttonTop += BUTTON_MARGIN + DEFAULT_HEIGHT;
            JLabel finalHeightLabel = new JLabel(Constant.LABEL_FINAL_HEIGHT);
            finalHeightLabel.setBounds(MARGIN_LEFT, buttonTop, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            this.add(finalHeightLabel);
            Integer[] heightArray = new Integer[19];
            for (int i = 0; i < 19; i++) {
                heightArray[i] = 10000 * (i + 1);
            }
            JComboBox<Integer> finalHeightBox = new JComboBox<Integer>(heightArray);
            finalHeightBox.setBounds(MARGIN_LEFT + DEFAULT_WIDTH, buttonTop, BUTTON_WIDTH * 2 + MARGIN_LEFT, DEFAULT_HEIGHT);
            finalHeightBox.addActionListener((ActionEvent e) -> {
                rocket.setTargetHeight((int) finalHeightBox.getSelectedItem()); 
                showResult();
            });
            this.add(finalHeightBox);
            put(FINAL_HEIGHT_BOX, finalHeightBox);
        }
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
            showRocket();
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
            showRocket();
            showResult();
        };
    }
    
    protected void showRocket() {
        JTextArea rocketText = (JTextArea) get(ROCKET_TEXT);
        rocketText.setText(rocket.getRocket());
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
     * 复制按钮监听事件
     */
    private ActionListener copyListener() {
        return (ActionEvent e) -> {
            Rocket.copy(rocket);
        };
    }
    
    /**
     * 粘贴按钮监听事件
     */
    private ActionListener pasteListener() {
        return (ActionEvent e) -> {
            Rocket clipboard = Rocket.paste();
            if (clipboard != null) rocket = clipboard;
            showRocket();
            updateLeftButton();
            showResult();
        };
    }
    
    /**
     * 更新左边区域
     */
    private void updateLeftButton() {
        JComboBox<?> oxidantBox = (JComboBox<?>) get(OXIDANT_TYPE);
        if (oxidantBox != null) oxidantBox.setSelectedItem(rocket.getOxidantType().chiness());
        JComboBox<?> finalHeightBox = (JComboBox<?>) get(FINAL_HEIGHT_BOX);
        if (finalHeightBox != null) finalHeightBox.setSelectedItem(rocket.getTargetHeight());
    }
    
    /**
     * 显示结果
     */
    protected void showResult() {
        JTextArea resultText = (JTextArea) get(RESULT);
        String issues = RocketUtil.checkRocket(rocket);
        if (issues != null) {
            resultText.setText(issues);
            return;
        }
        String result = calcResult();
        if (result == null) resultText.setText("火箭无法起飞！");
        else resultText.setText(result);
    }
    
    /**
     * 计算结果
     */
    protected abstract String calcResult();
    
    public void put(String key, Component component) {
        componentMap.put(key, component);
    }
    
    public Component get(String key) {
        return componentMap.get(key);
    }
    
    public void setEnabled(boolean flag) {
        for (Map.Entry<String, Component> component : componentMap.entrySet()) {
            component.getValue().setEnabled(flag);
        }
    }
    
    /**
     * 左边区域忽略的组件
     */
    private boolean isIgnore(RocketComponent rocketComponent) {
        return leftIgnoreList.contains(rocketComponent);
    }
}
