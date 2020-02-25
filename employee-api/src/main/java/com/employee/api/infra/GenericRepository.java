package com.employee.api.infra;

import java.util.Optional;
import java.util.Set;

import org.bson.Document;

import com.employee.api.util.Page;
import com.employee.api.util.Pageable;

public interface GenericRepository<K, V> {

	public Optional<K> getById(V v, Class<K> clazz);

	public Page<K> getByIds(Pageable pageable, Set<V> v, Class<K> clazz);

	public Page<K> getByQuery(Pageable pageable, Document query, Class<K> clazz);

	public Optional<K> save(K entity, Class<K> clazz);

	public Boolean delete(V v, Class<K> clazz);

}
