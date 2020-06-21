package com.lyq.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class NewTime {
    public static void main(String[] args) {
        LocalDate now1 = LocalDate.now();
        System.out.println(now1.getYear());
        System.out.println(now1);

        LocalTime now2 = LocalTime.now();
        System.out.println(now2);

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        System.out.println(now.format(f));

        LocalDate of = LocalDate.of(2010, 12, 3);
        //判断一个日期是在另一个日期之前或之后
        boolean after = now1.isAfter(of);
        System.out.println(after);
        boolean before = now1.isBefore(of);
        System.out.println(before);

        //判断两个日期是否相同
        boolean equal = now1.isEqual(of);
        System.out.println(equal);
        //判断是不是闰年
        boolean leapYear = of.isLeapYear();
        System.out.println(leapYear);

        // 添加年月日时分秒的方法 plus系列的方法 都会返回一个新的LocalDateTime的对象
        //每次加完时间量，都会返回一个新的日期对象
        LocalDateTime localDateTime = now.plusDays(10);
        System.out.println(localDateTime);
        LocalDateTime localDateTime1 = now.plusYears(2);
        System.out.println(localDateTime1);


        //减去年月日时分秒的方法 minus 系列的方法 注意都会返回一个新的LocalDateTime的对象
        LocalDateTime localDateTime2 = now.minusYears(2);
        System.out.println(localDateTime2);

        Instant now3 = Instant.now();
        long epochSecond = now3.getEpochSecond();//获取的秒值，从计算机元年到当前时刻
        System.out.println(epochSecond);
        long l = now3.toEpochMilli();//获取的是毫秒值
        System.out.println(l);


        Instant start = Instant.now();
        for (int i = 0; i < 100000; i++) {
            System.out.println(' ');
        }
        Instant end = Instant.now();
        //Duration 可以计算两个时间的间隔
        Duration between = Duration.between(start, end);
        long l1 = between.toMillis();
        System.out.println(l);

        LocalDate birthday = LocalDate.of(1995, 10, 10);
        LocalDate now4 = LocalDate.now();
        //Period 计算两个日期的间隔
        Period between1 = Period.between(birthday, now4);
        int years = between1.getYears();
        int months = between1.getMonths();
        int days = between1.getDays();
        System.out.println(years);
        System.out.println(months);
        System.out.println(days);
    }
}