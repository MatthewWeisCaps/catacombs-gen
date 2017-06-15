package com.mweis.game.entity.agents.zombie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.mweis.game.entity.Agent;

public enum ZombieState implements State<ZombieAgent> {
	/*
	 * Stand still and poll for new enemies / await a message.
	 */
	IDLE() {
		@Override
		public void enter(ZombieAgent entity) {
			entity.getBody().setLinearVelocity(0.0f, 0.0f);
			entity.getBody().setAngularVelocity(0.0f);
			entity.getBody().setAwake(false);
		}

		@Override
		public void update(ZombieAgent entity) {

		}

		@Override
		public void exit(ZombieAgent entity) {
			
		}

		@Override
		public boolean onMessage(ZombieAgent entity, Telegram telegram) {
			if (telegram.message == 0) { // new enemy to chase
				entity.arriveSB.setTarget((Agent)telegram.extraInfo);
				entity.getStateMachine().changeState(ZombieState.SEEK);
				return true;
			}
			return false;
		}
	},
	/*
	 * Seek the current target
	 */
	SEEK() {
		@Override
		public void enter(ZombieAgent entity) {
//			if (entity.seek.getTarget() == null) {
//				entity.getStateMachine().revertToPreviousState();
//			}
			entity.arriveSB.setEnabled(true);
			entity.prioritySB.setEnabled(true);
			entity.steering.setSteeringBehavior(entity.prioritySB);
		}

		@Override
		public void update(ZombieAgent entity) {
			if (entity.prioritySB.getSelectedBehaviorIndex() == 0) {
//				System.out.println("avoiding obsticle");
			}
			entity.steering.update(GdxAI.getTimepiece().getDeltaTime());
		}

		@Override
		public void exit(ZombieAgent entity) {
			entity.seekSB.setEnabled(false);
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
