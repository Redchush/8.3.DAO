package root.dao.mysql.util;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;


public enum ResourceManager {

    STRUCTURE(Constants.DB_STRUCTURE),
    PARTS(Constants.QUERY_PARTS),
    DB(Constants.DB_MAIN);

    /**
     * Contains paths to resources
     */
    private static class Constants {
        private static final String DB_STRUCTURE = "resource.databaseStructure";
        private static final String QUERY_PARTS = "resource.queryParts";
        private static final String DB_MAIN = "resource.database";
    }

    /**
     * Contains prefixes for search in resources
     */
     public static class Prefix{
        public static final String PR_CLASS = "clazz.";
        public static final String OPT_COL_NUBERS = ".num";
        public static final int OPT_START_COL_INDEX = 1;
    }

    private ResourceBundle resourceBundle;

    ResourceManager(String string){
        resourceBundle = ResourceBundle.getBundle(string);
    }

    public String getString(String key){
        return resourceBundle.getString(key);
    }

    public Set<String> keySet() {
        return resourceBundle.keySet();
    }

    public String[] getStringArray(String key) {
        return resourceBundle.getStringArray(key);
    }

    public Object getObject(String key) {
        return resourceBundle.getObject(key);
    }

    public Locale getLocale() {
        return resourceBundle.getLocale();
    }

    public Enumeration<String> getKeys() {
        return resourceBundle.getKeys();
    }

    public boolean containsKey(String key) {
        return resourceBundle.containsKey(key);
    }
}
