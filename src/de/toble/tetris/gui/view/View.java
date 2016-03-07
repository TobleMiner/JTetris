package de.toble.tetris.gui.view;

import java.awt.Dimension;

public interface View
{
	public void notifyChanged();

	public Dimension getSizeConstraints();
}
