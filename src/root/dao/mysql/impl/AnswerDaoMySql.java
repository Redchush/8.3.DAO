package root.dao.mysql.impl;

import root.dao.AnswerDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Answer;
import root.model.Post;
import root.model.User;

import java.sql.*;
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
         return super.update(entity);
    }


 /*
 * use constructor public Answer(int id, User author, Post parent, String content, Timestamp createdDate,
 *                   Timestamp updatedDate, boolean banned)
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

    @Override
    protected Answer updateDbRecord(Answer entity, String query) throws DaoException {
        ResultSet set = null;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);

            int post_id = entity.getParent().getId();
            statement.setInt(1, post_id);
            int user_id = entity.getAuthor().getId();
            statement.setInt(2, user_id);

            String message = entity.getContent();
            statement.setString(3, message);
            Timestamp created_date = entity.getCreatedDate();
            statement.setTimestamp(4, created_date);

            Timestamp updated_date = entity.getUpdatedDate();
            statement.setTimestamp(5, updated_date);

            boolean isBanned = entity.isBanned();
            statement.setBoolean(6, isBanned);

            set = statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Can't execute update by query " + query + " and entitry " + entity);
        } finally {
            close(statement);
        }
        return entity;
    }
}

//
//         answers.1 = id
//        answers.2 = post_id - >1
//        answers.3 = user_id -> 2
//        answers.4 = message -> 3
//        answers.5 = created_date ->4
//        answers.6 = updated_date -> 5
//        answers.7 = banned - > 6
