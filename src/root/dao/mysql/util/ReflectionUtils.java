package root.dao.mysql.util;

import root.dao.mysql.Bannable;

import java.lang.reflect.ParameterizedType;


public class ReflectionUtils {

    public static Class getGenericParameterClass(Class actualClass, int ind){
         return (Class) ((ParameterizedType) actualClass
                            .getGenericSuperclass())
                            .getActualTypeArguments()[ind];
    }

    public static Class getDefaultGenericParameterClass(Class actualClass){
        int ind = 0;
        return (Class) ((ParameterizedType) actualClass
                .getGenericSuperclass())
                .getActualTypeArguments()[ind];
    }

    public static String getGenericParameterClassSimpleName(Class actualClass){
        return getDefaultGenericParameterClass(actualClass).getSimpleName();
    }

    public static String getGenericFormattedClassName(String name){
        return name.toLowerCase();
    }

    public static String getGenericFormattedClassName(Class actualClass){
        return getGenericParameterClassSimpleName(actualClass).toLowerCase();
    }


    public static boolean isClassMaintainInterface (Object classToCheck, Class infTocCheck) {
        Class[] inf = classToCheck.getClass().getInterfaces();
        boolean isBannable = false;
        for (Class cl : inf) {
            if (cl.equals(Bannable.class)) {
                isBannable = true;
            }
        }
        return isBannable;
    }
}
