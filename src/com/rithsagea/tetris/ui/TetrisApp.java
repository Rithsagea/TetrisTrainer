package com.rithsagea.tetris.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import com.rithsagea.tempera.resources.TemperaImage;
import com.rithsagea.tempera.ui.TemperaApp;
import com.rithsagea.tetris.Controls;
import com.rithsagea.tetris.TetrisBoard;
import com.rithsagea.tetris.TetrisTile;
import com.rithsagea.tetris.TileColor;
import com.rithsagea.tetris.minos.SevenBagRandomizer;
import com.rithsagea.tetris.minos.Tetromino;

public class TetrisApp extends TemperaApp {

	private TetrisBoard board;
	
	private TemperaImage grid;
	private HashMap<TileColor, TemperaImage> pieceSkin;
	
	private Controls controls;
	private boolean das;
	private long dasCharge;
	private long dropCharge;
	
	@Override
	public void setup() {
		board = new TetrisBoard(new SevenBagRandomizer(5, System.nanoTime()));
		board.setGravity(2);
		
		controls = new Controls();
		
		grid = new TemperaImage(TetrisApp.class.getResourceAsStream("grid.png")).resize(248, 480);
		pieceSkin = new HashMap<>();
		
		TemperaImage skin = new TemperaImage(TetrisApp.class.getResourceAsStream("b1.png")).resize(216, 24);
		for(TileColor color : TileColor.values()) {
			pieceSkin.put(color, skin.subimage(24 * color.ordinal(), 0, 24, 24));
		}
		
		setBounds(1080, 660);
		setBackground(Color.BLACK);
	}
	
	@Override
	public void update() {
		
		if(keyboard.keyDownOnce(controls.left)) board.left();
		if(keyboard.keyDownOnce(controls.right)) board.right();
		if(keyboard.keyDownOnce(controls.harddrop)) board.harddrop();
		if(keyboard.keyDownOnce(controls.cwrotate)) board.cwrotate();
		if(keyboard.keyDownOnce(controls.ccwrotate)) board.ccwrotate();
		if(keyboard.keyDownOnce(controls.fliprotate)) board.fliprotate();
		if(keyboard.keyDownOnce(controls.hold)) board.hold();
		
		if(keyboard.keyDown(controls.softdrop)) {
			if(dropCharge == -1) dropCharge = System.nanoTime();
			else if(System.nanoTime() - dropCharge > 1000000 * controls.dropdelay) {
				board.softdrop();
				if(controls.dropdelay != 0)
					dropCharge = -1;
			}
		}
		
		if(!(keyboard.keyDown(controls.left) || keyboard.keyDown(controls.right))) {
			das = false;
			dasCharge = -1;
		}
		else {
			if(!das) { // charge das
				if(dasCharge == -1) dasCharge = System.nanoTime();
				else if(System.nanoTime() - dasCharge > 1000000 * controls.das) {
					das = true;
				}
			} else {
				if(System.nanoTime() - dasCharge > 1000000 * controls.arr) {
					dasCharge = System.nanoTime();
					if(keyboard.keyDown(controls.left) && keyboard.keyDown(controls.right)) {
						if(keyboard.keyDownTicks(controls.left) < keyboard.keyDownTicks(controls.right))
							board.left();
						else
							board.right();
					} else {
						if(keyboard.keyDown(controls.left)) {
							board.left();
						}
	
						if(keyboard.keyDown(controls.right)) {
							board.right();
						}
					}
				}
			}
		}
	}

	private void drawPiece(Graphics g, Tetromino piece, int orientation, int xPos, int yPos) {
		boolean[][] shape = piece.getShape(orientation);
		int hl = shape.length / 2;
		for(int x = 0; x < shape.length; x++) {
			for(int y = 0; y < shape.length; y++) {
				if(shape[y][x]) {
					g.drawImage(pieceSkin.get(piece.getColor()).getImage(),
							xPos + (24 * (x - hl)),
							yPos + (24 * (y - hl)), null);
				}
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(grid.getImage(), 129, 9, null);
		
		for(int y = TetrisBoard.HEIGHT / 2; y < TetrisBoard.HEIGHT; y++) {
			for(int x = 0; x < TetrisBoard.WIDTH; x++) {
				TetrisTile tile = board.getTile(x,  y);
				if(tile.filled)
					g.drawImage(pieceSkin.get(tile.color).getImage(),
							130 + 24 * x, 10 + 24 * (y - TetrisBoard.HEIGHT / 2), null);
			}
		}
		
		Tetromino[] preview = board.getPreview();
		for(int i = 0; i < preview.length; i++) {
			drawPiece(g, preview[i], 0, 420, 56 + 72 * i);
		}
		
		if(board.getHeldPiece() != null) {
			drawPiece(g, board.getHeldPiece(), 0, 50, 56);
		}
		
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		drawPiece(g, board.getCurrentPiece(), board.getOrientation(),
				106 + (board.getPieceX()) * 24,
				10 + (board.getGhost() - TetrisBoard.HEIGHT / 2) * 24);
	}
}
