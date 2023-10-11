package domain.interfaces;

import java.util.List;

public interface IRepository<T, I> {
	public T find(I identifier);
	public void save(T obj);
	public void delete(T obj);
	public List<T> getAll();
}
