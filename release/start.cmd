SET mutationrate=0.01
SET numberOfGAs=128
SET rounds=200
SET numberOfMovesPerRound=40
SET RAM=12g
java -Xmx%RAM% -jar 2048.jar %mutationrate% %numberOfGAs% %rounds% %numberOfMovesPerRound%
PAUSE