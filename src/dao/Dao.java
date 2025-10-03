package dao;

import model.exceptions.DatabaseException;

import java.sql.SQLException;
import java.util.List;


public interface Dao<T> {
    T get(int id) throws SQLException;
    List<T> getAll() throws SQLException;
    T insert(T t) throws SQLException;
    T update(T t) throws SQLException;
    boolean delete(T t) throws SQLException;
}