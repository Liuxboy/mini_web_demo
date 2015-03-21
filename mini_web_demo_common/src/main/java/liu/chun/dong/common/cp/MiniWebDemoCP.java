package liu.chun.dong.common.cp;

import liu.chun.dong.common.bean.MiniWebDemoCPMBean;
import liu.chun.dong.common.config.MiniWebDemoCPConfig;
import liu.chun.dong.common.util.JMXUtil;
import liu.chun.dong.common.util.JdbcUtil;
import liu.chun.dong.common.util.OracleUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:33
 * @comment MiniWebDemoCP
 */
public class MiniWebDemoCP implements MiniWebDemoCPMBean {
    private static final Logger log = new Logger();

    private static final String[] classPaths = System.getProperty("java.class.path", "classes").split(System.getProperty("path.separator", ";"));

    private static final AtomicInteger POOL_ID = new AtomicInteger(0);

    /**
     * pool id
     */
    private final int poolId;

    /**
     * 连接池名称
     * (跟配置文件名一致，即jdbc.properties的名称是jdbc)
     */
    private String poolName;

    /**
     * 配置
     */
    private final MiniWebDemoCPConfig config;

    /**
     * 连接编号
     */
    private final AtomicInteger connectionNo = new AtomicInteger(0);

    /**
     * 池中可用连接数
     */
    private final AtomicInteger validConnectionNum = new AtomicInteger(0);
    /**
     * 可用连接
     * 使用LinkedHashMap，并且用accessOrder，
     * 确保最近使用的连接在枚举器的最后，而最久使用的连接在最前面
     */
    private final Map<Integer, PooledConnection> validConnectionsPool = new ConcurrentHashMap<Integer, PooledConnection>();

    /**
     * 空闲连接Id号
     * 数据结构：堆栈
     *      最近使用的连接在堆栈的顶部，而最久未使用的连接在堆栈的底部
     */
    private final LinkedStack<Integer> idleConnectionsId = new LinkedStack<Integer>();

    /**
     * 关闭标志
     */
    private AtomicBoolean shutdown = new AtomicBoolean(false);

    /**
     * 初始化标志
     */
    private AtomicBoolean inited = new AtomicBoolean(false);

    /**
     * monitor thread
     */
    private Thread monitor;

    /**
     * true - config是从properties文件读入的
     */
    private boolean configFromProperties = false;

    MiniWebDemoCP(String poolName) throws SQLException {
        this.config = new MiniWebDemoCPConfig();
        this.config.setProperties(loadProperties(poolName));
        this.poolId = POOL_ID.getAndIncrement();
        this.poolName = poolName;
        this.configFromProperties = true;
        initPool();
    }

    public MiniWebDemoCP(MiniWebDemoCPConfig config) throws SQLException {
        this.config = config;
        this.poolId = POOL_ID.getAndIncrement();
        this.poolName = "UCP#" + this.poolId;
        this.configFromProperties = false;
        initPool();
    }

    // by wuhongqiang 2014.2.25
    // change the method name from startMonitor to initPool
    private void initPool() throws SQLException {
        if (this.config == null || this.config.getConnUrl() == null) {
            throw new SQLException("jdbc.url cannot be NULL");
        }
        if (config.getDriver() != null) {
            try {
                Class.forName(config.getDriver());
                log.info("load ", config.getDriver(), " ok");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e.toString(), e);
            }
        }
        boolean isOracle10 = config.isOracle() && DriverManager.getDriver(config.getConnUrl()).getMajorVersion() == 10;
        if (isOracle10 && config.isUseOracleImplicitPSCache()) {
            config.getConnectionProperties().setProperty(OracleUtil.ORACLE_FREECACHE_PROPERTY_NAME, OracleUtil.ORACLE_FREECACHE_PROPERTY_VALUE_TRUE);
        } else {
            config.getConnectionProperties().remove(OracleUtil.ORACLE_FREECACHE_PROPERTY_NAME);
        }
        if (inited.getAndSet(true)) {
            return;
        }
        if (! config.isLazyInit()) {
            //尝试建立一条连接
            if (validConnectionNum.get() < config.getMinConnections()) {
                newConnection(false);
            }
        }
        //异步初始化最小连接（最小连接尚未建立，pool已经可用）的代码
//        for (int i = 0; i < config.getMinConnections() && validConnectionNum.get() < config.getMinConnections(); i++) {
//            if (validConnectionNum.incrementAndGet() <= config.getMinConnections()) {
//                int connId = connectionNo.getAndIncrement();
//                try {
//                    PooledConnection pconn = new PooledConnection(this, connId);
//                    validConnectionsPool.put(connId, pconn);
//                    idleConnectionsId.push(connId);
//                } catch (SQLException e) {
//                    validConnectionNum.decrementAndGet();
//                    throw e;
//                }
//            } else {
//                validConnectionNum.decrementAndGet();
//            }
//        }
        //同步初始化最小连接（最小连接尚未建立，pool不可用）的代码
//        for (int i = 0; i < config.getMinConnections(); i++) {
//            int connId = connectionNo.getAndIncrement();
//
//            //20130514 zy 修改当新建连接失败的时，清空连接池中所有的连接，以防连接泄露
//            try {
//                PooledConnection pconn = new PooledConnection(this, connId);
//                validConnectionsPool.put(connId, pconn);
//                idleConnectionsId.push(connId);
//            } catch (SQLException e) {
//                for (Map.Entry<Integer, PooledConnection> entry : validConnectionsPool.entrySet()) {
//                    entry.getValue().close();
//                }
//                validConnectionsPool.clear();
//                throw e;
//            }
//        }
//        validConnectionNum.set(config.getMinConnections());
//        if (config.isVerbose()) {
//            log.info(poolName, " initial)", config.getMinConnections(), " connections to ", config.getConnUrl());
//        }

