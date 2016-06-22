package root.dao.mysql.impl;

import root.dao.RoleDao;
import root.dao.exception.DaoException;
import root.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class RoleDaoMySql extends AbstractDaoMySql<Role>
        implements RoleDao {

    public RoleDaoMySql() {}

    public RoleDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<Role> findAll() throws DaoException {
        return super.findAll();
    }

    @Override
    public Role findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(Role entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(Role entity) {
        return false;
    }

    @Override
    public Role update(Role entity) throws DaoException {
        return super.update(entity);
    }

    @Override
    protected List<Role> createEntityList(ResultSet set) throws DaoException {
        List<Role> entities = new ArrayList<>();
        Role entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString("name");
                entity = new Role(id, name);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a tag list", e);
        }
        return entities;
    }

    @Override
    protected Role updateDbRecord(Role entity, String query) throws DaoException {
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            String name = entity.getName();
            statement.setString(1, name);
            set = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Can't execute update by query " + query + " and entitry " + entity);
        } finally {
            close(statement);
        }
        return entity;
    }
}

//roles.1 = id
//        roles.2 = name