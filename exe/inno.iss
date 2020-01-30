[Setup]
AppId=47D20A1C-E250-4735-99BC-DA9D57CD0CDD
AppName=Rocket CalculatorAppVersion=1.2.1
DefaultDirName={pf}\Rocket Calculator
DefaultGroupName=Rocket Calculator

[Files]
Source: "oni-rocket.exe"; DestDir: "{app}"
Source: "jre\*"; DestDir: "{app}\jre";Flags:igNoreversion recursesubdirs createallsubdirs

[Icons]
Name: "{userdesktop}\Rocket Calculator"; Filename: "{app}\oni-rocket.exe"; WorkingDir: "{app}"

[UninstallDelete]
Name: {app}; Type: filesandordirs