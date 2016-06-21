package root.dao;

import root.dao.exception.DaoException;
import root.model.Tag;

import java.util.List;

/**
 * Created by user on 21.06.2016.
 */
public interface TagDao extends AbstractDao<Tag> {
    @Override
    List<Tag> findAll() throws DaoException;

    @Override
    Tag findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id)throws DaoException;;

    @Override
    boolean delete(Tag entity)throws DaoException;;

    @Override
    boolean create(Tag entity)throws DaoException;;

    @Override
    Tag update(Tag entity)throws DaoException;;
}
