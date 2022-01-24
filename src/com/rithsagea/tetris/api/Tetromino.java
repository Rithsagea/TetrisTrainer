package com.rithsagea.tetris.api;

public enum Tetromino {
	I(TetrominoColor.CYAN, TetrominoData.KICK_TABLE_2, "-1,0|0,0|1,0|2,0"),
	J(TetrominoColor.BLUE, TetrominoData.KICK_TABLE_1, "-1,1|-1,0|0,0|1,0"),
	L(TetrominoColor.ORANGE, TetrominoData.KICK_TABLE_1, "-1,0|0,0|1,0|1,1"),
	O(TetrominoColor.YELLOW, TetrominoData.KICK_TABLE_3, "0,0|0,1|1,1|1,0"),
	S(TetrominoColor.LIME, TetrominoData.KICK_TABLE_1, "-1,0|0,0|0,1|1,1"),
	T(TetrominoColor.MAGENTA, TetrominoData.KICK_TABLE_1, "-1,0|0,0|0,1|1,0"),
	Z(TetrominoColor.RED, TetrominoData.KICK_TABLE_1, "-1,1|0,1|0,0|1,0");
	
	private int color;
	private final int[][][] data;
	private final int[][][] kickTable;
	
	private Tetromino(int color, int[][][] kickTable, String str) {
		this.color = color;
		this.kickTable = kickTable;
		this.data = new int[4][4][2];
		
		String[] blocks = str.split("\\|");
		for(int b = 0; b < 4; b++) {
			String[] blockData = blocks[b].split(",");
			data[0][b][0] = Integer.parseInt(blockData[0]);
			data[0][b][1] = Integer.parseInt(blockData[1]);
		}
		
		for(int x = 0; x < 3; x++) {
			for(int y = 0; y < 4; y++) {
				data[x + 1][y][0] = data[x][y][1];
				data[x + 1][y][1] = -data[x][y][0];
			}
		}
	}
	
	public int getColor() {
		return color;
	}
	
	public int[][][] getData() {
		return data;
	}
	
	public int[][][] getKickTable() {
		return kickTable;
	}
}
