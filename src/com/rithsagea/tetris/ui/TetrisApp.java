package com.rithsagea.tetris.ui;

import java.awt.Graphics;

import com.rithsagea.tempera.resources.TemperaImage;
import com.rithsagea.tempera.ui.TemperaApp;
import com.rithsagea.tetris.api.TetrisBoard;
import com.rithsagea.tetris.api.Tetromino;

public class TetrisApp extends TemperaApp {

	private static final int WIDTH = 10;
	private static final int HEIGHT = 40;
	
	private static final int BORDER = 5;
	
	private TetrisBoard board;
	private Controls controls;
	
	private TemperaImage grid;
	private TetrisSkin skin;
	
	public TetrisBoard getBoard() {
		return board;
	}
	
	@Override
	public void setup() {
		board = new TetrisBoard(WIDTH, HEIGHT, 42L);
		controls = new Controls();
		
		grid = new TemperaImage(TetrisApp.class.getResourceAsStream("grid.png")).resize(248, 480);
		skin = TetrisSkin.DEFAULT;
		
		// 5 preview 5 board 5 hold 5
		setBounds(BORDER * 4 + (8 + board.getWidth()) * 24, board.getHeight() / 2 * 24);
		setResizable(false);
		setFrameRate(120);
	}

	@Override
	public void update() {
		if(keyboard.keyDownOnce(controls.left)) board.left();
		if(keyboard.keyDownOnce(controls.right)) board.right();
		if(keyboard.keyDownOnce(controls.harddrop)) board.harddrop();
		if(keyboard.keyDownOnce(controls.cwrotate)) board.cwrotate();
		if(keyboard.keyDownOnce(controls.ccwrotate)) board.ccwrotate();
//		if(keyboard.keyDownOnce(controls.fliprotate)) board.fliprotate();
		if(keyboard.keyDownOnce(controls.hold)) board.hold();
		if(keyboard.keyDownOnce(controls.softdrop)) board.softdrop(); 
	}

	private void drawPiece(Graphics g, Tetromino piece, int orientation, int x, int y) {
		if(piece == null || g == null) return;
		for(int[] tile : piece.getData()[orientation]) {
			g.drawImage(skin.getSkin(piece.getColor()).getImage(), (tile[0] + 1) * 24 + x, (3 - tile[1]) * 24 + y, 24, 24, null);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		//grid
		g.drawImage(grid.getImage(), BORDER * 2 + 4 * 24 -1, 0, null);
		
		//board
		for(int y = 0; y < HEIGHT; y++) {
			for(int x = 0; x < WIDTH; x++) {
				if(board.get(x, y) != 0)
					g.drawImage(skin.getSkin(board.get(x, y)).getImage(), 
							BORDER * 2 + (x + 4) * 24, (HEIGHT / 2 - y - 1) * 24, null);
			}
		}
		
		//hold
		drawPiece(g, board.getHeldPiece(), 0, BORDER, BORDER);
		
		//preview
		Tetromino[] preview = board.getPreview();
		for(int x = 0; x < preview.length; x++) {
			drawPiece(g, preview[x], 0, BORDER * 3 + 14 * 24, BORDER + x * 24 * 3);
		}
	}

}
