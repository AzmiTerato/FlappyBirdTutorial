package game.gb;

import game.grid.EndgameUI;
import game.grid.Globals;
import game.grid.IngameUI;
import gdxl.animation.Animation;
import gdxl.animation.CompoundAnim;
import gdxl.animation.FadeAnim;
import gdxl.animation.MoveAnim;
import gdxl.animation.ScaleAnim;
import gdxl.calc.CompoundGraph;
import gdxl.calc.ConstantGraph;
import gdxl.calc.Graph;
import gdxl.calc.LinearGraph;
import gdxl.calc.QuadraticGraph;
import gdxl.calc.SineGraph;
import gdxl.graphics2d.Font;
import gdxl.graphics2d.Sprite;
import gdxl.ui.StaticSprite;
import gdxl.ui.TextBox;
import gdxl.ui.UIElement;

public class GBInterface {
	
	public static final Animation buttonPressedAnim = new MoveAnim(
		0.2f, 
		new ConstantGraph(0.0f, 1.0f), 
		new ConstantGraph(-0.05f, 1.0f)
	);
	
	public static final Animation buttonReleasedAnim = new MoveAnim(
		0.2f, 
		new ConstantGraph(0.0f, 1.0f), 
		new LinearGraph(-0.05f, 0.0f, 1.0f)
	);
	
	public static final Animation buttonEndAnim = new ScaleAnim(
		0.4f, 
		new LinearGraph(1.0f, 0.0f, 1.0f)
	);
	
	public static final Animation buttonStartAnim = new ScaleAnim(
		0.3f, 
		new CompoundGraph(new Graph[] {
			new LinearGraph(0.0f, 1.2f, 0.2f),
			new LinearGraph(1.2f, 1.0f, 0.8f),
		}, false)
	);
	
	public static IngameUI createIngameUI() {
		UIElement.Bridge root = new UIElement.Bridge();
		
		StaticSprite title = new StaticSprite(
			root, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				350.0f / 512.0f, 
				0.0f, 
				+0.3f, 
				0.0f
			), 
			Sprite.load("ui/title.png"), 
			new CompoundAnim(0.5f, new Animation[] {
				new FadeAnim(
					1.0f,
					new LinearGraph(0.0f, 1.0f, 1.0f)
				),
				new MoveAnim(
					1.0f,
					new ConstantGraph(0.0f, 1.0f), 
					new QuadraticGraph(+0.3f, 0.0f, 1.0f, 0.0f, true)
				)					
			}, new float [] {
				1.0f, 
				1.0f,
			}), 
			new MoveAnim(
				1.0f,
				new ConstantGraph(0.0f, 1.0f), 
				new SineGraph(
					1.0f, 
					1.0f, 
					0.0f, 
					new ConstantGraph(0.01f, 1.0f), 
					null, 
					null
				)
			), 
			new FadeAnim(0.5f, new LinearGraph(1.0f, 0.0f, 1.0f))
		);
		
