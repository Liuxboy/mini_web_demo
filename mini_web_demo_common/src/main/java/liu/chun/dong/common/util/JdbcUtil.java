package liu.chun.dong.common.util;

import liu.chun.dong.common.cp.Logger;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:55
 * @comment JdbcUtil
 */
public class JdbcUtil {
    private static final Logger LOGGER = new Logger();

    /**
     * Create a new instance of <code>Driver</code> with the driver class name.
     */
    public static Driver createDriver(String driverClassName) throws SQLException {
        Class<?> clazz = null;

        try {
            ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
            if (contextLoader != null) {
                clazz = contextLoader.loadClass(driverClassName);
            }
        } catch (ClassNotFoundException e) {
            // skip. will check later.
        }

        if (clazz == null) {
            try {
                clazz = Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                throw new SQLException(e.getMessage(), e);
            }
        }

        try {
            return (Driver) clazz.newInstance();
        } catch (IllegalAccessException e) {
            throw new SQLException(e.getMessage(), e);
        } catch (InstantiationException e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    /**
     * @param rs ResultSet
     */
    public static void closeQuietly(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.warn("E close(ResultSet)...", e);
            }
        }
    }
    /**
     * @param stmt Statement
     */
    public static void closeQuietly(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                LOGGER.warn("E close(Statement)...", e);
            }
        }
    }
}
