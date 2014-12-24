package edu.TeamH.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
	public static Texture brick;
	public static Texture coin;
	public static Texture mario_big_walking_1;
	public static Texture mario_duck;
	public static Texture flagPole;
	public static Texture spike; 
	public static Texture bush;
	public static Texture cloud;
	public static Texture bullet;
	public static Texture cannon;
	
	public static Texture coinSheet;
	public static Texture mysteryBox;
	public static Texture treeSheet;
	public static Texture starSheet;
	

	public static void create() {
		
		brick = new Texture(Gdx.files.internal("Brick.png"));
		coin = new Texture(Gdx.files.internal("rsz_coin.png"));
		mario_big_walking_1	= new Texture(Gdx.files.internal("mario_big_walking_1.png"));
		mario_duck= new Texture(Gdx.files.internal("mario_duck.png"));
		flagPole = new Texture(Gdx.files.internal("rsz_1flagpole.png"));
		spike = new Texture(Gdx.files.internal("Spike.png")); 
		bush = new Texture(Gdx.files.internal("bush.png"));
		cloud = new Texture(Gdx.files.internal("cloud.png"));
		bullet = new Texture(Gdx.files.internal("bullet_small.png")); 
		cannon = new Texture(Gdx.files.internal("cannon_small.png")); 
		
		coinSheet = new Texture(Gdx.files.internal("coinSheet_small.png"));
		mysteryBox = new Texture(Gdx.files.internal("mbox_packed.png"));
		treeSheet = new Texture(Gdx.files.internal("treeSheet.png"));
		starSheet = new Texture(Gdx.files.internal("star.png"));

	}
	
	public static void dispose() {
		brick.dispose();
		coin.dispose();
		mario_big_walking_1.dispose();
		mario_duck.dispose();
		flagPole.dispose();
		spike.dispose();
		bush.dispose();
		cloud.dispose();
		bullet.dispose();
		cannon.dispose();
		coinSheet.dispose();
		mysteryBox.dispose();
		treeSheet.dispose();
		starSheet.dispose();
	}

}
