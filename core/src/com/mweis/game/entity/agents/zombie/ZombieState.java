package com.mweis.game.entity.agents.zombie;

import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum ZombieState implements State<ZombieAgent> {
	/*
	 * Stand still and poll for new enemies / await a message.
	 */
	IDLE() {
		@Override
		public void enter(ZombieAgent entity) {
			entity.getBody().setLinearVelocity(0, 0);
			entity.getBody().setAngularVelocity(0.0f);
			entity.getBody().setAwake(false);
		}

		@Override
		public void update(ZombieAgent entity) {
			// this needs to be off a conical fixture
			if (entity.vision.contains(entity.player.getBody().getPosition().x, entity.player.getBody().getPosition().y)) {
				System.out.println("coll");
				entity.seek.setTarget(entity.player);
				entity.getStateMachine().changeState(ZombieState.SEEK);
				// TODO: send message to nearby zombies
			}
		}

		@Override
		public void exit(ZombieAgent entity) {
			
		}

		@Override
		public boolean onMessage(ZombieAgent entity, Telegram telegram) {
			return false;
		}
	},
	/*
	 * Seek the current target
	 */
	SEEK() {
		@Override
		public void enter(ZombieAgent entity) {
			entity.seek.setEnabled(true);
			entity.steering.setSteeringBehavior(entity.seek);
		}

		@Override
		public void update(ZombieAgent entity) {
			entity.steering.update(GdxAI.getTimepiece().getDeltaTime());
		}

		@Override
		public void exit(ZombieAgent entity) {
			entity.seek.setEnabled(false);
		}

		@Override
		public boolean onMessage(ZombieAgent entity, Telegram telegram) {
			return false;
		}
	},
	/*
	 * 
	 */
	ATTACK() {
		@Override
		public void enter(ZombieAgent entity) {
			
		}

		@Override
		public void update(ZombieAgent entity) {
			
		}

		@Override
		public void exit(ZombieAgent entity) {
			
		}

		@Override
		public boolean onMessage(ZombieAgent entity, Telegram telegram) {
			return false;
		}
	}
}
