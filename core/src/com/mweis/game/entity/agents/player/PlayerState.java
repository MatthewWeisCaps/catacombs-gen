package com.mweis.game.entity.agents.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.mweis.game.util.Constants;

public enum PlayerState implements State<PlayerAgent> {
	DEFAULT() {
		
		@Override
		public void enter(PlayerAgent entity) {
			System.out.println("entered");
		}

		@Override
		public void update(PlayerAgent entity) {
			
			float vx = 0.0f, vy = 0.0f, scale = 80 * 20.0f;
			if (Gdx.input.isKeyPressed(Keys.W)) {
				vy += 1.0f * GdxAI.getTimepiece().getDeltaTime() * scale;
			}
			if (Gdx.input.isKeyPressed(Keys.S)) {
				vy -= 1.0f * GdxAI.getTimepiece().getDeltaTime()* scale;
			}
			if (Gdx.input.isKeyPressed(Keys.A)) {
				vx -= 1.0f * GdxAI.getTimepiece().getDeltaTime()* scale;
			}
			if (Gdx.input.isKeyPressed(Keys.D)) {
				vx += 1.0f * GdxAI.getTimepiece().getDeltaTime()* scale;
			}
			if (vx != 0 && vy != 0) {
				vx /= Constants.SQRT_2;
				vy /= Constants.SQRT_2;
			}
			
			entity.getBody().setLinearVelocity(vx, vy);
		}

		@Override
		public void exit(PlayerAgent entity) {
			
		}

		@Override
		public boolean onMessage(PlayerAgent entity, Telegram telegram) {
			return false;
		}
	};
}
