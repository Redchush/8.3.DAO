package root.dao.mysql.impl;

import root.connection_pool.ConnectionPool;
import root.connection_pool.exception.ConnectionPoolException;
import root.dao.AbstractDao;
import root.dao.FavoritePostDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.dao.mysql.util.QueryMaker;
import root.model.FavoritePost;
import root.model.Post;
import root.model.Tag;
import root.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FavoritePostDaoMySql extends AbstractDaoMySql<FavoritePost> implements FavoritePostDao {

    public FavoritePostDaoMySql() {}

    public FavoritePostDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<FavoritePost> findAll() throws DaoException {
       return super.findAll();
    }

    @Override
    public FavoritePost findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(FavoritePost entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(FavoritePost entity) throws DaoException {
        return false;
    }

    @Override
    public FavoritePost update(FavoritePost entity) throws DaoException {
        return null;
    }

    @Override
    protected FavoritePost createSimpleEntity(ResultSet set) throws DaoException {
         FavoritePost post = createEntityList(set).get(0);
         return post;
    }

    @Override
    protected List<FavoritePost> createEntityList(ResultSet set) throws DaoException {
        List<FavoritePost> entities = new ArrayList<>();
        FavoritePost entity;
        try {
            while (set.next()) {
                int id = set.getInt("id");
                int userId = set.getInt("user_id");
                int postId = set.getInt("post_id");
                String comment = set.getString("comment");
                entity = new FavoritePost(id, new User(userId), new Post(postId), comment);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a tag list", e);
        }
        return entities;
    }
}
//favorite_users_posts.num = 3
//        favorite_users_posts.1 = user_id
//        favorite_users_posts.2 = user_id
//        favorite_users_posts.3 = post_id
//        favorite_users_posts.4 = comment