package liu.chun.dong.common.cp;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/8 16:56
 * @comment LoggerBase
 */

/*******************************************************************************
 * 模式符号 - 用途(附加说明);{可选附加选项}(附加选项说明)
 * c - 日志名称(通常是构造函数的参数);{数字}("a.b.c" 的名称使用 %c{2} 会输出 "b.c")
 * C - 调用者的类名(速度慢,不推荐使用);{数字}(同上)
 * d - 日志时间;{SimpleDateFormat所能使用的格式}
 * F - 调用者的文件名(速度极慢,不推荐使用)
 * l - 调用者的函数名、文件名、行号(速度极其极其慢,不推荐使用)
 * L - 调用者的行号(速度极慢,不推荐使用)
 * m - 日志
 * M - 调用者的函数名(速度极慢,不推荐使用)
 * n - 换行符号
 * p - 日志优先级别(DEBUG, INFO, WARN, ERROR)
 * r - 输出日志所用毫秒数
 * t - 调用者的进程名
 * x - Used to output the NDC (nested diagnostic context) associated with the thread that generated the logging event.
 * X - Used to output the MDC (mapped diagnostic context) associated with the thread that generated the logging event.
 ******************************************************************************/

import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

/***************************************************************************************************************************************************************
 * 模式修饰符 - 对齐 - 最小长度 - 最大长度 - 说明 %20c 右 20 ~ %-20c 左 20 ~ %.30c ~ ~ 30 %20.30c 右 20 30 %-20.30c 左 20 30
 **************************************************************************************************************************************************************/

class LoggerBase {
    private static final String NA = "UnknowClass";

    private static final org.slf4j.Logger ACCESS_LOGGER;
    private static ThreadLocal<Long> tlAccessStart = new ThreadLocal<Long>();

    private static final String NULL = "NULL";

    public static final String LINE_SEP = System.getProperty("line.separator");
    public static final int LINE_SEP_LEN  = LINE_SEP.length();

    private final org.slf4j.Logger delegate;

    static {
        ACCESS_LOGGER = LoggerFactory.getLogger(System.getProperty("logger.access.name", "ACCESS"));
    }

    public LoggerBase(String cls) {
        delegate = LoggerFactory.getLogger(cls);
    }

    @SuppressWarnings("unchecked")
    public LoggerBase(Class clazz) {
        if (clazz == liu.chun.dong.common.cp.LoggerFactory.class
                || clazz == Logger.class) {
            final String CLASSNAME = clazz.getName() + ".";
            String cls = getCallingClassName(CLASSNAME);
            delegate = LoggerFactory.getLogger(cls);
        } else {
            delegate = LoggerFactory.getLogger(clazz);
        }
    }

    public boolean isTraceEnabled() {
        return delegate.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return delegate.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return delegate.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return delegate.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return delegate.isErrorEnabled();
    }

    /**
     * 标记access的开始
     */
    public static void accessStart() {
        tlAccessStart.set(Long.valueOf(System.nanoTime()));
    }

    /**
     * 记录access日志
     * @param args
     */
    public static void access(Object ... args) {
        if (args == null) {
            args = new Object[] { NULL };
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(unull(args[i]));
        }
        //如果标记了开始时间，则在access最后追加耗时
        Long startNS = tlAccessStart.get();
        if (startNS != null) {
            sb.append(',').append((System.nanoTime() - startNS.longValue())).append("ns");
            tlAccessStart.remove();
        }
        ACCESS_LOGGER.info(sb.toString());
    }

    private static Object unull(Object obj) {
        return obj == null ? NULL : obj;
    }

    private static String concat(Object ... objects) {
        StringBuilder sb = new StringBuilder();
        concat(sb, objects);
        return sb.toString();
    }

    private static void concat(StringBuilder sb, Object ... objects) {
        for (int i = 0; i < objects.length; i++) {
            if ((i+1)==objects.length && objects[i] instanceof Throwable) {
                //最后一个对象如果是Throwable，则留给上层进行堆栈打印
                break;
            }
            //如果objects[i]是Array，则进入递归concat by wuhq 2014.2.19
            if (objects[i] != null && objects[i].getClass().isArray()) {
                concat(sb, (Object[]) objects[i]);
            } else {
                sb.append(unull(objects[i]));
            }
        }
    }

    private String logf(String format, Object ...args) {
        StringBuilder sb = new StringBuilder();
        java.util.Formatter formatter = new java.util.Formatter(sb);
        formatter.format(format, args);
        return sb.toString();
    }

    public void allf(String format, Object ...args) {
        if (delegate.isTraceEnabled()) {
            all(logf(format, args));
        }
    }

    public void all(Object ...objects) {
        if (delegate.isTraceEnabled()) {
            if (objects[objects.length-1] instanceof Throwable) {
                delegate.trace(concat(objects), (Throwable) objects[objects.length-1]);
            } else {
                all(concat(objects));
            }
        }
    }

