package root.dao.mysql.util;


import org.jetbrains.annotations.NotNull;
import root.dao.AbstractDao;
import root.model.Entity;

import java.util.List;

import static root.dao.mysql.util.ResourceData.*;
import static root.dao.mysql.util.ResourceManager.DB;
import static root.dao.mysql.util.ResourceManager.STRUCTURE;


public class QueryMaker {

    private static final String SELECT = "SELECT ";
    private static final String DELETE = "DELETE FROM ";
    private static final String UPDATE = "UPDATE ";
    private static final String INSERT = "INSERT INTO ";

    private static final String FROM = "FROM ";
    private static final String VALUES = "\nVALUES ";
    private static final String SET = "\nSET ";
    private static final String DEFAULT = "DEFAULT";
    private static final String APPEND_BY_ID = "\nWHERE id = ?";
    private static final String APPEND_SET_BANNED = "\nSET banned = true";

//    public static <T extends Entity> String getSelectAll(AbstractDao<T>  dao){
//        Class clazz = dao.getClass();
//        Class  modelClass = getModelClassFromGeneric(clazz);
//        String key = PR_SELECT + OPT_FULL + getClassPrefix(modelClass);
//        return PARTS.getString(key);
//    }

    public static <T extends Entity> String getSelectAll(AbstractDao<T>  dao){
        Class clazz = dao.getClass();
        String tableName = getReferencedTable(clazz, false);
        String tableFullName = getReferencedTable(clazz, true);
        int attrCount = getAttrCount(tableName);
        String fieldsString = getAllFieldsString(tableName, attrCount);

        StringBuilder query = new StringBuilder(SELECT);
        query.append(fieldsString)
             .append("\n")
             .append(FROM)
             .append(tableFullName);
        return query.toString();
    }

    public static <T extends Entity>  String getSelectById(AbstractDao<T> dao) {
        String queryAll = getSelectAll(dao);
        return queryAll + APPEND_BY_ID;
    }

    public static  <T extends Entity>  String getSelectWithCondition(AbstractDao<T> dao,
                                                                     List<String> conditions) {
        String queryAll = getSelectAll(dao);
        StringBuilder builder = new StringBuilder(queryAll);
        for (int i = 0; i < conditions.size(); i++) {
            if (i == 0)  {
                addCondition(builder, conditions.get(i), true);
            } else {
                addCondition(builder, conditions.get(i), false);
            }

        }
        return builder.toString();
    }

    public static  <T extends Entity> String getDeleteById(AbstractDao<T> dao){
        Class clazz = dao.getClass();
        StringBuilder result = new StringBuilder(DELETE);
        result.append(getReferencedTable(clazz, true))
              .append(APPEND_BY_ID);
        return result.toString();
    }

    public static  <T extends Entity> String getDeleteByBan(AbstractDao<T> dao){
        Class clazz = dao.getClass();
        StringBuilder result = new StringBuilder(UPDATE);
        result.append(getReferencedTable(clazz, true))
              .append(APPEND_SET_BANNED)
              .append(APPEND_BY_ID);
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
             .append(APPEND_BY_ID);
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

    private static StringBuilder addCondition(StringBuilder query, String attrName, boolean isFirst){
        String formatAnd = " AND %s = ? ";
        String formatWhere = "\nWHERE %s = ? ";
        String resultCondition = isFirst ? String.format(formatWhere, attrName)
                                : String.format(formatAnd, attrName);
        return query.append(resultCondition);
    }

    public static String getReferencedTable(Class clazz, boolean full){
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
        StringBuilder builder = new StringBuilder();
        String insert = "?, ";
        for (int i = 0; i < exprCount; i++) {
            for (int j = 0; j < fieldsCount; j++) {
                if ((isFirstDefault) && (j == 0)){
                    builder.append(DEFAULT).append(", ");
                }
                builder.append(insert);
            }
        }
        builder.replace(builder.length() - insert.length() - 2, builder.length(), "");
        return surroundWithParenthesis(builder);
    }

    private static String getSetPartOfQuery(String fieldEnum, boolean isFirstDefault){
        String replacement = " = ?, ";
        String lastReplacement = " = ?";
        String result = fieldEnum.replace("id,", "")
                                  .replaceAll(",", replacement);
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

    private static String surroundWithParenthesis(StringBuilder s){
        return s.insert(0, "(").append(")").toString();
    }


}
