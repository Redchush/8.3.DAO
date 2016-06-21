package root.dao.mysql.util;


import root.dao.AbstractDao;
import root.dao.mysql.impl.TagDaoMySql;
import root.dao.mysql.impl.UserDaoMySql;
import root.model.Tag;
import root.model.User;

import static root.dao.mysql.util.ResourceData.*;
import static root.dao.mysql.util.ResourceManager.*;


public class QueryMaker {

    public static final String SELECT = "SELECT ";
    public static final String DELETE = "DELETE ";
    public static final String UPDATE = "UPDATE ";
    public static final String CREATE = "CREATE ";

    public static String getSelectQueryAll(Object dao){
        Class clazz = dao.getClass();
        Class  modelClass = getModelClassFromGeneric(clazz);
        String key = PR_SELECT + OPT_FULL + getClassPrefix(modelClass);
        String query = PARTS.getString(key);
        return query;
    }

    public static String getSelectQueryById(Object dao) {
        String queryAll = getSelectQueryAll(dao);
        String result = queryAll + PARTS.getString("append.byId");
        return result;
    }

    public static String getDeleteQueryById(Object dao){
        Class clazz = dao.getClass();
        String result = DELETE+ getReferencedTable(clazz, true) +
                " " + PARTS.getString("append.byId");
        return result;
    }

    public static String getDeleteByBan(Object dao){
        Class clazz = dao.getClass();
        String result = DELETE + getReferencedTable(clazz, true) +
                " " + PARTS.getString("append.setBanned");
        return result;
    }

    private static String getReferencedTable(Class clazz, boolean full){
        Class  modelClass = getModelClassFromGeneric(clazz);
        String key = PR_CLASS + modelClass.getSimpleName();
        String result =  STRUCTURE.getString(key);
        return full ? DB.getString("database.name") + "." + result : result;
    }

    private static Class getModelClassFromGeneric(Class clazz){
        return ReflectionUtils.getGenericParameterClass(clazz, 0);
    }

    private static String getAllFieldsString(Class clazz){
        String keyPart = getReferencedTable(clazz, false);
        String columnCount = STRUCTURE.getString(keyPart  +"." + OPT_COL_NUBERS);
        int count = Integer.parseInt(columnCount);
        StringBuilder result = new StringBuilder();
        for (int i = OPT_START_COL_INDEX; i <= count; i++) {
            String value = STRUCTURE.getString(keyPart +"." + i);
            System.out.println(value);
        }
        return keyPart;
    }

   
}
