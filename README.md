# oxygen-not-include-rocket #
缺氧火箭计算器
## 1. 功能列表: ##
### 1.1 根据 发射高度 + 货仓数量 + 研究仓数量 + 推进器类型 + 氧化剂种类 + 燃料罐数量  => 计算出需要的燃料质量 ###
界面 √
取值 √
计算 √
输出 √
### 1.2 根据 发射高度 + 推进器类型 + 氧化剂种类 + 燃料罐数量 => 计算能负载的研究仓数量/货仓数量 ###
界面 √
取值 √
计算 √
输出 √
### 1.3 根据 火箭组成 => 计算最大的发射高度 ###
界面 √
取值 √
计算 √
输出 √

## 2. 计算公式 ##
### 2.1 最大飞行高度计算 (km) ### 
`maxHeight = fuelQuality * fuelEfficency * oxidantEfficency`  
燃料效率 ：蒸汽 20 | 精炼铁 30 | 石油 40 | 液氢 60  
氧化剂效率 ： 氧石 1 | 液氧 1.33  
### 2.2 质量惩罚距离 (km) ###
* 当总质量小于等于4000kg时  
`qualityPunishment = totalQuality = componentQuality + fuelQuality + oxidantQuality`  
火箭组件质量(干重) : 指挥舱 200kg | 研究仓 200kg | 货仓 2000kg | 观光仓 200kg | 燃料仓 100kg | 氧化剂仓 100kg | 固体助推器 200kg | 蒸汽推进器 2000kg | 石油推进器 200kg | 液氢推进器 500kg  
* 当总质量大于4000kg时   
`qualityPunishment = (totalQuality / 300) ^ 3.2`  
### 2.3 最终飞行高度计算 (km) ###
`finalHeight = maxHeight - qualityPunishment`

## 3. 下载链接 ##
链接：https://pan.baidu.com/s/1D7o3CVM6xQAFUjypZ3VRBw  
提取码：zi0x  
  
![image](https://raw.githubusercontent.com/wiki/chenwj2959/oxygen-rocket/oxygen-not-include-rocket-calculator.png)  
  
* 由于使用Java编写安装包rocket-calculator-install-1.1.exe包含完整jre所以会比较大
* 安装过java环境可以下载Rocket Calculator.exe，exe依赖于环境变量JAVA_HOME或JRE_HOME，最好使用jdk1.8
