package de.toble.tetris;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.toble.tetris.data.Playfield;
import de.toble.tetris.gui.view.desktop.JView;
import de.toble.tetris.gui.view.desktop.MonitorView;
import de.toble.tetris.input.KeyboardInput;

public class Tetris extends JFrame implements Runnable
{
	@Override
	public void run()
	{
		Playfield pf = new Playfield(new Dimension(20, 30));
		JView v = new MonitorView(pf);
		v.setSize(new Dimension(20, 30));
		pf.addView(v);
		pf.init();
		this.getContentPane().add(v);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Tetris");
		this.pack();
		this.setVisible(true);
		this.setSize(new Dimension(300, 300));
		this.addKeyListener(new KeyboardInput(pf));
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Tetris());
	}
}
