package game.grid;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import gdxl.Entity;
import gdxl.Touchable;
import gdxl.graphics2d.Matrices;
import gdxl.graphics2d.Sprite;

public class GridMap extends Entity<Grid> implements Touchable<Grid> {
	static final String TAG = "GridMap";
	
	public final Sprite bg = Sprite.load("bg-day.png");
	
	// Scene graph
	public final Entity.Group pipesGroup = new Entity.Group();
	public final Ground ground = new Ground();
	
	// Bird
	public final float birdStartY = 0.9f;
	public final float birdXOffset = -0.2f;
	public final Bird bird = new Bird();
	
	public final Camera gameCamera;
	public float cameraX1 = 0.5f;
	public float cameraX2 = 0.5f;
	public float cameraX = 0.5f;
	
	public final float speed = 0.4f / 25.0f;
	
	public final ArrayList<Opening> openings = new ArrayList<Opening>();
	public final float mapGenBufferX = 0.5f;
	float mapEndX = 1.0f;
	Stage stage = new Stage();
	float direction = +1.0f;		// Up
	int directionOpenings = 0;
	int maxDirectionOpenings = 0;
	int emptySpaces = 0;
	float openingY = (float)Math.random() * Globals.LENGTH;
	
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
		if(!bird.isHit)
			cameraX1 += speed;
		
		// Process map generation
		if(bird.isSuspended)
			mapEndX = cameraX1 + 0.5f;
		else while(mapEndX < (cameraX1 + 0.5f + mapGenBufferX)) {
			// Fill up empty spaces first
			if(emptySpaces < stage.emptySpaces) {
				emptySpaces++;
				mapEndX += stage.pipeSize;
				continue;
			}
			emptySpaces = 0;
			
			// Else need to create pipe now
			// Check if need to change direction
			if(directionOpenings >= maxDirectionOpenings) {
				// Change direction
				direction *= -1.0f;
				directionOpenings = 0;
				maxDirectionOpenings = stage.directionOpenings.generateInt();
			}
			
			// Generate opening parameters
			float x = mapEndX + (stage.pipeSize / 2.0f);
			float openingHeight = stage.openingHeight.generate();
			float y = openingY + (stage.directionOpeningVariance.generate() * direction);
			// Limit current openingY with current stage parameters
			float openingMinY = ground.height + stage.openingMinGroundDistance + (openingHeight / 2.0f);
			float openingMaxY = gameCamera.viewportHeight - stage.openingMinSkyDistance - (openingHeight / 2.0f);
			if(y < openingMinY)
				y = openingMinY;
			if(y > openingMaxY)
				y = openingMaxY;
			openingY = y;
			directionOpenings++;
			
			// Create opening
			Opening o = new Opening(stage, x, y, openingHeight);
			o.attach(pipesGroup);
			openings.add(o);
			
			// Move one step
			mapEndX += stage.pipeSize;
		}
		
		// Remove openins passed to the left of the screen
		float mapStartX = cameraX1 - 0.5f - mapGenBufferX;
		int actual = 0;
		for(int c = 0; c < openings.size(); c++) {
			Opening o = openings.get(c);
			// Check if this opening is visible
			if((o.x + (o.stage.pipeSize / 2.0f)) < mapStartX) {
				// Opening out of range, remove it
				o.detach();
				continue;
			}
			// Else keep it
			openings.set(actual, o);
			actual++;
		}
		// Trim arraylist
		while(openings.size() > actual)
			openings.remove(openings.size() - 1);
		
		// Process bird, check for collisions
		if(bird.isHit)
			return;
		
		float birdLeft = bird.x - (bird.collisionWidth / 2.0f);
		float birdRight = bird.x + (bird.collisionWidth / 2.0f);
		float birdTop = bird.y1 + (bird.collisionHeight / 2.0f); 
		float birdBottom = bird.y1 - (bird.collisionHeight / 2.0f); 
		for(int c = 0; c < openings.size(); c++) {
			Opening o = openings.get(c);
			
			float openingLeft = o.x - (o.stage.pipeSize / 2.0f);
			float openingRight = o.x + (o.stage.pipeSize / 2.0f);
			if(openingLeft > birdRight)
				continue;		// nowhere near this opening
			else if(openingRight < birdLeft) {
				// Bird passed this opening, collect points if still available
				// TODO
				continue;
			}
			// Else within range, check if right inside the opening 				
			float openingTop = o.y + (o.height / 2.0f);
			float openingBottom = o.y - (o.height / 2.0f);
			if(birdTop < openingTop && birdBottom > openingBottom)
				continue;		// right inside the opening
			
			// Else collision
			bird.hit();
			return;
		}
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
		
		// Set bird X
		bird.x = cameraX + birdXOffset;
		
	}
	
	@Override
	protected void renderFinish(Grid v, float r, float renderTime) {
		// Revert camera
		Matrices.popProjection();
		Matrices.pop();
	}
	
	@Override
	protected void recreate(Grid v) {
		// Receive input
		v.attachTouchable(this);
		
		// Reset bird
		bird.attach(this);
		bird.suspend(cameraX + birdXOffset, birdStartY);
	}

	@Override
	protected void release(Grid v) {
		v.detachTouchable(this);
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, Grid v) {
		if(!bird.isAttached())
			return false;		// not yet started
		
		if(bird.isSuspended)
			bird.isSuspended = false;
		if(!bird.isHit)
			bird.thrust();
		return true;
	}

	@Override
	public boolean touchMove(float x, float y, int pointer, Grid v) {
		return false;			// not tracked
	}

	@Override
	public boolean touchUp(float x, float y, int pointer, Grid v) {
		return false;			// not tracked
	}

	@Override
	public boolean keyDown(int key, Grid v) {
		return false;			// not tracked
	}

	@Override
	public boolean keyUp(int key, Grid v) {
		return false;			// not tracked
	}
}
