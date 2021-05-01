package com.daema.rest.common.util;

import com.daema.rest.common.exception.NonExistDateException;

import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>DateUtil 클래스는 일반적인 날짜 계산을 해주는 static 메소드들을 포함하고 있다. 예를 들면 지정된 일자의 몇 일 전
 * 또는 후 일자, 지정된 두 일자간의 차일 수, 지정된 일자의 해당년도의 총 일수, 지정된 일자의 몇 개월 전 또는 후 말일이나 초일 등을 계산해
 * 주는 유틸리티 특성의 메소드들을 포함한다. 날자 관련 업무인 영업일이나 휴일 처리를 하는 부분은 이 클래스에 포함되어 있지 않다.<br>
 * &nbsp;</p>
 */
public class DateUtil {

    /**
     * 어플리케이션에서 날짜들은 yyyyMMdd 포맷(예: 20020622) 등의 스트링 타입으로 처리되어야 한다
     * 이 클래스 변수의 값은 yyyyMMdd 로 초기화되어 있다
     */
    private static final String DFLT_IN_FORMAT = "yyyyMMdd";
    private static final String DFLT_HHmmss_IN_FORMAT = "yyyyMMddHHmmss";
    private static final String DFLT_OUT_FORMAT = "yyyyMMdd";

    private static final int BEFORE = -1;
    private static final int AFTER = 1;
    private static final int EQUAL = 0;

    public DateUtil() {
    }

    public static String getCurrentDate() {
        String dateFormat = "yyyyMMdd";
        Format df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }

    public static String getCurrentDateTime() {
        String dateFormat = "yyyyMMddHHmmss";
        Format df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }


    public static String getCurrentDateTimeMilli() {
        String dateFormat = "yyMMddHHmmssS";
        Format df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }


    /**
     * date 로 받은 날자가 그레고리안단력(양력 달력)에 존재하는 날자인지를 확인해준다
     *
     * @param date 검증받고 싶은 날자
     * @return 실존하는 날자일 경우 true, 존재하지 않을 경우 false 를 리턴
     * @throws ParseException 입력날자의 포맷이 틀렸을 경우 던져진다<br>
     *                        예를 들면 2002/02/04, 2002-05-06 등을 파라미터로 받을 경우 예외가 발생한다
     */
    public static boolean isExist(String date) throws ParseException {
        try {
            stringToDate(date, DFLT_IN_FORMAT);
        } catch (ParseException pEx) {
            if (pEx instanceof NonExistDateException)
                return false;
            else
                throw pEx;
        }
        return true;
    }

    /**
     * date로 받은 날자가 그레고리안단력(양력 달력)에 존재하는 날짜인지를 확인해준다
     * 이 메소드는 yyyyMMdd뿐만 아니라 yyMMdd형태도 지원한다.
     *
     * @param date     검증받고 싶은 날자
     * @param baseYear 기준 year, date가 yyMMdd인 경우에 baseYear보다 크다면
     *                 19를 앞에 부치고 작다면 20를 부친다.
     * @return 실존하는 날자일 경우 true, 존재하지 않을 경우 false 를 리턴
     * @throws ParseException 입력날자의 포맷이 틀렸을 경우 던져진다<br>
     *                        예를 들면 2002/02/04, 2002-05-06 등을 파라미터로 받을 경우
     *                        예외가 발생한다.
     */
    public static boolean isExist(String date, int baseYear)
            throws ParseException {
        if (date == null && date.length() < 6)
            return false;
        if (date != null && date.length() == 6) {
            int yy = Integer.parseInt(date.substring(0, 2));
            if (yy > baseYear) {
                date = 19 + date;
            } else {
                date = 20 + date;
            }
        } else if (date != null && date.length() == 8) {
            int yyyy = Integer.parseInt(date.substring(0, 4));
            if (yyyy > baseYear) {
                date = 19 + date.substring(2);
            } else {
                date = 20 + date.substring(2);
            }
        }
        return isExist(date);
    }

