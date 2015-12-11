package com.github.liuxboy.mini.web.demo.common.bean;

import java.sql.SQLException;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/8 18:33
 * @comment IDataBasePoolConfig
 */
public interface IDataBasePoolConfig {
    public String getPoolName();
    public void setPoolName(String poolName);

    public int getActiveConnectionsCount();
    public int getIdleConnectionsCount();

    public void reloadProperties() throws SQLException;
    public void shutdown();
}
