package alarm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Alarm {
	private LocalDateTime alarmDateTime;

	public Alarm(int hour, int minutes) {
		super();
		LocalTime alarmTime = LocalTime.of(hour, minutes);
		LocalDate alarmDate;
		if (LocalTime.now().isAfter(alarmTime)) {
			alarmDate = LocalDate.now().plusDays(1);
		} else {
			alarmDate = LocalDate.now();
		}
		this.alarmDateTime = LocalDateTime.of(alarmDate, alarmTime);
	}

	public boolean beforeAlarm() {
		boolean out;
		if (LocalDateTime.now().isBefore(this.alarmDateTime)) {
			out = true;
		} else {
			out = false;
		}
		return out;
	}

	public String timeLeft() {
		int minutes = (int) (LocalDateTime.now().until(alarmDateTime, ChronoUnit.MINUTES)) + 1;
		int hours = (int) minutes / 60;
		minutes = minutes % 60;
		return stringify(LocalTime.of(hours, minutes));
	}

	public String toString() {
		return stringify(alarmDateTime);
	}

	public void snooze(int minutes) {
		this.alarmDateTime = this.alarmDateTime.plusMinutes(minutes);
	}

	private String stringify(LocalTime time) {
		int minute = time.getMinute();
		int hour = time.getHour();
		String minutes = (minute < 10) ? "0" + minute : "" + minute;
		String hours = (hour < 10) ? "0" + hour : "" + hour;
		return (hours + ":" + minutes);
	}

	private String stringify(LocalDateTime time) {
		int minute = time.getMinute();
		int hour = time.getHour();
		String minutes = (minute < 10) ? "0" + minute : "" + minute;
		String hours = (hour < 10) ? "0" + hour : "" + hour;
		String day = (LocalDate.now().getDayOfYear() == time.getDayOfYear()) ? "Today" : "Tomorrow";
		return (day + " at " + hours + ":" + minutes);
	}

}
