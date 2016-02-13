package de.toble.tetris.data.brick;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import de.toble.tetris.data.Entity;
import de.toble.tetris.data.Tetris;

/**
 * Abstract representation of a Tetris brick
 * 
 * @author tsys
 *
 */
public abstract class Brick extends Entity
{
	/** {@code true} if brick needs ui update */
	protected boolean needsUpdate = false;

	/**
	 * Rotation of this brick. Orientation is determined by {@code rotation % 4}
	 */
	protected int rotation = 0;

	/** Ticks per block movement */
	private final int speed;

	/** Position of this brick */
	private Point pos;

	/**
	 * Default constructor. Sets an associated Tetris game and rate of fall of
	 * this brick
	 * 
	 * @param t
	 *            The associated Tetris game
	 * @param speed
	 *            Rate of fall
	 */
	public Brick(Tetris t, int speed)
	{
		super(t);
		this.speed = speed;
	}

	/**
	 * Returns a 2-dimensional array describing the shape of this brick
	 * dependent on its current rotation
	 * 
	 * @return A 2-dimensional array describing the shape of this brick
	 */
	public boolean[][] getShape()
	{
		return this.getShape(this.rotation);
	}

	/**
	 * Returns a 2-dimensional array describing the shape of this brick when
	 * rotated accordingly to {@code rotation}
	 * 
	 * @param rotation
	 *            Rotation of the brick
	 * @return A 2-dimensional array describing the shape of this brick
	 */
	protected abstract boolean[][] getShape(int rotation);

	/**
	 * Returns the color of this brick
	 * 
	 * @return Color of this brick
	 */
	public abstract Color getColor();

	/**
	 * Returns the rate of fall of this brick
	 * 
	 * @return The rate of fall of this brick
	 */
	public double getSpeed()
	{
		return speed;
	}

	@Override
	public Point getPosition()
	{
		return this.pos;
	}

	@Override
	public void setPosition(Point pos)
	{
		this.pos = (Point) pos.clone();
	}

	/**
	 * Tries to move brick by {@code dx}, {@code dy}
	 * 
	 * @param dx
	 *            Delta by which to move brick in X direction
	 * @param dy
	 *            Delta by which to move brick in Y direction
	 * @return Returns {@code true} if move doesn't result in collision
	 */
	public boolean move(int dx, int dy)
	{
		Point pos = ((Point) this.getPosition().clone());
		pos.translate(dx, dy);
		if(this.collidesWithBorders(pos)) return false;
		if(this.collides(pos)) return false;
		this.setPosition(pos);
		return true;
	}

	/**
	 * Moves this brick down by 1 block
	 * 
	 * @return Returns {@code true} if move doesn't result in collision
	 */
	private boolean moveDown()
	{
		return this.move(0, 1);
	}

	/**
	 * Makes this block a part of the solid mass of dead Tetris bricks that
	 * wither at the bottom of the Tetris game
	 */
	private void solidify()
	{
		int blocks = 0;
		boolean[][] shape = this.getShape();
		Point position = this.getPosition();
		for(int y = 0; y < shape.length; y++)
		{
			int posy = position.y + y;
			for(int x = 0; x < shape[y].length; x++)
			{
				if(!shape[y][x]) continue;
				int posx = position.x + x;
				blocks++;
				this.tetris.getGrid()[posy][posx] = this.getColor();
			}
		}
		this.remove = true;
		this.tetris.brickSet();
		assert blocks <= shape.length * shape[0].length;
	}

	/**
	 * Checks if {@code position} is outside the Tetris field
	 * 
	 * @param position
	 *            Position to check
	 * @return Returns {@code true} if {@code position} is outside the Tetris
	 *         field
	 */
	private boolean collidesWithBorders(Point position)
	{
		boolean[][] shape = this.getShape();
		for(int y = 0; y < shape.length; y++)
		{
			int posy = position.y + y;
			for(int x = 0; x < shape[y].length; x++)
			{
				if(!shape[y][x]) continue;
				int posx = position.x + x;
				if(!this.tetris.isInside(new Point(posx, posy))) return true;
			}
		}
		return false;
	}

	/**
	 * Checks if this brick collides either with the bottom of the Tetris field
	 * or any solid bricks
	 * 
	 * @return Returns {@code true} if this brick collides
	 */
	public boolean collides()
	{
		return this.collides(this.getPosition());
	}

	/**
	 * Checks if this brick at position {@code position} would collide either
	 * with the bottom of the Tetris field or any solid bricks
	 * 
	 * @return Returns {@code true} if this brick would collide
	 */
	private boolean collides(Point position)
	{
		boolean[][] shape = this.getShape();
		if(position.y + shape.length - 1 == this.tetris.getSize().height) return true;
		for(int y = 0; y < shape.length; y++)
		{
			int posy = position.y + y;
			for(int x = 0; x < shape[y].length; x++)
			{
				if(!shape[y][x]) continue;
				int posx = position.x + x;
				if(posx >= this.tetris.getSize().width) continue;
				if(this.tetris.collidesWithFill(new Point(posx, posy)))
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean update()
	{
		boolean forceUpdate = this.needsUpdate;
		this.needsUpdate = false;
		if(this.tetris.getTicks() % this.speed == 0) // Go down by one
		{
			if(!this.moveDown()) this.solidify();
			return true;
		}
		return forceUpdate;
	}

	/**
	 * Sets the rotation of this brick
	 * 
	 * @param rotation
	 *            New rotation
	 * @return Returns {@code true} if rotating doesn't result in any collisions
	 */
	public boolean setRotation(int rotation)
	{
		boolean[][] shape = this.getShape(rotation);
		Dimension size = this.tetris.getSize();
		if(this.pos.y + shape.length > size.height ||
				this.pos.x + shape[0].length > size.width)
			return false;
		this.rotation = rotation;
		return true;
	}

	/**
	 * Tries to rotate this brick by 90°
	 * 
	 * @return Returns {@code true} if rotating was successful
	 */
	public boolean rotate()
	{
		return this.setRotation(this.rotation + 1);
	}

	/**
	 * Sets if next update tick must result in an UI update
	 * 
	 * @param force
	 *            Set this to true if next update tick must force an UI update
	 */
	public void setNeedsUpdate(boolean force)
	{
		this.needsUpdate = force;
	}
}
