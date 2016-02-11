package de.toble.tetris.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GuiUtil
{
	public static void colorArrayToImg(Color[][] colors, BufferedImage img)
	{
		for(int y = 0; y < img.getHeight(); y++)
		{
			for(int x = 0; x < img.getWidth(); x++)
			{
				if(colors[y][x] == null) continue;
				img.setRGB(x, y, colors[y][x].getRGB());
			}
		}
	}
}
