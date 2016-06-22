package root.dao.mysql.impl;

import root.dao.CategoryDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CategoryDaoMySql extends AbstractDaoMySql<Category>
        implements CategoryDao, Bannable {

    public CategoryDaoMySql() {
    }

    public CategoryDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<Category> findAll() throws DaoException {
        return super.findAll();
    }

    @Override
    public Category findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException{
        return super.delete(id);
    }

    @Override
    public boolean delete(Category entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(Category entity) throws DaoException {
        return false;
    }

    @Override
    public Category update(Category entity) throws DaoException {
        return super.update(entity);
    }


    /*
    *   use constuctor  public Category(int id, String title, String description,
    *                Timestamp createdDate, Category parent,
    *                boolean published)
    */
    @Override
    protected List<Category> createEntityList(ResultSet set) throws DaoException {
        List<Category> entities = new ArrayList<>();
        Category entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                String title = set.getString("title");
                Timestamp createdDate = set.getTimestamp("created_date");
                String description = set.getString("description");

                int parentId = set.getInt("parent_category");
                Category parent = new Category(parentId);
                boolean isPublished = set.getBoolean("published");
                entity = new Category(id, title, description, createdDate, parent, isPublished);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a category list ", e);
        }
        return entities;
    }

    @Override
    protected Category updateDbRecord(Category entity, String query) throws DaoException {
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            String title = entity.getTitle();
            statement.setString(1, title);

            Timestamp created_date = entity.getCreatedDate();
            statement.setTimestamp(2, created_date);

            String description = entity.getDescription();
            statement.setString(3, description);

            int categoryId = entity.getParent().getId();
            statement.setInt(4, categoryId);

            boolean published = entity.isPublished();
            statement.setBoolean(5, published);

            set = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Can't execute update by query " + query + " and entitry " + entity);
        } finally {
            close(statement);
        }
        return entity;
    }
}
//  categories.num = 6;
//  categories.1 = id
//  categories.2 = title - 1
//  categories.3 = created_date - 2
//  categories.4 = description - 3
//  categories.5 = parent_category - 4
//  categories.6 = published - 5