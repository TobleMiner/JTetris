package de.toble.tetris.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.toble.tetris.data.Playfield;

public class KeyboardInput implements KeyListener
{
	private Playfield playfield;

	public KeyboardInput(Playfield playfield)
	{
		this.playfield = playfield;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT:
				this.playfield.moveRight();
				break;

			case KeyEvent.VK_LEFT:
				this.playfield.moveLeft();
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_SPACE:
				this.playfield.rotate();
				break;
			case KeyEvent.VK_DOWN:
				this.playfield.moveDown();
				break;
			case KeyEvent.VK_ESCAPE:
				this.playfield.pause();
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

}
