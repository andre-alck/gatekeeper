package br.com.asac.gatekeeper.utils.repository;

import java.util.Collection;

public interface CRUDOperations<T> {
	void create(T t);

	Collection<T> read();

	void update(T t);

	void delete(T t);
}
