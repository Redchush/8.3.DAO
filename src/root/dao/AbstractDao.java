package root.dao;

import root.dao.exception.DaoException;
import root.model.Entity;

import java.sql.Connection;
import java.util.List;


public interface AbstractDao<T extends Entity> {

    List<T> findAll() throws DaoException;

    T findEntityById(int id) throws DaoException;

    boolean delete(int id) throws DaoException;

    boolean delete(T entity) throws DaoException;

    boolean create(T entity) throws DaoException;

    T update(T entity) throws DaoException;

    void close(Connection connection) throws DaoException;
}
