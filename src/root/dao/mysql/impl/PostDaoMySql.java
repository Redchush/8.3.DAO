package root.dao.mysql.impl;

import root.dao.PostDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Category;
import root.model.Post;
import root.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class PostDaoMySql extends AbstractDaoMySql<Post>
        implements PostDao, Bannable {

    public PostDaoMySql() {
    }

    public PostDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<Post> findAll() throws DaoException {
      return super.findAll();
    }

    @Override
    public Post findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(Post entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(Post entity)  {
        return false;
    }

    @Override
    public Post update(Post entity) {
        return null;
    }

    @Override
    protected Post createSimpleEntity(ResultSet set) throws DaoException {
        Post entity = createEntityList(set).get(0);
        return entity;
    }

    /*
    * public Post(int id, User author, Category parent, String title,
    *            String content, Timestamp createdDate, Timestamp updatedDate, boolean banned)
    */
    @Override
    protected List<Post> createEntityList(ResultSet set) throws DaoException {
        List<Post> entities = new ArrayList<>();
        Post entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                int parentUserId = set.getInt("user_id");
                User parentUser = new User(parentUserId);

                int parentCategoryId = set.getInt("category_id");
                Category parentCategory = new Category(parentCategoryId);

                String title = set.getString("title");
                String content = set.getString("content");
                Timestamp createdDate = set.getTimestamp("created_date");
                Timestamp updatedDate = set.getTimestamp("updated_date");
                boolean isBanned = set.getBoolean("banned");

                entity = new Post(id, parentUser, parentCategory, title, content, createdDate, updatedDate, isBanned);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a category list ", e);
        }
        return entities;
    }
}
