package root.dao.mysql.impl;

import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.dao.mysql.util.QueryMaker;
import root.dao.mysql.util.ReflectionUtils;
import root.model.Entity;

import java.sql.*;
import java.util.List;


public abstract class AbstractDaoMySql<T extends Entity> implements AbstractDao<T> {
    protected Connection connection;

    protected AbstractDaoMySql() {}

    public AbstractDaoMySql(Connection connection) {
        if (connection != null) {
            this.connection = connection;
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = QueryMaker.getSelectQueryAll(this);
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            set = statement.executeQuery();
            return createEntityList(set);
        } catch (SQLException e) {
            throw new DaoException("Can't execute findAll by" + query);
        } finally {
            close(set);
            close(statement);
        }

    }

    @Override
    public T findEntityById(int id) throws DaoException {
        String query = QueryMaker.getSelectQueryById(this);
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            set = statement.executeQuery();
            return createSimpleEntity(set);
        } catch (SQLException e) {
            throw new DaoException("Can't execute findEntityById " + query);
        } finally {
            close(set);
            close(statement);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean isThisBannable = ReflectionUtils.isClassMaintainInterface(this, Bannable.class);
        String query;
        query = isThisBannable ? QueryMaker.getDeleteByBan(this)
                               : QueryMaker.getDeleteQueryById(this);
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException("Can't execute delete entity by " + query);
        } finally {
            close(set);
            close(statement);
        }
    }

    @Override
    public boolean delete(T entity) throws DaoException {
        int id = entity.getId();
        return delete(id);
    }

    @Override
    public abstract boolean create(T entity) throws DaoException;

    @Override
    public abstract T update(T entity) throws DaoException;

    @Override
    public void close(Connection connection) throws DaoException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Can't close the connection " + connection);
        }
    }

    protected abstract T createSimpleEntity(ResultSet set) throws DaoException;

    protected abstract List<T> createEntityList(ResultSet set) throws DaoException;

    protected void close(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Can't close the statement " + statement);
        }
    }

    protected void close(ResultSet set) throws DaoException {
        try {
            if (set != null) {
                set.close();
            }
        } catch (SQLException e) {
            throw new DaoException("Can't close the ResultSet " + set);
        }
    }

}
