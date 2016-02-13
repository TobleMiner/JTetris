package de.toble.tetris.data.brick;

import java.awt.Color;

import de.toble.tetris.data.Tetris;

public class BrickI extends Brick
{
	public BrickI(Tetris t, int speed)
	{
		super(t, speed);
	}

	@Override
	protected boolean[][] getShape(int rotation)
	{
		switch(rotation % 2)
		{
			case 0:
				return new boolean[][]{ { true }, { true }, { true }, { true } };
			case 1:
				return new boolean[][]{ { true, true, true, true } };
		}
		return null;
	}

	@Override
	public Color getColor()
	{
		return Color.GREEN;
	}
}
