package domain;

public interface IRepository<T> {
	public T find(int identifier);
	public void save(T obj);
}
