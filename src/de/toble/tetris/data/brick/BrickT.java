package de.toble.tetris.data.brick;

import java.awt.Color;

import de.toble.tetris.data.Tetris;

public class BrickT extends Brick
{
	public BrickT(Tetris p, int speed)
	{
		super(p, speed);
	}

	@Override
	protected boolean[][] getShape(int rotation)
	{
		switch(rotation % 4)
		{
			case 0:
				return new boolean[][]{ { false, true, false }, { true, true, true } };
			case 1:
				return new boolean[][]{ { true, false }, { true, true },
						{ true, false } };
			case 2:
				return new boolean[][]{ { true, true, true }, { false, true, false } };
			case 3:
				return new boolean[][]{ { false, true }, { true, true },
						{ false, true } };
		}
		return null;
	}

	@Override
	public Color getColor()
	{
		return Color.YELLOW;
	}

}
