package com.rithsagea.tetris.minos;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;

public class SevenBagRandomizer implements BagRandomizer {

	private Queue<Tetromino> bag;
	private Random rand;
	private int previews = 5;
	
	public SevenBagRandomizer(int previews, long seed) {
		bag = new ArrayDeque<>();
		rand = new Random(seed);
		this.previews = previews;
		
		while(bag.size() <= previews)
			refill();
	}
	
	private void refill() {
		Tetromino[] minos = Tetromino.values();
		for(int i = minos.length - 1; i > 0; i--) {
			int index = rand.nextInt(i + 1);
			
			Tetromino a = minos[index];
			minos[index] = minos[i];
			minos[i] = a;
		}
		
		for(Tetromino mino : minos) bag.add(mino);
	}
	
	@Override
	public Tetromino[] getPreview() {
		Tetromino[] preview = new Tetromino[5];
		Iterator<Tetromino> iter = bag.iterator();
		for(int i = 0; i < previews; i++) {
			preview[i] = iter.next();
		}
		return preview;
	}

	@Override
	public Tetromino next() {
		while(bag.size() <= previews)
			refill();
		
		return bag.poll();
	}

}
