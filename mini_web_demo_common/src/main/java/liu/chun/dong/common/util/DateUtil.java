package liu.chun.dong.common.util;

import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author：lijunfu
 *
 */
public class DateUtil {

    private static final Logger logger = Logger.getLogger(DateUtil.class);

    private static final SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final SimpleDateFormat yyyy_MM_dd_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat HH_mm_ss = new SimpleDateFormat("HH:mm:ss");

    /**
     * 获得当前时间的<code>java.util.Date</code>对象
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 获取昨天的这个时间点
     */
    public static String getYesterday(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return yyyy_MM_dd_HH_mm_ss.format(calendar.getTime());
    }

    /**
     *  获取明天的这个时间点
     */
    public static String getTomorrow(){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE, + 1);
        return yyyy_MM_dd_HH_mm_ss.format(calendar.getTime());
    }

    /**
     * 获取比dayTime前day天的时间点
     * @param dayTime
     * @param day
     */
    public static Date daysAgo(String dayTime , int day){
        Calendar calendar = Calendar.getInstance();//得到日历
        calendar.setTime(parseStr2Date(dayTime));
        calendar.add(Calendar.DAY_OF_MONTH, - day);
        return calendar.getTime();
    }

    /**
     * 获取比dayTime后day天的时间点
     * @param dayTime
     * @param day
     */
    public static Date daysAfter(String dayTime , int day){
        Calendar calendar = Calendar.getInstance();//得到日历
        calendar.setTime(parseStr2Date(dayTime));
        calendar.add(Calendar.DAY_OF_MONTH, + day);
        return calendar.getTime();
    }


    /**
     * 获得当前日期时间
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYYYY_MM_dd_HH_mm_ss() {
        return yyyy_MM_dd_HH_mm_ss.format(now());
    }

    /**
     * 获得当前日期时间
     * <p>
     * 日期时间格式yyyyMMddHHmmss
     *
     * @return
     */
    public static String getYYYYMMddHHmmss(){
        return yyyyMMddHHmmss.format(now());
    }

    /**
     * 获得当前日期时间的年-月-日
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYYYY_MM_dd(String dateStr){
        return dateStr.substring(0, 10);
    }

    /**
     * 获得当前日期时间的年-月-日
     * <p>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYYYY_MM(String dateStr){
        return dateStr.substring(0,7);
    }

    /**
     * added by liuchundong 2014-12-22 16:51:53
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return yyyyMMdd
     */
    public static String getYYYYMMDD(String dateStr){
        StringBuilder stringBuilder = new StringBuilder();
        if (dateStr==null){
            return null;
        }else {
            String[] dateTime= dateStr.split(" ");
            String[] date_temp = dateTime[0].split("-");
            for(String temp : date_temp){
                stringBuilder.append(temp);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * added by liuchundong 2014-12-22 16:51:53
     * @param dateStr yyyy-MM-dd HH:mm:ss
     * @return HHmmss
     */
    public static String getHHMMSS(String dateStr){
        StringBuilder stringBuffer = new StringBuilder();
        if (dateStr==null){
            return null;
        }else {
            String[] dateTime= dateStr.split(" ");
            String[] date_temp = dateTime[1].split(":");
            for(String temp : date_temp){
                stringBuffer.append(temp);
            }
        }
        return stringBuffer.toString();
    }

    /**added by liuchundong 2015-04-08 14:49:19
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getYYYYMMDD(Date date){
        String dateStr = date != null ? formatDate(date) : null;
        //dateStr格式为 YYYY-MM-DD
        StringBuilder stringBuilder = new StringBuilder();
        if (dateStr == null){
            return null;
        } else {
            String[] dateTime= dateStr.split(" ");
            String[] date_temp = dateTime[0].split("-");
            for(String temp : date_temp){
                stringBuilder.append(temp);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * added by liuchundong 2014-12-22 16:51:53
     * @param date yyyy-MM-dd HH:mm:ss
     * @return HHmmss
     */
    public static String getHHMMSS(Date date){
        StringBuilder stringBuffer = new StringBuilder();
        String dateStr = date != null ? parseDate2Str(date) : null;
        if (dateStr==null){
            return null;
        }else {
            String[] dateTime= dateStr.split(" ");
            String[] date_temp = dateTime[1].split(":");
            for(String temp : date_temp){
                stringBuffer.append(temp);
            }
        }
        return stringBuffer.toString();
    }

    /**
     * yyyy-MM-dd HH:mm:ss 转成date
     * @param dateStr
     * @return
     */
    public static Date parseStr2Date(String dateStr){
        Date date = null;
        try {
            if(dateStr == null || "".equals(dateStr)){
                return null;
            }
            date = yyyy_MM_dd_HH_mm_ss.parse(dateStr);
        } catch (ParseException e) {
            logger.error("|ParseException|" , e);
        }
        return date;
    }

    /**
     * date 转成 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String parseDate2Str(Date date){
        return yyyy_MM_dd_HH_mm_ss.format(date);

    }

    public static String formatDate(Date date){
        return yyyy_MM_dd.format(date);
    }

    /**
     * 根据时间获得毫秒数
     */
    public static long parseDate2Long(Date date){
        Long time = null;
        try {
            time = date.getTime();
        } catch (Exception e) {
            logger.error("|Exception|", e);
        }
        return time;
    }

    /**
     * 根据时间获得毫秒数
     */
    public static long parseDate2Long(String dateStr){
        Long time = null;
        try {
            time = parseStr2Date(dateStr).getTime();
        } catch (Exception e) {
            logger.error("|Exception|" , e);
        }
        return time;
    }

    /**
     * 当前时间与dateStr相差几天
     */
    public static float compareNow(String dateStr){
        float day = 0 ;
        try {
            Long start = new Date().getTime();
            Long end = yyyy_MM_dd_HH_mm_ss.parse(dateStr).getTime();
            day = (start - end) / (1000 * 3600 * 24);
        } catch (Exception e) {
            logger.error("|Exception|", e);
        }
        return day;
    }

    /**
     * 当前时间的前N秒的时间点
     */
    public static Date getXminSecAgo(String dateTime ,int second){
        Calendar calendar  = null;
        try {
            calendar = Calendar.getInstance();//得到日历
            calendar.setTime(yyyy_MM_dd_HH_mm_ss.parse(dateTime));
            calendar.add(Calendar.SECOND, - second);
        } catch (Exception e) {
            logger.error("|Exception|" , e);
        }
        return calendar.getTime();
    }

    /**
     * 当前时间的后N秒的时间点
     */
    public static Date getXminSecAfter(String dateTime , int second){
        Calendar calendar  = null;
        try {
            calendar = Calendar.getInstance();//得到日历
            calendar.setTime(yyyy_MM_dd_HH_mm_ss.parse(dateTime));
            calendar.add(Calendar.SECOND, + second);
        } catch (Exception e) {
            logger.error("|Exception|", e);
        }
        return calendar.getTime();
    }

    public static void main(String[] args){
        String dateTime = DateUtil.parseDate2Str(new Date());
        System.out.println(getYYYYMMDD(dateTime));
        System.out.println(getHHMMSS(dateTime));
        System.out.println(getYYYYMMDD(now()));
    }

}