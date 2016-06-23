package root.dao.mysql.impl;

import root.dao.RatingCommentDao;
import root.dao.exception.DaoException;
import root.dao.mysql.Bannable;
import root.model.Rating;
import root.model.RatingComment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean create(RatingComment entity) throws DaoException {
        return super.create(entity);
    }

    @Override
    public RatingComment update(RatingComment entity) throws DaoException {
        return super.update(entity);
    }

    @Override
    protected List<RatingComment> createEntityList(ResultSet set) throws SQLException {
        List<RatingComment> entities = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt(1);
            int ratingId = set.getInt("rating_id");
            Rating rating = new Rating(ratingId);

            String comment = set.getString("comment");
            boolean isBanned = set.getBoolean("banned");
            boolean isPositive = set.getBoolean("type");

            RatingComment entity = new RatingComment(id, rating, comment, isPositive, isBanned);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    protected void fillStatementWithFullAttributesSet(PreparedStatement statement, RatingComment entity, int from)
            throws SQLException {

        int rating_id = entity.getParent().getId();
        statement.setInt(1, rating_id);

        boolean type = entity.isPositive();
        statement.setBoolean(2, type);

        String comment = entity.getComment();
        statement.setString(3, comment);

        boolean idBanned = entity.isBanned();
        statement.setBoolean(4, idBanned);
    }
}
//
//        rating_comment.num =4
//        rating_comment.1 = id
//        rating_comment.2 = rating_id
//        rating_comment.3 = type
//        rating_comment.4 = comment
//        rating_comment.5 = banned
