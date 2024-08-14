package mx.mc.util;

/**
 *
 * @author hramirez
 */
public class DBContext {

    private static final int DB_COUNT = 2;
    private static final ThreadLocal<String> tlDbKey = new ThreadLocal<>();

    
    public static String getDBKey() {
        return tlDbKey.get();
    }

    
    public static void setDBKey(String dbKey) {
        tlDbKey.set(dbKey);
    }

    public static String getDBKeyByUserId(int userId) {
        int dbIndex = userId % DB_COUNT;
        return "db_" + (++dbIndex);
    }

}
