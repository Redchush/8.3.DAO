package root.dao.mysql.impl;

import commonUtil.ClassName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.dao.mysql.criteria.Criteria;
import root.dao.mysql.criteria.CriteriaMySql;
import root.dao.mysql.util.QueryMaker;
import root.model.Entity;

import java.sql.*;
import java.util.List;


public abstract class AbstractDaoMySql<T extends Entity> implements AbstractDao<T> {

    private static Logger logger = LogManager.getLogger(ClassName.getClassName());

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
        String query = QueryMaker.getSelectAll(this.getGenericType());
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            set = statement.executeQuery();
            logger.debug(this.getClass() + " successfully executed query\n " + query
                    + "\n.The result see in method createEntityList.");
            return createEntityList(set);
        } catch (SQLException e) {
            logger.info("Can't execute findAll with dao "
                    + this.getClass() + "  by query: \n" + query
                    + "\nwith filled statement: \n"  + statement  + "\n state :" + e.getSQLState(), e);
            throw new DaoException(this.getClass().getSimpleName() + " : error during executing findAll", e);
        } finally {
            close(set);
            close(statement);
        }
    }

    @Override
    public T findEntityById(int id) throws DaoException {
        String query = QueryMaker.getSelectById(this.getGenericType());
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            set = statement.executeQuery();
            return createSimpleEntity(set);
        } catch (SQLException e) {
            logger.info("Can't execute findEntityById by initial query: \n" + query
                    + "\nwith filled statement: \n" + statement +
                    "\nand entity id: " + id  + " state :" + e.getSQLState(), e);
            throw new DaoException(this.getClass() + " : error during executing findEntityById", e);
        } finally {
            close(set);
            close(statement);
        }
    }

    @Override
    public boolean delete(int id) throws DaoException {
        boolean isThisBannable = QueryMaker.isClassMaintainInterface(this, Bannable.class);
        String query;
        query = isThisBannable ? QueryMaker.getDeleteByBan(this.getGenericType())
                               : QueryMaker.getDeleteById(this.getGenericType());

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
                logger.error("Can't execute rollback from delete by initial query: \n" + query
                        + "\nwith filled statement: \n" + statement +
                        "\n and entity  id \n " + id  + " state :" + e.getSQLState(), e);
                throw new DaoException(this.getClass() + " error in rollback after delete by id");
            }
            logger.info("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    "\n and entity id: " + id  + " state :" + e.getSQLState(), e);
            throw new DaoException(this.getClass() + " can't delete by id");
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
           logger.info( this.getClass() + "can't execute delete by entity " + entity + ". See msg from " +
                   "delete(id) to find more");
           throw new DaoException("Can't execute delete by entity ", e);
       }
    }

    @Override
    public T update(T entity) throws DaoException{
        String query = QueryMaker.getUpdate(this.getGenericType());
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            fillStatementWithFullAttributesSet(statement, entity, 1);
            fillLastParameterWithId(statement, entity);
            int state = statement.executeUpdate();
            connection.commit();

            logger.debug(this.getClass() + " successfully executed query " + query
                    + ".The result is " + entity);

            return entity;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                logger.error("Can't execute rollback from update by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity +  " state :" + e.getSQLState(), e);
                throw new DaoException(this.getClass() + " error in rollback after update by entity", e);
            }

            logger.info("Can't execute update by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n "  + entity + " state :" + e.getSQLState(), e);
            throw new DaoException(this.getClass() + " error in rollback after update by entity", e);

        } finally {
            close(statement);
        }
    }

    @Override
    public boolean create(T entity) throws DaoException{
        String query = QueryMaker.getCreate(this.getGenericType());
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            fillStatementWithFullAttributesSet(statement, entity, 1);
            int state = statement.executeUpdate();
            connection.commit();

            logger.debug(this.getClass() + " successfully executed query " + query
                    + ".The result is " + entity);

            return state == 0;
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                logger.error("Can't execute rollback from create by initial query: \n" + query
                        + "with filled statement: \n" + statement +
                        " and entity \n " + entity  + " state :" + e.getSQLState(), e);

                throw new DaoException(this.getClass() + " error in rollback after create by id", e);
            }
            logger.info("Can't execute create by initial query: \n" + query
                    + "with filled statement: \n" + statement +
                    " and entity \n " + entity  + " state :" + e.getSQLState(), e);

            throw new DaoException(this.getClass() + " error in create by id", e);
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
            logger.warn("Can't close the connection " + connection);
            throw new DaoException("Can't close the connection " + connection);
        }
    }

    public Criteria createCreteria(){
        return new CriteriaMySql();
    }

    protected abstract Class<T> getGenericType();


    protected T createSimpleEntity(ResultSet set) throws SQLException {
        List<T> entities =  createEntityList(set);
        T entity = null;
        if (entities.size() == 1) {
            entity = entities.get(0);
        }
        logger.debug("Successfully create entity " + entity);
        return entity;
    }

    protected  abstract List<T> createEntityList(ResultSet set) throws SQLException;

    protected void close(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.warn(this.getClass() +  " : сan't close the statement " + statement, e);
            throw new DaoException(this.getClass() +  ": Can't close the statement", e);
        }
    }

    protected void close(ResultSet set) throws DaoException {
        try {
            if (set != null) {
                set.close();
            }
        } catch (SQLException e) {
            logger.warn(this.getClass() +  " : сan't close the ResultSet " + set, e);
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