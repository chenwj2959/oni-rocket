package com.cwj.game.oxygen.rocket.constant;

public enum FuelType {
    
    STEAM("蒸汽", 20),
    IRON("精炼铁", 30),
    PETROLEUM("石油", 40),
    HYDROGEN("液氢", 60);
    
    private String chiness;
    private int efficiency;
    FuelType(String chiness, int efficiency) {
        this.chiness = chiness;
        this.efficiency = efficiency;
    }
    /**
     * 返回中文名
     */
    public String chiness() {
        return chiness;
    }
    /**
     * 返回燃料效率
     */
    public int efficiency() {
        return efficiency;
    }
}
