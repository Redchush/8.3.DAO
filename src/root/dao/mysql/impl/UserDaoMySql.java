package root.dao.mysql.impl;


import root.dao.UserDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.dao.mysql.util.QueryMaker;
import root.model.Role;
import root.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMySql extends AbstractDaoMySql<User>
        implements UserDao, Bannable {

    public UserDaoMySql() {}

    public UserDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<User> findAll() throws DaoException {
       return super.findAll();
    }

    @Override
    public User findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
         return super.delete(id);
    }

    @Override
    public boolean delete(User entity) throws DaoException {
         return super.delete(entity);
     }

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public User update(User entity) {

        return null;
    }


    /*
    *   use constructor public User(int id, Role role, String login, String password, String email, String lastName,
    *   String firstName, String createdDate, String udatedDate, boolean isBanned)
    */
    @Override
    protected User createSimpleEntity(ResultSet set) throws DaoException {
        User user = createEntityList(set).get(0); ;
        return user;
    }


    @Override
    protected List<User> createEntityList(ResultSet set) throws DaoException {
        List<User> entities = new ArrayList<>();
        User entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                String login = set.getString("login");
                String password = set.getString("password");
                String email = set.getString("email");
                Role role = new Role(set.getInt("role_id"));
                String lastName = set.getString("last_name");
                String firstName = set.getString("first_name");
                Timestamp createdDate = set.getTimestamp("created_date");
                Timestamp udatedDate = set.getTimestamp("updated_date");
                boolean isBanned = set.getBoolean("banned");
                entity = new User(id, role, login, password, email, lastName,
                        firstName, createdDate, udatedDate, isBanned);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a user list", e);
        }
      return entities;
    }
}
