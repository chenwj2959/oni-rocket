package com.cwj.game.oxygen.rocket.framework;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import com.cwj.game.oxygen.rocket.model.Rocket;

public abstract class AbstractCalculate extends JPanel {

    private static final long serialVersionUID = 1L;
    
    protected Rocket rocket;
    
    // 默认间隔大小
    public static final int MARGIN_TOP = 25;
    public static final int MARGIN_LEFT = 20;
    
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 25;
    
    public static final int BUTTON_MARGIN_TOP = 10;
    public static final int BUTTON_WIDTH = 50;
    
    public static final int ROCKET_TEXT_WIDTH = 80;
    public static final int RESULT_TEXT_WIDTH = 150;
    
    private HashMap<String, Component> componentMap;
    
    public AbstractCalculate() {
        this.setLayout(null);
        componentMap = new HashMap<>();
        rocket = new Rocket();
    }
    
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
}
