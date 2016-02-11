package de.toble.tetris.data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.toble.tetris.Game;
import de.toble.tetris.data.brick.Brick;
import de.toble.tetris.data.brick.BrickI;
import de.toble.tetris.data.brick.BrickL;
import de.toble.tetris.data.brick.BrickN;
import de.toble.tetris.data.brick.BrickSquare;
import de.toble.tetris.data.brick.BrickT;
import de.toble.tetris.gui.view.View;

public class Playfield extends Game
{
	int bricks = 0;

	private List<Entity> entityRegistry = new ArrayList<>();
	private List<Entity> entityRemove = new ArrayList<>();
	private List<Entity> entityAdd = new ArrayList<>();

	private Entity activeEntity = null;

	private List<View> views = new ArrayList<>();

	private final Color[][] grid;

	private final Dimension size;

	public Playfield(Dimension size)
	{
		super();
		this.grid = new Color[size.height][size.width];
		this.size = size;
	}

	@Override
	public void init()
	{
		super.init();
		this.activeEntity = this.getNewBrick();
		this.entityRegistry.add(this.activeEntity);
	}

	private Brick getNewBrick()
	{
		Class<?>[] classes = { BrickI.class, BrickL.class, BrickSquare.class,
				BrickT.class, BrickN.class };
		Class<?> clazz = classes[this.rand.nextInt(classes.length)];
		try
		{
			Brick brick = (Brick) clazz.getConstructor(Playfield.class, int.class)
					.newInstance(this, 20);
			brick.setPosition(new Point(12, 0));
			return brick;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	private void removeLine(int index)
	{
		for(int y = index; y >= 0; y--)
		{
			if(y > 0)
			{
				this.grid[y] = this.grid[y - 1];
			}
			else
			{
				Arrays.fill(this.grid[y], null);
			}
		}
	}

	private void checkLines()
	{
		for(int y = 0; y < this.grid.length; y++)
		{
			for(int x = 0; x < this.grid[y].length; x++)
			{
				if(this.grid[y][x] == null) break;
				if(x == this.grid[y].length - 1)
				{
					this.removeLine(y);
					y--;
				}
			}
		}
	}

	@Override
	synchronized public void tick()
	{
		try
		{
			super.tick();
			boolean uiUpdate = false;
			for(Entity entity : this.entityRegistry)
			{
				if(entity.update()) uiUpdate = true;
				if(entity.getRemove()) this.entityRemove.add(entity);
			}
			this.entityRegistry.addAll(this.entityAdd);
			this.entityAdd.clear();
			this.entityRegistry.removeAll(this.entityRemove);
			this.entityRemove.clear();
			if(uiUpdate)
			{
				this.checkLines();
				this.views.forEach(view -> view.notifyChanged());
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void brickSet()
	{
		this.activeEntity = getNewBrick();
		this.entityAdd.add(this.activeEntity);
	}

	public Dimension getSize()
	{
		return this.size;
	}

	public Color[][] getGrid()
	{
		return this.grid;
	}

	/**
	 * @return the entityRegistry
	 */
	public List<Entity> getEntities()
	{
		return entityRegistry;
	}

	@Override
	public int getTPS()
	{
		return 60;
	}

	public void addView(View v)
	{
		this.views.add(v);
	}

	public boolean collidesWithFill(Point p)
	{
		return this.grid[p.y][p.x] != null;
	}

	public void moveRight()
	{
		if(this.activeEntity != null && this.activeEntity instanceof Brick)
		{
			((Brick) this.activeEntity).move(1, 0);
			((Brick) this.activeEntity).setNeedsUpdate(true);
		}
	}

	public void moveLeft()
	{
		if(this.activeEntity != null && this.activeEntity instanceof Brick)
		{
			((Brick) this.activeEntity).move(-1, 0);
			((Brick) this.activeEntity).setNeedsUpdate(true);
		}
	}

	public void rotate()
	{
		if(this.activeEntity != null && this.activeEntity instanceof Brick)
		{
			((Brick) this.activeEntity).rotate();
			((Brick) this.activeEntity).setNeedsUpdate(true);
		}
	}

	public void moveDown()
	{
		if(this.activeEntity != null && this.activeEntity instanceof Brick)
		{
			((Brick) this.activeEntity).move(0, 1);
			((Brick) this.activeEntity).setNeedsUpdate(true);
		}
	}

	public boolean isInside(Point point)
	{
		if(point.x < 0 || point.y < 0) return false;
		if(point.x >= this.size.width || point.y >= this.size.height) return false;
		return true;
	}
}
