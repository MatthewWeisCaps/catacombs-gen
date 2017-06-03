package com.mweis.game.view;

import com.badlogic.gdx.InputProcessor;

public interface Screen extends InputProcessor {

	public void show();

	public void render();
	
	public void update();
	
	public void resize(int width, int height);

	public void pause();

	public void resume();

	public void hide();

	public void dispose();
}
