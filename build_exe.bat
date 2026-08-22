@echo off

jpackage ^
  --input .\target ^
  --name armame-el-doparti-3.1.0 ^
  --main-jar armameeldoparti-3.1.0.jar ^
  --main-class armameeldopartidesktop.Main ^
  --type app-image ^
  --icon ".\src\main\res\img\icons\main_icon.ico"

if %errorlevel% neq 0 pause