package alarm;

import java.time.LocalTime;;

public class Alarm{
	private LocalTime alarmTime;

	public Alarm(int hour, int minutes) {
		super();
		this.alarmTime = LocalTime.of(hour, minutes);
	}

	public boolean Before() {
		boolean out;
		if (LocalTime.now().isBefore(this.alarmTime)) {
			out = true;
		} else {
			out = false;
		}
		return out;
	}
	
	public boolean After() {
		boolean out;
		if (LocalTime.now().isAfter(this.alarmTime)) {
			out = true;
		} else {
			out = false;
		}
		return out;
	}
	
	public String toString() {
		int minute = this.alarmTime.getMinute();
		int hour = this.alarmTime.getHour();
		String minutes = (minute < 10) ? "0"+minute:""+minute;
		String hours = (hour < 10) ? "0"+hour:""+hour; 
		return(hours + ":" + minutes);
	}
	
}