    /**
     * 지정된 일자의 몇일 후 일자를 얻을 수 있다
     *
     * @param date 지정된 날짜
     * @param day  몇일 후로 지정할 지 입력한다
     * @return 몇일 후 일자를 리턴
     * @throws ParseException
     */
    public static String afterSpecDay(String date, int day) throws ParseException {  // 지정된 일자의 몇일후 날짜
        Calendar calendar = stringToCalendar(date);
        calendar.add(Calendar.DATE, day);     // calendar 자체는 변경이 되면 안되니...
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자의 몇년,몇개월,몇일 후 일자를 얻을 수 있다
     *
     * @param date
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String afterSpecDay(String date, int year, int month, int day) throws ParseException {
        Calendar calendar = stringToCalendar(date);
        calendar.add(Calendar.YEAR, year);
        calendar.add(Calendar.MONTH, month);
        calendar.add(Calendar.DATE, day);     // calendar 자체는 변경이 되면 안되니...
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자의 몇일 전 일자를 얻을 수 있다
     *
     * @param date
     * @param day
     * @return
     * @throws ParseException
     */
    public static String beforeSpecDay(String date, int day) throws ParseException { // 지정된 일자의 몇일 전 날짜
        Calendar calendar = stringToCalendar(date);
        calendar.add(Calendar.DATE, -day);
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자의 몇년,몇개월,몇일 전 일자를 얻을 수 있다
     *
     * @param date  지정된 일자
     * @param year  몇년
     * @param month 몇달
     * @param day   몇일
     * @return 이전 일자를 문자열 형태로 반환한다.
     * @throws ParseException
     */
    public static String beforeSpecDay(String date, int year, int month, int day) throws ParseException { // 지정된 일자의 몇일 전 날짜
        Calendar calendar = stringToCalendar(date);
        calendar.add(Calendar.YEAR, -year);
        calendar.add(Calendar.MONTH, -month);
        calendar.add(Calendar.DATE, -day);
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자의 해당년도 총일수를 리턴한다
     *
     * @param date 지정된 일자를 나타내는 문자열
     * @return int 지정된 일자의 해당년도 총 일수를 반환
     * @throws ParseException
     */
    public static int getDayOfYear(String date) throws ParseException { // 지정된 일자의 해당년도의 총일 수
        Calendar calendar = stringToCalendar(date);
        if (((GregorianCalendar) calendar).isLeapYear(calendar.get(Calendar.YEAR)))
            return 366;
        else
            return 365;
    }

    /**
     * 지정된 일자의 몇 개월 전 초일을 리턴한다
     *
     * @param date  지정된 일자
     * @param month 전으로 세팅해야 할 몇달
     * @return 지정된 일자의 몇 개월 전 초일을 리턴한다
     * @throws ParseException
     */
    public static String getFirstDayBeforeMonth(String date, int month) throws ParseException {// 지정된 일자의 몇개월 전 초일
        Calendar calendar = stringToCalendar(date);
        calendar.add(Calendar.MONTH, -month);                        // 몇개월 전으로 셋
        int firstDay = 1;
        calendar.set(Calendar.DATE, firstDay);                       // 초일로 셋
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자의 몇 개월 전 말일을 리턴한다
     *
     * @param date
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getLastDayOfBeforeMonth(String date, int month) throws ParseException { // 지정된 일자의 몇개월 전 말일
        Calendar calendar = stringToCalendar(date);

        calendar.add(Calendar.MONTH, -month);                        // 몇개월 전으로 셋
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDay);                       // 말일로 셋
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자의 몇 개월 후 초일을 리턴한다
     *
     * @param date
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getFirstDayAfterMonth(String date, int month) throws ParseException {// 지정된 일자의 몇개월 후 초일
        Calendar calendar = stringToCalendar(date);

        calendar.add(Calendar.MONTH, month);                        // 몇개월 후로 셋
        int firstDay = 1;// 초일???
        calendar.set(Calendar.DATE, firstDay);                       // 초일로 셋
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자의 몇 개월 후 말일을 리턴한다
     *
     * @param date
     * @param month
     * @return
     * @throws ParseException
     */
    public static String getLastDayAfterMonth(String date, int month) throws ParseException { // 지정된 일자의 몇개월 후 말일
        Calendar calendar = stringToCalendar(date);

        calendar.add(Calendar.MONTH, month);                        // 몇개월 후로 셋
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDay);                       // 말일로 셋
        return printCalendar(calendar);
    }

    /**
     * 지정된 일자가 말일인지 검증한다
     *
     * @param date
     * @return 말일이면 true, 말일이 아니면 false 를 리턴한다
     * @throws ParseException
     */
    public static boolean isLastOfMonth(String date) throws ParseException { // 지정한 일자가 월말인지 체크
        Calendar calendar = stringToCalendar(date);
        return calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 지정된 두 일자간의 차일수
     *
     * @param fromDate 첫 번째 날자
     * @param toDate   두 번째 날자
     * @return 두일자의 차일수를 반환
     * @throws ParseException
     */
    public static int getBetweenDates(String fromDate, String toDate) throws ParseException {// 지정된 두 일자간의 차일수
        long betWeenTime = Math.abs(stringToDate(fromDate).getTime() - stringToDate(toDate).getTime());
        return (int) (betWeenTime / (1000 * 60 * 60 * 24));
    }

    /**
     * 지정된 두 시간간의 차이 시간(초)
     *
     * @param fromDate(yyyyMMddHHmmss) 첫 번째 날자시간
     * @param toDate(yyyyMMddHHmmss)   두 번째 날자시간
     * @return 두시간의 차일시간을 반환
     * @throws ParseException
     */
    public static int getBetweenDateTimes(String fromDate, String toDate) throws ParseException {// 지정된 두 시간간의 차이 시간(초)
        long betWeenTime = Math.abs(stringToDate(fromDate, "yyyyMMddHHmmss").getTime() - stringToDate(toDate, "yyyyMMddHHmmss").getTime());
        return (int) (betWeenTime / (1000));
    }

    /**
     * java.util.Calendar 인스턴스를 지정된 출력 포맷을 통해 스트링 타입으로 변환해준다.
     * 현재 버전은 다양한 입출력 포맷을 지원하지 않으므로 calendarToString() 메소드와 같다<br>
     * calendarToString() 은 입력포맷을 적용하여 스트링 타입으로 변환한다.   *
     *
     * @param cal
     * @return
     */
    private static String printCalendar(Calendar cal) {
        SimpleDateFormat formatter = new SimpleDateFormat(DFLT_OUT_FORMAT);
        Date date = cal.getTime();
        return formatter.format(date);
    }

    /**
     * 현재 시간을 yyyyMMdd 타입으로 리턴해준다
     *
     * @return
     */
    public static String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(new Date());
    }

    public static Calendar getNowCalendar() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal;
    }

    /**
     * java.lang.String 타입을 java.util.Date 타입으로 변환한다
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String s) throws ParseException {
        return stringToDate(s, DFLT_IN_FORMAT);
    }

    /**
     * java.lang.String 타입을 java.util.Date 타입으로 변환한다
     * 지원하는 format은 yyMMdd와 yyyyMMddd이다.
     *
     * @param s
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String date, int baseYear) throws ParseException {
        if (date != null && date.length() == 6) {
            int yy = Integer.parseInt(date.substring(0, 2));
            if (yy > baseYear) {
                date = 19 + date;
            } else {
                date = 20 + date;
            }
        } else if (date != null && date.length() == 8) {
            int yyyy = Integer.parseInt(date.substring(0, 4));
            if (yyyy > baseYear) {
                date = 19 + date.substring(2);
            } else {
                date = 20 + date.substring(2);
            }
        }
        return stringToDate(date);
    }

    /**
     * java.lang.String 타입을 java.util.Date 타입으로 변환한다.
     * 변환하는 과정에서 DateFormat.parse() API 를 사용하므로 변환하는 중에 파리미터로 입력받은 s, 즉 날자의
     * 포맷과 DateFormat 생성시 사용한 포맷이 동일한 지도 체크된다.
     * 예를 들면 s 가 2000/02/05 일 경우 현재 버전에서는 입력포맷을 yyyyMMdd 만 사용하므로 두 포맷이
     * 다르기 때문에 java.text.ParseException 이 발생한다<br>
     *
     * @param s
     * @param inFormat
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String s, String inFormat) throws ParseException {
        if (s == null)
            throw new ParseException("date string is null", 0);
        if (inFormat == null)
            throw new ParseException("format string is null", 0);

        SimpleDateFormat formatter = new SimpleDateFormat(inFormat);
        Date date = null;

        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            throw new ParseException(" wrong format or date :\"" + s +
                    "\" with format \"" + inFormat + "\"", 0);
        }
        // ex) s가 2002/02/03 일때, formatter 가 디폴트로 yyyy/MM/dd 로 셋팅되어 있으므로 아래와 같은 로직이 가능하다
        // 항상 s 문자열의 포맷으로 formatter 를 생성해야 한다.
        if (!formatter.format(date).equals(s))
            throw new NonExistDateException(
                    "Out of bound date:\"" + s + "\" with format \"" + inFormat + "\"");

        return date;
    }

    /**
     * java.util.Date 타입을  java.lang.String 타입으로 변환한다
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        return dateToString(date, DFLT_IN_FORMAT);
    }

    /**
     * java.util.Date 타입을  java.lang.String 타입으로 변환한다
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String inFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(inFormat);
        return formatter.format(date);
    }

    /**
     * java.util.Date 타입을 java.util.Calendar 타입으로 변환한다
     *
     * @param date
     * @return
     */
    private static Calendar dateToCalendar(Date date) {
        Calendar cal = getNowCalendar();
        cal.setTime(date);
        return cal;
    }


    /**
     * java.util.Calendar 타입을 java.util.Date 타입으로 변환한다
     *
     * @param cal
     * @return
     */
    public static Date calendarToDate(Calendar cal) {
        return cal.getTime();
    }

    /**
     * java.lang.String 타입을 java.util.Calendar 타입으로 변환한다
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Calendar stringToCalendar(String date) throws ParseException {
        return stringToCalendar(date, DFLT_IN_FORMAT);
    }

    public static Calendar stringToCalendarHHmmss(String date) throws ParseException {
        return stringToCalendar(date, DFLT_HHmmss_IN_FORMAT);
    }

    /**
     * java.lang.String 타입을 java.util.Calendar 타입으로 변환한다
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Calendar stringToCalendar(String date, String inFormat) throws ParseException {
        Date transDate = stringToDate(date, inFormat);
        return dateToCalendar(transDate);
    }

    /**
     * java.util.Calendar 타입을 java.lang.String 타입으로 변환한다
     *
     * @param cal
     * @return
     */
    public static String calendarToString(Calendar cal) {
        return calendarToString(cal, DFLT_IN_FORMAT);
    }

    /**
     * java.util.Calendar 타입을 java.lang.String 타입으로 변환한다
     *
     * @param cal
     * @return
     */
    public static String calendarToString(Calendar cal, String inFormat) {
        return dateToString(cal.getTime(), inFormat);
    }

    /**
     * 두개의 데이타 해당하는 문자열을 받아서 비교한후 나중 날짜를 문자열로 반환해 준다.
     *
     * @param firstData 비교할 첫번째 데이타
     * @return String 두 날짜 중 이후 날짜를 반환
     * @Param secondData 비교할 첫번째 데이타
     */
    public static String getAfterDateWithCompare(String firstData, String secondData) throws ParseException {
        if (isExist(firstData) && isExist(secondData)) {
            Calendar first = stringToCalendar(firstData);
            Calendar second = stringToCalendar(secondData);
            if (first.after(second)) {
                return calendarToString(first);
            } else if (first.before(second)) {
                return calendarToString(second);
            }
            return firstData;
        } else {
            throw new NonExistDateException(" wrong format or date: firstDataFormat : " + firstData + " seocondDataFormat : " + secondData);
        }
    }

    /**
     * 두개의 데이타 해당하는 문자열을 받아서 비교한후 이전 날짜를 문자열로 반환해 준다.
     *
     * @param firstData 비교할 첫번째 데이타
     * @return String 두 날짜 중 이전 날짜를 반환
     * @Param secondData 비교할 첫번째 데이타
     */
    public static String getBeforeDateWithCompare(String firstData, String secondData) throws ParseException {
        if (isExist(firstData) && isExist(secondData)) {
            Calendar first = stringToCalendar(firstData);
            Calendar second = stringToCalendar(secondData);
            if (first.before(second)) {
                return calendarToString(first);
            } else if (first.after(second)) {
                return calendarToString(second);
            }
            return firstData;
        } else {
            throw new NonExistDateException(" wrong format or date: firstDataFormat : " + firstData + " seocondDataFormat : " + secondData);
        }
    }

    /**
     * 두개의 데이타 해당하는 문자열을 받아서 비교한후 상수 값 형태로 반환한다.
     * 0은 두 데이터가 동일, 1은 첫번째 데이터가 큰 경우, -1은 두번째 데이터가 큰 경우
     *
     * @param firstData 비교할 첫번째 데이타
     * @return int
     * @Param secondData 비교할 첫번째 데이타
     */
    public static int compareBetweenDates(String firstData, String secondData) throws ParseException {
        if (isExist(firstData) && isExist(secondData)) {
            Calendar first = stringToCalendar(firstData);
            Calendar second = stringToCalendar(secondData);
            if (first.before(second)) {
                return DateUtil.BEFORE;
            } else if (first.after(second)) {
                return DateUtil.AFTER;
            }
            return DateUtil.EQUAL;
        } else {
            throw new NonExistDateException(" wrong format or date: firstDataFormat : " + firstData + " secondDataFormat : " + secondData);
        }
    }

    public static int compareBetweenDatesHHmmss(String firstData, String secondData) throws ParseException {
        if (isExist(firstData.substring(0, 8)) && isExist(secondData.substring(0, 8))) {
            Calendar first = stringToCalendarHHmmss(firstData);
            Calendar second = stringToCalendarHHmmss(secondData);
            if (first.before(second)) {
                return DateUtil.BEFORE;
            } else if (first.after(second)) {
                return DateUtil.AFTER;
            }
            return DateUtil.EQUAL;
        } else {
            throw new NonExistDateException(" wrong format or date: firstDataFormat : " + firstData + " secondDataFormat : " + secondData);
        }
    }

    public static String replace(String source, char ch, String replace) {
        return replace(source, ch, replace, -1);
    }

    public static String replace(
            String source,
            char ch,
            String replace,
            int max) {
        return replace(source, ch + "", replace, max);
    }

    public static String replace(
            String source,
            String original,
            String replace) {
        return replace(source, original, replace, -1);
    }

    public static String replace(
            String source,
            String original,
            String replace,
            int max) {
        if (null == source)
            return null;
        int nextPos = 0; // 다음 position
        int currentPos = 0; // 현재 position
        int len = original.length();
        StringBuffer result = new StringBuffer(source.length());
        while ((nextPos = source.indexOf(original, currentPos)) != -1) {
            result.append(source.substring(currentPos, nextPos));
            result.append(replace);
            currentPos = nextPos + len;
            if (--max == 0) { // 바꿀 횟수를 줄어준다
                break;
            }
        }
        if (currentPos < source.length()) {
            result.append(source.substring(currentPos));
        }
        return result.toString();
    }

    public static String getYear(String src) {
        String year = "";
        if (src != null && src.length() >= 8) {
            src = DateUtil.replace(src, "-", "");
            year = src.substring(0, 4);
        }
        return year;
    }

    public static String getMonth(String src) {
        String month = "";

        if (src != null && src.length() >= 8) {
            src = DateUtil.replace(src, "-", "");
            month = src.substring(4, 6);
        }
        return month;
    }

    public static String getDay(String src) {
        String day = "";

        if (src != null && src.length() >= 8) {
            src = DateUtil.replace(src, "-", "");
            day = src.substring(6);
        }
        return day;
    }

    public static String getDate(String src) {
        String date = "";
        if (src != null && src.length() >= 8) {
            src = DateUtil.replace(src, "-", "");
            date = src.substring(0, 8);
        }
        return date;
    }

    public static String getUnixTimeStamp() {
        Calendar c = Calendar.getInstance();

        return Long.toString(c.getTimeInMillis() / 1000);
    }

    /**
     * 특정 unix timestamp 를 String 날짜로 변환
     */
    public static String getUnixTimeToString(String unixTS) {
        String convDate = "";
        long tmpDateTime = new BigDecimal(unixTS).longValue();

        if (String.valueOf(tmpDateTime).length() == 13) {
            tmpDateTime = tmpDateTime / 1000;
        } else {
            //예외처리
            tmpDateTime = 0;
        }

        if (tmpDateTime != 0) {
            Date date = new Date(tmpDateTime * 1000L);
            convDate = DateUtil.dateToString(date);
        }

        return convDate;
    }

    /**
     * 특정 요일의 일자
     *
     * @return Calendar.SUNDAY....
     */
    public static String getSpecWeekDay(int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, day);
        return dateToString(c.getTime());
    }

    /**
     * 현재 날짜의 주차
     *
     * @return
     */
    public static String getWeek() {
        Calendar c = Calendar.getInstance();
        String week = String.valueOf(c.get(Calendar.WEEK_OF_MONTH));
        return week;
    }

    public static void main(String[] agrs) {
        try {
            Calendar car = Calendar.getInstance();
            //System.out.println(DateUtil.printCalendar(car));
            System.out.println(getCurrentDateTimeMilli());
            //getBetweenDateTimes
			/*
			 *
			 *  public static int getBetweenDates(String fromDate, String toDate) throws ParseException
  {// 지정된 두 일자간의 차일수
    long betWeenTime = Math.abs(stringToDate(fromDate).getTime() - stringToDate(toDate).getTime());
    return (int) (betWeenTime / (1000*60*60*24));
  }
			 */
            System.out.println(getBetweenDates("20100614", getCurrentDate()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 해당 날짜가 포함된 해당년도 주 구하기
     *
     * @param date 지정된 일자를 나타내는 문자열
     * @return int 지정된 일자의 해당년도 주를 반환
     * @throws ParseException
     */
    public static int getWeekOfYear(String date) throws ParseException { // 지정된 일자의 해당년도의 주
        Calendar calendar = stringToCalendar(date);
        return calendar.get(calendar.WEEK_OF_YEAR);
    }


    /**
     * 두 날짜 사이의 날짜들 구하기
     *
     * @param String start_date, String end_date
     * @return String days
     * @throws ParseException
     */
    public static List<String> getBetweenDays(String start_date, String end_date) throws ParseException {
        Date startDate = stringToDate(start_date);
        Date endDate = stringToDate(end_date);

        List<String> dates = new ArrayList<String>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            String day = dateToString(result);
            dates.add(day);
            calendar.add(calendar.DATE, 1);
        }

        return dates;
    }

    /**
     * String 타입 날짜와 포맷형식을 인자값으로 전달 -> 포맷변경
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static String getFormatDate(String date, String format) throws ParseException {
        String dateFormat = format;
        Format df = new SimpleDateFormat(dateFormat);
        return df.format(stringToDate(date));
    }

    /**
     * 오늘날짜 년도(yyyy) 구하기
     *
     * @return
     */

    public static String getTodayYYYY() {
        return getTodayYYYYMM().substring(0, 4);
    }

    /**
     * 오늘날짜 년, 월(yyyyMM) 구하기
     *
     * @return
     */

    public static String getTodayYYYYMM() {
        String dateFormat = "yyyyMM";
        Format df = new SimpleDateFormat(dateFormat);
        return df.format(new Date());
    }

    /**
     * 오늘 날짜의 일자(dd) 구하기
     */
    public static int getTodayDD() throws ParseException {

        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 현재 날짜의 특정 요일 구하기
     */
    public static String getSpecificDateOnCurrentWeek(String today, int dayCount) throws ParseException {
        Date start_day = stringToDate(today);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start_day);

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.add(Calendar.DATE, dayCount);

        Date result = calendar.getTime();

        return dateToString(result);
    }

    public static Long getLongUnixTimeStamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

}
