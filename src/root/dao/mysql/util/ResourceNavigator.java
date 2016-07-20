package root.dao.mysql.util;

import root.model.Entity;

import java.util.ArrayList;

import static root.dao.mysql.util.ResourceManager.DB;
import static root.dao.mysql.util.ResourceManager.Prefix.*;
import static root.dao.mysql.util.ResourceManager.STRUCTURE;

public class ResourceNavigator {

    public static String getReferencedTable(Class modelClass, boolean full){
//        Class  modelClass = ReflectionUtil.getGenericParameterClass(daoClazz, 0);
        String key = PR_CLASS + modelClass.getSimpleName();
        String result =  STRUCTURE.getString(key);
        return full ? DB.getString("database.name") + "." + result : result;
    }

    public static String getReferencedTable(Class<? extends Entity> clazz){
        return STRUCTURE.getString(PR_CLASS + clazz.getSimpleName().toLowerCase());
    }

    public static ArrayList<String> getAllFields(String tableName){
        int attrCount = getAttrCount(tableName);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = OPT_START_COL_INDEX; i <= attrCount; i++) {
            String value = STRUCTURE.getString(tableName + "." + i);
            arrayList.add(value);
        }
        return arrayList;
    }

    public static int getAttrCount(String tableName){
        String columnCount = STRUCTURE.getString(tableName  + OPT_COL_NUBERS);
        return Integer.parseInt(columnCount);
    }

    public static boolean isClassMaintainInterface (Object classToCheck, Class infTocCheck) {
        Class[] inf = classToCheck.getClass().getInterfaces();
        boolean isMaintain = false;
        for (Class cl : inf) {
            if (cl.equals(infTocCheck)) {
                isMaintain = true;
            }
        }
        return isMaintain;
    }

}
