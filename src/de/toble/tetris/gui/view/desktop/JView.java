package de.toble.tetris.gui.view.desktop;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JPanel;

import de.toble.tetris.data.Tetris;
import de.toble.tetris.gui.render.EntityRender;
import de.toble.tetris.gui.view.View;

public abstract class JView extends JPanel implements View
{
	protected final HashMap<Class<?>, EntityRender> rendererRegistry = new HashMap<>();

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

	protected void registerRenderer(EntityRender renderer, Class<?> type)
	{
		this.rendererRegistry.put(type, renderer);
	}

	protected EntityRender getRenderer(Class<?> type)
	{
		EntityRender render = this.rendererRegistry.get(type);
		while(render == null)
		{
			type = type.getSuperclass();
			if(type == null) break;
			render = this.rendererRegistry.get(type);
		}
		return render;
	}
}
