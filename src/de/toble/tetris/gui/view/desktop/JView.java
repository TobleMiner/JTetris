package de.toble.tetris.gui.view.desktop;

import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JPanel;

import de.toble.tetris.data.Tetris;
import de.toble.tetris.gui.render.entity.EntityRender;
import de.toble.tetris.gui.view.View;

public abstract class JView extends JPanel implements View
{
	protected final HashMap<Class<?>, EntityRender> renderRegistry = new HashMap<>();

	protected final Tetris tetris;

	public JView(Tetris data)
	{
		this.tetris = data;
	}

	@Override
	public void notifyChanged()
	{
		for(Component comp : this.getComponents())
		{
			if(comp instanceof View) ((View) comp).notifyChanged();
		}
	}

	protected void registerRender(EntityRender renderer, Class<?> type)
	{
		this.renderRegistry.put(type, renderer);
	}

	protected EntityRender getRender(Class<?> type)
	{
		while(type != null)
		{
			EntityRender render = this.renderRegistry.get(type);
			if(render != null) return render;
			type = type.getSuperclass();
		}
		return null;
	}

	@Override
	public Dimension getSizeConstraints()
	{
		return null;
	}
}
