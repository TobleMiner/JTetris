package de.toble.tetris.gui.view.desktop;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import de.toble.tetris.data.Tetris;

public class JTetrisSidePanel extends JView
{
	private final JLabel score;

	public JTetrisSidePanel(Tetris data)
	{
		super(data);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.score = new JLabel();
		this.score.setAlignmentX(CENTER_ALIGNMENT);
		this.add(this.score);
	}

	@Override
	public void notifyChanged()
	{
		this.score.setText(Integer.toString(this.tetris.getScore()));
	}
}
