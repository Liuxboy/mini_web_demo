package liu.chun.dong.common.cp;

/**
 * @author wyliuchundong
 * @version 1.0.0
 * @date 2015/3/8 16:55
 * @comment Logger
 */
public class Logger extends LoggerBase{
    public Logger() {
        super(Logger.class);
    }

    public Logger(String cls) {
        super(cls);
    }

    @SuppressWarnings("unchecked")
    public Logger(Class cls) {
        super(cls);
    }
}
