package app.interfaces;

import java.util.List;

public interface IDBService<T> {
    public void INSERT(T object);
    public void DELETE(long id);
    public void UPDATE(T object);
    public List<T> getAll();
    public T getByID(long id);
}
