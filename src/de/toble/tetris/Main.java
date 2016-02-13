package de.toble.tetris;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.toble.tetris.data.Tetris;
import de.toble.tetris.gui.view.desktop.JView;
import de.toble.tetris.gui.view.desktop.MonitorView;
import de.toble.tetris.input.KeyboardInput;

public class Main extends JFrame implements Runnable
{
	@Override
	public void run()
	{
		Tetris tetris = new Tetris(new Dimension(20, 30));
		JView v = new MonitorView(tetris);
		v.setSize(new Dimension(20, 30));
		tetris.addView(v);
		tetris.init();
		this.getContentPane().add(v);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Tetris");
		this.pack();
		this.setVisible(true);
		this.setSize(new Dimension(300, 300));
		this.addKeyListener(new KeyboardInput(tetris));
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Main());
	}
}
