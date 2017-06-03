package com.mweis.game.view.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mweis.game.box2d.Box2dBodyFactory;
import com.mweis.game.entity.Agent;
import com.mweis.game.entity.agents.player.PlayerAgent;
import com.mweis.game.entity.agents.player.PlayerState;
import com.mweis.game.entity.component.AgentComponent;
import com.mweis.game.entity.systems.AgentSystem;
import com.mweis.game.util.Constants;
import com.mweis.game.view.Screen;

public class GameScreen implements Screen {
	
	private Engine engine;
	private Entity player;
	private Body playerBody; // for quick ref
	
	private Box2DDebugRenderer renderer;
	private OrthographicCamera camera;
	
	private World world;
	
	@Override
	public void show() {
		
		world = new World(Vector2.Zero, true);
		engine = new Engine();
		
		engine.addSystem(new AgentSystem(Constants.DELTA_TIME));
		
		player = new Entity();
		playerBody = Box2dBodyFactory.createDynamicSquare(Vector2.Zero, world);
		
		Agent<PlayerAgent, PlayerState> agent = new PlayerAgent(playerBody);
		AgentComponent<PlayerAgent, PlayerState> ac = new AgentComponent<PlayerAgent, PlayerState>(agent);
		player.add(ac);
		
		engine.addEntity(player);
		
		renderer = new Box2DDebugRenderer();
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 90, 90 * (h / w));
		camera.update();
	}

	@Override
	public void render() {
		
		Vector3 position = camera.position;
		position.interpolate(new Vector3(playerBody.getPosition(), 0.0f), 0.175f, Interpolation.smooth);
		
		camera.position.set(position);
		camera.update();
		
		renderer.render(world, camera.combined);
	}

	@Override
	public void update() {
		engine.update(GdxAI.getTimepiece().getDeltaTime());
		world.step(GdxAI.getTimepiece().getDeltaTime(), 8, 3);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = 90f;
		camera.viewportHeight = 90f * height / width;
		camera.update();
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		world.dispose();
		renderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		camera.zoom += amount * camera.zoom * 0.2;
		return true;
	}
}
