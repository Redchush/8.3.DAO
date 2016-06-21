package root.dao.mysql.impl;

import root.dao.TagDao;
import root.dao.exception.DaoException;
import root.model.Tag;

import java.sql.Connection;
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
    public boolean create(Tag entity) {
        return false;
    }

    @Override
    public Tag update(Tag entity) {
        return null;
    }

    @Override
    protected Tag createSimpleEntity(ResultSet set) throws DaoException {
        Tag tag =  createEntityList(set).get(0);
        return tag;
    }

    @Override
    protected List<Tag> createEntityList(ResultSet set) throws DaoException {
        List<Tag> entities = new ArrayList<>();
        Tag entity;
        try {
            while (set.next()) {
                int id = set.getInt(1);
                String name = set.getString("name");
                entity = new Tag(id, name);
                entities.add(entity);
             }
        } catch (SQLException e) {
            throw new DaoException("Cant create a tag list", e);
        }
        return entities;
    }
}
