package root.dao;

import root.dao.exception.DaoException;
import root.model.User;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface UserDao extends AbstractDao<User> {
    @Override
    List<User> findAll() throws DaoException;

    @Override
    User findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id) throws DaoException;

    @Override
    boolean delete(User entity) throws DaoException;

    @Override
    boolean create(User entity) throws DaoException;

    @Override
    User update(User entity) throws DaoException;
}
