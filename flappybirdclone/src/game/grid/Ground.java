package game.grid;

import com.badlogic.gdx.math.Matrix4;

import gdxl.Entity;
import gdxl.graphics2d.Matrices;
import gdxl.graphics2d.Sprite;

public class Ground extends Entity<Grid> {
	
	public final Sprite ground = Sprite.load("ground.png");
	
	public final float size = 336.0f / 288.0f;
	public final float height = size * ground.length;
	
	@Override
	protected void render(Grid v, float r, float renderTime) {
		Matrix4 m = Matrices.modelView;
		
		float cameraX = v.map.cameraX;
		
		float x = (float)Math.floor((cameraX - 0.5f) / size) * size;
		Matrices.push();
		m.translate(x + (size / 2.0f), size * ground.length / 2.0f, 0.0f);
		m.scale(size, size, size);
		while(x < (cameraX + 0.5f)) {
			ground.render();
			m.translate(1.0f, 0.0f, 0.0f);
			x += size;
		}
		Matrices.pop();
	}

}
