package com.cwj.game.oxygen.rocket.constant;

public class Constant {

    // === 燃料效率 ===
    // 蒸汽
    public static final int FUEL_EFFICIENCY_STEAM = 20;
    // 精炼铁
    public static final int FUEL_EFFICIENCY_IRON = 30;
    // 石油
    public static final int FUEL_EFFICIENCY__PETROLEUM = 40;
    // 液氢
    public static final int FUEL_EFFICIENCY_HYDROGEN = 60;
    
    // === 氧化剂效率 ===
    // 固体氧(氧石)
    public static final int OXIDANT_EFFICIENCY_SOLID = 1;
    // 液氧
    public static final double OXIDANT_EFFICIENCY_LIQUID = 4 / 3;
    
    // === 火箭部件 ===
    // 蒸汽推进器
    public static final int ENGINE_STEAM = 1;
    // 石油推进器
    public static final int ENGINE_PETROLEUM = 2;
    // 液氢推进器
    public static final int ENGINE_HYDROGEN = 3;
    
    // === 火箭部件质量 ===
    // 指挥仓
    public static final int QUALITY_COMMANDER = 200;
    // 研究仓
    public static final int QUALITY_RESEARCH = 200;
    // 货仓
    public static final int QUALITY_WAREHOUSE = 2000;
    // 燃料仓
    public static final int QUALITY_FUEL_BIN = 100;
    // 氧化剂仓
    public static final int QUALITY_OXIDANT_BIN = 100;
    // 蒸汽推进器
    public static final int QUALITY_ENGINE_STEAM = 2000;
    // 石油推进器
    public static final int QUALITY_ENGINE_PETROLEUM = 200;
    // 液氢推进器
    public static final int QUALITY_ENGINE_HYDROGEN = 500;
    // 观光仓
    public static final int QUALITY_TOURISM = 200;
}
