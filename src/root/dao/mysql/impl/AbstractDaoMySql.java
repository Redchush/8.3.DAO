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

    public AbstractDaoMySql() {}

    public AbstractDaoMySql(Connection connection) {
        if (connection != null) {
            this.connection = connection;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) throws DaoException {
        if (connection != null){
            this.close(this.connection);
        }
        this.connection = connection;
    }

    @Override
    public List<T> findAll() throws DaoException {
        String query = QueryMaker.getSelectAll(this);
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            set = statement.executeQuery();
            return createEntityList(set);
        } catch (SQLException e) {
            throw new DaoException("Can't execute findAll with dao "
                    + this.getClass() + "  by query " + query
                    + "\nwith filled statement: \n"  + statement  + " state :" + e.getSQLState(), e);
        } finally {
            close(set);
            close(statement);
        }
    }

    @Override
    public T findEntityById(int id) throws DaoException {
        String query = QueryMaker.getSelectById(this);
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            set = statement.executeQuery();
            return createSimpleEntity(set);
        } catch (SQLException e) {
            throw new DaoException("Can't execute findEntityById by initial query: \n" + query
                    + "\nwith filled statement: \n" + statement +
                    "\nand entity id: " + id  + " state :" + e.getSQLState(), e);
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
                               : QueryMaker.getDeleteById(this);
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int state = statement.executeUpdate();
            connection.commit();
            return state == 1;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                throw new DaoException("Can't execute rollback from delete by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity  id \n " + id  + " state :" + e.getSQLState(), e);
            }
            throw new DaoException("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    "\n and entity id: " + id  + " state :" + e.getSQLState(), e);
        } finally {
            close(set);
            close(statement);
        }
    }

    @Override
    public boolean delete(T entity) throws DaoException {
       try{
           int id = entity.getId();
           return delete(id);
       } catch (DaoException e) {
           throw new DaoException("Can't execute delete by entity " + entity , e);
       }
    }

    @Override
    public T update(T entity) throws DaoException{
        String query = QueryMaker.getUpdate(this);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            fillStatementWithFullAttributesSet(statement, entity, 1);
            fillLastParameterWithId(statement, entity);
            int state = statement.executeUpdate();
            connection.commit();
            return entity;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                throw new DaoException("Can't execute rollback from update by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity +  " state :" + e.getSQLState(), e);
            }
            throw new DaoException("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n "  + entity + " state :" + e.getSQLState(), e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean create(T entity) throws DaoException{
        String query = QueryMaker.getCreate(this);
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            fillStatementWithFullAttributesSet(statement, entity, 1);
            int state = statement.executeUpdate();
            connection.commit();
            return state == 0;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                throw new DaoException("Can't execute rollback from create by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity  + " state :" + e.getSQLState(), e);
            }
            throw new DaoException("Can't execute create by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n " + entity  + " state :" + e.getSQLState(), e);
        } finally {
            close(statement);
        }
    }

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
            entity = entities.get(0);
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

    protected abstract void fillStatementWithFullAttributesSet(PreparedStatement statement,
                                                               T entity, int from)
            throws SQLException;
}
// state :23000 not unique