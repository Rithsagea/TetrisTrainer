package com.rithsagea.tetris;

public class Test {
	
	private static final int WIDTH = 5;
	private static final int HEIGHT = 5;
	
	private static TetrisBoard generate(Tetromino m, int r) {
		TetrisBoard board = new TetrisBoard(WIDTH, HEIGHT);
		board.write(m, r, WIDTH / 2, HEIGHT / 2);
		board.set(WIDTH / 2, HEIGHT / 2, TetrominoColor.GRAY);
		
		return board;
	}
	
	public static void main(String[] args) {
		for(int r = 0; r < 4; r++) {
			TetrisBoard[] boards = new TetrisBoard[7];
			
			for(int m = 0; m < 7; m++) {
				boards[m] = generate(Tetromino.values()[m], r);
			}
			
			
			for(int y = HEIGHT - 1; y >= 0; y--) {
				for(int m = 0; m < 7; m++) {
					for(int x = 0; x < WIDTH; x++) {
						System.out.print(boards[m].get(x, y) == 0 ? "." :
							(x == 2 && y == 2 ? "O" : "X"));
					}
					System.out.print(" ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
