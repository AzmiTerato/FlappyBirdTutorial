package game.grid;

import com.badlogic.gdx.math.Matrix4;

import gdxl.Entity;
import gdxl.animation.Animation;
import gdxl.animation.ScaleAnim;
import gdxl.audio.Audio;
import gdxl.audio.Sound;
import gdxl.calc.ConstantGraph;
import gdxl.calc.Graph;
import gdxl.calc.SineGraph;
import gdxl.graphics2d.Matrices;
import gdxl.graphics2d.Sprite;

public class Bird extends Entity<Grid> {
	public final Sprite bird = Sprite.load("bird-orange.png");
	public final float size = 34.0f / 288.0f;

	public final Sprite wing = Sprite.load("bird-wing.png");
	public final float wingX = -10.0f / 34.0f;
	public final float wingY = +3.0f / 34.0f;
	public final float wingSize = 14.0f / 34.0f;
	
	final Animation.Loop wingAnim;
	
	public final Audio.Sound thrustSound = Sound.load("sounds/wing.ogg");
	public final Audio.Sound fallSound = Sound.load("sounds/swooshing.ogg");
	public final float fallSoundRotate = +20.0f;
	
	final Graph yHoverGraph;
	
	final float yThrust = +1.8f / 25.0f;
	final float gravity = -0.3f / 25.0f;

	float rotateRiseSpeed = 600.0f / 25.0f; 
	float rotateRise = -30.0f;
	float rotateDropSpeed = 140.0f / 25.0f; 
	float rotateDrop = +60.0f;
	
	float rotate1 = 0.0f;
	float rotate2 = 0.0f;
	boolean fallSoundPlayed = false;

	float ySpeed = 0.0f;
	float yHover = 0.0f;
	
	public float x = 0.0f;
	public float y1 = 0.0f;
	public float y2 = 0.0f;
	
	public boolean isSuspended = false;
	
	public void suspend(float suspendX, float suspendY) {
		isSuspended = true;
		ySpeed = 0.0f;
		x = suspendX;
		y1 = y2 = suspendY;
	}

	public boolean isStatic() {
		return y1 == y2;
	}
	
	public void thrust() {
		if(isSuspended)
			return;
		ySpeed = yThrust;
		thrustSound.play();
	}
	
	@Override
	protected void process(Grid v, float renderTime) {
		y2 = y1;
		rotate2 = rotate1;
		
		if(!isSuspended)
			ySpeed += gravity;
		
		y1 += ySpeed;
		
		// Add hover
		y1 -= yHover;
		yHover = yHoverGraph.generate(renderTime);
		y1 += yHover;
		
		float minY = v.map.ground.height + (size / 2.0f);
		if(y1 < minY)
			y1 = minY;
	
		if(ySpeed > 0.0f) {
			rotate1 -= rotateRiseSpeed;
			if(rotate1 < rotateRise)
				rotate1 = rotateRise;
			fallSoundPlayed = false;
		} else if(ySpeed < 0.0f) {
			if(rotate1 > fallSoundRotate && !fallSoundPlayed) {
				fallSound.play();
				fallSoundPlayed = true;
			}
			rotate1 += rotateDropSpeed;
			if(rotate1 > rotateDrop)
				rotate1 = rotateDrop;
		}
	}
	
	@Override
	protected void render(Grid v, float r, float renderTime) {
		Matrix4 m = Matrices.modelView;
		Matrices.push();
		
		float y = y2 + ((y1 - y2) * r);
		float rotate = rotate2 + ((rotate1 - rotate2) * r);
		
		// Render bird
		m.translate(x, y, 0.0f);
		m.scale(size, size, size);
		m.rotate(0, 0, -1, rotate);
		bird.render();
		
		// Render wing
		m.translate(wingX, wingY, 0.0f);
		m.scale(wingSize, wingSize, wingSize);
		// Animate wing
		wingAnim.updateAndApply(wing, getRenderDeltaTime());
		wing.render();
		
		Matrices.pop();
	}
	
	public Bird() {
		Animation wingAnim = new ScaleAnim(
			0.5f, 
			ScaleAnim.Location.BOTTOM,
			new ConstantGraph(1.0f, 1.0f), 
			new SineGraph(
				1.0f, 
				1.0f, 
				0.0f, 
				new ConstantGraph(1.0f, 1.0f), 
				null, 
				null
			)
		);
		
		this.wingAnim = wingAnim.loop();
		this.wingAnim.reset();
		
		
		this.yHoverGraph = new SineGraph(
			0.6f, 
			1.0f, 
			0.0f, 
			new ConstantGraph(0.015f, 1.0f), 
			null, 
			null
		);
		
		// Flow control
		processEnabled = true;
	}
}