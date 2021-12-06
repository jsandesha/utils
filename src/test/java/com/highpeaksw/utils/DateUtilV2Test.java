package com.highpeaksw.utils;

import static org.junit.Assert.*;

import java.time.*;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.highpeaksw.utils.exception.DataException;

/**
 * @author Merry
 * @author sandesha
 */
public class DateUtilV2Test {

    @Test
    public void testGetZoneIdFromShortZoneIdSuccess() throws DataException
    {
        assertEquals(ZoneId.of("Asia/Kolkata"), DateUtilV2.getZoneIdFromShortZoneId("IST"));
        assertEquals(ZoneId.of("UTC"), DateUtilV2.getZoneIdFromShortZoneId("UTC"));
        assertEquals(ZoneId.of("UTC"), DateUtilV2.getZoneIdFromShortZoneId("GMT"));
    }

    @Test
    public void testGetZoneIdFromShortZoneIdInvalidShortId()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getZoneIdFromShortZoneId("IS"));
        assertEquals("Error while fetching zone id from short zone id", dataException.getErrorMessage());
    }

    @Test
    public void testGetZoneIdFromShortZoneIdNullInput()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getZoneIdFromShortZoneId(null));
        assertEquals("Short zone ID is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetUTCLocalDateTimeOffsetBySystemTimeZoneFromMillisecondsForASpecificTime() throws DataException
    {
        long currentMillis = System.currentTimeMillis();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentMillis), ZoneOffset.UTC);
        assertEquals(localDateTime,
                DateUtilV2.getUTCLocalDateTimeOffsetBySystemTimeZoneFromMilliseconds(currentMillis));
    }

    @Test
    public void testConvertAndGetLocalDateTimeInAZoneForATimeToOtherZoneNoInputZoneIsProvided()
    {
        DataException dataException = assertThrows(DataException.class, () -> DateUtilV2
                .convertAndGetLocalDateTimeInAZoneForATimeToOtherZone(System.currentTimeMillis(), null, "IST"));

        assertEquals("Input zone id is required", dataException.getErrorMessage());
    }

    @Test
    public void testConvertAndGetLocalDateTimeInAZoneForATimeToOtherZoneNoRequiredZoneIsProvided()
    {
        DataException dataException = assertThrows(DataException.class, () -> DateUtilV2
                .convertAndGetLocalDateTimeInAZoneForATimeToOtherZone(System.currentTimeMillis(), "IST", null));

        assertEquals("Required zone id is required", dataException.getErrorMessage());
    }

    @Test
    public void testConvertAndGetLocalDateTimeInAZoneForATimeToOtherZoneTwoDiffZones() throws DataException
    {
        long millis = System.currentTimeMillis();
        LocalDateTime localDateTimeIST = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis),
                ZoneId.of("Asia/Kolkata"));
        assertEquals(19800,
                Math.abs(Duration
                        .between(localDateTimeIST,
                                DateUtilV2.convertAndGetLocalDateTimeInAZoneForATimeToOtherZone(millis, "IST", "UTC"))
                        .getSeconds()));
    }

    @Test
    public void testConvertAndGetLocalDateTimeInAZoneForATimeToOtherZoneTwoSameZones() throws DataException
    {
        long millis = System.currentTimeMillis();
        LocalDateTime localDateTimeIST = LocalDateTime.ofInstant(Instant.ofEpochMilli(millis),
                ZoneId.of("Asia/Kolkata"));
        assertEquals(0,
                Math.abs(Duration
                        .between(localDateTimeIST,
                                DateUtilV2.convertAndGetLocalDateTimeInAZoneForATimeToOtherZone(millis, "IST", "IST"))
                        .getSeconds()));
    }

    @Test
    public void testGetFutureLocalDateTimeByNumberOfDaysSuccess() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertEquals(localDateTime.plusDays(2), DateUtilV2.getFutureLocalDateTimeByNumberOfDays(localDateTime, 2));
    }

    @Test
    public void testFutureLocalDateTimeByNumberOfDaysFailureOnAssertion() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertNotEquals(localDateTime.plusDays(3), DateUtilV2.getFutureLocalDateTimeByNumberOfDays(localDateTime, 2));
    }

    @Test
    public void testGetFutureLocalDateTimeByNumberOfDaysNullDateAsInput()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getFutureLocalDateTimeByNumberOfDays(null, 3));
        assertEquals("Input date is missing", dataException.getErrorMessage());
    }

    @Test
    public void testGetFutureLocalDateTimeByNumberOfDaysNullNumberOfDaysAsInput()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getFutureLocalDateTimeByNumberOfDays(LocalDateTime.now(), 0));
        assertEquals("Number of days must be more than 0 to get the future Date", dataException.getErrorMessage());
    }

    @Test
    public void testGetFutureLocalDateTimeByNumberOfSecondsSuccess() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertEquals(localDateTime.plusSeconds(2),
                DateUtilV2.getFutureLocalDateTimeByNumberOfSeconds(localDateTime, 2));
    }

    @Test
    public void testGetFutureLocalDateTimeByNumberOfSecondsFailureOnAssertion() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertNotEquals(localDateTime.plusSeconds(3),
                DateUtilV2.getFutureLocalDateTimeByNumberOfSeconds(localDateTime, 2));
    }

    @Test
    public void testGetFutureLocalDateTimeByNumberOfSecondsOnNullInputDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getFutureLocalDateTimeByNumberOfSeconds(null, 3));
        assertEquals("Start date time is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetFutureLocalDateTimeByNumberOfSecondsOnNullInputSecondsToBeAdded()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getFutureLocalDateTimeByNumberOfSeconds(LocalDateTime.now(), -1));
        assertEquals("Number of seconds must be more than 0 to get the future Date", dataException.getErrorMessage());
    }

    @Test
    public void testGetPastLocalDateTimeByNumberOfDaysSuccess() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertEquals(localDateTime.minusDays(2), DateUtilV2.getPastLocalDateTimeByNumberOfDays(localDateTime, 2));
    }

    @Test
    public void testGetPastLocalDateTimeByNumberOfDaysFailureOnAssertion() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertNotEquals(localDateTime.minusDays(3), DateUtilV2.getPastLocalDateTimeByNumberOfDays(localDateTime, 2));
    }

    @Test
    public void testGetPastLocalDateTimeByNumberOfDaysFailureOnNullInputDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getPastLocalDateTimeByNumberOfDays(null, 3));
        assertEquals("Start date time is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetPastLocalDateTimeByNumberOfDaysFailureOnNegativeSeconds()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getPastLocalDateTimeByNumberOfDays(LocalDateTime.now(), -1));
        assertEquals("Number of days must be more than 0 to get the past Date by that number",
                dataException.getErrorMessage());
    }

    @Test
    public void testGetPastLocalDateTimeByNumberOfSecondsSuccess() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertEquals(localDateTime.minusSeconds(2), DateUtilV2.getPastLocalDateTimeByNumberOfSeconds(localDateTime, 2));

    }

    @Test
    public void testGetPastLocalDateTimeByNumberOfSecondsFailureOnAssertion() throws DataException
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        assertNotEquals(localDateTime.minusSeconds(3),
                DateUtilV2.getPastLocalDateTimeByNumberOfSeconds(localDateTime, 2));
    }

    @Test
    public void testGetPastLocalDateTimeByNumberOfSecondsFailureOnNegativeInputSeconds()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getPastLocalDateTimeByNumberOfSeconds(LocalDateTime.now(), -2));
        assertEquals("Number of seconds must be more than 0 to get the past Date by that number",
                dataException.getErrorMessage());
    }

    @Test
    public void testGetFutureLocalDateByNumberOfDaysSuccess() throws DataException
    {
        assertEquals(LocalDate.now().plusDays(2), DateUtilV2.getFutureLocalDateByNumberOfDays(LocalDate.now(), 2));
    }

    @Test
    public void testGetFutureLocalDateByNumberOfDaysFailureOnAssertion() throws DataException
    {
        assertNotEquals(LocalDate.now().plusDays(3), DateUtilV2.getFutureLocalDateByNumberOfDays(LocalDate.now(), 2));
    }

    @Test
    public void testGetFutureLocalDateByNumberOfDaysFailureOnNullInputDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getFutureLocalDateByNumberOfDays(null, 2));
        assertEquals("Start date is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetFutureLocalDateByNumberOfDaysFailureOnNegativeNumberOfDays()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getFutureLocalDateByNumberOfDays(LocalDate.now(), -2));
        assertEquals("Number of days must be more than 0 to get the future Date by that number",
                dataException.getErrorMessage());
    }

    @Test
    public void testGetPastLocalDateByNumberOfDaysSuccess() throws DataException
    {
        assertEquals(LocalDate.now().minusDays(2), DateUtilV2.getPastLocalDateByNumberOfDays(LocalDate.now(), 2));
    }

    @Test
    public void testGetPastLocalDateByNumberOfDaysFailureOnAssertion() throws DataException
    {
        assertNotEquals(LocalDate.now().minusDays(3), DateUtilV2.getPastLocalDateByNumberOfDays(LocalDate.now(), 2));
    }

    @Test
    public void testGetPastLocalDateByNumberOfDaysNullInputDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getPastLocalDateByNumberOfDays(null, -2));
        assertEquals("Start date is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetPastLocalDateByNumberOfDaysNegativeNumberOfDays()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getPastLocalDateByNumberOfDays(LocalDate.now(), -2));
        assertEquals("Number of days must be more than 0 to get the past Date by that number",
                dataException.getErrorMessage());
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoLocalDatesSuccess() throws DataException
    {
        assertEquals(3,
                DateUtilV2.getTheNumberOfDaysBetweenTwoLocalDates(LocalDate.now(), LocalDate.now().plusDays(3)));
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoLocalDatesFailureOnAssertion() throws DataException
    {
        assertNotEquals(4,
                DateUtilV2.getTheNumberOfDaysBetweenTwoLocalDates(LocalDate.now(), LocalDate.now().plusDays(3)));
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoLocalDatesNullFromInputDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getTheNumberOfDaysBetweenTwoLocalDates(null, LocalDate.now().plusDays(3)));

        assertEquals("Start date is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoLocalDatesNullToInputDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getTheNumberOfDaysBetweenTwoLocalDates(LocalDate.now().plusDays(3), null));
        assertEquals("End date is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoStringDatesSuccess() throws DataException
    {
        assertEquals(3, DateUtilV2.getTheNumberOfDaysBetweenTwoStringDates("02-07-1997", "05-07-1997"));
        assertEquals(5, DateUtilV2.getTheNumberOfDaysBetweenTwoStringDates("26-02-2020", "02-03-2020"));
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoStringDatesFailureOnAssertion() throws DataException
    {
        assertNotEquals(4, DateUtilV2.getTheNumberOfDaysBetweenTwoStringDates("02-07-1997", "05-07-1997"));
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoStringDatesFailureOnNullInputEndDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getTheNumberOfDaysBetweenTwoStringDates("02-07-1997", null));
        assertEquals("End date is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetTheNumberOfDaysBetweenTwoStringDatesFailureOnNullInputStartDate()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getTheNumberOfDaysBetweenTwoStringDates(null, "02-07-1997"));
        assertEquals("Start date is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetLocalDateFromHumanReadableStringDateNullInput()
    {
        DataException dataException = assertThrows(DataException.class,
                () -> DateUtilV2.getLocalDateFromHumanReadableStringDate(null));

        assertEquals("Input string is required", dataException.getErrorMessage());
    }

    @Test
    public void testGetLocalDateFromHumanReadableStringDate() throws DataException
    {
        LocalDate expectedOutput = LocalDate.parse("12-02-2020", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        assertEquals(expectedOutput, DateUtilV2.getLocalDateFromHumanReadableStringDate("12-02-2020"));
    }
}
