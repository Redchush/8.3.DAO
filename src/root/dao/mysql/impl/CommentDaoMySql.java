package root.dao.mysql.impl;

import root.dao.CommentDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class CommentDaoMySql extends AbstractDaoMySql<Comment>
        implements CommentDao,Bannable {

    public CommentDaoMySql() {
    }

    public CommentDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<Comment> findAll() throws DaoException {
        return super.findAll();
    }

    @Override
    public Comment findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(Comment entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(Comment entity) {
        return false;
    }

    @Override
    public Comment update(Comment entity) {
        return null;
    }

    @Override
    protected Comment createSimpleEntity(ResultSet set) throws DaoException {
        Comment comment = createEntityList(set).get(0);
        return comment;
    }

    @Override
    protected List<Comment> createEntityList(ResultSet set) throws DaoException {
        List<Comment> entities = new ArrayList<>();
        Comment entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                int parentUserId = set.getInt("user_id");
                User parentUser = new User(parentUserId);

                int parentPostId = set.getInt("post_id");
                Answer parentAnswer = new Answer(parentPostId);

                String content = set.getString("message");
                Timestamp createdDate = set.getTimestamp("created_date");
                Timestamp updatedDate = set.getTimestamp("updated_date");
                boolean isBanned = set.getBoolean("banned");

                entity = new Comment(id, parentUser, parentAnswer, content, createdDate, updatedDate, isBanned);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a comment list ", e);
        }
        return entities;
    }
}
//        comments.num = 6
//        comments.1 = id
//        comments.2 = answers_id
//        comments.3 = user_id
//        comments.4 = created_date
//        comments.5 = message
//        comments.6 = banned