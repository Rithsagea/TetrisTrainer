package com.rithsagea.tetris;

import com.rithsagea.tempera.math.MathUtil;
import com.rithsagea.tetris.minos.BagRandomizer;
import com.rithsagea.tetris.minos.Tetromino;

public class TetrisBoard {
	public static final int HEIGHT = 40;
	public static final int WIDTH = 10;
	
	private TetrisTile[][] board;
	
	private long nanosPerCell;
	
	private BagRandomizer bag;
	
	private Tetromino currentPiece;
	private Tetromino heldPiece;
	private int pieceOrientation;
	private int pieceX;
	private int pieceY;
	
	private boolean holdAvailable;
	
	private int ghostY;
	
	public TetrisBoard(BagRandomizer bag) {
		this.bag = bag;
		
		board = new TetrisTile[WIDTH][HEIGHT];
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				board[x][y] = new TetrisTile();
			}
		}
		
		placePiece();
	}
	
	public TetrisTile getTile(int x, int y) {
		return board[x][y];
	}
	
	//https://tetris.wiki/Tetris_(NES,_Nintendo)
	public void setGravity(int level) {
		double secondsPerCell = Math.pow(0.8 - ((level - 1) * 0.007), level - 1);
		nanosPerCell = (long) (secondsPerCell * 1000000000);
	}
	
	public long getNanosPerCell() {
		return nanosPerCell;
	}
	
	public void writeTile(Tetromino tetromino, int orientation, int x, int y) {
		boolean[][] shape = tetromino.getShape(orientation);
		int hl = shape.length / 2 + 1;
		
		for(int b = 0; b < shape.length; b++) {
			for(int a = 0; a < shape[0].length; a++) {
				if(shape[a][b]) {
					board[b-hl + x][a-hl + y].filled = true;
					board[b-hl + x][a-hl + y].color = tetromino.getColor();
				}
			}
		}
	}
	
	public void deleteTile(Tetromino tetromino, int orientation, int x, int y) {
		boolean[][] shape = tetromino.getShape(orientation);
		int hl = shape.length / 2 + 1;
		
		for(int b = 0; b < shape.length; b++) {
			for(int a = 0; a < shape[0].length; a++) {
				if(shape[a][b]) {
					board[b-hl + x][a-hl + y].filled = false;
					board[b-hl + x][a-hl + y].color = tetromino.getColor();
				}
			}
		}
	}
	
	public boolean checkTile(Tetromino tetromino, int orientation, int x, int y) {
		boolean[][] shape = tetromino.getShape(orientation);
		int hl = shape.length / 2 + 1;
		
		for(int b = 0; b < shape.length; b++) {
			for(int a = 0; a < shape[0].length; a++) {
				if(shape[a][b]) {
					if(MathUtil.within(b-hl+x, 0, TetrisBoard.WIDTH - 1) &&
						MathUtil.within(a-hl+y, 0, TetrisBoard.HEIGHT - 1)) {
						if(board[b-hl+ x][a-hl+y].filled) return false;
					} else { return false; }
				}
			}
		}
		
		return true;
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
	
	public void placePiece() {
		currentPiece = bag.next();
		pieceX = 5;
		pieceY = 21;
		pieceOrientation = 0;
		holdAvailable = true;
		
		boolean flag;
		for(int y = HEIGHT - 1; y >= 0; y--) {
			flag = true;
			for(int x = 0; x < WIDTH; x++) {
				if(!board[x][y].filled) flag = false;
			}
			
			if(flag) {
				for(int i = y - 1; i >= 0; i--) {
					for(int x = 0; x < WIDTH; x++) {
						board[x][i + 1].filled = board[x][i].filled;
						board[x][i + 1].color = board[x][i].color;
					}
				}
				y++;
			}
		}
		
		writeTile(currentPiece, pieceOrientation, pieceX, pieceY);
		updateGhost();
	}
	
	public void hold() {
		if(holdAvailable) {
			holdAvailable = false;
			deleteTile(currentPiece, pieceOrientation, pieceX, pieceY);
			
			Tetromino t = heldPiece;
			if(t == null) t = bag.next();
			heldPiece = currentPiece;
			currentPiece = t;
			
			pieceX = 5;
			pieceY = 21;
			pieceOrientation = 0;
			holdAvailable = false;
			
			writeTile(currentPiece, pieceOrientation, pieceX, pieceY);
			
			updateGhost();
		}
	}
	
	private void updateGhost() {
		deleteTile(currentPiece, pieceOrientation, pieceX, pieceY);
		int y;
		for(y = pieceY; y <= HEIGHT; y++) {
			if(!checkTile(currentPiece, pieceOrientation, pieceX, y))
				break;
		}
		writeTile(currentPiece, pieceOrientation, pieceX, pieceY);
		ghostY = y - 2;
	}
	
	public int getGhost() {
		return ghostY;
	}
	
	public int getPieceX() {
		return pieceX;
	}
	
	public int getPieceY() {
		return pieceY;
	}
	
	public int getOrientation() {
		return pieceOrientation;
	}
	
	// controls
	
	public boolean alter(int o, int x, int y) {
		deleteTile(currentPiece, pieceOrientation, pieceX, pieceY);
		boolean flag = false;
		if(checkTile(currentPiece, o, x, y)) {
			pieceOrientation = o;
			pieceX = x;
			pieceY = y;
			flag = true;
			updateGhost();
		}
		writeTile(currentPiece, pieceOrientation, pieceX, pieceY);
		return flag;
	}
	
	public void right() {
		alter(pieceOrientation, pieceX + 1, pieceY);
	}
	
	public void left() {
		alter(pieceOrientation, pieceX - 1, pieceY);
	}
	
	public void softdrop() {
		alter(pieceOrientation, pieceX, pieceY + 1);
	}
	
	public void harddrop() {
		while(alter(pieceOrientation, pieceX, pieceY + 1));
		placePiece();
	}
	
	private void rotate(int newOrientation) {
		int[][][] kickTable = currentPiece.getKickTable();
		for(int x = 0; x < kickTable[0].length; x++) {
			if(alter(newOrientation,
					pieceX + kickTable[pieceOrientation][x][0] - kickTable[newOrientation][x][0],
					pieceY - kickTable[pieceOrientation][x][1] + kickTable[newOrientation][x][1]))
				return;
		}
	}
	
	public void cwrotate() {
		rotate((pieceOrientation + 1) % 4);
	}
	
	public void ccwrotate() {
		rotate((pieceOrientation + 3) % 4);
	}
	
	public void fliprotate() {
		rotate((pieceOrientation + 2) % 4);
	}
}
