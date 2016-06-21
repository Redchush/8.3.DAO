package root.dao.mysql.impl;

import root.dao.AnswerDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Answer;
import root.model.Comment;
import root.model.Post;
import root.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class AnswerDaoMySql extends AbstractDaoMySql<Answer> implements AnswerDao, Bannable {

    public AnswerDaoMySql() {}

    public AnswerDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<Answer> findAll() throws DaoException {
        return super.findAll();
    }

    @Override
    public Answer findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(Answer entity)  throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(Answer entity) throws DaoException {
        return false;
    }

    @Override
    public Answer update(Answer entity) throws DaoException {
        return null;
    }

    @Override
    protected Answer createSimpleEntity(ResultSet set) throws DaoException {
        return null;
    }

 /*    public Answer(int id, User author, Post parent, String content, Timestamp createdDate,
 *        Timestamp updatedDate, boolean banned)
 */
    @Override
    protected List<Answer> createEntityList(ResultSet set) throws DaoException {
        List<Answer> entities = new ArrayList<>();
        Answer entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                int parentUserId = set.getInt("user_id");
                User parentUser = new User(parentUserId);

                int postId = set.getInt("post_id");
                Post parentPost = new Post(postId);

                String content = set.getString("message");
                Timestamp createdDate = set.getTimestamp("created_date");
                Timestamp updatedDate = set.getTimestamp("updated_date");
                boolean isBanned = set.getBoolean("banned");

                entity = new Answer(id, parentUser, parentPost, content, createdDate, updatedDate, isBanned);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a category list ", e);
        }
        return entities;


    }
}

//        answers.1 = id
//        answers.2 = post_id
//        answers.3 = user_id
//        answers.4 = message
//        answers.5 = created_date
//        answers.6 = updated_date
//        answers.7 = banned
