package root.dao;

import root.dao.exception.DaoException;
import root.model.Role;

import java.util.List;


public interface RoleDao extends AbstractDao<Role> {
    @Override
    List<Role> findAll() throws DaoException;

    @Override
    Role findEntityById(int id) throws DaoException;

    @Override
    boolean delete(int id)throws DaoException;;

    @Override
    boolean delete(Role entity)throws DaoException;;

    @Override
    boolean create(Role entity)throws DaoException;;

    @Override
    Role update(Role entity)throws DaoException;;
}
