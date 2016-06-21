package root.dao;

import root.dao.exception.DaoException;
import root.model.Rating;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface RatingDao extends AbstractDao<Rating> {

    List<Rating> findAll() throws DaoException;

    Rating findEntityById(int id) throws DaoException;

    boolean delete(int id) throws DaoException;

    boolean delete(Rating entity) throws DaoException;

    boolean create(Rating entity) throws DaoException;

    Rating update(Rating entity) throws DaoException;
}
