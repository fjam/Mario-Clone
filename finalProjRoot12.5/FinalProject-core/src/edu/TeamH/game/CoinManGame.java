
package edu.TeamH.game;

import java.util.ArrayList;



import java.util.StringTokenizer;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import edu.TeamH.game.tools.TextureManager;


public class CoinManGame implements ApplicationListener{
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private CoinMan player1;

	private ArrayList<GameObjs> lst = new ArrayList<GameObjs>();
	private ArrayList<GameObjs> delete = new ArrayList<GameObjs>();
	private ArrayList<GameObjs> addToLst = new ArrayList<GameObjs>();
	private ArrayList<GameObjs> firstBackground = new ArrayList<GameObjs>();
	private ArrayList<GameObjs> secondBackground = new ArrayList<GameObjs>();
	private ArrayList<GameObjs> foreground = new ArrayList<GameObjs>();
	private ArrayList<GameObjs> bulletDropList = new ArrayList<GameObjs>();
	

	//for game buttons (ios,android) if and when implemented
	private Rectangle leftButton,rightButton, jumpButton, downButton, menuButtonNext, menuButtonStart, menuButtonRestart;
	
	private Sprite spriteLeftButton, spriteRightButton, spriteJumpButton, spriteDownButton,
					spriteTouchedLeftButton, spriteTouchedRightButton, spriteTouchedJumpButton, spriteTouchedDownButton,
					menuCongrats, menuNext,	menuStart, menuGameOver, menuRestart, menuGameComplete;
	
	private Texture buttonTexture, buttonTouchedTexture, menuButtonTex, menuButtonGameCompleteTex, background;
	
	private BitmapFont ScoreFont;
	private BitmapFont CreditsFont;
	private BitmapFont TitleFont;
	
	private float fontPos = 0;	
	private int lvl, maxLvl;	
	private float coinTimer;
	private byte coinState;
	private byte gameState = 1; // 1==Main_Menu, 2 == Main_Game, 3 == Next_level_screen, 4 == Gameover_screen, 5 == GameComplete_screen
	
	private boolean isLeftTouched, isRightTouched, isJumpTouched, isJumpSBTouched, isDownTouched, 
					isZTouched, isSTouched, isMKeyTouched, isLeftKeyTouched, isRightKeyTouched, isJumpKeyTouched, isDownKeyTouched;

	private boolean gameOverSoundPlayed, gameOverSoundCheck, starCheck, 
					stageClearSoundPlayed, stageClearSoundCheck, worldClearSoundPlayed, worldClearSoundCheck;
	
	
	@Override
	public void create(){
		
		TextureManager.create();
		SoundManager.create();
		SoundManager.background.setLooping(true);
		SoundManager.background.setVolume(0.1f);
		SoundManager.background.play();
		SoundManager.stage.setVolume(0.1f);
		SoundManager.gameOver.setVolume(0.1f);
		SoundManager.worldClear.setVolume(0.1f);
		 
		background = new Texture("bg.jpg");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 400);
		batch = new SpriteBatch();

		player1 = new CoinMan();
		player1.setPosition(200,200);
		
		menuButtonTex = new Texture(Gdx.files.internal("menuButtons.png"));
		menuButtonGameCompleteTex = new Texture(Gdx.files.internal("gameComplete.png"));
		menuCongrats = new Sprite(menuButtonTex, 65, 221, 390, 224); // position on sprite sheet (menuButtonTex)
		menuNext = new Sprite(menuButtonTex,130,524,303,100);
		menuButtonNext = new Rectangle(0, 0, 303, 100);
		
		menuStart = new Sprite(menuButtonTex, 131, 644, 250,250);
		menuStart.setPosition(((Gdx.graphics.getWidth()/2)-(menuStart.getWidth()/2)), 70);
		
		menuButtonStart = new Rectangle(0, 0, 250, 250);
		menuButtonStart.x = menuStart.getX();
		menuButtonStart.y = menuStart.getY();
		
		menuGameOver = new Sprite(menuButtonTex, 25, 0, 475, 220);
		menuGameOver.setPosition(((Gdx.graphics.getWidth()/2)-(menuGameOver.getWidth()/2)), 145);
		
		menuRestart = new Sprite(menuButtonTex, 168, 452, 170, 63);
		menuRestart.setPosition(((Gdx.graphics.getWidth()/2)-(menuRestart.getWidth()/2)), 85);
		
		menuButtonRestart = new Rectangle(0, 0, 170, 63);
		menuButtonRestart.x = menuRestart.getX();
		menuButtonRestart.y = menuRestart.getY();
		
