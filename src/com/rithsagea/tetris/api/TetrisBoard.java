package com.rithsagea.tetris.api;

public class TetrisBoard {
	private int width;
	private int height;
	
	/**
	
	TetrisTile[height][width]
	
	height 0 bottom
	height max top
	
	width 0 left
	width max top
	
	 */
	
	private int[][] board;
	
	public TetrisBoard(int width, int height) {
		this.width = width;
		this.height = height;
		
		board = new int[height][width];
	}
	
	public TetrisBoard(int height) {
		this(10, height);
	}
	
	public TetrisBoard() {
		this(10, 40);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	//ACTUAL STUFF
	public int get(int x, int y) {
		return board[y][x];
	}
	
	public void set(int x, int y, int tile) {
		if(y < 0 || y >= height || x < 0 || x >= width) return;
		board[y][x] = tile;
	}
	
	public void write(Tetromino m, int rotation, int x, int y) {
		for(int[] block : m.getData()[rotation]) {
			set(block[0] + x, block[1] + y, 'X');
		}
	}
}
