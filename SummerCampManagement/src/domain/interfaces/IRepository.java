package domain.interfaces;

import java.util.List;

public interface IRepository<T> {
	public T find(int identifier);
	public T findActivity(String identifier);

	public void save(T obj);
	public List<T> getAll();
}
