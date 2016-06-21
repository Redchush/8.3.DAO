package root.dao.mysql.impl;

import root.dao.CategoryDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Category;
import root.model.Role;
import root.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
        return null;
    }

    @Override
    protected Category createSimpleEntity(ResultSet set) throws DaoException {
        Category category = createEntityList(set).get(0);
        return category;
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
}
