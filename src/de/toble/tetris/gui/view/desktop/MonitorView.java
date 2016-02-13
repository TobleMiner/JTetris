package de.toble.tetris.gui.view.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;

import de.toble.tetris.data.Tetris;

public class MonitorView extends JView
{
	public MonitorView(Tetris data)
	{
		super(data);
		this.setDoubleBuffered(true);
		this.setLayout(new BorderLayout());
		JTetris playfieldView = new JTetris(data);
		this.add(playfieldView, BorderLayout.CENTER);
		JTetrisSidePanel sidepanel = new JTetrisSidePanel(data);
		this.add(sidepanel, BorderLayout.LINE_END);
		sidepanel.setPreferredSize(new Dimension(100, 0));
	}

	@Override
	public void notifyChanged()
	{
		super.notifyChanged();
		this.invalidate();
	}
}
