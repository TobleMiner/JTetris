package de.toble.tetris.gui.view.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.toble.tetris.data.Entity;
import de.toble.tetris.data.Tetris;
import de.toble.tetris.data.brick.Brick;
import de.toble.tetris.gui.GuiUtil;
import de.toble.tetris.gui.render.SimpleBrickRender;

public class JTetris extends JView
{
	public JTetris(Tetris data)
	{
		super(data);
		this.setDoubleBuffered(true);
		this.registerRender(new SimpleBrickRender(), Brick.class);
	}

	@Override
	public void paint(Graphics graphics)
	{
		Color[][] grid = this.tetris.getGrid();
		Dimension size = this.tetris.getSize();
		BufferedImage img = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_3BYTE_BGR);
		if(this.tetris.isGameOver())
		{
			Graphics2D gfx = img.createGraphics();
			gfx.setBackground(new Color(0xec1a1a));
			gfx.clearRect(0, 0, size.width, size.height);
		}
		GuiUtil.colorArrayToImg(grid, img);
		assert this.tetris.getEntities().size() <= 1;
		new ArrayList<Entity>(this.tetris.getEntities()).forEach(
				entity -> this.getRender(entity.getClass()).render(entity, img));
		Image newImg = img.getScaledInstance(this.getWidth(), this.getHeight(),
				BufferedImage.SCALE_FAST);
		graphics.drawImage(newImg, 0, 0, null);
	}

	@Override
	public void notifyChanged()
	{
		this.repaint();
	}
}
