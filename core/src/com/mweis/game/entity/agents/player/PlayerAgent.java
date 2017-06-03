package com.mweis.game.entity.agents.player;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.physics.box2d.Body;
import com.mweis.game.entity.Agent;

public class PlayerAgent extends Agent<PlayerAgent, PlayerState> {
	
	public PlayerAgent(Body body) {
		super.setBody(body);
		super.setStateMachine(new DefaultStateMachine<PlayerAgent, PlayerState>(this));
		super.getStateMachine().changeState(PlayerState.DEFAULT);
	}
	
}
