# Eriantys Game 2022 final project 

## Team component
- [Giosia Christian](https://github.com/ChristianGiosia)
- [Galbiati Davide](https://github.com/Dado-hash)
- [Gentile Giuseppe](https://github.com/giuseppeegentile)

## Implemented Functionalities
- Beginner rules
- Socket
- Cli interface
- Advanced rules
- GUI
- 4 players game (AF)

## How to run the project
Make sure you have installed java.

### First run the server jar
From command line, go to the directory of the project deliveries/jar. Then run the server by typing the following command:
#### java -jar eryantis-server.jar
Once the server is started you can either run CLI game or GUI.
### To run the CLI game 
Remain in the directory of the previous point, and then type the following command: 
#### java -jar eryantis-client.jar --c
or 
#### java -jar eryantis-client.jar --cli
Do this for every player you want to play with. 
For windows users, if using CLI, make sure to enter this command:
#### reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f
### To run the GUI game 
Remain in the directory of the previous point, and then type the following command: 
#### java -jar eryantis-client.jar 
Do this for every player you want to play with. 

You can also run a player with CLI interface and another with GUI.
The port used to connect to the server is 16847.
