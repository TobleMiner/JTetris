package de.toble.tetris.gui.render.entity.brick;

import java.awt.Point;
import java.awt.image.BufferedImage;

import de.toble.tetris.data.brick.Brick;

public class LighthouseBrickRender extends BrickRender
{
	@Override
	public void renderBrick(Brick brick, BufferedImage img)
	{
		boolean[][] shape = brick.getShape();
		int color = brick.getColor().getRGB();
		Point pos = brick.getPosition();
		int blocks = 0;
		for(int y = 0; y < shape.length; y++)
		{
			for(int x = 0; x < shape[y].length; x++)
			{
				if(shape[y][x])
				{
					img.setRGB(pos.x + x, img.getHeight() - (pos.y + y + 1), color);
					blocks++;
				}
			}
		}
		assert blocks <= shape.length * shape[0].length;
	}
}
