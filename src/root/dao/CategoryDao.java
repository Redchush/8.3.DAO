package root.dao;

import root.dao.exception.DaoException;
import root.model.Category;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface CategoryDao extends AbstractDao<Category> {
    @Override
    List<Category> findAll() throws DaoException;

    @Override
    Category findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id) throws DaoException;

    @Override
    boolean delete(Category entity) throws DaoException;

    @Override
    boolean create(Category entity) throws DaoException;

    @Override
    Category update(Category entity) throws DaoException;
}
