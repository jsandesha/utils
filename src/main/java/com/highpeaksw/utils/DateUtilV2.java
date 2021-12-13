package com.highpeaksw.utils;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.*;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;

import com.highpeaksw.utils.constants.GeneralConstants;
import com.highpeaksw.utils.exception.DataException;

import lombok.extern.slf4j.Slf4j;

/**
 * A utility class which manages the Date and Time operations using thread-safe, immutable java.time
 * APIs
 * 
 * @see java.time
 * 
 * @author sandesha
 * @author Merry
 */

@Slf4j
public class DateUtilV2 {

    private static final String DD_MM_YYYY = "dd-MM-yyyy";

    private static final String START_DATE_NULL_ERROR = "Start date is required";

    private static final String START_DATE_TIME_NULL_ERROR = "Start date time is required";

    private DateUtilV2() throws DataException
    {
        throw new DataException(GeneralConstants.EXCEPTION, GeneralConstants.CONSTRUCTOR_CREATION_ERROR,
                HttpStatus.BAD_REQUEST);
    }

    /**
     * This method take the short Zone id as input and returns {@link ZoneId} instance for the same.
     *
     * @param shortZoneId
     *            Short zone id. {@link ZoneId#SHORT_IDS}
     * @return {@link ZoneId} for the input short zone id
     */
    public static ZoneId getZoneIdFromShortZoneId( String shortZoneId ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(shortZoneId, "Short zone ID is required");
            return (shortZoneId.equals("UTC") || shortZoneId.equals("GMT")) ? ZoneId.of("UTC")
                    : ZoneId.of(ZoneId.SHORT_IDS.get(shortZoneId));
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching zone id from short zone id",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * This method returns the CURRENT {@link LocalDateTime} in UTC. It considers the System's default
     * zone as the parameter to do the conversion. For example, if the server is in IST, this method
     * returns the LocalDateTime in UTC by reducing the offset of 5 hour and 30 minutes
     *
     * @return {@link LocalDateTime} in UTC/GMT
     */
    public static LocalDateTime getCurrentUTCLocalDateTimeInUTCOffsetBySystemTimeZone() throws DataException
    {
        try
        {
            return LocalDateTime.now(ZoneOffset.UTC);
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching current UTC local time",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method returns the {@link LocalDateTime} in UTC for the time passed as input. It considers
     * the System's default zone as the parameter to do the conversion. For example, if the server is in
     * IST, this method returns the LocalDateTime in UTC by reducing the offset of 5 hour and 30 minutes
     *
     * If the input {@param milliseconds} is null or empty, this method sends the current time in UTC
     *
     * @param milliseconds
     *            time in milliseconds in the server timezone
     *
     * @return {@link LocalDateTime} in UTC/GMT
     */
    public static LocalDateTime getUTCLocalDateTimeOffsetBySystemTimeZoneFromMilliseconds( Long milliseconds )
            throws DataException
    {
        try
        {
            milliseconds = NullEmptyUtils.isNullOrEmpty(milliseconds) ? System.currentTimeMillis() : milliseconds;
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC);
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION,
                    "Error while fetching UTC local date time from milliseconds", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method returns the {@link LocalDateTime} from the milliseconds sent in a timezone to
     * {@link LocalDateTime} of different timezone. For example, if {@param inputZoneShortId} is passed
     * as IST and {@param timeRequiredInZoneShortId} as PST, the method converts DateTime in IST to
     * DateTime in PST and returns the {@link LocalDateTime} instance {@link LocalDateTime}.
     *
     * NOTE: If {@param milliseconds} is null or empty, current time of the server will be used
     *
     * @param milliseconds
     *            Time of the day in milliseconds
     * @param inputZoneShortId
     *            Short Zone Ids of the zone from where the conversion should happen. For Short zone ids
     *            refer {@link ZoneId#SHORT_IDS}. NOTE: For UTC/GMT ZONE. Pass UTC or GMT as input
     * @param timeRequiredInZoneShortId
     *            Short Zone Ids of the zone from where the conversion should happen. For Short zone ids
     *            refer {@link ZoneId#SHORT_IDS}. NOTE: For UTC/GMT ZONE. Pass UTC or GMT as input
     * @return {@link LocalDateTime} converted to Zone {@param timeRequiredInZoneString}
     * @throws DataException
     *             If mandatory inputs are empty
     *
     * @see ZoneId
     */
    public static LocalDateTime convertAndGetLocalDateTimeInAZoneForATimeToOtherZone( Long milliseconds,
            String inputZoneShortId, String timeRequiredInZoneShortId ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(inputZoneShortId, "Input zone id is required");
            NullEmptyUtils.throwExceptionIfInputIsNullOrEmpty(timeRequiredInZoneShortId,
                    "Required zone id is required");

            milliseconds = NullEmptyUtils.isNullOrEmpty(milliseconds) ? System.currentTimeMillis() : milliseconds;

            LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds),
                    getZoneIdFromShortZoneId(inputZoneShortId));
            return localDateTime.atZone(getZoneIdFromShortZoneId(inputZoneShortId))
                    .withZoneSameInstant(getZoneIdFromShortZoneId(timeRequiredInZoneShortId)).toLocalDateTime();
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while converting time zone",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method adds the number of days {@param numberOFDays} to the {@param localDateTime} and
     * returns the {@link LocalDateTime} instance.
     *
     * For example, if you pass a {@param localDateTime} as 2021-11-30T13:30:00.184650 and
     * {@param numberOfDays} as 2, this method returns the {@link LocalDateTime} with value
     * 2021-12-02T13:30:00.184650.
     *
     * @param localDateTime
     *            {@link LocalDateTime} from which number of days are to be counted
     * @param numberOfDays
     *            number of days to be added to the date
     * @return {@link LocalDateTime} after adding number of days to the input date time
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static LocalDateTime getFutureLocalDateTimeByNumberOfDays( LocalDateTime localDateTime, int numberOfDays )
            throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(localDateTime, "Input date is missing");
            if( numberOfDays < 1 )
            {
                throw new DataException(GeneralConstants.EXCEPTION,
                        "Number of days must be more than 0 to get the future Date", HttpStatus.BAD_REQUEST);
            }
            return localDateTime.plusDays(numberOfDays);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching future date",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method adds the number of seconds {@param numberOfSeconds} to the {@param localDateTime} and
     * returns the {@link LocalDateTime} instance.
     *
     * For example, if you pass a {@param localDateTime} as 2021-11-30T13:30:00.184650 and
     * {@param numberOfSeconds} as 20, this method returns the {@link LocalDateTime} with value
     * 2021-11-30T13:50:00.184650
     *
     * @param localDateTime
     *            {@link LocalDateTime} from which seconds are to be counted
     * @param numberOfSeconds
     *            number of seconds to be added to the date time
     * @return {@link LocalDateTime} after adding seconds to the input date time
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static LocalDateTime getFutureLocalDateTimeByNumberOfSeconds( LocalDateTime localDateTime,
            long numberOfSeconds ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(localDateTime, START_DATE_TIME_NULL_ERROR);
            if( numberOfSeconds < 1 )
            {
                throw new DataException(GeneralConstants.EXCEPTION,
                        "Number of seconds must be more than 0 to get the future Date", HttpStatus.BAD_REQUEST);
            }
            NullEmptyUtils.throwExceptionIfInputIsNull(numberOfSeconds, "Seconds to be added is required");
            return localDateTime.plusSeconds(numberOfSeconds);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching future time",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to subtract n number of days to a date time and return the result.
     *
     * @param localDateTime
     *            {@link LocalDateTime} from which number of days are to be counted
     * @param numberOfDays
     *            number of days to be subtracted from the date
     * @return {@link LocalDateTime} after subtracting number of days from the input date time
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static LocalDateTime getPastLocalDateTimeByNumberOfDays( LocalDateTime localDateTime, int numberOfDays )
            throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(localDateTime, START_DATE_TIME_NULL_ERROR);
            if( numberOfDays < 1 )
            {
                throw new DataException(GeneralConstants.EXCEPTION,
                        "Number of days must be more than 0 to get the past Date by that number",
                        HttpStatus.BAD_REQUEST);
            }
            return localDateTime.minusDays(numberOfDays);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching past time",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to subtract n seconds from a date time and return the result.
     *
     * @param localDateTime
     *            {@link LocalDateTime} from which seconds are to be counted
     * @param numberOfSeconds
     *            number of seconds to be subtracted from the date time
     * @return {@link LocalDateTime} after subtracting seconds from the input date time
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static LocalDateTime getPastLocalDateTimeByNumberOfSeconds( LocalDateTime localDateTime,
            long numberOfSeconds ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(localDateTime, START_DATE_TIME_NULL_ERROR);
            if( numberOfSeconds < 1 )
            {
                throw new DataException(GeneralConstants.EXCEPTION,
                        "Number of seconds must be more than 0 to get the past Date by that number",
                        HttpStatus.BAD_REQUEST);
            }
            return localDateTime.minusSeconds(numberOfSeconds);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching past time",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to add n number of days to a date and return the result.
     *
     * @param localDate
     *            {@link LocalDate} from which number of days are to be counted
     * @param numberOfDays
     *            number of days to be added to the date
     * @return {@link LocalDate} after adding number of days to the input date time
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static LocalDate getFutureLocalDateByNumberOfDays( LocalDate localDate, int numberOfDays )
            throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(localDate, START_DATE_NULL_ERROR);
            if( numberOfDays < 1 )
            {
                throw new DataException(GeneralConstants.EXCEPTION,
                        "Number of days must be more than 0 to get the future Date by that number",
                        HttpStatus.BAD_REQUEST);
            }
            NullEmptyUtils.throwExceptionIfInputIsNull(numberOfDays, "Number of days to be added is required");
            return localDate.plusDays(numberOfDays);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching future date",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * method to subtract n number of days to a date and return the result.
     *
     * @param localDate
     *            {@link LocalDate} from which number of days are to be counted
     * @param numberOfDays
     *            number of days to be subtracted from the date
     * @return {@link LocalDate} after subtracting number of days from the input date time
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static LocalDate getPastLocalDateByNumberOfDays( LocalDate localDate, int numberOfDays ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(localDate, START_DATE_NULL_ERROR);
            if( numberOfDays < 1 )
            {
                throw new DataException(GeneralConstants.EXCEPTION,
                        "Number of days must be more than 0 to get the past Date by that number",
                        HttpStatus.BAD_REQUEST);
            }
            return localDate.minusDays(numberOfDays);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching past date",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to get the number of days in between two dates.
     *
     * @param localDateFrom
     *            {@link LocalDate} from which number of days are to be counted
     * @param localDateTo
     *            {@link LocalDate} till which number of days are to be counted
     * @return {@link Integer} number of days between the input date
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static long getTheNumberOfDaysBetweenTwoLocalDates( LocalDate localDateFrom, LocalDate localDateTo )
            throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(localDateFrom, START_DATE_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNull(localDateTo, "End date is required");
            return DAYS.between(localDateFrom, localDateTo);
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching number of days between two dates",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to get the number of days in between two string dates.
     *
     * @param dateFrom
     *            {@link String} from which number of days are to be counted
     * @param dateTo
     *            {@link String} till which number of days are to be counted
     * @return {@link Integer} number of days between the input date
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static long getTheNumberOfDaysBetweenTwoStringDates( String dateFrom, String dateTo ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(dateFrom, START_DATE_NULL_ERROR);
            NullEmptyUtils.throwExceptionIfInputIsNull(dateTo, "End date is required");
            return DAYS.between(getLocalDateFromHumanReadableStringDate(dateFrom),
                    getLocalDateFromHumanReadableStringDate(dateTo));
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error while fetching number of days between two dates",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * method to convert string date of particular format to {@link LocalDate}.
     *
     * @param inputDate
     *            string input date
     * @return {@link LocalDate} from the input string
     * @throws DataException
     *             If mandatory inputs are empty
     */
    public static LocalDate getLocalDateFromHumanReadableStringDate( String inputDate ) throws DataException
    {
        try
        {
            NullEmptyUtils.throwExceptionIfInputIsNull(inputDate, "Input string is required");
            return LocalDate.parse(inputDate, DateTimeFormatter.ofPattern(DD_MM_YYYY));
        }
        catch( DataException e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw e;
        }
        catch( Exception e )
        {
            log.error(GeneralConstants.ERROR, e);
            throw new DataException(GeneralConstants.EXCEPTION, "Error fetching date from a string",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
