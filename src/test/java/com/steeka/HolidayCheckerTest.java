package com.steeka;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HolidayCheckerTest {

    @Test
    void generateHolidaysForYearsBlockTest() {

        List<Integer> yearsToGenerate = Arrays.asList(2020, 2021, 2022, 2023, 2024, 2025, 2026);
        Map<Integer, List<LocalDate>> holidayMap = HolidayChecker.generateHolidaysForYearsBlock(yearsToGenerate);
        assertEquals(holidayMap.size(), yearsToGenerate.size());

        List<LocalDate> holidays2021 = holidayMap.get(2021);
        assertEquals(holidays2021.get(0).getDayOfMonth(), 5); //holiday was on Sunday so adjusted day off was on Monday
        assertEquals(holidays2021.get(1).getDayOfMonth(), 6); //september 6th

        List<LocalDate> holidays2026 = holidayMap.get(2026);
        assertEquals(holidays2026.get(1).getDayOfMonth(), 7); //labor day is Monday september 7th
    }

    @Test
    void calculateLaborDayHoliday() {

        LocalDate laborDayHoliday = HolidayChecker.calculateLaborDayHoliday(2024);
        assertEquals(laborDayHoliday.getDayOfMonth(), 2);
    }

    @Test
    public void calculateIndependenceDayHolidayTest() {

        LocalDate independenceDayHoliday1 = HolidayChecker.calculateIndependenceDayHoliday(2024);
        assertEquals(independenceDayHoliday1.getDayOfWeek(), DayOfWeek.THURSDAY);

        LocalDate independenceDayHoliday2 = HolidayChecker.calculateIndependenceDayHoliday(2021);
        assertEquals(independenceDayHoliday2.getDayOfMonth(), 5);
        assertEquals(independenceDayHoliday2.getDayOfWeek(), DayOfWeek.MONDAY);

    }


    @Test
    public void checkTest() {


    }
}