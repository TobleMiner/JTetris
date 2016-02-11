package de.toble.tetris.data.brick;

import java.awt.Color;

import de.toble.tetris.data.Playfield;

public class BrickL extends Brick
{

	public BrickL(Playfield p, int speed)
	{
		super(p, speed);
	}

	@Override
	protected boolean[][] getShape(int rotation)
	{
		switch(rotation % 4)
		{
			case 0:
				return new boolean[][]{ { true, false }, { true, false },
						{ true, true } };
			case 1:
				return new boolean[][]{ { true, true, true }, { false, false, true } };
			case 2:
				return new boolean[][]{ { true, true }, { false, true },
						{ false, true } };
			case 3:
				return new boolean[][]{ { true, false, false }, { true, true, true } };
		}
		return null;
	}

	@Override
	public Color getColor()
	{
		return Color.RED;
	}

}
