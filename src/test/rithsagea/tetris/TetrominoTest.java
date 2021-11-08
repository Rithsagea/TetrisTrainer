package test.rithsagea.tetris;

import com.rithsagea.tetris.minos.Tetromino;

public class TetrominoTest {
	public static void main(String... args) {
		
		for(Tetromino piece : Tetromino.values()) {
			System.out.println("-=-=- Piece: " + piece + " -=-=-");
			for(int rot = 0; rot < 4; rot++) {
				System.out.println("-=-=- Rotation: " + rot + " -=-=-");
				boolean[][] shape = piece.getShape(rot);
				for(int row = 0; row < shape.length; row++) {
					for(int col = 0; col < shape.length; col++) {
						System.out.print(shape[row][col] ? '■' : '.');
					}
					System.out.println();
				}
			}
		}
	}
}
