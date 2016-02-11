package de.toble.tetris.data.brick;

import java.awt.Color;

import de.toble.tetris.data.Playfield;

public class BrickI extends Brick
{
	public BrickI(Playfield p, int speed)
	{
		super(p, speed);
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
