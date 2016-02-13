package de.toble.tetris.gui.view.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JComponent;

import de.toble.tetris.data.Entity;
import de.toble.tetris.data.Playfield;
import de.toble.tetris.gui.GuiUtil;

public class JTetris extends JComponent
{
	private final Playfield playfield;

	public JTetris(Playfield data)
	{
		super();
		this.playfield = data;
		this.setDoubleBuffered(true);
	}

	@Override
	public void paint(Graphics graphics)
	{
		Color[][] grid = this.playfield.getGrid();
		Dimension size = this.playfield.getSize();
		BufferedImage img = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_3BYTE_BGR);
		if(this.playfield.isGameOver())
		{
			Graphics2D gfx = img.createGraphics();
			gfx.setBackground(new Color(0xec1a1a));
			gfx.clearRect(0, 0, size.width, size.height);
		}
		GuiUtil.colorArrayToImg(grid, img);
		assert this.playfield.getEntities().size() <= 1;
		new ArrayList<Entity>(this.playfield.getEntities())
				.forEach(entity -> entity.render(img));
		Image newImg = img.getScaledInstance(this.getWidth(), this.getHeight(),
				BufferedImage.SCALE_FAST);
		graphics.drawImage(newImg, 0, 0, null);
	}
}
