package edu.zipcloud.cloudstreetmarket.core.services;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;

public interface ActionService<T extends Action> {
	T get(Long action);
	T save(T action);
	T create(T action);
	T hydrate(T action);
	void delete(Long actionId);
}
