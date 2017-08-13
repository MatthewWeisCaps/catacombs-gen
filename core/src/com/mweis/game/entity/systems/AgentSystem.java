package com.mweis.game.entity.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.mweis.game.entity.Agent;
import com.mweis.game.entity.components.AgentComponent;
import com.mweis.game.util.Mappers;

public class AgentSystem extends IntervalIteratingSystem {
	
	public AgentSystem(float interval) {
		super(Family.all(AgentComponent.class).get(), interval);
	}

	@Override
	protected void processEntity(Entity entity) {
		Mappers.agentMapper.get(entity).agent.getStateMachine().update();
	}
}
