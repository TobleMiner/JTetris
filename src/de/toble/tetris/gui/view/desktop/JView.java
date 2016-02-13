package de.toble.tetris.gui.view.desktop;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JPanel;

import de.toble.tetris.data.Tetris;
import de.toble.tetris.gui.render.EntityRender;
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
		EntityRender render = this.renderRegistry.get(type);
		while(render == null)
		{
			type = type.getSuperclass();
			if(type == null) break;
			render = this.renderRegistry.get(type);
		}
		return render;
	}
}
