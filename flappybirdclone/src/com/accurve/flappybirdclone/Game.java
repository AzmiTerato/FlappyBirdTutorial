package com.accurve.flappybirdclone;

import game.grid.Globals;
import game.grid.Grid;
import game.grid.GridMap;
import gdxl.File;
import gdxl.Sys;
import gdxl.graphics2d.Material;
import gdxl.graphics2d.Renderer;
import gdxl.graphics2d.TextureJET;
import gdxl.graphics2d.TextureUtils;
import gdxl.graphics2d.compressors.Mosaic;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;

public class Game extends Sys {
	static final String TAG = "Game";
	
	public static String configCompiledFS = "compiled.fs";

	@Override
	protected void created() {
		// Initialize platform
		// Configure external override
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			File.allowExternalOverride = true;	
			File.externalOverridePath = "flappybirdclone/";
			// Show debug messages
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			// Configure compiled directory
			File.optimizedCacheDir = File.openExternal("compiled");
		}

		// Load compiled filesystem if exists
		if(File.existsFS(configCompiledFS))
			File.loadFS(configCompiledFS);
		
		// Configure texture compressors
		TextureJET.compressors = new TextureJET.TextureCompressor[] {
			new Mosaic(6, 8),				// 64x64 fragments, 8bits per channel
		};
		TextureUtils.resolutionGainThreshold = 1.0f;			// Prefer upsampling
		// Default material extension
		Material.defaultMaterialType = Material.simpleMaterialType;
		// Create standard renderer
		Renderer.renderer = new Renderer();
		
		// Done initializing framework
		Sys.debug(TAG, "Framework initialized");
		
		Grid grid = new Grid();
		Globals.grid = grid;

		grid.map = new GridMap();
		grid.map.attach(grid.mapGroup);
		grid.mapGroup.attach(grid);
		

		
		activate(Globals.grid);
	}
	
	@Override
	protected void destroyed() {
		// Recompile FS
		if(Gdx.app.getType() == ApplicationType.Desktop) {
			File.expandAllFS();
			File.saveFS(configCompiledFS);
		}
	}
}
