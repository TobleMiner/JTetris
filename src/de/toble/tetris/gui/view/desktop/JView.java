package de.toble.tetris.gui.view.desktop;

import java.awt.Component;

import javax.swing.JPanel;

import de.toble.tetris.data.Playfield;
import de.toble.tetris.gui.view.View;

public abstract class JView extends JPanel implements View
{
	protected Playfield playfield;

	public JView(Playfield data)
	{
		this.playfield = data;
	}

	@Override
	public void notifyChanged()
	{
		for(Component comp : this.getComponents())
		{
			if(comp instanceof View) ((View) comp).notifyChanged();
		}
	}
}
