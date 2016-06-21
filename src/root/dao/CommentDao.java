package root.dao;

import root.dao.exception.DaoException;
import root.model.Comment;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface CommentDao extends AbstractDao<Comment> {
    @Override
    List<Comment> findAll() throws DaoException;

    @Override
    Comment findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id) throws DaoException;

    @Override
    boolean delete(Comment entity) throws DaoException;

    @Override
    boolean create(Comment entity) throws DaoException;

    @Override
    Comment update(Comment entity) throws DaoException;
}
