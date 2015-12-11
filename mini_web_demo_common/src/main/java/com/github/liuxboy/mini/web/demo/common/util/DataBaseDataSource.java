package com.github.liuxboy.mini.web.demo.common.util;


import com.github.liuxboy.mini.web.demo.common.constant.DataBaseConfigImpl;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;
import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Logger;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/8 18:32
 * @comment DataBaseDataSource
 */
public class DataBaseDataSource extends DataBaseConfigImpl implements DataSource, ObjectFactory{
    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    private DataBasePoolConfigImpl pool = null;
    private PrintWriter logWriter = null;

    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {
        logWriter = out;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

//    public void setLoginTimeout(int seconds) throws SQLException {
//        throw new UnsupportedOperationException("setLoginTimeout is unsupported.");
//    }
//
//    public int getLoginTimeout() throws SQLException {
//        throw new UnsupportedOperationException("getLoginTimeout is unsupported.");
//    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        // TODO Auto-generated method stub
        return false;
    }

    public Object getObjectInstance(Object object, Name name, Context nameCtx, Hashtable<?, ?> environment) throws Exception {

        Reference ref = (Reference) object;
        Enumeration<RefAddr> addrs = ref.getAll();
        Properties props = new Properties();
        while (addrs.hasMoreElements()) {
            RefAddr addr = addrs.nextElement();
            if (addr.getType().equals("driverClassName") || addr.getType().equals("driver")) {
                //TODO test the logical is correct?
                Class.forName((String) addr.getContent());
            } else {
                props.put(addr.getType(), addr.getContent());
            }
        }
        DataBaseDataSource ds = new DataBaseDataSource();
        ds.setProperties(props);
        return ds;
    }

    public Connection getConnection() throws SQLException {
        if (this.pool == null) {
            maybeInit();
        }
        return this.pool.getConnection();
    }

    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException("getConnection(username, password) is unsupported.");
    }

    private void maybeInit() throws SQLException {

        try {
            this.rwl.readLock().lock();
            if (this.pool == null) { // this.pool is protected in getConnection
                this.rwl.readLock().unlock();
                this.rwl.writeLock().lock();
                try {
                    if (this.pool == null) { // read might have passed, write
                        // might not
                        this.pool = new DataBasePoolConfigImpl(this);
                    }
                } finally {
                    this.rwl.readLock().lock();
                    this.rwl.writeLock().unlock();
                }
            }
        } finally {
            this.rwl.readLock().unlock();
        }
    }

}
