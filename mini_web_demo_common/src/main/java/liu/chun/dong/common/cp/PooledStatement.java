package liu.chun.dong.common.cp;

import java.lang.reflect.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:39
 * @comment PooledStatement
 */
public class PooledStatement implements InvocationHandler {
    private static final Logger log = new Logger();

    /**
     * 归属连接
     */
    private final PooledConnection connection;
    /**
     * 语句Id
     */
    private final int statementId;
    /**
     * 语句名称
     */
    private final String statementName;

    /**
     * 封装过的语句
     */
    private Statement statement;
    /**
     * 真实的语句
     */
    private Statement real_statement;

    /**
     * 是否检出（使用中）
     */
    protected AtomicBoolean checkOut = new AtomicBoolean(false);
    /**
     * 检入时间
     */
    private long timeCheckIn = System.currentTimeMillis();
    /**
     * 检出时间
     */
    private long timeCheckOut;
    /**
     * 检出线程
     */
    private Thread threadCheckOut;

    /**
     * 是否默认的结果集类型
     */
    private boolean isDefaultResultSetType = true;
    /**
     * 语句打开的结果集
     */
    protected ResultSet resultSet;
    /**
     * 连接是否关闭
     */
    private AtomicBoolean closed = new AtomicBoolean(false);

    /**
     * 语句是否正在执行
     */
    private long busying = 0;

    protected String methodDoing;

    PooledStatement(PooledConnection conn, Statement stmt, int stmtId) throws SQLException {
        connection = conn;
        statementId = stmtId;
        statementName = conn.getConnectionName() + ".STMT#" + stmtId;
        real_statement = stmt;
        if (stmt.getResultSetType() == ResultSet.TYPE_FORWARD_ONLY
                && stmt.getResultSetConcurrency() == ResultSet.CONCUR_READ_ONLY) {
            isDefaultResultSetType = true;
        } else {
            isDefaultResultSetType = false;
        }
        statement = buildProxy();
    }

    public Statement checkOut() throws SQLException {
        if (checkOut.getAndSet(true)) {
            if (threadCheckOut.equals(Thread.currentThread())) {
                return statement;
            } else {
                throw new SQLException(statementName + "已经被"+threadCheckOut.getName()+"检出", "60003");
            }
        }
        timeCheckOut = System.currentTimeMillis();
        threadCheckOut = Thread.currentThread();
        return statement;
    }

