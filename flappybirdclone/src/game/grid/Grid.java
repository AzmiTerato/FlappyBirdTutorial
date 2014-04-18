package game.grid;

import gdxl.Entity;
import gdxl.utils.Universe2D;

public class Grid extends Universe2D {
	
	public Entity.Group mapGroup = new Entity.Group();
	
	public GridMap map;
	
	public IngameUI ui;
	public EndgameUI endgameUI;

	
	
	public Grid() {
		super(Globals.TIMESTEP, Globals.INPUT_DEPTH, Globals.LENGTH);
		
	}
}
