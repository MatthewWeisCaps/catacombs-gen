package com.mweis.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.ai.fsm.State;
import com.mweis.game.entity.Agent;

public class AgentComponent<A extends Agent<A, S>, S extends State<A>> implements Component {
	public Agent<A, S> agent;
	
	public AgentComponent(Agent<A, S> agent) {
		this.agent = agent;
	}
}
