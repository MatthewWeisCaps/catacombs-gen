package com.mweis.game.util;

import com.badlogic.ashley.core.ComponentMapper;
import com.mweis.game.entity.component.AgentComponent;

public final class Mappers {
	@SuppressWarnings("rawtypes")
	public static final ComponentMapper<AgentComponent> agentMapper = ComponentMapper.getFor(AgentComponent.class);
	
	private Mappers() { };
}
