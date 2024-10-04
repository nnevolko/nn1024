package com.steeka;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
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

    private static Map<Integer, List<LocalDate>> generateHolidaysForYearsBlock(List<Integer> years) {
        //Optimization: possibly refactor with streams
        //Also may have to take these calculations into a Utility Method
        Map<Integer, List<LocalDate>> yearHolidays = new HashMap<>();
        for (Integer year : years) {
            List<LocalDate> holidaysThisYear = new ArrayList<>();
            calculateIndependenceDayHoliday(year, holidaysThisYear);
            calculateLaborDayHoliday(year, holidaysThisYear);
            yearHolidays.put(year, holidaysThisYear);
        }
        return yearHolidays;
    }


    private static void calculateLaborDayHoliday(Integer year, List<LocalDate> holidaysThisYear) {
        //grab first day of september and derive Labor day holiday
        LocalDate septemberFirst = LocalDate.of(year, 9, 1);
        LocalDate laborDay = septemberFirst;
        if (septemberFirst.getDayOfWeek() == DayOfWeek.MONDAY){
            laborDay= septemberFirst;
        } else{
            //calculate here
        }
        holidaysThisYear.add(laborDay);
    }

    private static void calculateIndependenceDayHoliday(Integer year, List<LocalDate> holidaysThisYear) {
        LocalDate independenceDay = LocalDate.of(year, 07, 04);
        switch (independenceDay.getDayOfWeek()) {
            case SATURDAY:
                holidaysThisYear.add(independenceDay.minusDays(1));
                break;
            case SUNDAY:
                holidaysThisYear.add(independenceDay.plusDays(1));
                break;
            default:
                holidaysThisYear.add(independenceDay);
        }
    }

    public void check(LocalDate checkoutDate, int daysRented) {
        // Thougts
        // consider precalculating holidays for the plus 50 and  minus 50 years
        // that would work as cache

    }
}
