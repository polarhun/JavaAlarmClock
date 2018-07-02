package alarm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Alarm {
	private LocalDateTime alarmDateTime;
	private boolean snoozed;
	private String beep;

	public Alarm(int hour, int minutes, String beep) {
		super();
		this.beep = beep;
		this.snoozed = false;
		LocalTime alarmTime = LocalTime.of(hour, minutes);
		LocalDate alarmDate;
		if (LocalTime.now().isAfter(alarmTime)) {
			alarmDate = LocalDate.now().plusDays(1);
		} else {
			alarmDate = LocalDate.now();
		}
		this.alarmDateTime = LocalDateTime.of(alarmDate, alarmTime);
	}
	
	
	public void playBeep(SoundManager beepManager) {
		beepManager.playBeep(this.beep);
	}


	public LocalDateTime getAlarmTime() {
		return alarmDateTime;
	}

	/* Check if alarm is after the current time */
	public boolean afterNow() {
		if (this.alarmDateTime.isAfter(LocalDateTime.now())) {
			return true;
		} else {
			return false;
		}
	}

	/* Check if alarm is after the other alarm */
	public boolean afterAlarm(Alarm other) {
		LocalDateTime input = other.getAlarmTime();
		if (this.alarmDateTime.isAfter(input)) {
			return true;
		} else {
			return false;
		}
	}

	public String timeLeft() {
		int minute = (int) (LocalDateTime.now().until(alarmDateTime, ChronoUnit.MINUTES)) + 1;
		int hour = (int) minute / 60;
		minute = minute % 60;
		String minutes = (minute < 10) ? "0" + minute : "" + minute;
		String hours = (hour < 10) ? "0" + hour : "" + hour;
		return (hours + ":" + minutes);
	}

	public String toString() {
		return stringify(alarmDateTime);
	}

	public void snooze(int minutes) {
		this.snoozed = true;
		this.alarmDateTime = this.alarmDateTime.plusMinutes(minutes);
	}


	private String stringify(LocalDateTime time) {
		int minute = time.getMinute();
		int hour = time.getHour();
		String minutes = (minute < 10) ? "0" + minute : "" + minute;
		String hours = (hour < 10) ? "0" + hour : "" + hour;
		String day = (LocalDate.now().getDayOfYear() == time.getDayOfYear()) ? "Today" : "Tomorrow";
		String snooze = (snoozed) ? " (S)" : "";
		return (day + " at " + hours + ":" + minutes + snooze);
	}

}
