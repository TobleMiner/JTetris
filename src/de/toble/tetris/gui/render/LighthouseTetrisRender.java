package de.toble.tetris.gui.render;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LighthouseTetrisRender
{
	public void render(Color[][] colors, BufferedImage img)
	{
		for(int y = 0; y < img.getHeight(); y++)
		{
			for(int x = 0; x < img.getWidth(); x++)
			{
				if(colors[y][x] == null) continue;
				img.setRGB(x, img.getHeight() - y - 1, colors[y][x].getRGB());
			}
		}
	}
}
