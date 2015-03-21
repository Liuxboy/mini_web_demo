package liu.chun.dong.common.cp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:40
 * @comment PooledPreparedStatement
 */
public class PooledPreparedStatement extends PooledStatement {
    private static final Logger log = new Logger();

    private PreparedStatement pstmt;
    private PreparedStatement real_pstmt;

    private final String sql;
    private final Object paras[];

    PooledPreparedStatement(PooledConnection conn, PreparedStatement stmt, int stmtId, String sql) throws SQLException {
        super(conn, stmt, stmtId);
        real_pstmt = (PreparedStatement) getStatement();
        this.sql = sql;
        this.paras = new Object[getQMCount()];
    }

    /*
    * Do nothing for generic PooledPreparedStatement. For subclass implementation.
    */
    public void cleanCache() {}

    @SuppressWarnings("unchecked")
    protected PreparedStatement buildProxy() {
        Statement stmt = getStatement();
        Class[] intfs = stmt.getClass().getInterfaces();
        boolean impled = false; //是否实现了Connection接口
        for (Class intf: intfs) {
            if (intf.getName().equals(PreparedStatement.class.getName())) {
                impled = true;
                break;
            }
        }
        if (!impled) {
            //没有实现Connection接口，则强制增加
            Class[] tmp = intfs;
            intfs = new Class[tmp.length + 1];
            System.arraycopy(tmp, 0, intfs, 0, tmp.length);
            intfs[tmp.length] = PreparedStatement.class;
        }
        pstmt = (PreparedStatement) Proxy.newProxyInstance(stmt.getClass().getClassLoader(), intfs, this);
        return pstmt;
    }

    protected Object _invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        Object ret = null;
        String mname = method.getName();
        try {
            if (mname.equals("addBatch") && (args == null || args.length == 0)) {
                methodDoing = "addBatch(" + toString() + ")";
                real_pstmt.addBatch();
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), getStatementName(), ".", methodDoing, " use ");
                }
            } else if (mname.equals("execute") && (args == null || args.length == 0)) {
                methodDoing = "execute(" + toString() + ")";
                ret = real_pstmt.execute();
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), getStatementName(), ".", methodDoing, "[", ret, "] use ");
                }
            } else if (mname.equals("executeQuery") && (args == null || args.length == 0)) {
                methodDoing = "executeQuery(" + toString() + ")";
                resultSet = real_pstmt.executeQuery();
                ret = resultSet;
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), getStatementName(), ".", methodDoing, " use ");
                }
            } else if (mname.equals("executeUpdate") && (args == null || args.length == 0)) {
                methodDoing = "executeUpdate(" + toString() + ")";
                ret = real_pstmt.executeUpdate();
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), getStatementName(), ".", methodDoing, "[", ret, "] use ");
                }
            } else if (mname.startsWith("set") && args.length == 2 && args[0] instanceof Integer) {
                int idx = ((Integer) args[0]).intValue();
                if (args[1] == null) {
                    args[1] = "";
                }
                paras[idx - 1] = args[1];
                ret = method.invoke(real_pstmt, args);
            } else if (mname.equals("toString") && (args == null || args.length == 0)) {
                ret = toString();
            } else {
                ret = super._invoke(proxy, method, args);
            }
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
        return ret;
    }

    private int getQMCount() {
        int c = 0;
        int idx = 0;
        while (true) {
            idx = sql.indexOf("?", idx + 1);
            if (idx == -1) {
                break;
            }
            c++;
        }
        return c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(sql);
        if (paras == null) {
            return sql;
        }
        int idx = 0;
        for (int i = 0; i < paras.length; i++) {
            Object p = paras[i];
            idx = sb.toString().indexOf("?", idx + 1);
            if (p == null) {
                continue;
            }
            if (p instanceof String || p instanceof java.sql.Time || p instanceof java.sql.Timestamp || p instanceof java.sql.Date) {
                sb.replace(idx, idx + 1, "'" + p + "'");
            } else {
                sb.replace(idx, idx + 1, p.toString());
            }
        }
        return sb.toString();
    }
}
