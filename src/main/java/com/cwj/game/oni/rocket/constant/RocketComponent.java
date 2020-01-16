package com.cwj.game.oni.rocket.constant;

public enum RocketComponent {

    COMMANDER("指挥舱", 200),
    RESEARCH("研究仓", 200),
    WAREHOUSE("货仓", 2000),
    TOURISM("观光仓", 200),
    FUELBIN("燃料仓", 100),
    OXIDANTBIN("氧化剂仓", 100),
    ENGINE_STEAM("蒸汽推进器", 2000),
    ENGINE_IRON("固体推进器", 200),
    ENGINE_PETROLEUM("石油推进器", 200),
    ENGINE_HYDROGEN("液氢推进器", 500);
    
    private String name;
    private int quality;
    RocketComponent(String name, int quality) {
        this.name = name;
        this.quality = quality;
    }
    public String componentName() {
        return name;
    }
    public int quality() {
        return quality;
    }
    
    /**
     * 推进器质量
     */
    public int engineQuality(int engineNum) {
        return quality * engineNum;
    }
}
