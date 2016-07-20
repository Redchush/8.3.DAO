package root.dao.mysql.impl;

import root.dao.FavoritePostDao;
import root.dao.exception.DaoException;
import root.model.FavoritePost;
import root.model.Post;
import root.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        return super.create(entity);
    }

    @Override
    public FavoritePost update(FavoritePost entity) throws DaoException {
        return super.update(entity);
    }

    @Override
    protected List<FavoritePost> createEntityList(ResultSet set) throws SQLException {
        List<FavoritePost> entities = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt("id");
            int userId = set.getInt("user_id");
            int postId = set.getInt("post_id");
            String comment = set.getString("comment");
            FavoritePost entity = new FavoritePost(id, new User(userId), new Post(postId), comment);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    protected void fillStatementWithFullAttributesSet(PreparedStatement statement, FavoritePost entity, int from)
            throws SQLException {
        int user_id = entity.getAuthor().getId();
        statement.setInt(1, user_id);
        int post_id = entity.getRelatedPost().getId();
        statement.setInt(2, post_id);
        String comment = entity.getComment();
        statement.setString(3, comment);
    }

    @Override
    protected Class<FavoritePost> getGenericType() {
        return FavoritePost.class;
    }
}
//favorite_users_posts.num = 4
//        favorite_users_posts.1 = id
//        favorite_users_posts.2 = user_id
//        favorite_users_posts.3 = post_id
//        favorite_users_posts.4 = comment
