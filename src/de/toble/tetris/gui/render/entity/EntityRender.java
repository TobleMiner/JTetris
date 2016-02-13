package de.toble.tetris.gui.render.entity;

import java.awt.image.BufferedImage;

import de.toble.tetris.data.Entity;

public abstract class EntityRender
{
	public abstract void render(Entity entity, BufferedImage img);
}
