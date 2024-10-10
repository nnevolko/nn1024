package com.steeka;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 Functional Interface
 Check whether the date provided falls on the Holiday per specification:

2 Holidays a year
1. Independence day.
 If falls on the weekend -
    if Saturady - Observed on Friday
    If Sunday - Observed on Monday
2. Labor day. First Monday in September

* */

public class HolidayChecker {

    private static final int YEARS_TO_CHECK = 25;
    public final static Map<Integer, List<LocalDate>> holidayCache;

    static {
        holidayCache = Collections.unmodifiableMap(initializeCache());
    }

    private static Map<Integer, List<LocalDate>> initializeCache() {
        //refactor later to include Zone
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();

        // if the arguments of the range are primitive and what we need is the class than we need to use
        List<Integer> years =
                IntStream.range(currentYear - YEARS_TO_CHECK, currentYear + YEARS_TO_CHECK)
                        .boxed()
                        .collect(Collectors.toList());

        return generateHolidaysForYearsBlock(years);
    }

    public static Map<Integer, List<LocalDate>> generateHolidaysForYearsBlock(List<Integer> years) {
        //Optimization: possibly refactor with streams
        //Also may have to take these calculations into a Utility Method
        Map<Integer, List<LocalDate>> yearHolidays = new HashMap<>();
        for (Integer year : years) {
            List<LocalDate> holidaysThisYear = new ArrayList<>();
            holidaysThisYear.add(calculateIndependenceDayHoliday(year));
            holidaysThisYear.add(calculateLaborDayHoliday(year));
            yearHolidays.put(year, holidaysThisYear);
        }
        return yearHolidays;
    }


    public static LocalDate calculateLaborDayHoliday(Integer year) {
        //grab first day of september and derive Labor day holiday
        LocalDate septemberFirst = LocalDate.of(year, 9, 1);
        var laborDayHoliday = septemberFirst;
        if (septemberFirst.getDayOfWeek() != DayOfWeek.MONDAY) {
            //next week Monday would have been Sunday 7 + 1
            int daysToAdjust = DayOfWeek.SUNDAY.getValue() + 1 - septemberFirst.getDayOfWeek().getValue(); // use enum values to figure out next Monday
            laborDayHoliday = laborDayHoliday.plusDays(daysToAdjust);
        }
        return laborDayHoliday;
    }

    public static LocalDate calculateIndependenceDayHoliday(Integer year) {
        LocalDate independenceDayHoliday = LocalDate.of(year, 07, 04);
        switch (independenceDayHoliday.getDayOfWeek()) {
            case SATURDAY:
                independenceDayHoliday = independenceDayHoliday.minusDays(1);
                break;
            case SUNDAY:
                independenceDayHoliday = independenceDayHoliday.plusDays(1);
                break;
            default:
        }
        return independenceDayHoliday;
    }

    public void check(LocalDate checkoutDate, int daysRented) {
        // Thougts
        // consider precalculating holidays for the plus 50 and  minus 50 years
        // that would work as cache

    }
}
