# AI Sudoku Resolver

Run application :
```console
java -jar SudokuSolver.jar grilles_sudoku.txt [-t]
```
With "-t" option, grids are display at each resolution step.


About grid text file:
It should contain one line of number by replace empty case by 0.

Sample Grid:
```
003020600900305001001806400008102900700000008006708200002609500800203009005010300
```
Correspond to :
```
	-------------------------
	| 0 0 3 | 0 2 0 | 6 0 0 |
	| 9 0 0 | 3 0 5 | 0 0 1 |
	| 0 0 1 | 8 0 6 | 4 0 0 |
	-------------------------
	| 0 0 8 | 1 0 2 | 9 0 0 |
	| 7 0 0 | 0 0 0 | 0 0 8 |
	| 0 0 6 | 7 0 8 | 2 0 0 |
	-------------------------
	| 0 0 2 | 6 0 9 | 5 0 0 |
	| 8 0 0 | 2 0 3 | 0 0 9 |
	| 0 0 5 | 0 1 0 | 3 0 0 |
	-------------------------
```