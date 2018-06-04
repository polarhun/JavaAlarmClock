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
	
	public String toString() {
		return(""+this.alarmTime.getHour()+":"+this.alarmTime.getMinute());
	}
	
}
