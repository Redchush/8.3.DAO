package root.dao.mysql.util;


import org.jetbrains.annotations.NotNull;
import root.dao.mysql.criteria.Criteria;
import root.model.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static root.dao.mysql.util.ResourceNavigator.getAllFields;
import static root.dao.mysql.util.ResourceNavigator.getReferencedTable;


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


    public static String getSelectAll(Class clazz) {
        String tableName = getReferencedTable(clazz, false);
        String tableFullName = getReferencedTable(clazz, true);

        ArrayList<String> fieldsString = getAllFields(tableName);
        String enumPart = collectList(fieldsString, ", ", 0);

        StringBuilder query = new StringBuilder(SELECT);
        query.append(enumPart)
                .append("\n")
                .append(FROM)
                .append(tableFullName);
        return query.toString();
    }

    public static String getSelectAll(Class clazz, Criteria criteria) {
        String result = getSelectAll(clazz) + criteria.getString();
        return result;
    }

    public static String getSelectById(Class dao) {
        String queryAll = getSelectAll(dao);
        return queryAll + APPEND_BY_ID;
    }

    public static String getDeleteById(Class clazz) {
        StringBuilder result = new StringBuilder(DELETE);
        result.append(getReferencedTable(clazz, true))
                .append(APPEND_BY_ID);
        return result.toString();
    }

    public static String getDeleteByBan(Class clazz) {

        StringBuilder result = new StringBuilder(UPDATE);
        result.append(getReferencedTable(clazz, true))
                .append(APPEND_SET_BANNED)
                .append(APPEND_BY_ID);
        return result.toString();
    }

    @NotNull
    public static String getUpdate(Class clazz) {

        String tableName = getReferencedTable(clazz, false);
        String tableFullName = getReferencedTable(clazz, true);

        ArrayList<String> fields = getAllFields(tableName);
        String setPartOfQuery = modifyAndCollect(fields, ", ", " = ?", 1);

        StringBuilder query = new StringBuilder(UPDATE);
        query.append(tableFullName)
                .append(SET)
                .append(setPartOfQuery)
                .append(APPEND_BY_ID);
        return query.toString();
    }



    @NotNull
    public static <T extends Entity> String getCreate(Class clazz) {

        String tableName = getReferencedTable(clazz, false);
        String tableFullName = getReferencedTable(clazz, true);

        ArrayList<String> fields = getAllFields(tableName);
        String fieldsString = collectList(fields, ", ", 0);
        String setPart = surroundWithParenthesis(modifyAndCollectByReplacement(fields, ",", "?", 0));

        StringBuilder query = new StringBuilder(INSERT);
        query.append(tableFullName)
                .append(" ")
                .append(surroundWithParenthesis(fieldsString))
                .append(VALUES)
                .append(setPart.replaceFirst("\\?", DEFAULT));
        return query.toString();
    }


    public static boolean isClassMaintainInterface (Object classToCheck, Class infTocCheck){
        return ResourceNavigator.isClassMaintainInterface(classToCheck, infTocCheck);
    }

    /*decorative methods*/

    public static String collectList(List<String> list, String separator, int skip) {
        final Stream<String> stream = list.parallelStream();
        return stream.skip(skip).reduce((s1, s2) -> s1 + separator + s2).get();
    }

    public static String modifyAndCollect(List<String> list, String separator,
                                          String modificator, int skip) {
        final Stream<String> stream = list.parallelStream();
        return stream.skip(skip).map(s -> s + modificator)
                .reduce((s1, s2) -> s1 + separator + s2).get();
    }

    public static String modifyAndCollectByReplacement(List<String> list, String separator,
                                          String modificator, int skip) {
        final Stream<String> stream = list.parallelStream();
        return stream.skip(skip).map(s -> modificator)
                .reduce((s1, s2) -> s1 + separator + s2).get();
    }

    public static String surroundWithParenthesis(String s) {
        return "(" + s + ")";
    }

    public static String surroundWithParenthesis(StringBuilder s) {
        return s.insert(0, "(").append(")").toString();
    }

}
