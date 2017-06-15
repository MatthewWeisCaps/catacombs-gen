package com.mweis.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.mweis.game.box2d.Box2dSteeringUtils;
import com.mweis.game.util.Constants;
import com.mweis.game.view.ScreenManager;
import com.mweis.game.view.screens.GameScreen;

public class Game implements ApplicationListener {
	
	private float accumulator = 0.0f;
	
	@Override
	public void create() {
		Gdx.graphics.setTitle("Game");
		ScreenManager.setScreen(new GameScreen());
		
		// coord systems display
//		System.out.println("====");
//		Vector2 v1 = new Vector2(1.0f, 0.0f);
//		Vector2 v2 = new Vector2(1.0f, 1.0f);
//		Vector2 v3 = new Vector2(0.0f, 1.0f);
//		Vector2 v4 = new Vector2(-1.0f, 1.0f);
//		Vector2 v5 = new Vector2(-1.0f, 0.0f);
//		Vector2 v6 = new Vector2(-1.0f, -1.0f);
//		Vector2 v7 = new Vector2(0.0f, -1.0f);
//		Vector2 v8 = new Vector2(1.0f, -1.0f);
//		
//		System.out.println(Math.toDegrees(v1.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v1)));
//		System.out.println(Math.toDegrees(v2.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v2)));
//		System.out.println(Math.toDegrees(v3.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v3)));
//		System.out.println(Math.toDegrees(v4.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v4)));
//		System.out.println(Math.toDegrees(v5.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v5)));
//		System.out.println(Math.toDegrees(v6.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v6)));
//		System.out.println(Math.toDegrees(v7.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v7)));
//		System.out.println(Math.toDegrees(v8.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v8)));
////		System.out.println(Math.toDegrees(v9.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v9)));
////		System.out.println(Math.toDegrees(v10.angleRad()) + ", " + Math.toDegrees(Box2dSteeringUtils.vectorToAngle(v10)));
	}

	@Override
	public void resize(int width, int height) {
		ScreenManager.getCurrentScreen().resize(width, height);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float deltaTime = Gdx.graphics.getDeltaTime(); // this is the "real" dt, used to allow constant DT steps
		
		if (deltaTime > 0.25f) { deltaTime = 0.25f; }
		
		accumulator += deltaTime;
		
		while (accumulator >= Constants.DELTA_TIME) {
			GdxAI.getTimepiece().update(Constants.DELTA_TIME);
			ScreenManager.getCurrentScreen().update();
			MessageManager.getInstance().update();
			accumulator -= Constants.DELTA_TIME;
		}
		
		ScreenManager.getCurrentScreen().render();
	}

	@Override
	public void pause() {
		ScreenManager.getCurrentScreen().pause();
	} 

	@Override
	public void resume() {
		ScreenManager.getCurrentScreen().resume();
	}

	@Override
	public void dispose() {
		// NOTE: ScreenManager handles disposal of screens on change. 
		// This only exists for the final screen to close (Graphic resource dump on close)
		ScreenManager.getCurrentScreen().dispose();
	}
}
