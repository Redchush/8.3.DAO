package root.dao.mysql;


import root.dao.AbstractDao;
import root.dao.exception.DaoException;
import root.dao.mysql.impl.*;
import root.model.*;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class MySqlDaoFactory {

    private static MySqlDaoFactory instance;

    private final static Map<Class, AbstractDaoMySql> daoMap = new HashMap();

    static {
        daoMap.put(Answer.class, new AnswerDaoMySql());
        daoMap.put(Category.class, new CategoryDaoMySql());
        daoMap.put(Comment.class, new CommentDaoMySql());
        daoMap.put(FavoritePost.class, new FavoritePostDaoMySql());
        daoMap.put(Post.class, new PostDaoMySql());
        daoMap.put(Role.class, new RoleDaoMySql());
        daoMap.put(Tag.class, new TagDaoMySql());
        daoMap.put(User.class, new UserDaoMySql());
    }

    private MySqlDaoFactory(){

    }

    public static MySqlDaoFactory getInstance(){

        if (instance == null)
            synchronized (MySqlDaoFactory.class){
                if (instance == null)
                    instance = new MySqlDaoFactory();
            }
        return instance;
    }

    public AbstractDao getDaoByClass(Class clazz, Connection connection) throws DaoException{
        AbstractDaoMySql daoMySql = daoMap.get(clazz);
        daoMySql.setConnection(connection);
        return daoMySql;
    }





}
