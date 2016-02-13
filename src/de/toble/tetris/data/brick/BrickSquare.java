package de.toble.tetris.data.brick;

import java.awt.Color;

import de.toble.tetris.data.Tetris;

public class BrickSquare extends Brick
{
	public BrickSquare(Tetris t, int speed)
	{
		super(t, speed);
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
