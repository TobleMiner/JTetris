package de.toble.tetris;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Game implements Runnable
{
	private static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private boolean initialized = false;

	protected Random rand = new Random(System.currentTimeMillis());

	private long ticks = 0;

	/**
	 * Returns number of game ticks per second
	 * 
	 * @return Number of game ticks per second
	 */
	public abstract int getTPS();

	protected void tick()
	{
		this.ticks++;
	}

	public void init()
	{
		assert !initialized;
		int msPerTick = 1000 / getTPS();
		scheduler.scheduleAtFixedRate(this, msPerTick, msPerTick, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run()
	{
		this.tick();
	}

	/**
	 * @return The ticks
	 */
	public long getTicks()
	{
		return this.ticks;
	}

}
