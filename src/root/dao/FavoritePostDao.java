package root.dao;

import root.dao.exception.DaoException;
import root.model.FavoritePost;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface FavoritePostDao extends AbstractDao<FavoritePost> {
    @Override
    List<FavoritePost> findAll() throws DaoException;

    @Override
    FavoritePost findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id) throws DaoException;

    @Override
    boolean delete(FavoritePost entity) throws DaoException;

    @Override
    boolean create(FavoritePost entity) throws DaoException;

    @Override
    FavoritePost update(FavoritePost entity) throws DaoException;
}
