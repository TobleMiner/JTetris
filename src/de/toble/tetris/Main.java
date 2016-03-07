package de.toble.tetris;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.toble.tetris.data.Tetris;
import de.toble.tetris.gui.view.desktop.JView;
import de.toble.tetris.gui.view.desktop.MonitorView;
import de.toble.tetris.gui.view.lighthouse.Lighthouse;
import de.toble.tetris.input.KeyboardInput;
import de.toble.tetris.network.LighthouseNetwork;

public class Main extends JFrame implements Runnable
{
	@Override
	public void run()
	{
		Tetris tetris = new Tetris(new Dimension(20, 30));
		JView v = new MonitorView(tetris);
		tetris.addView(v);
		LighthouseNetwork lhnet = new LighthouseNetwork();
		try
		{
			lhnet.connect("10.42.0.135", 8000);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		Lighthouse lhview = new Lighthouse(tetris, lhnet);
		tetris.addView(lhview);
		tetris.init();
		this.getContentPane().add(v);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Tetris");
		this.pack();
		this.setVisible(true);
		this.setSize(new Dimension(300, 125));
		this.addKeyListener(new KeyboardInput(tetris));
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Main());
	}
}
