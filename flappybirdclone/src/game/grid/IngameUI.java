package game.grid;

import gdxl.Sys;
import gdxl.animation.Animation;
import gdxl.graphics2d.Sprite;
import gdxl.ui.Clickable;
import gdxl.ui.Menu;
import gdxl.ui.StaticSprite;
import gdxl.ui.TextBox;
import gdxl.ui.UIElement;

public class IngameUI extends Menu<Grid> {
	
	public static class StartButton extends Clickable<Grid> {
		public StartButton(UIElement<?> viewport, UIElement.Metrics metrics, Sprite buttonUp, Sprite buttonDown, Animation startAnim, Animation idleAnim, Animation pressedAnim, Animation releasedAnim, Animation endAnim) {
			super(viewport, metrics, buttonUp, buttonDown, startAnim, idleAnim, pressedAnim, releasedAnim, endAnim);
		}
		
		@Override
		public boolean activated(Grid v) {
			// Start map
			v.map.start(v);
			return false;
		}
	}

	public static class LeaderboardButton extends Clickable<Grid> {
		public LeaderboardButton(UIElement<?> viewport, UIElement.Metrics metrics, Sprite buttonUp, Sprite buttonDown, Animation startAnim, Animation idleAnim, Animation pressedAnim, Animation releasedAnim, Animation endAnim) {
			super(viewport, metrics, buttonUp, buttonDown, startAnim, idleAnim, pressedAnim, releasedAnim, endAnim);
		}
		
		
		@Override
		public boolean activated(Grid v) {
			// TODO
			Sys.debug("dd", "leaderboard");
			return false;
		}
	}

	public final StaticSprite title;
	public final StaticSprite copyright;
	
	public final Clickable<?> startButton;
	public final Clickable<?> leaderboardButton;
	
	public final TextBox scoreText;
	public final StaticSprite gameOverText;
	
	// Tutorial
	public final StaticSprite readyText;
	public final StaticSprite tutorial;
	
	public void showMainMenu() {
		title.attach();
		copyright.attach();
		startButton.attach();
		leaderboardButton.attach();
	}
	
	public void showTutorial() {
		title.detachWithAnim();
		copyright.detachWithAnim();
		startButton.detachWithAnim();
		leaderboardButton.detachWithAnim();
		
		readyText.attach();
		tutorial.attach();
	}
	
	public void showGameplay() {
		readyText.detachWithAnim();
		tutorial.detachWithAnim();
		scoreText.attach();
	}
	
	public void showGameOver() {
		if(gameOverText.isAttached())
			return;
		gameOverText.attach();
		scoreText.detach();
	}
	
	public void setScore(int score) {
		scoreText.setText(Integer.toString(score));
	}
	
	public void reset() {
		readyText.detach();
		tutorial.detach();
		gameOverText.detach();
		scoreText.detach();
		title.detach();
		copyright.detach();
		startButton.detach();
		leaderboardButton.detach();
		scoreText.setText("0");
	}
	
	public IngameUI(UIElement<?> root, StaticSprite title, StaticSprite copyright,
			Clickable<?> startButton, Clickable<?> leaderboardButton,
			TextBox scoreText, StaticSprite gameOverText,
			StaticSprite readyText, StaticSprite tutorial
	) {
		this.title = title;
		this.copyright = copyright;
		this.startButton = startButton;
		this.leaderboardButton = leaderboardButton;
		this.scoreText = scoreText;
		this.gameOverText = gameOverText;
		this.readyText = readyText;
		this.tutorial = tutorial;
		
		root.setViewport(viewport);
		root.attach();
	}
}
