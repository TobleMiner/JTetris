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

public class Tetris extends Game
{
	private final static double SPEED_MIN = 10;
	private final static double SPEED_MAX = 0.5;

	private final static int SCORE_BRICK_SET = 10;
	private final static int SCORE_ROW = 1000;
	private final static double SCORE_ROW_MULTIPLIER = 2;

	private int rows = 0;

	private int score = 0;

	private List<Entity> entityRegistry = new ArrayList<>();
	private List<Entity> entityRemove = new ArrayList<>();
	private List<Entity> entityAdd = new ArrayList<>();

	private Entity activeEntity = null;

	private List<View> views = new ArrayList<>();

	private Color[][] grid;

	private Dimension size;

	public Tetris(Dimension size)
	{
		super();
		this.init(size);
	}

	private void init(Dimension size)
	{
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

	private int calcBrickFallTicks()
	{
		double speed = SPEED_MIN * Math.pow(0.95, this.rows);
		speed = Math.max(speed, SPEED_MAX);
		return (int) Math.ceil(speed / this.size.height * this.getTPS());
	}

	private Brick getNewBrick()
	{
		Class<?>[] classes = { BrickI.class, BrickL.class, BrickSquare.class,
				BrickT.class, BrickN.class };
		Class<?> clazz = classes[this.rand.nextInt(classes.length)];
		try
		{
			Brick brick = (Brick) clazz.getConstructor(Tetris.class, int.class)
					.newInstance(this, this.calcBrickFallTicks());
			brick.setPosition(
					new Point(this.size.width / 2 - brick.getShape()[0].length / 2, 0));
			if(brick.collides()) return null;
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
				System.arraycopy(this.grid[y - 1], 0, this.grid[y], 0,
						this.grid[y].length);
			else
				Arrays.fill(this.grid[y], null);
		}
	}

	private void checkLines()
	{
		int score = 0;
		double multiplier = 1;
		for(int y = 0; y < this.grid.length; y++)
		{
			for(int x = 0; x < this.grid[y].length; x++)
			{
				if(this.grid[y][x] == null) break;
				if(x == this.grid[y].length - 1)
				{
					this.removeLine(y);
					y--;
					this.rows++;
					score += (int) (SCORE_ROW * multiplier);
					multiplier += SCORE_ROW_MULTIPLIER - 1;
				}
			}
		}
		this.score += score;
	}

	@Override
	synchronized public void tick()
	{
		System.out.println(this.getTicks());
		try
		{
			super.tick();
			boolean uiUpdate = false;
			System.out.println("Entity update");
			for(Entity entity : this.entityRegistry)
			{
				if(entity.update()) uiUpdate = true;
				if(entity.getRemove()) this.entityRemove.add(entity);
			}
			System.out.println("Entity add/rm");
			this.entityRegistry.addAll(this.entityAdd);
			this.entityAdd.clear();
			this.entityRegistry.removeAll(this.entityRemove);
			this.entityRemove.clear();
			System.out.println("Ui/score update");
			if(uiUpdate)
			{
				this.checkLines();
				System.out.println("Ui update");
				this.views.forEach(view -> view.notifyChanged());
			}
			System.out.println("Done");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void brickSet()
	{
		this.score += SCORE_BRICK_SET;
		this.activeEntity = getNewBrick();
		if(this.activeEntity == null)
			this.gameOver();
		else
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
		Dimension constraints = v.getSizeConstraints();
		if(constraints != null) this.init(constraints);
	}

	public boolean collidesWithFill(Point p)
	{
		return this.grid[p.y][p.x] != null;
	}

	synchronized public void moveRight()
	{
		if(this.activeEntity != null && this.activeEntity instanceof Brick)
		{
			((Brick) this.activeEntity).move(1, 0);
			((Brick) this.activeEntity).setNeedsUpdate(true);
		}
	}

	synchronized public void moveLeft()
	{
		if(this.activeEntity != null && this.activeEntity instanceof Brick)
		{
			((Brick) this.activeEntity).move(-1, 0);
			((Brick) this.activeEntity).setNeedsUpdate(true);
		}
	}

	synchronized public void rotate()
	{
		if(this.activeEntity != null && this.activeEntity instanceof Brick)
		{
			((Brick) this.activeEntity).rotate();
			((Brick) this.activeEntity).setNeedsUpdate(true);
		}
	}

	synchronized public void moveDown()
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

	public int getScore()
	{
		return this.score;
	}
}