		StaticSprite copyright = new StaticSprite(
			root, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				250.0f / 512.0f, 
				0.0f, 
				-0.46f, 
				0.0f
			), 
			Sprite.load("ui/copyright.png"), 
			new CompoundAnim(0.5f, new Animation[] {
				new FadeAnim(
					1.0f,
					new LinearGraph(0.0f, 1.0f, 1.0f)
				),
				new MoveAnim(
					1.0f,
					new ConstantGraph(0.0f, 1.0f), 
					new QuadraticGraph(+0.3f, 0.0f, 1.0f, 0.0f, true)
				)					
			}, new float [] {
				1.0f, 
				1.0f,
			}), 
			null, 
			new FadeAnim(0.5f, new LinearGraph(1.0f, 0.0f, 1.0f))
		);
		
		Sprite startButtonMat = Sprite.load("ui/start-button.png");
		IngameUI.StartButton startButton = new IngameUI.StartButton(
			root, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				200.0f / 512.0f, 
				0.0f, 
				-0.15f, 
				-0.2f
			), 
			startButtonMat,
			startButtonMat,
			buttonStartAnim, 
			null, 
			buttonPressedAnim, 
			buttonReleasedAnim, 
			buttonEndAnim
		);
		startButton.setTouchableLayer(Globals.INPUT_UI);
				
		Sprite leaderboardButtonMat = Sprite.load("ui/leaderboard-button.png");
		IngameUI.LeaderboardButton leaderboardButton = new IngameUI.LeaderboardButton(
			root, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				200.0f / 512.0f, 
				0.0f, 
				-0.15f, 
				+0.2f
			), 
			leaderboardButtonMat,
			leaderboardButtonMat,
			buttonStartAnim, 
			null, 
			buttonPressedAnim, 
			buttonReleasedAnim, 
			buttonEndAnim
		);
		leaderboardButton.setTouchableLayer(Globals.INPUT_UI);

		StaticSprite gameOverText = new StaticSprite(
			root,
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				380.0f / 512.0f, 
				0.0f, 
				+0.3f, 
				0.0f
			), 
			Sprite.load("ui/game-over-text.png"),
			new CompoundAnim(0.5f, new Animation[] {
				new FadeAnim(
					1.0f,
					new LinearGraph(0.0f, 1.0f, 1.0f)
				),
				new MoveAnim(
					1.0f,
					new ConstantGraph(0.0f, 1.0f), 
					new QuadraticGraph(+0.3f, 0.0f, 1.0f, 0.0f, true)
				)					
			}, new float [] {
				1.0f, 
				1.0f,
			}), 
			new MoveAnim(
				1.0f,
				new ConstantGraph(0.0f, 1.0f), 
				new SineGraph(
					1.0f, 
					1.0f, 
					0.0f, 
					new ConstantGraph(0.01f, 1.0f), 
					null, 
					null
				)
			), 
			new FadeAnim(0.5f, new LinearGraph(1.0f, 0.0f, 1.0f))
		);
		
		StaticSprite readyText = new StaticSprite(
			root,
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				380.0f / 512.0f, 
				0.0f, 
				+0.3f, 
				0.0f
			), 
			Sprite.load("ui/get-ready-text.png"),
			new CompoundAnim(0.5f, new Animation[] {
				new FadeAnim(
					1.0f,
					new LinearGraph(0.0f, 1.0f, 1.0f)
				),
				new MoveAnim(
					1.0f,
					new ConstantGraph(0.0f, 1.0f), 
					new QuadraticGraph(+0.3f, 0.0f, 1.0f, 0.0f, true)
				)					
			}, new float [] {
				1.0f, 
				1.0f,
			}), 
			new MoveAnim(
				1.0f,
				new ConstantGraph(0.0f, 1.0f), 
				new SineGraph(
					1.0f, 
					1.0f, 
					0.0f, 
					new ConstantGraph(0.01f, 1.0f), 
					null, 
					null
				)
			), 
			new FadeAnim(0.5f, new LinearGraph(1.0f, 0.0f, 1.0f))
		);
		
		StaticSprite tutorial = new StaticSprite(
			root,
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				228.0f / 512.0f, 
				0.0f, 
				-0.2f, 
				0.0f
			), 
			Sprite.load("ui/tutorial.png"),
			new FadeAnim(
				0.3f,
				new LinearGraph(0.0f, 1.0f, 1.0f)
			), 
			null, 
			new FadeAnim(0.5f, new LinearGraph(1.0f, 0.0f, 1.0f))
		);
		
		Font font = new Font("ui/flappyoutline");
		TextBox scoreText = new TextBox(root, 
			new UIElement.Metrics(
				0.0f,
				0.0f, 
				0.3f, 
				0.0f, 
				+0.4f,
				0.0f
			), 
			font, 
			Font.Alignment.CENTER, 
			Font.HAlignment.CENTER, 
			0.27f, 
			new FadeAnim(1.0f, new LinearGraph(0.0f, 1.0f, 1.0f)),
			null, 
			new FadeAnim(1.0f, new LinearGraph(1.0f, 0.0f, 1.0f))
		);
		
		
		return new IngameUI(
			root, 
			title, 
			copyright, 
			startButton, 
			leaderboardButton, 
			scoreText, 
			gameOverText, 
			readyText, 
			tutorial
		);
	}
	
	public static EndgameUI createEndgameUI() {
		UIElement.Bridge root = new UIElement.Bridge();
		
		StaticSprite endgameBg = new StaticSprite(
			root, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				380.0f / 512.0f, 
				0.0f, 
				0.0f, 
				0.0f
			), 
			Sprite.load("ui/score-bg.png"), 
			new MoveAnim(
				0.5f,
				new ConstantGraph(0.0f, 1.0f), 
				new QuadraticGraph(-2.0f, 0.0f, 1.0f, 0.0f, true)), 
			null, 
			null
		);
		endgameBg.attach();
		
		Font font = new Font("ui/flappyoutline");
		TextBox currentScore = new TextBox(
			endgameBg, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				57.0f / 238.0f, 
				0.0f, 
				+19.0f / 126.0f, 
				+63.0f / 238.0f
			), 
			font, 
			Font.Alignment.RIGHT, 
			Font.HAlignment.CENTER, 
			22.0f / 57.0f, 
			null, null, null
		);
		currentScore.attach();
		
		TextBox bestScore = new TextBox(
			endgameBg, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				57.0f / 238.0f, 
				0.0f, 
				-23.0f / 126.0f, 
				+63.0f / 238.0f
			), 
			font, 
			Font.Alignment.RIGHT, 
			Font.HAlignment.CENTER, 
			22.0f / 57.0f, 
			null, null, null
		);
		bestScore.attach();
		
		StaticSprite newBestScore = new StaticSprite(
			endgameBg, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				32.0f / 238.0f, 
				0.0f, 
				-5.0f / 126.0f, 
				+38.0f / 238.0f
			), 
			Sprite.load("ui/new-sticker.png"), 
			new ScaleAnim(
				0.5f, 
				new CompoundGraph(new Graph[] {
					new LinearGraph(0.0f, 1.4f, 0.2f),
					new LinearGraph(1.4f, 1.0f, 0.8f),
				}, false)
			), 
			null, null
		);
		
		Sprite restartButtonMat = Sprite.load("ui/ok-button.png");
		EndgameUI.RestartButton restartButton = new EndgameUI.RestartButton(
			root, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				160.0f / 512.0f, 
				0.0f, 
				-0.3f, 
				-0.2f
			), 
			restartButtonMat, 
			restartButtonMat, 
			buttonStartAnim, 
			null, 
			buttonPressedAnim, 
			buttonReleasedAnim, 
			buttonEndAnim
		); 
		restartButton.setTouchableLayer(Globals.INPUT_UI);

		Sprite shareButtonMat = Sprite.load("ui/share-button.png");
		EndgameUI.ShareButton shareButton = new EndgameUI.ShareButton(
			root, 
			new UIElement.Metrics(
				0.0f, 
				0.0f, 
				160.0f / 512.0f, 
				0.0f, 
				-0.3f, 
				+0.2f
			), 
			shareButtonMat,
			shareButtonMat, 
			buttonStartAnim, 
			null, 
			buttonPressedAnim, 
			buttonReleasedAnim, 
			buttonEndAnim
		);
		shareButton.setTouchableLayer(Globals.INPUT_UI);

		return new EndgameUI(
			root,
			currentScore, 
			bestScore, 
			newBestScore, 
			restartButton, 
			shareButton
		);
	}
	
	

}
