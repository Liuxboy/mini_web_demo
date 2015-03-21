package liu.chun.dong.common.cp;

import liu.chun.dong.common.util.OracleUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 14:11
 * @comment OraclePooledPreparedStatement
 */
public class OraclePooledPreparedStatement extends PooledPreparedStatement {
    private static final Logger log = new Logger();

    private boolean useOracleImplicitCache;

    OraclePooledPreparedStatement(PooledConnection conn, PreparedStatement stmt, int stmtId, String sql,
                                  boolean useOracleImplicitCache) throws SQLException {
        super(conn, stmt, stmtId, sql);
        this.useOracleImplicitCache = useOracleImplicitCache;
    }

    @Override
    public void cleanCache() {
        if (useOracleImplicitCache) {
            OracleUtil.enterImplicitCache(this.getStatement());
        }
    }

    @Override
    public Statement checkOut() throws SQLException {
        if (useOracleImplicitCache) {
            OracleUtil.exitImplicitCacheToActive(this.getStatement());
        }
        return super.checkOut();
    }

    @Override
    public void close() {
        if (useOracleImplicitCache) {
            OracleUtil.exitImplicitCacheToClose(this.getStatement());
        }
        super.close();
    }
}
