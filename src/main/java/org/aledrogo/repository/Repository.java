package org.aledrogo.repository;

public abstract class Repository<T> {
    public abstract T getById(int id);
    public abstract T create(T entity);
    public abstract T update(T entity);
    public abstract void delete(T entity);
}
