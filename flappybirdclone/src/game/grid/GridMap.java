package game.grid;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import gdxl.Entity;
import gdxl.graphics2d.Matrices;
import gdxl.graphics2d.Sprite;

public class GridMap extends Entity<Grid> {
	static final String TAG = "GridMap";
	
	public final Sprite bg = Sprite.load("bg-day.png");
	
	// Scene graph
	public final Entity.Group pipesGroup = new Entity.Group();
	public final Ground ground = new Ground();
	
	public final Camera gameCamera;
	public float cameraX1 = 0.5f;
	public float cameraX2 = 0.5f;
	public float cameraX = 0.5f;
	
	public final float speed = 0.4f / 25.0f;
	
	public GridMap() {
		// Create game camera
		gameCamera = new OrthographicCamera(1.0f, Globals.LENGTH);
		gameCamera.position.y = +Globals.LENGTH / 2.0f;
		
		// Create scene graph
		pipesGroup.attach(this);
		ground.attach(this);
		
		// Flow control
		processEnabled = true;
	}
	
	@Override
	protected void process(Grid v, float renderTime) {
		// Timestep
		cameraX2 = cameraX1;
		// Add speed
		cameraX1 += speed;
	}
	
	@Override
	protected void render(Grid v, float r, float renderTime) {
		// Update game camera
		cameraX  = cameraX2 + ((cameraX1 - cameraX2) * r);
		gameCamera.position.x = cameraX;
		gameCamera.update();
		
		// Push game camera
		Matrices.push();
		Matrices.pushProjection();
		Matrices.loadCamera(gameCamera);
		
		// Render background
		Matrix4 m = Matrices.modelView;
		Matrices.push();
		m.translate(cameraX, +gameCamera.viewportHeight / 2.0f, 0.0f);
		m.scale(1.0f, gameCamera.viewportHeight / bg.length, 1.0f);
		bg.render();
		Matrices.pop();
	}
	
	@Override
	protected void renderFinish(Grid v, float r, float renderTime) {
		// Revert camera
		Matrices.popProjection();
		Matrices.pop();
	}
}
