package de.toble.tetris.data;

import java.awt.Point;

public abstract class Entity
{
	/** The associated Tetris game */
	protected final Tetris tetris;

	/** {@code true} if entity shall be removed by tick scheduler */
	protected boolean remove = false;

	/**
	 * Default constructor
	 * 
	 * @param t
	 *            The Tetris game
	 */
	public Entity(Tetris t)
	{
		this.tetris = t;
	}

	/**
	 * Update entity state.
	 * 
	 * @return Returns {@code true} if entity changed
	 */
	public abstract boolean update();

	/**
	 * Returns the position of this entity
	 * 
	 * @return The position of this entity
	 */
	public abstract Point getPosition();

	/**
	 * Sets the position of this entity
	 * 
	 * @param pos
	 */
	public abstract void setPosition(Point pos);

	/**
	 * Marks this entity for removal
	 * 
	 * @param remove
	 *            {@code true} if entity shall be removed
	 */
	public void setRemove(boolean remove)
	{
		this.remove = remove;
	}

	/**
	 * Returns {@code true} if this entity shall be removed by the mighty tick
	 * scheduler
	 * 
	 * @return {@code true} if this entity shall be removed by the mighty tick
	 *         scheduler
	 */
	public boolean getRemove()
	{
		return this.remove;
	}
}
