package root.dao.mysql.impl;

import root.dao.RatingCommentDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Answer;
import root.model.Rating;
import root.model.RatingComment;
import root.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class RatingCommentDaoMySql extends AbstractDaoMySql<RatingComment>
        implements RatingCommentDao, Bannable {

    public RatingCommentDaoMySql() {
    }

    public RatingCommentDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<RatingComment> findAll() throws DaoException {
        return super.findAll();
    }

    @Override
    public RatingComment findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(RatingComment entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(RatingComment entity) {
        return false;
    }

    @Override
    public RatingComment update(RatingComment entity) {
        return null;
    }

    @Override
    protected RatingComment createSimpleEntity(ResultSet set) throws DaoException {
        RatingComment comment = createEntityList(set).get(0);
        return comment;
    }

    @Override
    protected List<RatingComment> createEntityList(ResultSet set) throws DaoException {
        List<RatingComment> entities = new ArrayList<>();
        RatingComment entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                int ratingId = set.getInt("answers_rating_id");
                Rating rating = new Rating(ratingId);

                String comment = set.getString("comment");
                boolean isBanned = set.getBoolean("banned");
                boolean isPositive = set.getBoolean("type");

                entity = new RatingComment(id, rating, comment, isPositive, isBanned);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a category list ", e);
        }
        return entities;
    }
}
//answer_property.num =4
//        answer_property.1 = id
//        answer_property.2 = answers_rating_id
//        answer_property.3 = type
//        answer_property.4 = comment
//        answer_property.5 = banned