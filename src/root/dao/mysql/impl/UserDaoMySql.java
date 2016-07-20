package root.dao.mysql.impl;


import root.dao.UserDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Role;
import root.model.User;

import java.sql.*;
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
    public boolean create(User entity) throws DaoException {
        return super.create(entity);
    }

    @Override
    public User update(User entity) throws DaoException {
        return super.update(entity);
    }
    /*
        *   use constructor public User(int id, Role role, String login, String password, String email, String lastName,
        *   String firstName, String createdDate, String udatedDate, boolean isBanned)
     */
    @Override
    protected List<User> createEntityList(ResultSet set) throws SQLException {
        List<User> entities = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt(1);
            String login = set.getString("login");
            String password = set.getString("password");
            String email = set.getString("email");
            Role role = new Role(set.getInt("role_id"));
            String lastName = set.getString("last_name");
            String firstName = set.getString("first_name");
            Timestamp createdDate = set.getTimestamp("created_date");
            Timestamp updatedDate = set.getTimestamp("updated_date");
            boolean isBanned = set.getBoolean("banned");
            User entity = new User(id, role, login, password, email, lastName,
                    firstName, createdDate, updatedDate, isBanned);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    protected void fillStatementWithFullAttributesSet(PreparedStatement statement, User entity, int from)
            throws SQLException {

        int role_id = entity.getRole().getId();
        statement.setInt(1, role_id);

        String login = entity.getLogin();
        statement.setString(2, login);
        String password = entity.getPassword();
        statement.setString(3, password);
        String email = entity.getEmail();
        statement.setString(4, email);

        String lastName = entity.getLastName();
        statement.setString(5, lastName);
        String firstName = entity.getFirstName();
        statement.setString(6, firstName);

        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(7, created_date);
        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(8, updated_date);

        boolean isBanned = entity.isBanned();
        statement.setBoolean(9, isBanned);
    }

    @Override
    protected Class<User> getGenericType() {
        return User.class;
    }
}
//         users.num = 10
//        users.1 = id
//        users.2 = role_id
//        users.3 = login
//        users.4 = password
//        users.5 = email
//        users.6= last_name
//        users.7 = first_name
//        users.8 =  created_date
//        users.9 =  updated_date
//        users.10 = banned
