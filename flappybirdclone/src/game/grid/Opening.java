package game.grid;

import gdxl.Entity;
import gdxl.graphics2d.Matrices;
import gdxl.graphics2d.Sprite;

import com.badlogic.gdx.math.Matrix4;

public class Opening extends Entity<Grid> {
	public final Stage stage;
	public final float x;
	public final float y;
	public final float height;
	
	public Opening(Stage stage, float x, float y, float height) {
		this.stage = stage;
		this.x = x;
		this.y = y;
		this.height = height;
	}
	
	@Override
	protected void render(Grid v, float r, float renderTime) {
		float cameraX = v.map.cameraX;
		
		// Do not render if not visible
		float leftX = x - (stage.pipeSize / 2.0f);
		float rightX = x + (stage.pipeSize / 2.0f);
		if(leftX > (cameraX + 0.5f) || rightX < (cameraX - 0.5f))
			return;		// not visible
		
		Matrix4 m = Matrices.modelView;

		Sprite pipe = stage.pipe;
		float pipeSize = stage.pipeSize;
		float pipeOffset = (height / 2.0f) + (pipeSize * pipe.length / 2.0f);

		// Render bottom pipe
		Matrices.push();
		m.translate(x, y - pipeOffset, 0.0f);
		m.scale(pipeSize, pipeSize, pipeSize);
		pipe.render();
		Matrices.pop();
		
		// Render top pipe
		Matrices.push();
		m.translate(x, y + pipeOffset, 0.0f);
		m.scale(pipeSize, -pipeSize, pipeSize);			// invert Y dimension
		pipe.render();
		Matrices.pop();
	}
}