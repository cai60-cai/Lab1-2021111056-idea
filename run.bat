@echo off
rem 设置编译路径
set SOURCE_PATH=src\com\lab
set BIN_PATH=bin

rem 编译 Java 文件
javac -d %BIN_PATH% %SOURCE_PATH%\main\Main.java ^
    %SOURCE_PATH%\graph\BridgeWordsResult.java ^
    %SOURCE_PATH%\graph\Graph.java ^
    %SOURCE_PATH%\graph\GraphVisualizer.java ^
    %SOURCE_PATH%\processing\TextFileProcessor.java ^
    %SOURCE_PATH%\graph\PathFinder.java ^
    %SOURCE_PATH%\graph\RandomWalk.java

rem 检查编译是否成功
if errorlevel 1 (
    echo 编译失败
    exit /b 1
)

rem 运行 Java 程序
if "%~1"=="" (
    java -cp %BIN_PATH% com.lab.main.Main
) else (
    java -cp %BIN_PATH% com.lab.main.Main %~1
)
