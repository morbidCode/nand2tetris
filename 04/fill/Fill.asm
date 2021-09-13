// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

@256
D=A
@rows
M=D
@32
D=A
@columns
M=D
@screenState
M=0
@keyboardValue
M=0
(PROBE)
@KBD
D=M
@keyboardValue
D=D-M
@PROBE
D;JEQ
@SCREEN
D=A
@screenPointer
M=D
@i
M=0
@KBD
D=M
@keyboardValue
M=D
@CLEAR_SCREEN
D;JEQ
@BLACK_SCREEN
D;JGT
(LOOP)
@rows
D=M
@i
D=D-M
@PROBE
D;JEQ
@j
M=0
(COLUMN_LOOP)
@columns
D=M
@j
D=D-M
@INC
D;JEQ
@screenState
D=M
@screenPointer
A=M
M=D
@j
M=M+1
@screenPointer
M=M+1
@COLUMN_LOOP
0;JMP
(CLEAR_SCREEN)
@screenState
M=0
@LOOP
0;JMP
(BLACK_SCREEN)
@screenState
M=-1
@LOOP
0;JMP
(INC)
@i
M=M+1
@LOOP
0;JMP
