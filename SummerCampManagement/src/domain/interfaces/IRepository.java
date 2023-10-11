package domain.interfaces;

import java.util.List;

public interface IRepository<T> {
	public T find(int identifier);
	public T findActivity(String activityName);
	public void save(T obj);
	public void saveActivity(String activityName);

	public List<T> getAll();
}