		menuGameComplete = new Sprite(menuButtonGameCompleteTex, 0, 0, 270, 105);
		menuGameComplete.setPosition(((Gdx.graphics.getWidth()/2)-(menuGameComplete.getWidth()/2)), 145);
		
		buttonTexture = new Texture(Gdx.files.internal("arrows.png"));
		buttonTouchedTexture = new Texture(Gdx.files.internal("touchedArrows.png"));
		
		spriteLeftButton = new Sprite(buttonTexture, 0, 4, 60, 64);
		spriteRightButton = new Sprite(buttonTexture, 60, 4, 60, 63);
		spriteJumpButton = new Sprite(buttonTexture, 0, 68, 60, 60);
		spriteDownButton = new Sprite(buttonTexture, 60, 68, 60, 60);
		
		leftButton = new Rectangle(0, 0, 60, 64);
		rightButton = new Rectangle(0, 0, 60, 63);
		jumpButton = new Rectangle(0, 0, 60, 60);
		downButton = new Rectangle(0, 0, 60, 60);
		
		spriteTouchedLeftButton = new Sprite(buttonTouchedTexture, 0, 4, 58, 64);
		spriteTouchedRightButton = new Sprite(buttonTouchedTexture, 60, 4, 60, 63);
		spriteTouchedJumpButton = new Sprite(buttonTouchedTexture, 0, 68, 60, 60);
		spriteTouchedDownButton = new Sprite(buttonTouchedTexture, 60, 68, 60, 60);

		
		spriteLeftButton.setPosition(20,20);
		spriteRightButton.setPosition(120, 20);
		spriteJumpButton.setPosition(700, 20);
		spriteDownButton.setPosition(630, 20);
		
		spriteTouchedLeftButton.setPosition(20,20);
		spriteTouchedRightButton.setPosition(120, 20);
		spriteTouchedJumpButton.setPosition(700, 20);
		spriteTouchedDownButton.setPosition(730, 20);
		
		isLeftTouched = false;
		isRightTouched = false;
		isJumpTouched = false;
		isJumpSBTouched = false;
		isDownTouched = false;
		isZTouched = false;
		isSTouched = false;
		isLeftKeyTouched = false;
		isRightKeyTouched = false;
		isJumpKeyTouched = false;
		isDownKeyTouched = false;		
		isMKeyTouched = false;
		
		//dynamic levels
		lvl = 1;
		maxLvl = 3;
		loadlvl("level1");
		
		coinTimer = 0.0F;
		coinState = 1;
        
		ScoreFont = new BitmapFont(Gdx.files.internal("font/ScoreFont.fnt"));
			Gdx.files.internal("font/ScoreFont.png" );
		ScoreFont.setScale(2);
		
		CreditsFont = new BitmapFont(Gdx.files.internal("font/CreditsFont.fnt"));
			Gdx.files.internal("font/CreditsFont.png" );
		CreditsFont.setScale(1);
		
		
		TitleFont = new BitmapFont(Gdx.files.internal("font/TitleFont.fnt"));
			Gdx.files.internal("font/TitleFont.png" );
		TitleFont.setScale(2);
		
