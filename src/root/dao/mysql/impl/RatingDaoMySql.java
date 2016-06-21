package root.dao.mysql.impl;

import root.dao.RatingDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Answer;
import root.model.Rating;
import root.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
    public boolean create(Rating entity) {
        return false;
    }

    @Override
    public Rating update(Rating entity) {
        return null;
    }


    @Override
    protected Rating createSimpleEntity(ResultSet set) throws DaoException {
        Rating entity = createEntityList(set).get(0); ;
        return entity;
    }

    /*
    *      public Rating(int id, Answer parentAnswer, User author, int rating, Timestamp createdDate,
    *             Timestamp updatedDate, List<RatingComment> ratingComment, boolean isBanned)
    */
    @Override
    protected List<Rating> createEntityList(ResultSet set) throws DaoException {
        List<Rating> entities = new ArrayList<>();
        Rating entity;
        try {
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

                entity = new Rating(id, parentAnswer, parentUser, raiting, createdDate, updated_date, isBanned);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new DaoException("Cant create a category list ", e);
        }
        return entities;
    }
}

