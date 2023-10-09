## Repositorio en el cual guardamos los datos en un array en memoria
### Utilizamos una interfaz para mas adelante implementar otro tipo de almacenamiento, ya sea una BD
```
public class InMemoryCampRepository implements IRepository<Camp>{

	private Map<Integer, Camp> mapOfCamp;
	
	public InMemoryCampRepository() {
		this.mapOfCamp = new HashMap<Integer, Camp>();
	}
	
	@Override
	public Camp find(int identifier) {
		if (this.mapOfCamp.get(identifier) == null) {
			throw new NotFoundException();
		}
		return this.mapOfCamp.get(identifier);
	}

	@Override
	public void save(Camp obj) {
		this.mapOfCamp.put(obj.getCampID(), obj);
	}
}
```