    @SuppressWarnings("unchecked")
    protected Statement buildProxy() {
        Class[] intfs = real_statement.getClass().getInterfaces();
        boolean impled = false; //是否实现了Connection接口
        for (Class intf: intfs) {
            if (intf.getName().equals(Statement.class.getName())) {
                impled = true;
                break;
            }
        }
        if (!impled) {
            //没有实现Connection接口，则强制增加
            Class[] tmp = intfs;
            intfs = new Class[tmp.length + 1];
            System.arraycopy(tmp, 0, intfs, 0, tmp.length);
            intfs[tmp.length] = Statement.class;
        }
        return (Statement) Proxy.newProxyInstance(real_statement.getClass().getClassLoader(), intfs, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        busying = System.nanoTime();
        try {
            methodDoing = method.getName() + "()";
            Object obj = _invoke(proxy, method, args);
            if (methodDoing.startsWith("execute") && ! methodDoing.startsWith("executeQuery")) {
                connection.setDirty();
            }
            return obj;
        } catch (SQLException e) {
            if (methodDoing.startsWith("execute")) {
                log.error(e.toString(), "[ErrorCode=", e.getErrorCode(), ";SQLState=", e.getSQLState(), "] on ", methodDoing, "(", ((args == null || args.length == 0) ? toString() : args[0]), ")");
            }
            String sqls = e.getSQLState();
            if (sqls == null || sqls.startsWith("08")) {
                close();
                connection.recover(e);
            }
            throw e;
        } finally {
            busying = 0;
        }
    }

    protected Object _invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.nanoTime();
        Object ret = null;
        String mname = method.getName();
        try {
            if (mname.equals("close")) {
                if (! checkOut.getAndSet(false)) {
                    return null;
                }
                timeCheckIn = System.currentTimeMillis();
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                    }
                    resultSet = null;
                }
                if (this instanceof PooledPreparedStatement) {
                    connection.checkIn((PooledPreparedStatement) this);
                } else {
                    connection.checkIn(this);
                }
                if (isVerbose()) {
                    log.all(statementName, ".close() use ", (System.nanoTime() - start), " ns");
                }
            } else if (mname.equals("addBatch") && args != null && args.length == 1) {
                methodDoing = "addBatch(" + args[0] + ")";
                real_statement.addBatch((String) args[0]);
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), statementName, ".addBatch(", args[0], ") use ");
                }
            } else if (mname.equals("executeBatch")) {
                ret = real_statement.executeBatch();
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), statementName, ".executeBatch()[", Array.getLength(ret), "] use ");
                }
            } else if (mname.equals("executeQuery") && args != null && args.length == 1) {
                methodDoing = "executeQuery(" + args[0] + ")";
                resultSet = real_statement.executeQuery((String) args[0]);
                ret = resultSet;
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), statementName, ".executeQuery(", args[0], ") use ");
                }
            } else if (mname.startsWith("execute") && args != null && args.length > 0) {
                methodDoing = "execute(" + args[0] + ")";
                ret = method.invoke(real_statement, args);
                if (isPrintSQL()) { //if (isVerbose()) {
                    printSQL(log, (System.nanoTime() - start), statementName, ".", mname, "(", args[0], ")[", ret, "] use ");
                }
            } else {
                ret = method.invoke(real_statement, args);
                if (isVerbose()) {
                    log.all(statementName, ".", mname, "(...) use ", (System.nanoTime() - start), " ns");
                }
            }
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
        return ret;
    }

    /**
     * 根据执行时间，打印不同级别的SQL日志
     * @param logger
     * @param usedNS
     * @param infos
     */
    protected void printSQL(Logger logger, long usedNS, Object ... infos) {
        if (! isPrintSQL()) {
            return;
        }
        if (usedNS/1000 <= connection.getInfoSQLThreshold()*1000) {
            logger.debug(infos, usedNS, " ns");
        } else if (usedNS/1000 <= connection.getWarnSQLThreshold()*1000) {
            logger.info(infos, usedNS, " ns");
        } else {
            logger.warn(infos, usedNS, " ns");
        }
    }

    public Statement getProxy() {
        return statement;
    }

    public Statement getStatement() {
        return real_statement;
    }

    /**
     * Return statementId.
     * @return statementId
     */
    public int getStatementId() {
        return statementId;
    }

    /**
     * Return checkOut.
     * @return checkOut
     */
    public boolean isCheckOut() {
        return checkOut.get();
    }

    /**
     * Return isDefaultResultSetType.
     * @return isDefaultResultSetType
     */
    public boolean isDefaultResultSetType() {
        return isDefaultResultSetType;
    }

    public void close() {
        if (closed.getAndSet(true)) {
            return;
        }
        try {
            real_statement.close();
        } catch (SQLException e) {
        }
        log.debug(statementName, " real closed.");
    }

    /**
     * Return statementName.
     * @return statementName
     */
    public String getStatementName() {
        return statementName;
    }

    public boolean isVerbose() {
        return connection.isVerbose();
    }

    public boolean isPrintSQL() {
        return connection.isPrintSQL();
    }

    public long getCheckOutTime() {
        if (checkOut.get()) {
            return System.currentTimeMillis() - timeCheckOut;
        }
        return 0L;
    }

    public boolean isBusying() {
        if (checkOut.get() && busying > 0) {
            log.debug(statementName, " invoking ", methodDoing, " use ", (System.nanoTime() - busying), " ns");
            return true;
        }
        return false;
    }

    public boolean isClosed() {
        return closed.get();
    }

    /**
     * Return timeCheckIn.
     * @return timeCheckIn
     */
    public long getTimeCheckIn() {
        return timeCheckIn;
    }
}
