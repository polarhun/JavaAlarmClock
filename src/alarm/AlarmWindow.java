package alarm;

import java.awt.Color;
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
import java.awt.FlowLayout;

public class AlarmWindow {

	private JFrame frame;
	private JTextField txtMinute;
	private JTextField txtHour;
	private JLabel lblActualCurrent;
	private JLabel lblHour;
	private JLabel lblMinute;
	private JLabel lblHelp;
	private JLabel lblActualTimeLeft;
	private JButton btnSnooze;
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
					lblActualTimeLeft.setText(alarm.timeLeft());
					if (!alarm.beforeAlarm()) {
						lblActualTimeLeft.setText("00:00");
						beepManager.playBeep("Beep");
						btnSnooze.setEnabled(true);
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

		JPanel panelTxt = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelTxt.getLayout();
		flowLayout_1.setVgap(20);
		flowLayout_1.setHgap(100);
		panelSelectors.add(panelTxt);

		txtHour = new JTextField();
		panelTxt.add(txtHour);
		txtHour.setColumns(10);

		txtMinute = new JTextField();
		panelTxt.add(txtMinute);
		txtMinute.setColumns(10);

		JPanel panelSelectorlbl = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelSelectorlbl.getLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(40);
		panelSelectors.add(panelSelectorlbl);

		lblHour = new JLabel("Hour");
		panelSelectorlbl.add(lblHour);
		lblHour.setHorizontalAlignment(SwingConstants.CENTER);
		lblHour.setLabelFor(txtHour);

		JPanel panelTimeLeft = new JPanel();
		panelSelectorlbl.add(panelTimeLeft);

		JLabel lblTimeLeft = new JLabel("Time Left:");
		panelTimeLeft.add(lblTimeLeft);

		lblActualTimeLeft = new JLabel("--");
		panelTimeLeft.add(lblActualTimeLeft);

		lblMinute = new JLabel("Minute");
		panelSelectorlbl.add(lblMinute);
		lblMinute.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinute.setLabelFor(txtMinute);

		JPanel panelControl = new JPanel();
		frame.getContentPane().add(panelControl);
		panelControl.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panelHelp = new JPanel();
		panelHelp.setForeground(Color.BLACK);
		panelControl.add(panelHelp);

		lblHelp = new JLabel("Please Input Hour and Minute");
		lblHelp.setForeground(Color.BLACK);
		panelHelp.add(lblHelp);

		JPanel panelButtons = new JPanel();
		panelControl.add(panelButtons);

		JButton btnSetAlarm = new JButton("Set Alarm");
		panelButtons.add(btnSetAlarm);

		JButton btnStopAlarm = new JButton("Stop Alarm");
		btnStopAlarm.setEnabled(false);
		panelButtons.add(btnStopAlarm);

		btnSnooze = new JButton("Snooze");
		btnSnooze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				beepManager.stopBeep();
				AlarmWindow.alarm.snooze(5);
				lblActualCurrent.setText(AlarmWindow.alarm.toString());
				flag = true;
			}
		});
		btnSnooze.setEnabled(false);
		panelButtons.add(btnSnooze);
		btnStopAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				beepManager.stopBeep();
				flag = false;
				lblActualCurrent.setText("--");
				lblActualTimeLeft.setText("--");
				btnStopAlarm.setEnabled(false);
				btnSnooze.setEnabled(false);
			}
		});
		btnSetAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setAlarm();
				lblActualTimeLeft.setText(alarm.timeLeft());
				btnStopAlarm.setEnabled(true);
			}
		});
	}

	private void setAlarm() {
		boolean valid = false;
		int hour = 0, minute = 0;
		try {
			hour = Integer.parseInt(txtHour.getText());
			minute = Integer.parseInt(txtMinute.getText());
			valid = true;
			AlarmWindow.alarm = new Alarm(hour, minute);
		} catch (NumberFormatException e) {
			lblHelp.setText("Not integer input");
		}

		if (valid) {
			validateGUI();
			lblActualCurrent.setText(AlarmWindow.alarm.toString());
			flag = true;
		} else {
			invalidateGUI();
		}
	}

	private void invalidateGUI() {
		lblHour.setForeground(Color.RED);
		lblMinute.setForeground(Color.RED);
		lblHelp.setForeground(Color.RED);
	}

	private void validateGUI() {
		lblHour.setForeground(Color.BLACK);
		lblMinute.setForeground(Color.BLACK);
		lblHelp.setText("Please Input Hour and Minute");
		lblHelp.setForeground(Color.BLACK);
	}
}
