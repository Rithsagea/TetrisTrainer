package com.rithsagea.tetris.ui;

import com.rithsagea.tempera.resources.TemperaImage;

public class TetrisSkin {
	
	public static final TetrisSkin DEFAULT = new TetrisSkin("b1.png");
	
	private TemperaImage mesh;
	private TemperaImage[] skin;
	
	private TetrisSkin(String path) {
		skin = new TemperaImage[10];
		
		mesh = new TemperaImage(TetrisSkin.class.getResourceAsStream(path)).resize(216, 24);
		for(int x = 0; x < 9; x++) {
			skin[x] = mesh.subimage(24 * x, 0, 24, 24);
		}
	}
	
	public TemperaImage getMesh() {
		return mesh;
	}
	
	public TemperaImage getSkin(int color) {
		return skin[color - 1];
	}
	
}
