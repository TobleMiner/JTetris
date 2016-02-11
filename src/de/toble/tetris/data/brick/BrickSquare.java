package de.toble.tetris.data.brick;

import java.awt.Color;

import de.toble.tetris.data.Playfield;

public class BrickSquare extends Brick
{
	public BrickSquare(Playfield p, int speed)
	{
		super(p, speed);
	}

	@Override
	protected boolean[][] getShape(int rotation)
	{
		return new boolean[][]{ { true, true }, { true, true } };
	}

	@Override
	public Color getColor()
	{
		return Color.BLUE;
	}

}