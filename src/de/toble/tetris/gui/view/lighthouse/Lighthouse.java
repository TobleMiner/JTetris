package de.toble.tetris.gui.view.lighthouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

import de.toble.tetris.data.Entity;
import de.toble.tetris.data.Tetris;
import de.toble.tetris.data.brick.Brick;
import de.toble.tetris.gui.render.LighthouseTetrisRender;
import de.toble.tetris.gui.render.entity.brick.LighthouseBrickRender;
import de.toble.tetris.gui.view.desktop.JView;
import de.toble.tetris.network.LighthouseNetwork;

public class Lighthouse extends JView
{
	private static int WIDTH = 28;
	private static int HEIGHT = 14;
	private static int COLOR_BYTES = 3;

	private byte[] buffer;

	private LighthouseNetwork network;

	public Lighthouse(Tetris tetris, LighthouseNetwork network)
	{
		super(tetris);
		this.buffer = new byte[WIDTH * HEIGHT * COLOR_BYTES];
		this.network = network;
		this.network.setLighthouse(this);
		this.registerRender(new LighthouseBrickRender(), Brick.class);
		this.sendFrame();
	}

	@Override
	public void notifyChanged()
	{
		this.renderToBuffer(this.buffer);
	}

	private void renderToBuffer(byte[] buffer)
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
		new LighthouseTetrisRender().render(grid, img);
		assert this.tetris.getEntities().size() <= 1;
		new ArrayList<Entity>(this.tetris.getEntities())
				.forEach(entity -> this.getRender(entity.getClass()).render(entity, img));
		System.arraycopy(((DataBufferByte) img.getRaster().getDataBuffer()).getData(), 0,
				buffer, 0, buffer.length);
	}

	@Override
	public Dimension getSizeConstraints()
	{
		return new Dimension(WIDTH, HEIGHT);
	}

	public synchronized void sendFrame()
	{
		this.network.sendFrame(this.buffer);
	}
}
