package de.toble.tetris.gui.view;

import javax.swing.JComponent;

import de.toble.tetris.data.Playfield;

public abstract class View extends JComponent
{
	protected Playfield playfield;

	public View(Playfield data)
	{
		this.playfield = data;
	}

	public abstract void notifyChanged();
}
