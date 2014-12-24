package edu.TeamH.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import edu.TeamH.game.CoinManGame;
import edu.TeamH.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "CoinMan";
		config.width = 800;
		config.height = 480; 
		new LwjglApplication(new CoinManGame(), config);
	}
}