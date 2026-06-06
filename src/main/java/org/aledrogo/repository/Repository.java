package org.aledrogo.repository;

import java.util.ArrayList;

public abstract class Repository<T> {
    public abstract T getById(int id);
    public abstract ArrayList<T> getAll();
    public abstract T create(T entity);
    public abstract T update(T entity);
    public abstract void delete(T entity);
}
