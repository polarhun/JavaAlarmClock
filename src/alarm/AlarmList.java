package alarm;

import java.util.LinkedList;

import javax.swing.AbstractListModel;


public class AlarmList extends AbstractListModel{
	private LinkedList<Alarm> list;

	public AlarmList() {
		super();
		this.list = new LinkedList<>();
	}

	public void insert(Alarm alarm) {
		if (this.isEmpty()) {
			this.list.add(alarm);
		} else {
			int position = 0;
			while (position < this.list.size() && alarm.afterAlarm(this.list.get(position))) {
				position++;
			}
			this.list.add(position, alarm);
		}
	}

	public boolean isEmpty() {
		if (this.list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public Alarm getFirst() {
		if (this.isEmpty()) {
			return null;
		} else {
			return this.list.getFirst();
		}
	}
	
	public void removeFirst() {
		if (!this.isEmpty()) {
			this.list.removeFirst();
		}
	}
	
	public Alarm[] toArray() {
		return list.toArray(new Alarm[0]);
	}

	@Override
	public Object getElementAt(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getSize() {
		return list.size();
	}
}
