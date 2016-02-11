package de.toble.tetris.data.brick;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import de.toble.tetris.data.Entity;
import de.toble.tetris.data.Playfield;

public abstract class Brick extends Entity
{
	protected boolean needsUpdate = false;

	protected int rotation = 0;

	private final int speed;

	private Point pos;

	public Brick(Playfield p, int speed)
	{
		super(p);
		this.speed = speed;
	}

	public boolean[][] getShape()
	{
		return this.getShape(this.rotation);
	}

	protected abstract boolean[][] getShape(int rotation);

	public abstract Color getColor();

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

	public boolean move(int dx, int dy)
	{
		Point pos = ((Point) this.getPosition().clone());
		pos.translate(dx, dy);
		if(this.collidesWithBorders(pos))
		{
			return false;
		}
		if(this.collides(pos)) return false;
		this.setPosition(pos);
		return true;
	}

	private boolean moveDown()
	{
		return this.move(0, 1);
	}

	private void solidify()
	{
		boolean[][] shape = this.getShape();
		Point position = this.getPosition();
		for(int y = 0; y < shape.length; y++)
		{
			int posy = position.y + y;
			for(int x = 0; x < shape[y].length; x++)
			{
				if(!shape[y][x]) continue;
				int posx = position.x + x;
				this.playfield.getGrid()[posy][posx] = this.getColor();
			}
		}
		this.remove = true;
		this.playfield.brickSet();
	}

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
				if(!this.playfield.isInside(new Point(posx, posy)))
				{
					return true;
				}
			}
		}
		return false;
	}

	private boolean collides()
	{
		return this.collides(this.getPosition());
	}

	private boolean collides(Point position)
	{
		boolean[][] shape = this.getShape();
		if(position.y + shape.length - 1 == this.playfield.getSize().height) return true;
		for(int y = 0; y < shape.length; y++)
		{
			int posy = position.y + y;
			for(int x = 0; x < shape[y].length; x++)
			{
				if(!shape[y][x]) continue;
				int posx = position.x + x;
				if(posx >= this.playfield.getSize().width) continue;
				if(this.playfield.collidesWithFill(new Point(posx, posy)))
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
		if(this.playfield.getTicks() % this.speed == 0) // Go down by one
		{
			if(!this.moveDown()) this.solidify();
			return true;
		}
		return forceUpdate;
	}

	@Override
	public void render(BufferedImage img)
	{
		boolean[][] shape = this.getShape();
		int color = this.getColor().getRGB();
		Point pos = this.getPosition();
		for(int y = 0; y < shape.length; y++)
		{
			for(int x = 0; x < shape[0].length; x++)
			{
				if(shape[y][x]) img.setRGB(pos.x + x, pos.y + y, color);
			}
		}
	}

	public boolean setRotation(int rotation)
	{
		boolean[][] shape = this.getShape(rotation);
		Dimension size = this.playfield.getSize();
		if(this.pos.y + shape.length > size.height ||
				this.pos.x + shape[0].length > size.width)
			return false;
		this.rotation = rotation;
		return true;
	}

	public boolean rotate()
	{
		return this.setRotation(this.rotation + 1);
	}

	public void setNeedsUpdate(boolean force)
	{
		this.needsUpdate = force;
	}
}
