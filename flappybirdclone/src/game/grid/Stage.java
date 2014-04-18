package game.grid;

import gdxl.calc.Range;
import gdxl.graphics2d.Sprite;

public class Stage {
	public final Sprite pipe = Sprite.load("pillar-green.png");
	public final float pipeSize = 52.0f / 288.0f;
	
	public final float openingMinGroundDistance = 0.1f;
	public final float openingMinSkyDistance = 0.1f;
	public final int emptySpaces = 2;
	public final Range openingHeight = new Range(0.3f, 0.2f);
	public final Range directionOpeningVariance = new Range(0.0f, 0.4f);
	public final Range directionOpenings = new Range(2, 3);
}