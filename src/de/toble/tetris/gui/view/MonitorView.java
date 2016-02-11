package de.toble.tetris.gui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.toble.tetris.data.Entity;
import de.toble.tetris.data.Playfield;
import de.toble.tetris.gui.GuiUtil;

public class MonitorView extends View
{

	public MonitorView(Playfield data)
	{
		super(data);
		this.setDoubleBuffered(true);
	}

	@Override
	public void notifyChanged()
	{
		this.repaint();
	}

	@Override
	public void paint(Graphics graphics)
	{
		Color[][] grid = this.playfield.getGrid();
		Dimension size = this.playfield.getSize();
		BufferedImage img = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_3BYTE_BGR);
		GuiUtil.colorArrayToImg(grid, img);
		new ArrayList<Entity>(this.playfield.getEntities())
				.forEach(entity -> entity.render(img));
		Image newImg = img.getScaledInstance(this.getWidth(), this.getHeight(),
				BufferedImage.SCALE_FAST);
		graphics.drawImage(newImg, 0, 0, null);
	}

}
