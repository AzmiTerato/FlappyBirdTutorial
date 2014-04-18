package game.grid;

public class Globals {
	
	public static final float TIMESTEP = 1.0f / 25.0f;			// process loop at 25fps
	
	public static final float WIDTH = 720.0f;
	public static final float HEIGHT = 1280.0f;
	
	public static final float LENGTH = HEIGHT / WIDTH;		// aspect ratio 16:9 (9:16)
	
	public static final int INPUT_GAMEPLAY = 0;
	public static final int INPUT_UI = 1;
	public static final int INPUT_DEPTH = INPUT_UI + 1;
	
	
	public static Grid grid;
}
