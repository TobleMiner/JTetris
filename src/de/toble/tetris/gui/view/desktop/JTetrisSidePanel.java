package de.toble.tetris.gui.view.desktop;

import java.awt.Font;

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
		JLabel scoreCaption = new JLabel("SCORE");
		Font def = scoreCaption.getFont();
		Font boldFont = new Font(def.getName(), Font.BOLD, def.getSize());
		scoreCaption.setFont(boldFont);
		scoreCaption.setAlignmentX(CENTER_ALIGNMENT);
		this.add(scoreCaption);
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
