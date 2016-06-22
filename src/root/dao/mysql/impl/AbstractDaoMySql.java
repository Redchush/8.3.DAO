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
    public T update(T entity) throws DaoException{
        String query = QueryMaker.getUpdate(this);
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            fillStatementWithFullAttributesSet(statement, entity, 1);
            fillLastParameterWithId(statement, entity);
            set = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Can't execute update by query " + query + " and entitry " + entity);
        } finally {
            close(statement);
        }
        return entity;
    }

    @Override
    public abstract boolean create(T entity) throws DaoException;

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

    protected T createSimpleEntity(ResultSet set) throws SQLException {
        List<T> entities =  createEntityList(set);
        T entity = null;
        if (entities.size() == 1) {
            entity = createEntityList(set).get(0);
        }
        return entity;
    }

    protected  abstract List<T> createEntityList(ResultSet set) throws SQLException;

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

    protected void fillLastParameterWithId(PreparedStatement statement, T entity) throws SQLException {
        ParameterMetaData metaData = statement.getParameterMetaData();
        int attrCount= metaData.getParameterCount();
        int id = entity.getId();
        statement.setInt(attrCount, id);
    }

    protected abstract void fillStatementWithFullAttributesSet(PreparedStatement statement, T entity, int from)
            throws SQLException;




}
