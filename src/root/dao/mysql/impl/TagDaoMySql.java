package root.dao.mysql.impl;

import root.dao.TagDao;
import root.dao.exception.DaoException;
import root.model.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class TagDaoMySql extends AbstractDaoMySql<Tag>
        implements TagDao {

    public TagDaoMySql() {}

    public TagDaoMySql(Connection connection) {
        super(connection);
    }

    @Override
    public List<Tag> findAll() throws DaoException {
        return super.findAll();
    }

    @Override
    public Tag findEntityById(int id) throws DaoException {
        return super.findEntityById(id);
    }

    @Override
    public boolean delete(int id) throws DaoException {
        return super.delete(id);
    }

    @Override
    public boolean delete(Tag entity) throws DaoException {
        return super.delete(entity);
    }

    @Override
    public boolean create(Tag entity) throws DaoException {
        return super.create(entity);
    }

    @Override
    public Tag update(Tag entity) throws DaoException {
        return super.update(entity);
    }

    @Override
    protected List<Tag> createEntityList(ResultSet set) throws SQLException {
        List<Tag> entities = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt(1);
            String name = set.getString("name");
            Tag entity = new Tag(id, name);
            entities.add(entity);
        }
        return entities;
    }

    @Override
    protected void fillStatementWithFullAttributesSet(PreparedStatement statement, Tag entity, int from)
            throws SQLException {
        String name = entity.getName();
        statement.setString(1, name);
    }

    @Override
    protected Class<Tag> getGenericType() {
        return Tag.class;
    }
}

//        tags.num = 2
//        tags.1 = id
//        tags.2 = name