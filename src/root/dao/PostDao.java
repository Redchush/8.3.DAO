package root.dao;

import root.dao.exception.DaoException;
import root.model.Post;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface PostDao extends AbstractDao<Post> {
    @Override
    List<Post> findAll() throws DaoException;

    @Override
    Post findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id) throws DaoException;

    @Override
    boolean delete(Post entity) throws DaoException;

    @Override
    boolean create(Post entity) throws DaoException;

    @Override
    Post update(Post entity) throws DaoException;
}