    public void all(Object obj) {
        if (! delegate.isTraceEnabled()) {
            return;
        }
        if (obj instanceof Throwable) {
            delegate.trace("StackTrace:", (Throwable) obj);
        } else {
            delegate.trace(obj == null ? NULL : obj.toString());
        }
    }

    public void debugf(String format, Object ...args) {
        if (delegate.isDebugEnabled()) {
            debug(logf(format, args));
        }
    }

    public void debug(Object ...objects) {
        if (delegate.isDebugEnabled()) {
            if (objects[objects.length-1] instanceof Throwable) {
                delegate.debug(concat(objects), (Throwable) objects[objects.length - 1]);
            } else {
                debug(concat(objects));
            }
        }
    }

    public void debug(Object obj) {
        if (! delegate.isDebugEnabled()) {
            return;
        }
        if (obj instanceof Throwable) {
            delegate.debug("StackTrace:", (Throwable) obj);
        } else {
            delegate.debug(obj == null ? NULL : obj.toString());
        }
    }

    public void infof(String format, Object ...args) {
        if (delegate.isInfoEnabled()) {
            info(logf(format, args));
        }
    }

    public void info(Object ...objects) {
        if (delegate.isInfoEnabled()) {
            if (objects[objects.length-1] instanceof Throwable) {
                delegate.info(concat(objects), (Throwable) objects[objects.length - 1]);
            } else {
                info(concat(objects));
            }
        }
    }

    public void info(Object obj) {
        if (! delegate.isInfoEnabled()) {
            return;
        }
        if (obj instanceof Throwable) {
            delegate.info("StackTrace:", (Throwable) obj);
        } else {
            delegate.info(obj == null ? NULL : obj.toString());
        }
    }

    public void warnf(String format, Object ...args) {
        if (delegate.isWarnEnabled()) {
            warn(logf(format, args));
        }
    }

    public void warn(Object ...objects) {
        if (delegate.isWarnEnabled()) {
            if (objects[objects.length-1] instanceof Throwable) {
                delegate.warn(concat(objects), (Throwable) objects[objects.length - 1]);
            } else {
                warn(concat(objects));
            }
        }
    }

    public void warn(Object obj) {
        if (! delegate.isWarnEnabled()) {
            return;
        }
        if (obj instanceof Throwable) {
            delegate.warn("StackTrace:", (Throwable) obj);
        } else {
            delegate.warn(obj == null ? NULL : obj.toString());
        }
    }

    public void errorf(String format, Object ...args) {
        if (delegate.isErrorEnabled()) {
            error(logf(format, args));
        }
    }

    public void error(Object ...objects) {
        if (delegate.isErrorEnabled()) {
            if (objects[objects.length-1] instanceof Throwable) {
                delegate.error(concat(objects), (Throwable) objects[objects.length - 1]);
            } else {
                error(concat(objects));
            }
        }
    }

    public void error(Object obj) {
        if (! delegate.isErrorEnabled()) {
            return;
        }
        if (obj instanceof Throwable) {
            delegate.error("StackTrace:", (Throwable) obj);
        } else {
            delegate.error(obj == null ? NULL : obj.toString());
        }
    }

    private String getCallingClassName(String CLASSNAME) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        (new Throwable()).printStackTrace(pw);
        String stack = sw.toString();

        // Given the current structure of the package, the line
        // containing "org.apache.log4j.Category." should be printed just
        // before the caller.

        // This method of searching may not be fastest but it's safer
        // than counting the stack depth which is not guaranteed to be
        // constant across JVM implementations.
        int ibegin = stack.lastIndexOf(CLASSNAME);
        if (ibegin == -1) {
            return NA;
        }

        ibegin = stack.indexOf(LINE_SEP, ibegin);
        if (ibegin == -1) {
            return NA;
        }
        ibegin += LINE_SEP_LEN;

        // determine end of line
        int iend = stack.indexOf(LINE_SEP, ibegin);
        if (iend == -1) {
            return NA;
        }

        // VA has a different stack trace format which doesn't
        // need to skip the inital 'at'
        // back up to first blank character
        ibegin = stack.lastIndexOf("at ", iend);
        if (ibegin == -1) {
            return NA;
        }
        // Add 3 to skip "at ";
        ibegin += 3;
        // everything between is the requested stack item
        String fullInfo = stack.substring(ibegin, iend);
        // modify by shenjl never null
//        if (fullInfo == null) {
//            return NA;
//        }

        // Starting the search from '(' is safer because there is
        // potentially a dot between the parentheses.
        iend = fullInfo.lastIndexOf('(');
        if (iend == -1) {
            return NA;
        }
        iend = fullInfo.lastIndexOf('.', iend);

        // This is because a stack trace in VisualAge looks like:
        //java.lang.RuntimeException
        //  java.lang.Throwable()
        //  java.lang.Exception()
        //  java.lang.RuntimeException()
        //  void test.test.B.print()
        //  void test.test.A.printIndirect()
        //  void test.test.Run.main(java.lang.String [])
        if (iend == -1) {
            return NA;
        } else {
            return fullInfo.substring(0, iend);
        }
    }

}
