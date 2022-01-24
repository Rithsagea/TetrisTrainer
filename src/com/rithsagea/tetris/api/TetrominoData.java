package com.rithsagea.tetris.api;

public class TetrominoData {
	protected static final int[][][] KICK_TABLE_1 = {
			//rotation 0
			{
				{0, 0},
				{0, 0},
				{0, 0},
				{0, 0},
				{0, 0}
			},
			
			//rotation r (1)
			{
				{0, 0},
				{1, 0},
				{1, -1},
				{0, 2},
				{1, 2}
			},
			
			//rotation 180 (2)
			{
				{0, 0},
				{0, 0},
				{0, 0},
				{0, 0},
				{0, 0}
			},
			
			//rotation l (3)
			{
				{0, 0},
				{-1, 0},
				{-1, -1},
				{0, 2},
				{-1, 2}
			}
	};
	
	protected static final int[][][] KICK_TABLE_2 = {
			//rotation 0
			{
				{0, 0},
				{-1, 0},
				{2, 0},
				{-1, 0},
				{2, 0}
			},
			
			//rotation r (1)
			{
				{-1, 0},
				{0, 0},
				{0, 0},
				{0, 1},
				{0, -2}
			},
			
			//rotation 180 (2)
			{
				{-1, 1},
				{1, 1},
				{-2, 1},
				{1, 0},
				{-2, 0}
			},
			
			//rotation l (3)
			{
				{0, 1},
				{0, 1},
				{0, 1},
				{0, -1},
				{0, 2}
			}
	};
	
	protected static final int[][][] KICK_TABLE_3 = {
			{{0, 0}},
			{{0, -1}},
			{{-1, -1}},
			{{-1, 0}}
	};
}
