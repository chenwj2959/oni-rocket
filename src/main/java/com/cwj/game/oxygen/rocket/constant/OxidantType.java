package com.cwj.game.oxygen.rocket.constant;

public enum OxidantType {

    OXYGEN_STONE("氧石", 1),
    LIQUID_OXYGEN("液氧", 1.33);
    
    private String chiness;
    private double efficiency;
    OxidantType(String chiness, double efficiency) {
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
     * 返回氧化剂效率
     */
    public double efficiency() {
        return efficiency;
    }
    
    public static String[] allValue() {
        OxidantType[] values = OxidantType.values();
        String[] valueStr = new String[values.length];
        int ans = 0;
        for (OxidantType oxidantType : values) {
            valueStr[ans++] = oxidantType.chiness();
        }
        return valueStr;
    }
}
