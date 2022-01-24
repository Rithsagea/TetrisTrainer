package com.rithsagea.tetris.api;

public class TetrisBoard {
	
	public static final int PREVIEWS = 5;
	
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
	private Bag bag;
	
	private Tetromino currentPiece;
	private Tetromino heldPiece;
	private int pieceX;
	private int pieceY;
	private int pieceRotation;
	
	public TetrisBoard(int width, int height, long seed) {
		this.width = width;
		this.height = height;
		
		board = new int[height][width];
		bag = new Bag(PREVIEWS, seed);
		
		placePiece();
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Tetromino getCurrentPiece() {
		return currentPiece;
	}
	
	public Tetromino getHeldPiece() {
		return heldPiece;
	}
	
	public Tetromino[] getPreview() {
		return bag.getPreview();
	}
	
	//BOARD MANIPULATION
	public int get(int x, int y) {
		return board[y][x];
	}
	
	public void set(int x, int y, int tile) {
		if(y < 0 || y >= height || x < 0 || x >= width) return;
		board[y][x] = tile;
	}
	
	public void write(Tetromino m, int rotation, int x, int y) {
		for(int[] block : m.getData()[rotation]) {
			set(block[0] + x, block[1] + y, m.getColor());
		}
		
		set(x, y, 1);
	}
	
	public void delete(Tetromino m, int rotation, int x, int y) {
		for(int[] block : m.getData()[rotation]) {
			set(block[0] + x, block[1] + y, 0);
		}
	}
	
	public boolean test(int x, int y) {
		if(y < 0 || y >= height || x < 0 || x >= width) return false;
		return board[y][x] == 0;
	}
	
	public boolean test(Tetromino m, int rotation, int x, int y) {
		for(int[] block : m.getData()[rotation]) {
			if(!test(block[0] + x, block[1] + y)) return false;
		}
		return true;
	}
	
	public void clear() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				board[y][x] = 0;
			}
		}
	}

	//MACROS
	public void placePiece() {
		//DEBUG
		System.out.printf("%s: (%d)[%d,%d]\n", currentPiece, pieceRotation, pieceX, pieceY);
		
		currentPiece = bag.next();
		pieceX = 4;
		pieceY = 19;
		pieceRotation = 0;
		
		//clear lines
		
		write(currentPiece, pieceRotation, pieceX, pieceY);	
	}
	
	public boolean alter(int o, int x, int y) {
		boolean flag = false;
		
		delete(currentPiece, pieceRotation, pieceX, pieceY);
		if(test(currentPiece, o, x, y)) {
			pieceRotation = o;
			pieceX = x;
			pieceY = y;
			flag = true;
//			updateGhost();
		}
		
		write(currentPiece, pieceRotation, pieceX, pieceY);
		return flag;
	}
	
	public void alterQuiet(int o, int x, int y) {
		delete(currentPiece, pieceRotation, pieceX, pieceY);
		if(test(currentPiece, o, x, y)) {
			pieceRotation = o;
			pieceX = x;
			pieceY = y;
//			updateGhost();
		}
		
		write(currentPiece, pieceRotation, pieceX, pieceY);
	}
	
	public void right() {
		alterQuiet(pieceRotation, pieceX + 1, pieceY);
	}
	
	public void left() {
		alterQuiet(pieceRotation, pieceX - 1, pieceY);
	}
	
	public void softdrop() {
		alterQuiet(pieceRotation, pieceX, pieceY - 1);
	}
	
	public void harddrop() {
		while(alter(pieceRotation, pieceX, pieceY - 1));
		placePiece();
	}
	
	private void rotate(int newOrientation) {
		int[][][] kickTable = currentPiece.getKickTable();
		for(int x = 0; x < kickTable[0].length; x++) {
			if(alter(newOrientation,
					pieceX + (kickTable[pieceRotation][x][0] - kickTable[newOrientation][x][0]),
					pieceY + (kickTable[pieceRotation][x][1] - kickTable[newOrientation][x][1])))
				return;
		}
	}
	
	public void cwrotate() {
		rotate((pieceRotation + 1) % 4);
	}
	
	public void ccwrotate() {
		rotate((pieceRotation + 3) % 4);
	}

	public void hold() {
		delete(currentPiece, pieceRotation, pieceX, pieceY);
		
		if(heldPiece == null) {
			heldPiece = currentPiece;
			currentPiece = bag.next();
		} else {
			Tetromino temp = heldPiece;
			heldPiece = currentPiece;
			currentPiece = temp;
		}
		
		pieceX = 4;
		pieceY = 19;
		pieceRotation = 0;
		
		write(currentPiece, pieceRotation, pieceX, pieceY);
	}
}
