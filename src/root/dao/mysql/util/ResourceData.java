package root.dao.mysql.util;


public class ResourceData {

    public static final String DB_STRUCTURE = "property.databaseStructure";
    public static final String DB_QUERIES = "property.databaseFullQueries";
    public static final String DB_PARTS = "property.databaseQueriesParts";
    public static final String DB_MAIN = "property.database";


    //prefixes //
    public static final String PR_SELECT = "select.";
    public static final String PR_UPDATE= "update.";
    public static final String PR_DELETE = "delete.";
    public static final String PR_TABLE = "table.";
    public static final String PR_CLASS = "clazz.";

    //OPTIONS //
    public static final String OPT_FULL = "full.";
    public static final String OPT_BY_ID = "byId.";
    public static final String OPT_COL_NUBERS = "num";
    public static final int OPT_START_COL_INDEX = 1;

    public static String getClassPrefix(Class clazz){
        return clazz.getSimpleName().toLowerCase();
    }
}
