1. 在根目录执行mvn clean assembly:assembly 打包jar
2. 复制到此目录重命名为oxygen-rocekt.jar
3. 复制1.8的jre文件到此目录，文件夹命名为jre
4. 执行config.exe4j将jar打包成exe
5. 执行inno.iss生成安装包