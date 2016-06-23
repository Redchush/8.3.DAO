package root.dao.mysql.impl;

import root.dao.RatingDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Answer;
import root.model.Rating;
import root.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RatingDaoMySql extends AbstractDaoMySql<Rating>
        implements RatingDao, Bannable {

    public RatingDaoMySql() {}

    public RatingDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<Rating> findAll() throws DaoException {
        return super.findAll();
    }

    @Override
    public Rating findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(Rating entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(Rating entity) throws DaoException {
        return super.create(entity);
    }

    @Override
    public Rating update(Rating entity) throws DaoException {
        return super.update(entity);
    }

    /*
    *      public Rating(int id, Answer parentAnswer, User author, int rating, Timestamp createdDate,
    *             Timestamp updatedDate, List<RatingComment> ratingComment, boolean isBanned)
    */
    @Override
    protected List<Rating> createEntityList(ResultSet set) throws SQLException {
        List<Rating> entities = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt(1);
            int parentAnswerId = set.getInt("answer_id");
            Answer parentAnswer = new Answer(parentAnswerId);

            int raiting = set.getInt("raiting");
            int parentUserId = set.getInt("user_id");
            User parentUser = new User(parentUserId);

            Timestamp createdDate = set.getTimestamp("created_date");
            Timestamp updated_date = set.getTimestamp("updated_date");

            boolean isBanned = set.getBoolean("banned");

            Rating entity = new Rating(id, parentAnswer, parentUser, raiting, createdDate, updated_date, isBanned);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    protected void fillStatementWithFullAttributesSet(PreparedStatement statement, Rating entity, int from)
            throws SQLException {

        int user_id = entity.getAuthor().getId();
        statement.setInt(1, user_id);

        int answer_id = entity.getParent().getId();
        statement.setInt(2, answer_id);

        int rating = entity.getRating();
        statement.setInt(3, rating);

        Timestamp created_date = entity.getCreatedDate();
        statement.setTimestamp(4, created_date);
        Timestamp updated_date = entity.getUpdatedDate();
        statement.setTimestamp(5, updated_date);

        boolean isBanned = entity.isBanned();
        statement.setBoolean(6, isBanned);
    }
}

//
//        rating.num = 7
//        rating.1 = id
//        rating.2 = user_id
//        rating.3 = answer_id
//        rating.4 = raiting
//        rating.5 = created_date
//        rating.6 = updated_date
//        rating.7 = banned

