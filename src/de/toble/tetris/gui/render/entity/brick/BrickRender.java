package de.toble.tetris.gui.render.entity.brick;

import java.awt.image.BufferedImage;

import de.toble.tetris.data.Entity;
import de.toble.tetris.data.brick.Brick;
import de.toble.tetris.gui.render.entity.EntityRender;

public abstract class BrickRender extends EntityRender
{
	@Override
	public void render(Entity entity, BufferedImage img)
	{
		this.renderBrick((Brick) entity, img);
	}

	public abstract void renderBrick(Brick brick, BufferedImage img);
}
