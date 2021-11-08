package com.rithsagea.tetris.minos;

import com.rithsagea.tetris.TileColor;

public enum Tetromino {
	
	I(TileColor.CYAN, TetrominoData.KICK_TABLE_2, 5, new String[] {
			  "     "
			+ "     "
			+ " xxxx"
			+ "     "
			+ "     ",
			
			  "     "
			+ "  x  "
			+ "  x  "
			+ "  x  "
			+ "  x  ",
			
			  "     "
			+ "     "
			+ "xxxx "
			+ "     "
			+ "     ",
			
			  "  x  "
			+ "  x  "
			+ "  x  "
			+ "  x  "
			+ "     "
	}),
	
	O(TileColor.YELLOW, TetrominoData.KICK_TABLE_3, 3, new String[] {
			  " xx"
			+ " xx"
			+ "   ",
			
			  "   "
			+ " xx"
			+ " xx",
			  
			  "   "
			+ "xx "
			+ "xx ",
			
			  "xx "
			+ "xx "
			+ "   ",
	}),
	
	T(TileColor.MAGENTA, TetrominoData.KICK_TABLE_1, 3, new String[] {
			  " x "
			+ "xxx"
			+ "   ",
			
			  " x "
			+ " xx"
			+ " x ",
			
			  "   "
			+ "xxx"
			+ " x ",
			
			  " x "
			+ "xx "
			+ " x "
	}),
	
	S(TileColor.GREEN, TetrominoData.KICK_TABLE_1, 3, new String[] {
			  " xx"
			+ "xx "
			+ "   ",
			
			  " x "
			+ " xx"
			+ "  x",
			
			  "   "
			+ " xx"
			+ "xx ",
			
			  "x  "
			+ "xx "
			+ " x "
	}),
	
	Z(TileColor.RED, TetrominoData.KICK_TABLE_1, 3, new String[] {
			  "xx "
			+ " xx"
			+ "   ",
			
			  "  x"
			+ " xx"
			+ " x ",
			
			  "   "
			+ "xx "
			+ " xx",
			
			  " x "
			+ "xx "
			+ "x  "
	}),
	
	J(TileColor.BLUE, TetrominoData.KICK_TABLE_1, 3, new String[] {
			  "x  "
			+ "xxx"
			+ "   ",
			
			  " xx"
			+ " x "
			+ " x ",
			
			  "   "
			+ "xxx"
			+ "  x",
			
			  " x "
			+ " x "
			+ "xx "
	}),
	
	L(TileColor.ORANGE, TetrominoData.KICK_TABLE_1, 3, new String[] {
			  "  x"
			+ "xxx"
			+ "   ",
			
			  " x "
			+ " x "
			+ " xx",
			
			  "   "
			+ "xxx"
			+ "x  ",
			
			  "xx "
			+ " x "
			+ " x "
	});
	
	private TileColor color;
	private int[][][] kickTable;
	private boolean[][][] shapes;
	
	private Tetromino(TileColor color, int[][][] kickTable, int shapeWidth, String[] shapes) {
		this.color = color;
		this.shapes = new boolean[4][shapeWidth][shapeWidth];
		this.kickTable = kickTable;
		
		int rotation = 0;
		for(String shape : shapes) {
			int row = 0;
			int col = 0;
			boolean data[][] = new boolean[shapeWidth][shapeWidth];
			for(char c : shape.replace("\n", "").toCharArray()) {
				if(c != ' ') data[row][col] = true;
				
				col++;
				if(col >= shapeWidth) {
					col = 0;
					row++;
				}
			}
			
			this.shapes[rotation] = data;
			rotation++;
		}
	}
	
	public TileColor getColor() {
		return color;
	}
	
	/**
	 * 0 - 0 deg
	 * 1 - 90 deg
	 * 2 - 180 deg
	 * 3 - 270 deg
	 */
	public boolean[][] getShape(int rotation) {
		return shapes[rotation];
	}
	
	public int[][][] getKickTable() {
		return kickTable;
	}
}
