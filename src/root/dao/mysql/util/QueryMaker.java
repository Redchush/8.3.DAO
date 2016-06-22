package root.dao.mysql.util;


import root.dao.AbstractDao;
import root.model.Entity;

import static root.dao.mysql.util.ResourceData.*;
import static root.dao.mysql.util.ResourceManager.*;


public class QueryMaker {

    private static final String SELECT = "SELECT ";
    private static final String DELETE = "DELETE ";
    private static final String UPDATE = "UPDATE ";
    private static final String CREATE = "CREATE ";
    private static final String SET = "\nSET ";
    private static final String DEFAULT = "DEFAULT";

    public static <T extends Entity> String getSelectQueryAll(AbstractDao<T>  dao){
        Class clazz = dao.getClass();
        Class  modelClass = getModelClassFromGeneric(clazz);
        String key = PR_SELECT + OPT_FULL + getClassPrefix(modelClass);
        return PARTS.getString(key);
    }

    public static <T extends Entity>  String getSelectQueryById(AbstractDao<T> dao) {
        String queryAll = getSelectQueryAll(dao);
        return queryAll + PARTS.getString("append.byId");
    }

    public static  <T extends Entity> String getDeleteQueryById(AbstractDao<T> dao){
        Class clazz = dao.getClass();
        StringBuilder result = new StringBuilder(DELETE);
        result.append(getReferencedTable(clazz, true))
              .append(" ")
              .append(PARTS.getString("append.byId"));
        return result.toString();
    }

    public static  <T extends Entity> String getDeleteByBan(AbstractDao<T> dao){
        Class clazz = dao.getClass();
        StringBuilder result = new StringBuilder(DELETE);
        result.append(getReferencedTable(clazz, true))
              .append(" ")
              .append(PARTS.getString("append.setBanned"));
        return result.toString();
    }


    public static <T extends Entity> String getUpdate(AbstractDao<T> dao) {
        Class clazz = dao.getClass();

        String tableName = getReferencedTable(clazz, false);
        int attrCount = getAttrCount(tableName);
        String fieldsString = getAllFieldsString(tableName, attrCount);
        String setPartOfQuery = getSetPartOfQuery(fieldsString);

        StringBuilder query = new StringBuilder(UPDATE);
        query.append(fieldsString)
             .append(SET)
             .append(setPartOfQuery)
             .append("\n")
             .append(PARTS.getString("append.byId"));
        return query.toString();
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

    private static int getAttrCount(String tableName){
        String columnCount = STRUCTURE.getString(tableName  +"." + OPT_COL_NUBERS);
        return Integer.parseInt(columnCount);
    }

    private static String getSetPartOfQuery(String fieldEnum){
        String replacement = " = ?, ";
        String lastReplacement = " = ?";
        return fieldEnum.replaceAll(",", replacement)
                        .replaceFirst("\\?" , DEFAULT)
                                    + lastReplacement;
    }

    private static String getAllFieldsString(String tableName, int attrCount){
        StringBuilder result = new StringBuilder();
        for (int i = OPT_START_COL_INDEX; i <= attrCount; i++) {
            String value = STRUCTURE.getString(tableName +"." + i);
            result.append(value).append(", ");
        }
        result.deleteCharAt(result.length() - 2);
        return result.toString();
    }

  /*  public static void main(String[] args) {
        try {
            AbstractDao<User> dao = new UserDaoMySql();
            String query = getDeleteQueryById(dao);
            System.out.println(query);
        } finally {}
        try {
             AbstractDao<User> dao = new UserDaoMySql();
             User user = new User();
             String string = getUpdate(dao);
            System.out.println(string);
        } finally {}
        try {
            AbstractDao<Answer> dao = new AnswerDaoMySql();
            Answer user = new Answer();
            String string = getUpdate(dao);
            System.out.println(string);
        } finally {}
    }*/

}
