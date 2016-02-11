package de.toble.tetris.data.brick;

import java.awt.Color;

import de.toble.tetris.data.Playfield;

public class BrickN extends Brick
{
	public BrickN(Playfield p, int speed)
	{
		super(p, speed);
	}

	@Override
	protected boolean[][] getShape(int rotation)
	{
		switch(rotation % 4)
		{
			case 0:
				return new boolean[][]{ { false, true, true }, { true, true, false } };
			case 1:
				return new boolean[][]{ { true, false }, { true, true },
						{ false, true } };
			case 2:
				return new boolean[][]{ { true, true, false }, { false, true, true } };
			case 3:
				return new boolean[][]{ { false, true }, { true, true },
						{ true, false } };
		}
		return null;
	}

	@Override
	public Color getColor()
	{
		return Color.ORANGE;
	}
}
