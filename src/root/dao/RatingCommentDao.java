package root.dao;

import root.dao.exception.DaoException;
import root.model.RatingComment;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface RatingCommentDao {
    List<RatingComment> findAll() throws DaoException;

    RatingComment findEntityById(int id) throws DaoException;

    boolean delete(int id) throws DaoException;

    boolean delete(RatingComment entity) throws DaoException;

    boolean create(RatingComment entity) throws DaoException;

    RatingComment update(RatingComment entity) throws DaoException;
}
