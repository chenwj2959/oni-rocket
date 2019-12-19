package com.cwj.game.oxygen.rocket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.cwj.game.oxygen.rocket.core.CalcFuelQuality;
import com.cwj.game.oxygen.rocket.core.CalcMaxHeight;
import com.cwj.game.oxygen.rocket.core.CalcMaxLoad;

public class MainActive {
    
    public static void main(String[] args) {
        JFrame windows = new JFrame("RP缺氧火箭计算器1.1");
        windows.setSize(600, 475);
        windows.setIconImage(new ImageIcon(MainActive.class.getResource("/logo.png")).getImage());
        windows.setLocationRelativeTo(null);
        windows.setResizable(false);
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setLayout(null);
        
        JTabbedPane tab = new JTabbedPane();
        tab.add("计算最大高度", new CalcMaxHeight());
        tab.add("计算燃料质量", new CalcFuelQuality());
        tab.add("计算最大负载", new CalcMaxLoad());
        
        windows.setContentPane(tab);
        windows.setVisible(true);
    }
}