        monitor = new CPMonitor();
        monitor.setName("CPM:" + poolName);
        monitor.setDaemon(true);
        monitor.start();

        if (config.getJmxLevel() > 0) {
            JMXUtil.register(this.getClass().getPackage().getName() + ":type=pool-" + poolName, this);
            JMXUtil.register(this.getClass().getPackage().getName() + ":type=pool-" + poolName + ",name=config", config);
        }
    }

    public boolean isShutdown() {
        return shutdown.get();
    }

    /**
     * 关闭连接池
     */
    public void shutdown() {
        if (shutdown.getAndSet(true)) {
            return;
        }
        ConnectionFactory.remove(poolName);
        if (monitor != null) {
            monitor.interrupt();
            try {
                monitor.join();
            } catch (InterruptedException e) {
            }
        }
        for (int i = 0; i < 10; i++) { //最多检测10次，超过之后强制关闭
            Integer[] connIds = idleConnectionsId.toArray();
            for (Integer connId: connIds) {
                PooledConnection pc = validConnectionsPool.get(connId);
                try {
                    pc.lock(); //锁住连接，不允许checkout
                } catch (InterruptedException e) {
                    continue;
                }
                try {
                    if (pc.isCheckOut()) {
                        //已经checkout，则停止检测
                        break;
                    }
                    if (! closeConnection(pc)) {
                        //连接栈底部不是当前连接
                        //说明连接正等待被检出
                        break;
                    }
                } finally {
                    pc.unlock();
                }
            }
            if (validConnectionNum.get() <= 0) {
                break;
            }
            logVerboseInfo(true);
            idleConnectionsId.awaitNotEmpty(1, TimeUnit.SECONDS);
        }
        for (Map.Entry<Integer, PooledConnection> e: validConnectionsPool.entrySet()) {
            PooledConnection pc = e.getValue();
            if (pc.isCheckOut()) {
                log.info("force closing ... ", pc.getConnectionName(), " checkout by ", pc.getThreadCheckOut().getName(),
                        " for " + pc.getCheckOutTime() + " ms[", (pc.isBusying() ? "BUSYING" : "IDLE"), "]");
            }
            pc.close();
            validConnectionNum.decrementAndGet();
        }
        validConnectionsPool.clear();
        JMXUtil.unregister(this.getClass().getPackage().getName() + ":type=pool-" + poolName);
        JMXUtil.unregister(this.getClass().getPackage().getName() + ":type=pool-" + poolName + ",name=config");
    }

    public void reloadProperties() {
        if (configFromProperties) {
            Properties prop = loadProperties(poolName);
            config.setProperties(prop);
        }
    }

    /**
     * 读取配置文件
     */
    private Properties loadProperties(String propfile) {
        Properties prop = new Properties();
        File pfile = null;
        for (int i = 0; i <= classPaths.length; i++) {
            if (i == classPaths.length) {
                pfile = new File("./" + propfile + ".properties");
            } else {
                pfile = new File(classPaths[i] + "/" + propfile + ".properties");
            }
            if (pfile.exists()) {
                break;
            }
        }
        if (pfile != null && pfile.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(pfile);
                prop.load(fis);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                log.warn(e);
            } finally {
                JdbcUtil.closeQuietly(fis);
            }
        } else {
            ResourceBundle rb = ResourceBundle.getBundle(propfile);
            for (String property : MiniWebDemoCPConfig.PROPERTIES) {
                if (rb.containsKey(property)) {
                    prop.setProperty(property, rb.getString(property));
                }
            }
        }
        return prop;
    }

    /**
     * 从池中poll连接
     * @return
     * @throws java.sql.SQLException
     */
    public Connection getConnection() throws SQLException {
        return getConnection(! config.isTransactionMode());
    }

    /**
     * 从池中poll连接
     * @param autoCommit
     * @return
     * @throws java.sql.SQLException
     */
    public Connection getConnection(boolean autoCommit) throws SQLException {
        if (shutdown.get()) {
            //throw new SQLException("connection pool is shutdown", "60003");
            throw new SQLException("connection pool is shutdown", "08001");
        }
        long start = System.nanoTime();
        Integer connId = idleConnectionsId.pop();
        if (connId == null && ! config.isLazyInit()) {
            connId = newConnection(true);
//            //没有空闲连接
//            if (validConnectionNum.incrementAndGet() <= config.getMaxConnections()) {
//                //当前连接数 < 最大连接数，则创建连接
//                connId = connectionNo.getAndIncrement();
//                try {
//                    PooledConnection pconn = new PooledConnection(this, connId);
//                    Connection conn = pconn.checkOut(autoCommit);
//                    validConnectionsPool.put(connId, pconn);
//                    if (config.isVerbose()) {
//                        log.info(poolName, " +)", validConnectionNum.get() ," connections to ", config.getConnUrl());
//                        log.debug(pconn.getConnectionName(), ".getConnection(", autoCommit, "), use ", (System.nanoTime() - start), " ns");
//                    }
//                    return conn;
//                } catch (SQLException e) {
//                    validConnectionNum.decrementAndGet();
//                    throw e;
//                }
//            } else {
//                validConnectionNum.decrementAndGet();
//            }
        }
        if (connId == null) {
            if (validConnectionNum.get() >= config.getMaxConnections()) {
                log.info("connections of ", poolName, " to ", config.getConnUrl(), " exhausted, wait ", config.getCheckoutTimeoutMilliSec(), " ms for idle connection");
            }
            try {
                connId = idleConnectionsId.pop(config.getCheckoutTimeoutMilliSec(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                log.info(e);
            }
        }
        if (connId == null) {
            //throw new SQLException("connections of " + poolName + " to " + config.getConnUrl() + " exhausted", "60004");
            //modify by wuhq on 2013.03.21 for changing the sqlstate to 08001 indicate it's connect error
            //add by wuhq. 2014.02.19 在连接池耗尽时，打印连接池的状态
            logVerboseInfo(true);
            throw new SQLException("Timed out waiting for a free available connection of " + poolName + " to " + config.getConnUrl(), "08001");
        }
        PooledConnection pconn = validConnectionsPool.get(connId);
        try {
            Connection conn = pconn.checkOut(autoCommit);
            if (config.isVerbose()) {
                log.debug(pconn.getConnectionName(), ".getConnection(", autoCommit, "), use ", (System.nanoTime() - start), " ns");
            }
            return conn;
        } catch (SQLException e) {
            checkIn(pconn);
            throw e;
        }
    }

    void checkIn(PooledConnection pconn) {
        int connId = pconn.getConnectionId();
        idleConnectionsId.push(connId);
    }

    /**
     * Return poolName.
     * @return poolName
     */
    public String getPoolName() {
        return poolName;
    }

    /**
     * new one pooled connection
     * @param directReturn
     * @return connId
     * @throws SQLException
     */
    private Integer newConnection(boolean directReturn) throws SQLException {
        if (validConnectionNum.incrementAndGet() <= config.getMaxConnections()) {
            //当前连接数 < 最大连接数，则创建连接
            Integer connId = connectionNo.getAndIncrement();
            try {
                PooledConnection pconn = new PooledConnection(MiniWebDemoCP.this, connId);
                validConnectionsPool.put(connId, pconn);
                if (directReturn) {
                    return connId;
                } else {
                    idleConnectionsId.push(connId);
                }
                if (config.isVerbose()) {
                    log.info(poolName, " +)", validConnectionNum.get(), " connections to ", config.getConnUrl());
                }
            } catch (SQLException e) {
                validConnectionNum.decrementAndGet();
                throw e;
            }
        } else {
            validConnectionNum.decrementAndGet();
        }
        return null;
    }

    /**
     * close one pooled connection
     * @param pc
     * @return
     */
    private boolean closeConnection(PooledConnection pc) {
        //移除连接栈底部连接
        if (! idleConnectionsId.removeStackBottom(pc.getConnectionId())) {
            //连接栈底部不是当前连接
            //说明连接正等待被检出
            //则停止检测
            return false;
        }
        validConnectionNum.decrementAndGet();
        validConnectionsPool.remove(pc.getConnectionId());
        pc.close();
        if (config.isVerbose()) {
            log.info(poolName, " -)", validConnectionNum.get() ," connections to ", config.getConnUrl());
        }
        return true;
    }

    /**
     * log连接的状态信息
     */
    private void logVerboseInfo(boolean verbose) {
        if (verbose) {
            //显示当前活动连接的状态
            int i = 0;
            for (Map.Entry<Integer, PooledConnection> e: validConnectionsPool.entrySet()) {
                PooledConnection pc = e.getValue();
                if (pc.isCheckOut()) {
                    i++;
                    //change log level from DEBUG to INFO by wuhq 2014.2.19
                    log.info(pc.getConnectionName(), " checkout by ", pc.getThreadCheckOut().getName(), " for " + pc.getCheckOutTime() + " ms[", (pc.isBusying() ? "BUSYING" : "IDLE"), "]");
                }
            }
            log.debug(poolName, ": ", i, "/", validConnectionNum.get(), "/", config.getMaxConnections());
        } else {
            log.debug(poolName, ": ", validConnectionNum.get(), "/", config.getMaxConnections());
        }
    }

    private class CPMonitor extends Thread {

        /**
         * （从堆栈底部的连接开始检查）
         * 空闲连接的检查：
         *  1、关闭超过minConnections设置的空闲连接
         *  2、对空闲的连接进行存活检测
         * @return 下次检查的时间间隔(ms)（根据保留的堆栈底部的连接的最后check时间计算得出）
         * @throws InterruptedException
         */
        private long idleConnectionCheckOrClose() throws InterruptedException {
            long timeToNextCheck = config.getIdleTimeoutMilliSec();
            Integer[] connIds = idleConnectionsId.toArray();
            for (Integer connId: connIds) {
                PooledConnection pc = validConnectionsPool.get(connId);
                pc.lock(); //锁住连接，不允许checkout
                try {
                    if (pc.isCheckOut()) {
                        //已经checkout，则停止检测
                        break;
                    }
                    //下次检测时间=检入时间+检测间隔
                    timeToNextCheck = pc.getTimeCheckIn() + config.getIdleTimeoutMilliSec() - System.currentTimeMillis();
                    if (timeToNextCheck <= 0) {
                        //达到需要检测或回收的时间
                        //置下次检测时间=当前时间+最大检测间隔
                        timeToNextCheck = config.getIdleTimeoutMilliSec();
                        if (validConnectionNum.get() > config.getMinConnections()) {
                            //当前连接数>最少连接数，则回收该连接
                            if (! closeConnection(pc)) {
                                //连接栈底部不是当前连接
                                //说明连接正等待被检出
                                //则停止检测
                                break;
                            }
                        } else {
                            //否则，检测该连接
                            pc.doCheck();
                        }
                    } else {
                        break;
                    }
                } finally {
                    pc.unlock();
                }
            }
            return timeToNextCheck;
        }

        /**
         * 保持最小连接数
         */
        private void newMoreConnections(long waitTime) throws InterruptedException {
            try {
                while (validConnectionNum.get() < config.getMinConnections()) {
                    newConnection(false);
                }
            } catch (SQLException e) {
                log.warn(e);
            }
            long nanos = TimeUnit.MILLISECONDS.toNanos(waitTime);
            final long timeout = System.nanoTime() + nanos;
            while (timeout-System.nanoTime() > 0) {
                nanos = idleConnectionsId.awaitRequireMore(timeout-System.nanoTime(), TimeUnit.NANOSECONDS);
                if (nanos > 0) {
                    try {
                        newConnection(false);
                    } catch (SQLException e) {
                        log.warn(e);
                    }
                } else {
                    break;
                }
            }
        }

        public void run() {
            log.info(getName(), " start!");
            long idleTimeout = config.getIdleTimeoutMilliSec();
            while (! shutdown.get()) {
                try {
                    //保持最小连接数
                    newMoreConnections(idleTimeout);
                    //检查连接可用性，并关闭额外的连接
                    idleTimeout = idleConnectionCheckOrClose();
                    //log连接池的信息
                    logVerboseInfo(config.isVerbose());
                } catch (InterruptedException e) {
                    if (shutdown.get()) {
                        break;
                    }
                } catch (Exception e) {
                    idleTimeout = config.getIdleTimeoutMilliSec();
                    log.warn(e);
                } catch (Throwable t) {
                    idleTimeout = config.getIdleTimeoutMilliSec();
                    log.error(t);
                }
//                //sleep，等待下次idle检查
//                try {
//                    long sleep = nextCheckTime - System.currentTimeMillis();
//                    if (sleep > 0) {
//                        Thread.sleep(sleep);
//                    }
//                } catch (InterruptedException e) {
//                    if (shutdown.get()) {
//                        break;
//                    }
//                }
            }
            log.info(getName(), " quit!");
        }
    }

    private class LinkedStack<E> {
        private LinkedList<E> stack;
        private final ReentrantLock operLock = new ReentrantLock();
        private final Condition notEmpty = operLock.newCondition();
        private final Condition requireMore = operLock.newCondition();

        public LinkedStack() {
            stack = new LinkedList<E>();
        }

        public int size() {
            return stack.size();
        }

        public boolean removeStackBottom(E e) {
            operLock.lock();
            try {
                if (stack.size() == 0) {
                    return false;
                }
                E x = stack.getFirst();
                if (x.equals(e)) {
                    stack.removeFirst();
                    return true;
                } else {
                    return false;
                }
            } finally {
                operLock.unlock();
            }
        }

        public void push(E e) {
            int c = -1;
            operLock.lock();
            try {
                c = stack.size();
                stack.addLast(e);
                if (c == 0) {
                    notEmpty.signal();
                }
            } finally {
                operLock.unlock();
            }
        }

        public E pop() {
            operLock.lock();
            try {
                if (0 == stack.size()) {
                    return null;
                }
                return stack.removeLast();
            } finally {
                operLock.unlock();
            }
        }

        /**
         * 等待请求更多(连接)
         * @param timeout 最长的等待时间
         * @param unit
         * @return 剩余的等待时间(ns)
         * @throws InterruptedException
         */
        public long awaitRequireMore(long timeout, TimeUnit unit) throws InterruptedException {
            operLock.lockInterruptibly();
            try {
                try {
//                    log.info("wait ", unit.toMillis(timeout), " ms...");
                    return requireMore.awaitNanos(unit.toNanos(timeout));
                } catch (InterruptedException e) {
                    requireMore.signal();
                    throw e;
                }
            } finally {
                operLock.unlock();
            }
        }

        /**
         * 等待直到非空
         * @param timeout
         * @param unit
         * @return
         */
        public boolean awaitNotEmpty(long timeout, TimeUnit unit) {
            try {
                operLock.lockInterruptibly();
            } catch (InterruptedException e) {
                return true;
            }
            try {
                return notEmpty.await(timeout, unit);
            } catch (InterruptedException e) {
                return true;
            } finally {
                operLock.unlock();
            }
        }

        public E pop(long timeout, TimeUnit unit) throws InterruptedException, SQLException {
            long nanos = unit.toNanos(timeout);
            operLock.lockInterruptibly();
            try {
                while (true) {
                    if (stack.size() > 0) {
                        E x = stack.removeLast();
                        if (stack.size() > 0) {
                            notEmpty.signal();
                        }
                        return x;
                    } else {
                        //stack is empty, signal for more connection
                        requireMore.signal();
                    }
                    if (nanos <= 0) {
                        return null;
                    }
                    try {
                        nanos = notEmpty.awaitNanos(nanos);
                    } catch (InterruptedException ie) {
                        notEmpty.signal(); // propagate to a non-interrupted thread
                        throw ie;
                    }
                }
            } finally {
                operLock.unlock();
            }
        }

        public Integer[] toArray() {
            operLock.lock();
            try {
                Integer[] ret = new Integer[stack.size()];
                return stack.toArray(ret);
            } finally {
                operLock.unlock();
            }
        }

    }

    public int getActiveConnectionsCount() {
        return validConnectionNum.get();
    }

    public int getIdleConnectionsCount() {
        return idleConnectionsId.size();
    }

    public MiniWebDemoCPConfig getConfig() {
        return config;
    }

    public int getPoolId() {
        return poolId;
    }

    public void setPoolName(String poolName) {
        if (! configFromProperties) {
            this.poolName = poolName;
            if (this.monitor != null) {
                this.monitor.setName("CPM:" + poolName);
            }
        }
    }
}
