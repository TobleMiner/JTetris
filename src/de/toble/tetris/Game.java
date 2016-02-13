package de.toble.tetris;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class Game implements Runnable
{
	private static ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	private boolean initialized = false;

	protected Random rand = new Random(System.currentTimeMillis());

	private long ticks = 0;

	private ScheduledFuture<?> future;

	protected boolean gameOver = false;

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
		this.start();
	}

	protected void start()
	{
		if(this.gameOver) return;
		int msPerTick = 1000 / getTPS();
		this.future = scheduler.scheduleAtFixedRate(this, msPerTick, msPerTick,
				TimeUnit.MILLISECONDS);
	}

	protected void stop()
	{
		this.future.cancel(true);
		this.future = null;
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

	synchronized public void pause()
	{
		if(this.future != null)
			this.stop();
		else
			this.start();
	}

	protected void gameOver()
	{
		this.gameOver = true;
		this.stop();
	}

	public boolean isGameOver()
	{
		return this.gameOver;
	}
}
