package edu.TeamH.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;





import edu.TeamH.game.tools.TextureManager;


class CoinMan extends GameObjs{
	Rectangle bottom, left, right, top, full;
	Sprite sprite;
	
	//Texture texture;
	int action;
	int starLength = 10; // in seconds
	float velocityY;
	boolean dir, speedPressed, starOn, spriteReload, isStanding, isDucking;
	final int fLives;
	int lives, coinsCollected, score, intFatigue, colorRotation;
	float fatigueLevel, starTime;
	
	public CoinMan(){
		sprite = new Sprite(TextureManager.mario_big_walking_1); //(texture, 0,0,128, 128);
		
		//For collision detection
		full = new Rectangle(0, 0f, sprite.getWidth(), sprite.getHeight());
		bottom = new Rectangle(0 , 0, sprite.getWidth(), 1);
		left = new Rectangle(sprite.getWidth()-1, 2, 1, sprite.getHeight()-2);
		right = new Rectangle(1, 2, 1, sprite.getHeight()-3);
		top = new Rectangle(0, sprite.getHeight() -1, sprite.getWidth(), 1);
		
		this.setPosition(0, 0);
		velocityY = 0;
		
		dir = false;
		speedPressed = false;
		fLives = 2;
		lives = fLives;
		coinsCollected = 0;
		score = 0;
		fatigueLevel = 0;
		intFatigue = 0;
		starOn = false;
		spriteReload = false;
		isStanding = false;
		isDucking = false;
		starTime = 0;
		colorRotation = 0;
		
	}	
	
	//for collision detection of the screen edges and bricks
	public int hits(Rectangle r){
		if(bottom.overlaps(r)){
			return 1;
		}
		if(left.overlaps(r)){
			return 2;
		}
		if(right.overlaps(r)){
			return 3;
		}
		if(top.overlaps(r)){
			return 4;
		}		

		return -1;
	}
	
	//what happens when we interact with other objects
	public void action(int type, float x, float y){
		if(type == 1 || type == 4){
			velocityY = 0;
			setPosition(bottom.x, y);
		}
		
		if(type == 2 || type == 3){
			velocityY = 0;
			setPosition(x, bottom.y);
		}
	}
	
	//To update actions
	public void update(float delta){
		velocityY -= 50 * delta;
		top.y += velocityY;
		/*left.y += velocityY;
		right.y += velocityY;
		full.y += velocityY;*/
		bottom.y += velocityY;
		
		sprite.setPosition(bottom.x, bottom.y);
	}
	
	//position of sprite
	public void setPosition(float x, float y){
		full.x = x;
		full.y = y;
		
		left.x = x+sprite.getWidth()-1;
		left.y = y ;
		
		right.x = x+1;
		right.y = y+2;
		
		top.x = x;
		top.y = y+sprite.getHeight() -1;
		
		bottom.x = x;
		bottom.y = y;
		
		sprite.setPosition(x, y);
	}
	
	public void moveLeft(float delta){
		if(speedPressed){
			bottom.x -=(175 * delta);
		}else{
			bottom.x -=(100 * delta);
		}
		
		sprite.setPosition(bottom.x, bottom.y);
		//sprite.setPosition(bottom.x - (100 * delta), bottom.y);
		if(!dir){
			dir = true;
			sprite.flip(true, false);
		}
	}
	
	public void moveRight(float delta){
		if(speedPressed){
			bottom.x +=(175 * delta);
		}else{
			bottom.x +=(100 * delta);
		}
		sprite.setPosition(bottom.x, bottom.y);
			
		//sprite.setPosition(bottom.x + (100 * delta), bottom.y);
		if(dir){
			dir = false;
			sprite.flip(true, false);
		}
	}
	
	public void jump(){
		if(velocityY == 0){
			velocityY = 12;	
		}
	}
	
	//To draw objects
	public void draw(SpriteBatch batch){
		if(starOn){
			if (starTime > starLength){
				starTime = 0;
				starOn = false;
			}else{
				starTime += Gdx.graphics.getDeltaTime() ;
			}
			switch(colorRotation){
				case 0:	
					sprite.setColor(Color.CYAN);
					colorRotation++;
					break;
				case 1:	
					sprite.setColor(Color.WHITE);
					colorRotation++;
					break;
				case 2:	
					sprite.setColor(Color.YELLOW);
					colorRotation++;
					break;
				case 3:	
					sprite.setColor(Color.GREEN);
					colorRotation++;
					break;
				case 4:	
					sprite.setColor(Color.ORANGE);
					colorRotation=0;
					break;
			}
			sprite.draw(batch);
		}else{
			if(spriteReload == true){
				spriteReload = false;
				if(isStanding){
					sprite.setTexture(TextureManager.mario_big_walking_1);
				}else if(isDucking){
					sprite.setTexture(TextureManager.mario_duck);
				}
			}
			sprite.draw(batch);
		}
	}

	@Override
	public Rectangle getBox() {
		// TODO Auto-generated method stub
		return full;
	}

	@Override
	public int hitAction(int side) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getLives(){
		
		return lives;
	}
	
	public void setLives(int num){
		lives = num;
	}
	
	public void lostLife(){
		score -= 50;
		lives--;

	}
	
	public void gotCoin(){
		coinsCollected += 1;
		score += 100;
	}
	public void reset(){
		lives = fLives;
		coinsCollected = 0;
		score = 0;
		fatigueLevel = 0;
		intFatigue = 0;
	}
	public void bulletWin(){
		score +=200;
	}
	public int getScore(){
		return score;
	}

	@Override
	public TextureRegion currentFrame() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void marioDuck(){

		sprite.setTexture(TextureManager.mario_duck);
		sprite.setSize(14, 18);
		
		top.set(sprite.getX(), sprite.getY()+sprite.getHeight()-5, sprite.getWidth(), 1);
		right.set(sprite.getX()+sprite.getWidth()-1,sprite.getY(), 1, sprite.getHeight()-5);
		left.set(sprite.getX(), sprite.getY(), 1,  sprite.getHeight()-5);
		bottom.set(sprite.getX(), sprite.getY(),sprite.getWidth(), 1);
		full.set(sprite.getX(), sprite.getY(),sprite.getWidth(),sprite.getHeight());
		isStanding = false;
		isDucking = true;


	}
	public void marioStand(){

		sprite = new Sprite(TextureManager.mario_big_walking_1); //(texture, 0,0,128, 128);
		sprite.setSize(16, 29);
		this.setPosition(bottom.getX(), bottom.getY());	
		if(dir){
			sprite.flip(true, false);
		}
		isStanding = true;
		isDucking = false;
	}

	@Override
	public float getTimer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTimer(float newTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public float getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resetPosition() {
		// TODO Auto-generated method stub
		
	}
	
	public void gotStar(){
		starOn = true;
		spriteReload = true;
	}
	public Boolean getStar(){
		return starOn;
	}
}