package liu.chun.dong.common.util;

import liu.chun.dong.common.cp.Logger;
import oracle.jdbc.internal.OraclePreparedStatement;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:59
 * @comment OracleUtil
 */
public class OracleUtil {
    private static final Logger log = new Logger();

    public final static String ORACLE_FREECACHE_PROPERTY_NAME = "oracle.jdbc.FreeMemoryOnEnterImplicitCache";
    public final static String ORACLE_FREECACHE_PROPERTY_VALUE_TRUE = "true";

    /**
     * Will clear the PS caches for this statement in driver.
     * Not working on version 10 driver. Working on v11.
     */
    public static void enterImplicitCache(Statement statement) {
        try {
            OraclePreparedStatement oraclePreparedStatement = unwrapInternal(statement);

            if (oraclePreparedStatement != null) {
                oraclePreparedStatement.enterImplicitCache();
            }
        } catch(SQLException e) {
            log.warn(e);
        }
    }

    /**
     * Call when the PreparedStatement re-used.
     */
    public static void exitImplicitCacheToActive(Statement statement) {
        try {
            OraclePreparedStatement oraclePreparedStatement = unwrapInternal(statement);

            if (oraclePreparedStatement != null) {
                oraclePreparedStatement.exitImplicitCacheToActive();
            }
        } catch (SQLException e) {
            log.warn(e);
        }
    }

    /**
     * Call when the prepared statement needs to be removed.
     */
    public static void exitImplicitCacheToClose(Statement statement) {
        try {
            OraclePreparedStatement oraclePreparedStatement = unwrapInternal(statement);

            if (oraclePreparedStatement != null) {
                oraclePreparedStatement.exitImplicitCacheToClose();
            }
        } catch(SQLException e) {
            log.warn(e);
        }
    }

    /**
     * Unwrap Statement to get the internal OraclePreparedStatement.
     */
    private static OraclePreparedStatement unwrapInternal(Statement stmt) throws SQLException {
        if (stmt instanceof OraclePreparedStatement) {
            return (OraclePreparedStatement) stmt;
        }

        OraclePreparedStatement unwrapped = stmt.unwrap(OraclePreparedStatement.class);

        if (unwrapped == null) {
            log.error("can not unwrap statement : " + stmt.getClass());
        }

        return unwrapped;
    }
}
