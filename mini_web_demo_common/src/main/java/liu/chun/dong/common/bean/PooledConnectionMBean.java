package liu.chun.dong.common.bean;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/18 13:37
 * @comment PooledConnectionMBean
 */
public interface PooledConnectionMBean {
    public String getConnectionName();
    public boolean isCheckOut();
    public boolean isBusying();
    public boolean isClosed();

    public String getCheckOutThreadName();

    public int getCachedStatementsCount();
    public int getCachedPreStatementsCount();
    public String[] getCachedPreStatementsSQLs();

    public void doCheck();
    public void close();
}
