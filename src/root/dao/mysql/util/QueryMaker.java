package root.dao.mysql.util;


import org.jetbrains.annotations.NotNull;
import root.dao.AbstractDao;
import root.model.Entity;

import static root.dao.mysql.util.ResourceData.*;
import static root.dao.mysql.util.ResourceManager.*;


public class QueryMaker {

    private static final String SELECT = "SELECT ";
    private static final String DELETE = "DELETE FROM ";
    private static final String UPDATE = "UPDATE ";
    private static final String INSERT = "INSERT INTO ";
    private static final String VALUES = "\nVALUES ";
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

    @NotNull
    public static <T extends Entity> String getUpdate(AbstractDao<T> dao) {
        Class clazz = dao.getClass();
        String tableName = getReferencedTable(clazz, false);
        String tableFullName = getReferencedTable(clazz, true);
        int attrCount = getAttrCount(tableName);
        String fieldsString = getAllFieldsString(tableName, attrCount);
        String setPartOfQuery = getSetPartOfQuery(fieldsString, true);

        StringBuilder query = new StringBuilder(UPDATE);
        query.append(tableFullName)
             .append(SET)
             .append(setPartOfQuery)
             .append("\n")
             .append(PARTS.getString("append.byId"));
        return query.toString();
    }

    @NotNull
    public static <T extends Entity> String getCreate(AbstractDao<T> dao) {
        Class clazz = dao.getClass();
        String tableName = getReferencedTable(clazz, false);
        String tableFullName = getReferencedTable(clazz, true);
        int attrCount = getAttrCount(tableName);
        String fieldsString = getAllFieldsString(tableName, attrCount);
        String setPartOfQuery = getValuesPartOfQuery(attrCount, 1, true);
        StringBuilder query = new StringBuilder(INSERT);
        query.append(tableFullName)
             .append(" ")
             .append(surroundWithParenthesis(fieldsString))
             .append(VALUES)
             .append(setPartOfQuery);
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

    private static String getValuesPartOfQuery(int fieldsCount, int exprCount, boolean isFirstDefault){
        StringBuilder builder = new StringBuilder("(");
        int counter = 0;
        String insert = "?, ";
        for (int i = 0; i < fieldsCount; i++) {
             if ((isFirstDefault) && (i == 0)){
                 builder.append(DEFAULT).append(", ");
             }
             builder.append(insert);
        }
        builder.replace(builder.length() - insert.length() - 2, builder.length(), ")");
        return builder.toString();
    }

    private static String getSetPartOfQuery(String fieldEnum, boolean isFirstDefault){
        String replacement = " = ?, ";
        String lastReplacement = " = ?";
        String result = fieldEnum.replaceAll(",", replacement);
        return isFirstDefault ? result.replaceFirst("\\?" , DEFAULT) + lastReplacement
                              : result + lastReplacement;
    }

    private static String getAllFieldsString(String tableName, int attrCount){
        StringBuilder result = new StringBuilder();
        String appender = ", ";
        for (int i = OPT_START_COL_INDEX; i <= attrCount; i++) {
            String value = STRUCTURE.getString(tableName +"." + i);
            result.append(value).append(appender);
        }
        result.delete(result.length() - appender.length(), result.length());
        return result.toString();

    }

    private static String surroundWithParenthesis(String s){
        return "(" + s + ")";
    }
}
