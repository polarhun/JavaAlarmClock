package alarm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

public class AlarmWindow {

	private JFrame frame;
	private JTextField txtMinute;
	private JTextField txtHour;
	private JLabel lblActualCurrent;
	private static Alarm alarm;
	private static boolean flag;
	private Timer inputUpdate;
	private SoundManager beepManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlarmWindow window = new AlarmWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AlarmWindow() {
		alarm = null;
		flag = false;
		beepManager = SoundManager.getInstance();
		initialize();

		// * inputUpdate checks every second if alarm has been set, if it has it will do
		// alarm functionality. */
		inputUpdate = new javax.swing.Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (flag) {
					if(!alarm.Before()) {
						beepManager.playBeep("Beep");
						flag = false;
					}
				}
			}
		});
		inputUpdate.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(3, 1, 0, 0));

		JPanel panelDetails = new JPanel();
		frame.getContentPane().add(panelDetails);
		panelDetails.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lblWelcome = new JLabel("Welcome to Alarm");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 26));
		panelDetails.add(lblWelcome);

		JPanel paneCurrent = new JPanel();
		panelDetails.add(paneCurrent);

		JLabel lblCurrentText = new JLabel("Current:");
		paneCurrent.add(lblCurrentText);

		lblActualCurrent = new JLabel("--");
		paneCurrent.add(lblActualCurrent);

		JPanel panelSelectors = new JPanel();
		frame.getContentPane().add(panelSelectors);
		panelSelectors.setLayout(new GridLayout(2, 2, 100, 0));

		txtHour = new JTextField();
		panelSelectors.add(txtHour);
		txtHour.setColumns(10);

		txtMinute = new JTextField();
		panelSelectors.add(txtMinute);
		txtMinute.setColumns(10);

		JLabel lblHour = new JLabel("Hour");
		lblHour.setHorizontalAlignment(SwingConstants.CENTER);
		lblHour.setLabelFor(txtHour);
		panelSelectors.add(lblHour);

		JLabel lblMinute = new JLabel("Minute");
		lblMinute.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinute.setLabelFor(txtMinute);
		panelSelectors.add(lblMinute);

		JPanel panelConfirm = new JPanel();
		frame.getContentPane().add(panelConfirm);

		JButton btnSetAlarm = new JButton("Set Alarm");
		btnSetAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setAlarm();
			}
		});
		panelConfirm.add(btnSetAlarm);
		
		JButton btnStopAlarm = new JButton("Stop Alarm");
		btnStopAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				beepManager.stopBeep();
			}
		});
		panelConfirm.add(btnStopAlarm);
	}

	private void setAlarm() {
		int hour = Integer.parseInt(txtHour.getText());
		int minute = Integer.parseInt(txtMinute.getText());
		AlarmWindow.alarm = new Alarm(hour, minute);
		lblActualCurrent.setText(AlarmWindow.alarm.toString());
		flag = true;
	}
}