		gameOverSoundPlayed = false;
		gameOverSoundCheck = false;
		stageClearSoundPlayed = false;
		stageClearSoundCheck = false;
		worldClearSoundPlayed = false;
		worldClearSoundCheck = false;
		starCheck= false;

	}
	
	@Override
	public void dispose(){
		batch.dispose();
		TextureManager.dispose();
		SoundManager.dispose();

	}
	
	@Override
	public void render(){
		switch(this.gameState){
		case 1:
			this.mainMenu();
			break;
		case 2:
			this.mainGame();
			break;
		case 3:
			this.nextLevel();
			break;
		case 4:
			this.gameOver();
			break;
		case 5:
			this.gameComplete();
			break;
		}
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	
	public void updateCamera(){
		if(!isZTouched){
			leftButton.x = player1.sprite.getX() - 390;//stabilizing the buttons (fixing the position)
			spriteLeftButton.setPosition(leftButton.x, 20);
			
			rightButton.x = player1.sprite.getX() - 320;
			spriteRightButton.setPosition(rightButton.x, 20);
			
			jumpButton.x = player1.sprite.getX() + 250;
			spriteJumpButton.setPosition(jumpButton.x, 20);
			
			downButton.x = player1.sprite.getX() + 320;
			spriteDownButton.setPosition(downButton.x, 20);
			
			spriteTouchedLeftButton.setPosition(leftButton.x, 20);
			spriteTouchedRightButton.setPosition(rightButton.x, 20);
			spriteTouchedJumpButton.setPosition(jumpButton.x, 20);
			spriteTouchedDownButton.setPosition(downButton.x, 20);
		}else{
			leftButton.x = player1.sprite.getX() - 580; 
			spriteLeftButton.setPosition(leftButton.x, 20);
			
			rightButton.x = player1.sprite.getX() - 510;
			spriteRightButton.setPosition(rightButton.x, 20);
			
			jumpButton.x = player1.sprite.getX() + 410;
			spriteJumpButton.setPosition(jumpButton.x, 20);
			
			downButton.x = player1.sprite.getX() + 480;
			spriteDownButton.setPosition(downButton.x, 20);
			
			spriteTouchedLeftButton.setPosition(leftButton.x, 20);
			spriteTouchedRightButton.setPosition(rightButton.x, 20);
			spriteTouchedJumpButton.setPosition(jumpButton.x, 20);
			spriteTouchedDownButton.setPosition(downButton.x, 20);
			
		}
		
		camera.position.x = player1.sprite.getX(); // so the camera follows the character
		camera.update();
	}
	
	public void loadlvl(String lvl){
		//to clear whatever is in memory, that is about the current level.
		lst.clear();
		firstBackground.clear();
		foreground.clear();
		addToLst.clear();
		secondBackground.clear();
		
		FileHandle file = Gdx.files.internal(lvl);
		StringTokenizer tokens = new StringTokenizer(file.readString());
		
		while(tokens.hasMoreTokens()){
			String type = tokens.nextToken();
			if(type.equals("Brick")){
				lst.add(new Brick(
						Integer.parseInt(tokens.nextToken()), 
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Spikes")){
				lst.add(new Spikes(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Coin")){
				lst.add(new Coin(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Coin2")){
				lst.add(new Coin2(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Star")){
				lst.add(new Star(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("MBox")){
				lst.add(new MysteryBox(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("flagEnd")){ 
				lst.add(new flagEnd(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Bush")){
				firstBackground.add(new Bush(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Tree")){
				secondBackground.add(new Tree(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Cloud")){
				firstBackground.add(new Cloud(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Bullet")){
				lst.add(new Bullet(
						Integer.parseInt(tokens.nextToken()),
						Integer.parseInt(tokens.nextToken())));
			}else if(type.equals("Cannon")){
				int num1 = Integer.parseInt(tokens.nextToken());
				int num2 = Integer.parseInt(tokens.nextToken());
				foreground.add(new Cannon(num1,num2));
				lst.add(new Cannon(num1,num2));	
			}
		}
	}
	
	private void moveCoins(){
		float deltaTime = Gdx.graphics.getDeltaTime();
		coinTimer+= deltaTime * 100.0f;
		//System.out.println(coinTimer);
		if(coinState == 1){
			if(coinTimer <= 50){
				for(GameObjs obj : lst){
					if (obj.getClass() == Coin.class){
						obj.setPosition(obj.getBox().getX()+(30*deltaTime), obj.getBox().getY());
					}
				}
			}else{
				coinState = 2;
			}
		}else if(coinState == 2){
			if(coinTimer <= 100){
				for(GameObjs obj : lst){
					if (obj.getClass() == Coin.class){
						obj.setPosition(obj.getBox().getX()-(30*deltaTime), obj.getBox().getY());
					}
				}
				
			}else{
				coinTimer = 0.0F;
				coinState = 1;
			}
		}
	}
	
	public void mainMenu(){
		Gdx.gl.glClearColor(1f, 1f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
	
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		menuStart.draw(batch);

		CreditsFont.draw(batch, "Group H:", 0, 54);
		CreditsFont.draw(batch, "Fahad Jameel, Omri Meshulam, Shaharyar Mian", 0, 38);
		CreditsFont.draw(batch, "Game Programming Final Project", 0, 20);
		
		TitleFont.draw(batch, "COIN MAN", 290, 369);
		
		batch.end();
		
		//camera.position.x = 700; 
		//camera.position.y = 360;

		camera.position.x = 400; 
		camera.position.y = 200;


		camera.update();
		

		if(Gdx.input.isKeyPressed(Input.Keys.M)){
			if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
				if(!isMKeyTouched){
					SoundManager.pauseAll();
					isMKeyTouched = true;
				}else{
					SoundManager.background.play();
					isMKeyTouched = false;
				}
			}
		}
		
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos); // converts pixels/screen resolution to gamespace (different devices)
			Rectangle touch = new Rectangle(touchPos.x-16, touchPos.y-16, 32, 32);
			if(touch.overlaps(menuButtonStart)){
				gameState = 2;
			}
		}
	}
	
	public void mainGame(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(GameObjs obj : secondBackground){
			System.out.println(Gdx.graphics.getDeltaTime());
			if(obj.getClass() == Tree.class){
				obj.update(Gdx.graphics.getDeltaTime());
				batch.draw(obj.currentFrame(), obj.getBox().getX(), obj.getBox().getY(), obj.currentFrame().getRegionWidth()*obj.getSize(),obj.currentFrame().getRegionHeight()*obj.getSize()); 
			}
		}
		
		for(GameObjs obj : firstBackground){
			obj.draw(batch);
		}
		

		player1.draw(batch);
		
		for(GameObjs obj : lst){
			if(obj.getClass() == Coin2.class || obj.getClass() == MysteryBox.class || obj.getClass() == Star.class){
				obj.update(Gdx.graphics.getDeltaTime());
				batch.draw(obj.currentFrame(), obj.getBox().getX(), obj.getBox().getY()); 
			}else{
				obj.draw(batch);
			}
		}
		
		if(!isLeftTouched && !isLeftKeyTouched){
			spriteLeftButton.draw(batch);
		}else{
			spriteTouchedLeftButton.draw(batch);
		}
		if(!isRightTouched && !isRightKeyTouched){
			spriteRightButton.draw(batch);
		}else{
			spriteTouchedRightButton.draw(batch);
		}
		if(!isDownTouched && !isDownKeyTouched){
			spriteDownButton.draw(batch);
		}else{
			spriteTouchedDownButton.draw(batch);
		}
		if(!isJumpTouched && !isJumpKeyTouched && !isJumpSBTouched){
			spriteJumpButton.draw(batch);
		}else{
			spriteTouchedJumpButton.draw(batch);
		}
		player1.intFatigue = (int) player1.fatigueLevel;
		if(!isZTouched){
			ScoreFont.draw(batch, "Lives=" + player1.getLives(), fontPos, 400);
			ScoreFont.draw(batch, "Score=" + player1.getScore(), fontPos, 370);
			ScoreFont.draw(batch, "Fatigue Level=" + player1.intFatigue, fontPos, 30);	
		}else{
			ScoreFont.draw(batch, "Lives=" + player1.getLives(), fontPos-210, 710);
			ScoreFont.draw(batch, "Score=" + player1.getScore(), fontPos-210, 680);
			ScoreFont.draw(batch, "Fatigue Level =" + (player1.intFatigue), fontPos-210, 30);
		}
		
		moveCoins();
		
		for(GameObjs obj : foreground){
			if(obj.getClass() == Cannon.class){
				obj.draw(batch);
				obj.update(Gdx.graphics.getDeltaTime());
				if(obj.getTimer() >= 100){
					addToLst.add(new Bullet((((int)obj.getBox().x)+14),(((int)obj.getBox().y))+20));
					obj.setTimer(0);
				}
			}
		}

		
		batch.end();

		if(!bulletDropList.isEmpty()){
			for(GameObjs myObj : bulletDropList){
				if(myObj.getBox().y > -myObj.getBox().getHeight()){
					myObj.setPosition(myObj.getBox().getX(), myObj.getBox().getY()-(100 * Gdx.graphics.getDeltaTime()));
				}else{
					delete.add(myObj);
				}
			}	
		}
		
		
		Rectangle tmp = new Rectangle(0,0,800,10);		
		
		if(player1.hits(tmp) != -1){
			player1.action(1, 0, 10 );
		}
		
		boolean changed = false;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			isLeftKeyTouched = true;
			player1.moveLeft(Gdx.graphics.getDeltaTime());
			for(GameObjs obj : secondBackground){
				obj.moveLeft(Gdx.graphics.getDeltaTime());
			}
		}else{
			if(isLeftKeyTouched){
				isLeftKeyTouched = false;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			isRightKeyTouched = true;
			player1.moveRight(Gdx.graphics.getDeltaTime());
			for(GameObjs obj : secondBackground){
				obj.moveRight(Gdx.graphics.getDeltaTime());
			}
		}else{
			if(isRightKeyTouched){
				isRightKeyTouched = false;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			player1.jump();
			if(!isJumpSBTouched){
				if(!isMKeyTouched){
					SoundManager.jump.play();
				}
			}
			isJumpSBTouched = true;
		}else{
			if(isJumpSBTouched){
				isJumpSBTouched = false;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			player1.jump();
			if(!isJumpKeyTouched){
				if(!isMKeyTouched){
					SoundManager.jump.play();
				}
			}
			isJumpKeyTouched = true;
		}else{
			if(isJumpKeyTouched){
				isJumpKeyTouched = false;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if(!isDownKeyTouched){
				player1.marioDuck();
				isDownKeyTouched = true;
			}
		}else{
			if(isDownKeyTouched){
				isDownKeyTouched = false;
				player1.marioStand();	
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.M)){
			if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
				if(!isMKeyTouched){
					SoundManager.pauseAll();
					isMKeyTouched = true;
				}else{
					SoundManager.background.play();
					isMKeyTouched = false;
				}
			}
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			isSTouched = true;
			if(player1.fatigueLevel <= 100 || player1.getStar()){
				player1.speedPressed = true;
				if(this.isLeftKeyTouched || this.isLeftTouched || this.isRightKeyTouched || this.isRightTouched){
					if(!player1.getStar()){
						player1.fatigueLevel += 50 * Gdx.graphics.getDeltaTime();
					}
				}else{
					if(player1.fatigueLevel >= 0){
						player1.fatigueLevel -= 50 * Gdx.graphics.getDeltaTime();
					}
				}
				//System.out.println(player1.fatigueLevel);
			}else{
				player1.speedPressed = false;
			}
		}else {
			if(player1.fatigueLevel >= 0){
				player1.fatigueLevel -= 50 * Gdx.graphics.getDeltaTime();
			}
			if(isSTouched){
				isSTouched = false;
				player1.speedPressed = false;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Z)){
			isZTouched = true;
			camera.setToOrtho(false, 1200, 720);
		}else {
			if(isZTouched){
				isZTouched = false;
				camera.setToOrtho(false, 800, 400);
			}
		}
		for(int i=0; i<5; i++){
			if(Gdx.input.isTouched()){
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touchPos); // converts pixels/screen resolution to gamespace (different devices)
				Rectangle touch = new Rectangle(touchPos.x-16, touchPos.y-16, 32, 32);
				
				if(touch.overlaps(leftButton)){
					player1.moveLeft(Gdx.graphics.getDeltaTime());
					for(GameObjs obj : secondBackground){
						obj.moveLeft(Gdx.graphics.getDeltaTime());
					}
					isLeftTouched = true;
				}
				if(touch.overlaps(rightButton)){
					player1.moveRight(Gdx.graphics.getDeltaTime());
					for(GameObjs obj : secondBackground){
						obj.moveRight(Gdx.graphics.getDeltaTime());
					}
					isRightTouched = true;
				}
				if(touch.overlaps(jumpButton)){
					player1.jump();
					if(!isJumpTouched){
						if(!isMKeyTouched){
							SoundManager.jump.play();
						}
					}
					isJumpTouched = true;
				}
				if(touch.overlaps(downButton)){
					player1.marioDuck();
					isDownTouched = true;
				}
			}else{
				if(isLeftTouched){
					isLeftTouched = false;
				}
				if(isRightTouched){
					isRightTouched = false;
				}
				if(isJumpTouched){
					isJumpTouched = false;
				}
				if(isDownTouched){
					isDownTouched = false;
					player1.marioStand();
				}
			}
		}	
		
		player1.update(Gdx.graphics.getDeltaTime());
		
		for(GameObjs obj : lst){
			
			if(obj.getClass()== Bullet.class){
				if(!bulletDropList.contains(obj)){
					obj.moveLeft(Gdx.graphics.getDeltaTime());
					if(obj.getBox().getX()<-200){ // for now till someone finds out how to see if the x coordinate is out of view from the left
						delete.add(obj);
					}
				}
			}
			//System.out.println(player1.hits(obj.getBox()));
			switch (player1.hits(obj.getBox())){
			case 1:
				switch (obj.hitAction(1)){
				case 1:
					player1.action(1, 0, obj.getBox().y + obj.getBox().height);
					break;
				case 2:
					if(obj.getClass() == Bullet.class){
						if(player1.getStar()){
							if(!bulletDropList.contains(obj)){
								player1.bulletWin();
								bulletDropList.add(obj);
								if(!isMKeyTouched){ SoundManager.enemyStomp.play(0.3f); }
							}
						}else{
							player1.setPosition(0, 400); //life respawn position
							for(GameObjs objS : secondBackground){
								objS.resetPosition();
							}
							if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
							if(player1.getLives()==0){
								this.gameState = 4;
							}else{
								player1.lostLife();
							}
						}
					}else if(obj.getClass() == Spikes.class){
						player1.setPosition(0, 400); //life respawn position
						for(GameObjs objS : secondBackground){
							objS.resetPosition();
						}
						if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
						if(player1.getLives()==0){
							this.gameState = 4;
						}else{
							player1.lostLife();
						}
					}
					break;
				case 3:
					if(obj.getClass() == Coin.class || obj.getClass() == Coin2.class){
						if(!isMKeyTouched){ SoundManager.coin.play(0.3f); }
						player1.gotCoin();
						delete.add(obj);
					}else if(obj.getClass() == Star.class){
						player1.gotStar();
						delete.add(obj);
					}
					break;
				case 4:
					if(lvl+1>maxLvl){
						gameState = 5;
					}else{
						lvl++;
						changed = true;
					}
					break;
				case 5:
					player1.action(1, 0, obj.getBox().y + obj.getBox().height);
					if(!bulletDropList.contains(obj)){
						player1.bulletWin();
						bulletDropList.add(obj);
						if(!isMKeyTouched){ SoundManager.enemyStomp.play(0.3f); }
					}
					break;
				case 6:
					
					break;
				}
				break;
			case 2:
				switch(obj.hitAction(2)){
					case 1:
						player1.action(2, obj.getBox().x - player1.sprite.getWidth(), 0);
						for(GameObjs objS : secondBackground){
							objS.moveLeft(Gdx.graphics.getDeltaTime());
						}
						break;
					case 2:
						if(obj.getClass() == Bullet.class){
							if(player1.getStar()){
								if(!bulletDropList.contains(obj)){
									player1.bulletWin();
									bulletDropList.add(obj);
									if(!isMKeyTouched){ SoundManager.enemyStomp.play(0.3f); }
								}
							}else{
								player1.setPosition(0, 400); //life respawn position
								for(GameObjs objS : secondBackground){
									objS.resetPosition();
								}
								if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
								if(player1.getLives()==0){
									this.gameState = 4;
								}else{
									player1.lostLife();
								}
							}
						}else if(obj.getClass() == Spikes.class){
							player1.setPosition(0, 400); //life respawn position
							for(GameObjs objS : secondBackground){
								objS.resetPosition();
							}
							if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
							if(player1.getLives()==0){
								this.gameState = 4;
							}else{
								player1.lostLife();
							}
						}
						break;
					case 3:
						if(obj.getClass() == Coin.class || obj.getClass() == Coin2.class){
							if(!isMKeyTouched){ SoundManager.coin.play(0.3f); }
							player1.gotCoin();
							delete.add(obj);
						}else if(obj.getClass() == Star.class){
							player1.gotStar();
							delete.add(obj);
						}
						break;
					case 4:
						if(lvl+1>maxLvl){
							gameState = 5;
						}else{
							lvl++;
							changed = true;
						}
						break;
					case 6:

						break;
				}
				break;
			case 3:
				switch(obj.hitAction(3)){
					case 1:
						player1.action(3, obj.getBox().x +obj.getBox().width, 0);
						for(GameObjs objS : secondBackground){
							objS.moveRight(Gdx.graphics.getDeltaTime());
						}
						break;
					case 2:
						if(obj.getClass() == Bullet.class){
							if(player1.getStar()){
								if(!bulletDropList.contains(obj)){
									player1.bulletWin();
									bulletDropList.add(obj);
									if(!isMKeyTouched){ SoundManager.enemyStomp.play(0.3f); }
								}
							}else{
								player1.setPosition(0, 400); //life respawn position
								for(GameObjs objS : secondBackground){
									objS.resetPosition();
								}
								if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
								if(player1.getLives()==0){
									this.gameState = 4;
								}else{
									player1.lostLife();
								}
							}
						}else if(obj.getClass() == Spikes.class){
							player1.setPosition(0, 400); //life respawn position
							for(GameObjs objS : secondBackground){
								objS.resetPosition();
							}
							if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
							if(player1.getLives()==0){
								this.gameState = 4;
							}else{
								player1.lostLife();
							}
						}
						break;	
					case 3:
						if(obj.getClass() == Coin.class || obj.getClass() == Coin2.class){
							if(!isMKeyTouched){ SoundManager.coin.play(0.3f); }
							player1.gotCoin();
							delete.add(obj);
						}else if(obj.getClass() == Star.class){
							player1.gotStar();
							delete.add(obj);
						}
						break;
					case 4:
						if(lvl+1>maxLvl){
							gameState = 5;
						}else{
							lvl++;
							changed = true;
						}
						break;
					case 6:
						
						break;
				}
				break;
			case 4:
				switch(obj.hitAction(4)){
					case 1:
						player1.action(4, 0, obj.getBox().y - player1.getBox().height);
						break;
					case 2:
						if(obj.getClass() == Bullet.class){
							if(player1.getStar()){
								if(!bulletDropList.contains(obj)){
									player1.bulletWin();
									bulletDropList.add(obj);
									if(!isMKeyTouched){ SoundManager.enemyStomp.play(0.3f); }
								}
							}else{
								player1.setPosition(0, 400); //life respawn position
								for(GameObjs objS : secondBackground){
									objS.resetPosition();
								}
								if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
								if(player1.getLives()==0){
									this.gameState = 4;
								}else{
									player1.lostLife();
								}
							}
						}else if(obj.getClass() == Spikes.class){
							player1.setPosition(0, 400); //life respawn position
							for(GameObjs objS : secondBackground){
								objS.resetPosition();
							}
							if(!isMKeyTouched){ SoundManager.death.play(0.5f); }
							if(player1.getLives()==0){
								this.gameState = 4;
							}else{
								player1.lostLife();
							}
						}
						break;
					case 3:
						if(obj.getClass() == Coin.class || obj.getClass() == Coin2.class){
							if(!isMKeyTouched){ SoundManager.coin.play(0.3f); }
							player1.gotCoin();
							delete.add(obj);
						}else if(obj.getClass() == Star.class){
							player1.gotStar();
							delete.add(obj);
						}
						break;
					case 4:
						if(lvl+1>maxLvl){
							gameState = 5;
						}else{
							lvl++;
							changed = true;
						}
						break;
					case 6:
						delete.add(obj);
						addToLst.add(new Star((((int)obj.getBox().x)+44),(((int)obj.getBox().y))+10));
						break;
				}
				break;
			}
		}
		
		if(player1.getStar()){
			if(!isMKeyTouched){
				if(starCheck == false){
					starCheck = true;
					SoundManager.background.pause();
					SoundManager.starTheme.play();
				}
			}
		}else if(starCheck == true){
			starCheck = false;
			SoundManager.starTheme.stop();
			SoundManager.background.play();
		}
		
		while(!delete.isEmpty()){
			lst.remove(delete.get(0));
			delete.remove(0);
		}
		while(!addToLst.isEmpty()){
			lst.add(addToLst.get(0));
			addToLst.remove(0);
		}
		
		if(changed){
			this.gameState = 3; // Next level
			this.menuButtonNext.x = player1.getBox().x - 142;
			this.menuButtonNext.y = 80;
			this.menuCongrats.setPosition(player1.getBox().x - 212 , 175);
			this.menuNext.setPosition(player1.getBox().x - 142, 80);
		}
		
		fontPos = player1.sprite.getX() - 380;
		
		System.out.println("player1.top.getX(): " + player1.top.getX() + " player1.top.getY(): " + player1.top.getY() + " player1.top.getHeight(): " + player1.top.getHeight() + " player1.top.getWidth(): " + player1.top.getWidth());
		System.out.println("player1.left.getX(): " + player1.left.getX() + " player1.left.getY(): " + player1.left.getY() + " player1.left.getHeight(): " + player1.left.getHeight() + " player1.left.getWidth(): " + player1.left.getWidth());
		System.out.println("player1.right.getX(): " + player1.right.getX() + " player1.right.getY(): " + player1.right.getY() + " player1.right.getHeight(): " + player1.right.getHeight() + " player1.right.getWidth(): " + player1.right.getWidth());
		System.out.println("player1.bottom.getX(): " + player1.bottom.getX() + " player1.bottom.getY(): " + player1.bottom.getY() + " player1.bottom.getHeight(): " + player1.bottom.getHeight() + " player1.bottom.getWidth(): " + player1.bottom.getWidth());

		// Controls were here
		
		updateCamera();
	}
	
	
	public void nextLevel(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(GameObjs obj : firstBackground){
			obj.draw(batch);
		}
		
		player1.draw(batch);
		
		for(GameObjs obj : lst){
			obj.draw(batch);
		}
		
		this.menuCongrats.draw(batch);
		this.menuNext.draw(batch);

		batch.end();
		
		stageClearSound();
		
		if(Gdx.input.isKeyPressed(Input.Keys.M)){
			if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
				if(!isMKeyTouched){
					SoundManager.pauseAll();
					isMKeyTouched = true;
				}else{
					SoundManager.stage.play();
					isMKeyTouched = false;
				}
			}
		}

		for(int i=0; i<5; i++){
			if(Gdx.input.isTouched()){
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touchPos); // converts pixels/screen resolution to gamespace (different devices)
				Rectangle touch = new Rectangle(touchPos.x-16, touchPos.y-16, 32, 32);
				if(touch.overlaps(menuButtonNext)){
					player1.setPosition(0, 400);
					loadlvl("level" + lvl);
					gameState = 2;
					this.stageClearSoundPlayed = false;
					stageClearSoundCheck = false;
					if(SoundManager.stage.isPlaying()){
						SoundManager.stage.stop();
						if(!isMKeyTouched){ SoundManager.background.play(); }
					}
				}	
			}
		}
	}
	
	public void gameOver(){

		Gdx.gl.glClearColor(1f, 1f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		menuRestart.draw(batch);
		menuGameOver.draw(batch);
		batch.end();
		
		camera.position.x = 400; 
		camera.position.y = 200;
		camera.update();
		
		
		gameOverSound();
		
		if(Gdx.input.isKeyPressed(Input.Keys.M)){
			if(Gdx.input.isKeyJustPressed(Input.Keys.M)){
				if(!isMKeyTouched){
					SoundManager.pauseAll();
					isMKeyTouched = true;
				}else{
					isMKeyTouched = false;
				}
			}
		}
		
		
		
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos); // converts pixels/screen resolution to gamespace (different devices)
			Rectangle touch = new Rectangle(touchPos.x-16, touchPos.y-16, 32, 32);
			if(touch.overlaps(menuButtonRestart)){
				gameState = 2;
				lvl = 1;
				loadlvl("level1");
				player1.reset();
				player1.setPosition(200, 200);
				gameOverSoundPlayed = false;
				SoundManager.gameOver.stop();
				gameOverSoundCheck = false;
				if(!isMKeyTouched){ SoundManager.background.play(); }				
			}
		}
		
	}
	
	public void gameComplete(){
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		menuRestart.draw(batch);
		menuGameComplete.draw(batch);
		batch.end();
		
		camera.position.x = 400; 
		camera.position.y = 200;
		camera.update();
		
		worldClearSound();
			
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos); // converts pixels/screen resolution to gamespace (different devices)
			Rectangle touch = new Rectangle(touchPos.x-16, touchPos.y-16, 32, 32);
			if(touch.overlaps(menuButtonRestart)){
				gameState = 2;
				lvl = 1;
				loadlvl("level1");
				player1.reset();
				player1.setPosition(200, 200);
				this.worldClearSoundPlayed = false;
				worldClearSoundCheck = false;
				if(SoundManager.worldClear.isPlaying()){
					SoundManager.worldClear.stop();
					if(!isMKeyTouched){ SoundManager.background.play(); }
				}
			}
		}
		
	}
	public void stageClearSound(){
		if(!stageClearSoundPlayed && !isMKeyTouched){
			if(SoundManager.background.isPlaying()){
				SoundManager.background.pause();
				SoundManager.stage.play();
				stageClearSoundPlayed = true;
				stageClearSoundCheck = true;
			}else{
				SoundManager.stage.play();
				stageClearSoundPlayed = true;
				stageClearSoundCheck = true;
			}
		}else{
			if(!isMKeyTouched){
				if(!stageClearSoundPlayed){
					SoundManager.stage.play();
					stageClearSoundPlayed = true;
				}else if(!SoundManager.stage.isPlaying() && stageClearSoundCheck){
					SoundManager.background.play();
					stageClearSoundCheck = false;
				}else if(!stageClearSoundCheck){
					if(!SoundManager.background.isPlaying()){
						SoundManager.background.play();
					}
				}
			}
		}
	}
	
	public void worldClearSound(){
		if(!worldClearSoundPlayed && !isMKeyTouched){
			if(SoundManager.background.isPlaying()){
				SoundManager.background.pause();
				SoundManager.worldClear.play();
				worldClearSoundPlayed = true;
				worldClearSoundCheck = true;
			}else{
				SoundManager.worldClear.play();
				worldClearSoundPlayed = true;
				worldClearSoundCheck = true;
			}
		}else{
			if(!isMKeyTouched){
				if(!worldClearSoundPlayed){
					SoundManager.worldClear.play();
					worldClearSoundPlayed = true;
				}else if(!SoundManager.worldClear.isPlaying() && worldClearSoundCheck){
					SoundManager.background.play();
					worldClearSoundCheck = false;
				}else if(!worldClearSoundCheck){
					if(!SoundManager.background.isPlaying()){
						SoundManager.background.play();
					}
				}
			}
		}
	}
	
	public void gameOverSound(){
		if(!gameOverSoundPlayed && !isMKeyTouched){
			if(SoundManager.background.isPlaying()){
				SoundManager.background.pause();
				SoundManager.gameOver.play();
				gameOverSoundPlayed = true;
				gameOverSoundCheck = true;
			}else{
				SoundManager.gameOver.play();
				gameOverSoundPlayed = true;
				gameOverSoundCheck = true;
			}
		}else{
			if(!isMKeyTouched){
				if(!gameOverSoundPlayed){
					SoundManager.gameOver.play();
					gameOverSoundPlayed = true;
				}else if(!SoundManager.gameOver.isPlaying() && gameOverSoundCheck){
					SoundManager.background.play();
					gameOverSoundCheck = false;
				}else if(!gameOverSoundCheck){
					if(!SoundManager.background.isPlaying()){
						SoundManager.background.play();
					}
				}
			}
		}
	}
}