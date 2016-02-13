package de.toble.tetris.data;

import java.awt.Point;
import java.awt.image.BufferedImage;

public abstract class Entity
{
	protected final Tetris tetris;

	protected boolean remove = false;

	public Entity(Tetris p)
	{
		this.tetris = p;
	}

	/**
	 * Update entity state.
	 * 
	 * @return Returns true if entity changed
	 */
	public abstract boolean update();

	public abstract Point getPosition();

	public abstract void setPosition(Point pos);

	public void setRemove(boolean remove)
	{
		this.remove = remove;
	}

	public boolean getRemove()
	{
		return this.remove;
	}

	public abstract void render(BufferedImage img);
}
