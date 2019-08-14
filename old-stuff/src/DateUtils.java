import org.nlogo.api.*;

import java.time.*;

/**
 * Allows us to attempt to parse a Java LocalDateTime, but fall back on custom code to parse a NetLogo date-and-time
 * in case this is what we have been provided with instead.
 */
class DateUtils
{
    static final String[] months = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	static LocalDateTime parseDateTime(String input) throws ExtensionException
	{
		LocalDateTime dateTime;

		try
		{
			dateTime = LocalDateTime.parse(input);
		}
		catch(Exception e)
		{
			dateTime = parseTimeFromString(input);
		}

		return dateTime;
	}

    public static LocalDateTime currentTime() 
    {
        return LocalDateTime.now();
    }
	private static LocalDateTime parseTimeFromString(String timeString)
		throws ExtensionException
	{
		if (timeString.length() != 27)
			throw new ExtensionException("Invalid time");

		int hour = Integer.parseInt(timeString.substring(0, 2));
		int minute = Integer.parseInt(timeString.substring(3, 5));
		int second = Integer.parseInt(timeString.substring(6, 8));

		boolean afternoon = timeString.substring(13, 15).equals("PM");
		if (afternoon)
			hour += 12;

		int day = Integer.parseInt(timeString.substring(16, 18));
		String month = timeString.substring(19, 22);
		int year = Integer.parseInt(timeString.substring(23));

		Month localMonth = Month.JANUARY;
		for (int i=0; i < months.length; i++)
			if (months[i].equalsIgnoreCase(month))
				localMonth = Month.of(i+1);

        if (hour == 24) 
            hour --;
		return LocalDateTime.of(year, localMonth, day, hour, minute, second);
	}
}
