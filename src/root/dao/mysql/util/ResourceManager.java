package root.dao.mysql.util;

import java.util.ResourceBundle;
import java.util.Set;

import static root.dao.mysql.util.ResourceData.*;

public enum ResourceManager {
    STRUCTURE(DB_STRUCTURE),
    QUERIES(DB_QUERIES),
    PARTS(DB_PARTS),
    DB(DB_MAIN);

    private ResourceBundle resourceBundle;

    private ResourceManager(String string){
        resourceBundle = ResourceBundle.getBundle(string);
    }

    public String getString(String key){
        return resourceBundle.getString(key);
    }

    public Set<String> keySet() {
        return resourceBundle.keySet();
    }

}
