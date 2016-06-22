package root.dao.mysql.impl;

import root.dao.CommentDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Answer;
import root.model.Comment;
import root.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CommentDaoMySql extends AbstractDaoMySql<Comment>
        implements CommentDao,Bannable {

    public CommentDaoMySql() {}

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
    public boolean create(Comment entity) throws DaoException {
        return super.create(entity);
    }

    @Override
    public Comment update(Comment entity) throws DaoException {
        return super.update(entity);
    }

    @Override
    protected List<Comment> createEntityList(ResultSet set) throws SQLException {
        List<Comment> entities = new ArrayList<>();
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

            Comment entity = new Comment(id, parentUser, parentAnswer, content, createdDate, updatedDate, isBanned);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    protected void fillStatementWithFullAttributesSet(PreparedStatement statement, Comment entity, int from) throws
                                                                                                    SQLException {

        int answers_id = entity.getParent().getId();
        statement.setInt(1, answers_id);
        int user_id = entity.getAuthor().getId();
        statement.setInt(2, user_id);
        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(3, created_date);
        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(4, updated_date);
        String message = entity.getContent();
        statement.setString(5, message);
        boolean isBanned = entity.isBanned();
        statement.setBoolean(6, isBanned);

    }
}
//comments.num = 7
//        comments.1 = id
//        comments.2 = answers_id -> 1
//        comments.3 = user_id -> 2
//        comments.4 = created_date ->3
//        comments.5 = updated_date -> 4
//        comments.6 = message ->5
//        comments.7 = banned ->6