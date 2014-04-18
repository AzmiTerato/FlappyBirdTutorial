package game.grid;

import gdxl.Sys;
import gdxl.animation.Animation;
import gdxl.audio.Audio;
import gdxl.audio.Sound;
import gdxl.calc.Graph;
import gdxl.calc.LinearGraph;
import gdxl.graphics2d.Sprite;
import gdxl.ui.Clickable;
import gdxl.ui.Menu;
import gdxl.ui.StaticSprite;
import gdxl.ui.TextBox;
import gdxl.ui.UIElement;

public class EndgameUI extends Menu<Grid> {
	
	public static class RestartButton extends Clickable<Grid> {
		public RestartButton(UIElement<?> viewport, UIElement.Metrics metrics, Sprite buttonUp, Sprite buttonDown, Animation startAnim, Animation idleAnim, Animation pressedAnim, Animation releasedAnim, Animation endAnim) {
			super(viewport, metrics, buttonUp, buttonDown, startAnim, idleAnim, pressedAnim, releasedAnim, endAnim);
		}
		
		@Override
		public boolean activated(Grid v) {
			v.mapGroup.detachChilds();
			v.map = new GridMap();
			v.map.attach(v.mapGroup);
			
			return false;
		}
	}
	
	public static class ShareButton extends Clickable<Grid> {
		public ShareButton(UIElement<?> viewport, UIElement.Metrics metrics, Sprite buttonUp, Sprite buttonDown, Animation startAnim, Animation idleAnim, Animation pressedAnim, Animation releasedAnim, Animation endAnim) {
			super(viewport, metrics, buttonUp, buttonDown, startAnim, idleAnim, pressedAnim, releasedAnim, endAnim);
		}
		
		@Override
		public boolean activated(Grid v) {
			Sys.debug("dd", "TODO");		// TODO
			return false;
		}
	}
	
	
	static int currentBestScore = 0;		// TODO
	
	public final Audio.Sound showSound = Sound.load("sounds/die.ogg");
	
	public final TextBox currentScore;
	public final TextBox bestScore;

	public final float tScoreCountStart = 0.6f;
	
	public final StaticSprite newBestScore;
	
	public final Clickable<?> restartButton;
	public final Clickable<?> shareButton;
	
	public final Graph scoreApproachGraph = new LinearGraph(0.0f, 1.0f, 2.0f);
	int score = 0;
	
	
	
	public EndgameUI(UIElement<?> root, TextBox currentScore, TextBox bestScore,
			StaticSprite newBestScore, Clickable<?> restartButton,
			Clickable<?> shareButton
	) {
		this.currentScore = currentScore;
		this.bestScore = bestScore;
		this.newBestScore = newBestScore;
		this.restartButton = restartButton;
		this.shareButton = shareButton;
		
		root.setViewport(viewport);
		root.attach();
	}

	@Override
	protected void recreate(Grid v) {
		score = -1;
		currentScore.setText("0");
		bestScore.setText(Integer.toString(currentBestScore));
		// Sound
		showSound.play();
	}
	
	@Override
	protected void release(Grid v) { 
		newBestScore.detach();
		shareButton.detach();
		restartButton.detach();
	}
	
	@Override
	protected void render(Grid v, float r, float renderTime) {
		// Update score
		if(renderTime < tScoreCountStart || score == v.map.getScore())
			return;		// nothing to update or not yet time
		if(renderTime >= scoreApproachGraph.getLength())
			score = v.map.getScore();
		else
			score = Math.round(scoreApproachGraph.generate(renderTime) * (float)v.map.getScore());
		currentScore.setText(Integer.toString(score));
		if(score == v.map.getScore()) {
			// Check best score
			restartButton.attach();
			shareButton.attach();
			if(score > currentBestScore) {
				newBestScore.attach();
				currentBestScore = score;		// TODO
			}
		}
	}
}
