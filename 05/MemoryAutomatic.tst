// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/05/MemoryAutomatic.tst

// This script is identical to the original test script (Memory.tst), 
// except the keyboard tests are done in a non-interactive manner
// (i.e. a manual key press is no longer required). Specifically, the script sets the keyboard
// memory map (RAM[24576]) to the scan code specified in the original test script, then sets it to 0 afterwards.
// Otherwise, all other tests are the same as in the original test script.
// Warning: only use this script if you don't wish to or cannot use the hardware simulator GUI for some reason.
// Warning: if you wish to use this script, make sure to use a normal register as your keyboard chip.
// The builtin Keyboard chip does not have an input pin, so values cannot be manually fed into it.
// The builtin Keyboard chip is just a register with a GUI sideeffect, so this should not be a major change

load Memory.hdl,
output-file MemoryAutomatic.out,
compare-to MemoryAutomatic.cmp,
output-list in%D1.6.1 load%B2.1.2 address%B1.15.1 out%D1.6.1;


// There is an interesting design error that has shown up in several students'
// Memory.hdl files that causes zeros to be written in the corresponding offset
// in the inactive memory segments to the actual write.  To detect this, the
// test must not only look for writes into the wrong segment, but changes.
// The following initialization writes a signal number into the memory where
// the bad writes may occur.

//// Set RAM[2000], RAM[4000] = 12345 (for following overwrite test)
set in 12345, set load 1, set address %X2000, tick, output; tock, output;
set address %X4000, tick, output; tock, output;


set in -1,				// Set RAM[0] = -1
set load 1,
set address 0,
tick,
output;
tock,
output;

set in 9999,			// RAM[0] holds value
set load 0,
tick,
output;
tock,
output;

set address %X2000,		// Did not also write to upper RAM or Screen
eval,
output;
set address %X4000,
eval,
output;


//// Set RAM[0], RAM[4000] = 12345 (for following overwrite test)
set in 12345, set load 1, set address %X0000, tick, output; tock, output;
set address %X4000, tick, output; tock, output;


set in 2222,			// Set RAM[2000] = 2222
set load 1,
set address %X2000,
tick,
output;
tock,
output;

set in 9999,			// RAM[2000] holds value
set load 0,
tick,
output;
tock,
output;

set address 0,			// Did not also write to lower RAM or Screen
eval,
output;
set address %X4000,
eval,
output;

set load 0,				// Low order address bits connected
set address %X0001, eval, output;
set address %X0002, eval, output;
set address %X0004, eval, output;
set address %X0008, eval, output;
set address %X0010, eval, output;
set address %X0020, eval, output;
set address %X0040, eval, output;
set address %X0080, eval, output;
set address %X0100, eval, output;
set address %X0200, eval, output;
set address %X0400, eval, output;
set address %X0800, eval, output;
set address %X1000, eval, output;
set address %X2000, eval, output;

set address %X1234,		// RAM[1234] = 1234
set in 1234,
set load 1,
tick,
output;
tock,
output;

set load 0,
set address %X2234,		// Did not also write to upper RAM or Screen 
eval, output;
set address %X6234,
eval, output;

set address %X2345,		// RAM[2345] = 2345
set in 2345,
set load 1,
tick,
output;
tock,
output;

set load 0,
set address %X0345,		// Did not also write to lower RAM or Screen 
eval, output;
set address %X4345,
eval, output;


//// Clear the overwrite detection value from the screen
set in 0, set load 1, set address %X4000, tick, output; tock, output;


// Keyboard test

set address 24576,
set in 75,
set load 1,
tick,
tock,
output;
set in 0,
set load 0,
tick,
tock,
output;
set load 1,
tick,
tock,
output;
set in 75,
set load 0,
tick,
tock,
output;

// Screen test

//// Set RAM[0FCF], RAM[2FCF] = 12345 (for following overwrite test)
set in 12345, set load 1, set address %X0FCF, tick, output; tock, output;
set address %X2FCF, tick, output; tock, output;

set load 1,
set in -1,
set address %X4FCF,
tick,
tock,
output,

set address %X504F,
tick,
tock,
output;

set address %X0FCF,		// Did not also write to lower or upper RAM
eval,
output;
set address %X2FCF,
eval,
output;

set load 0,				// Low order address bits connected
set address %X4FCE, eval, output;
set address %X4FCD, eval, output;
set address %X4FCB, eval, output;
set address %X4FC7, eval, output;
set address %X4FDF, eval, output;
set address %X4FEF, eval, output;
set address %X4F8F, eval, output;
set address %X4F4F, eval, output;
set address %X4ECF, eval, output;
set address %X4DCF, eval, output;
set address %X4BCF, eval, output;
set address %X47CF, eval, output;
set address %X5FCF, eval, output;


set address 24576,
set load 0,
tick,
tock,
output;
set in 89,
set load 1,
tick,
tock,
output;
set in 0,
set load 0,
tick,
tock,
output;
set load 1,
tick,
tock,
output;
