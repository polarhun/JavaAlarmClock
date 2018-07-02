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
import java.time.DateTimeException;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

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
	private JButton btnStopAlarm;
	private JButton buttonDelete;
	private JList listAlarms;
	private static AlarmList alarms;
	private static boolean silent;
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
		alarms = new AlarmList();
		silent = true;
		beepManager = SoundManager.getInstance();
		initialize();

		// * inputUpdate checks every second if alarm has been set, if it has it will do
		// alarm functionality. */
		inputUpdate = new javax.swing.Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateGUI();
			}

			private void updateGUI() {
				listAlarms.updateUI();
				if (!alarms.isEmpty()) {
					Alarm alarm = alarms.getFirst();
					lblActualTimeLeft.setText(alarm.timeLeft());
					if (!alarm.afterNow()) {
						lblActualTimeLeft.setText("00:00");
						if (silent) {
							beepManager.playBeep("Beep");
							silent = false;
						}
						btnSnooze.setEnabled(true);
					}
				}else {
					btnStopAlarm.setEnabled(false);
					lblActualCurrent.setText("--");
					lblActualTimeLeft.setText("--");
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
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {75, 25};
		gridBagLayout.rowHeights = new int[] { 275 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel panelAlarmControl = new JPanel();
		GridBagConstraints gbc_panelAlarmControl = new GridBagConstraints();
		gbc_panelAlarmControl.anchor = GridBagConstraints.WEST;
		gbc_panelAlarmControl.fill = GridBagConstraints.VERTICAL;
		gbc_panelAlarmControl.insets = new Insets(0, 0, 5, 5);
		gbc_panelAlarmControl.gridx = 0;
		gbc_panelAlarmControl.gridy = 0;
		frame.getContentPane().add(panelAlarmControl, gbc_panelAlarmControl);

		JPanel panelDetails = new JPanel();
		panelAlarmControl.add(panelDetails);
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
		panelAlarmControl.add(panelSelectors);
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
		panelAlarmControl.add(panelControl);
		panelControl.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panelHelp = new JPanel();
		panelHelp.setForeground(Color.BLACK);
		panelControl.add(panelHelp);

		lblHelp = new JLabel("Please Input Hour and Minute");
		lblHelp.setForeground(Color.BLACK);
		panelHelp.add(lblHelp);

		JPanel panelButtons = new JPanel();
		panelControl.add(panelButtons);

		JButton btnAddAlarm = new JButton("Add Alarm");
		panelButtons.add(btnAddAlarm);

		btnStopAlarm = new JButton("Stop Alarm");
		btnStopAlarm.setEnabled(false);
		panelButtons.add(btnStopAlarm);

		btnSnooze = new JButton("Snooze");
		btnSnooze.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				beepManager.stopBeep();
				silent = true;
				Alarm snoozed = alarms.getFirst();
				snoozed.snooze(5);
				alarms.removeFirst();
				alarms.insert(snoozed);
				lblActualCurrent.setText(alarms.getFirst().toString());
			}
		});
		btnSnooze.setEnabled(false);
		panelButtons.add(btnSnooze);
		btnStopAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				beepManager.stopBeep();
				silent = true;
				btnSnooze.setEnabled(false);
				alarms.removeFirst();
			}
		});
		btnAddAlarm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addAlarm();
			}
		});

		JPanel panelListControl = new JPanel();
		GridBagConstraints gbc_panelListControl = new GridBagConstraints();
		gbc_panelListControl.anchor = GridBagConstraints.EAST;
		gbc_panelListControl.insets = new Insets(0, 0, 0, 5);
		gbc_panelListControl.fill = GridBagConstraints.VERTICAL;
		gbc_panelListControl.gridx = 1;
		gbc_panelListControl.gridy = 0;
		frame.getContentPane().add(panelListControl, gbc_panelListControl);
		GridBagLayout gbl_panelListControl = new GridBagLayout();
		gbl_panelListControl.columnWidths = new int[] { 100 };
		gbl_panelListControl.rowHeights = new int[] {100, 25};
		gbl_panelListControl.columnWeights = new double[] { 1.0 };
		gbl_panelListControl.rowWeights = new double[] { 1.0, 0.0 };
		panelListControl.setLayout(gbl_panelListControl);
		
		listAlarms = new JList(alarms);
		listAlarms.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
					buttonDelete.setEnabled(true);
			}
		});
		listAlarms.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAlarms.setForeground(Color.BLACK);
		listAlarms.setBorder(new LineBorder(Color.PINK));
		listAlarms.setBackground(Color.WHITE);
		GridBagConstraints gbc_listAlarms = new GridBagConstraints();
		gbc_listAlarms.insets = new Insets(0, 0, 5, 0);
		gbc_listAlarms.fill = GridBagConstraints.BOTH;
		gbc_listAlarms.gridx = 0;
		gbc_listAlarms.gridy = 0;
		panelListControl.add(listAlarms, gbc_listAlarms);

		JPanel panelListButtons = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panelListButtons.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panelListButtons = new GridBagConstraints();
		gbc_panelListButtons.insets = new Insets(0, 0, 5, 0);
		gbc_panelListButtons.anchor = GridBagConstraints.SOUTH;
		gbc_panelListButtons.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelListButtons.gridx = 0;
		gbc_panelListButtons.gridy = 1;
		panelListControl.add(panelListButtons, gbc_panelListButtons);

		buttonDelete = new JButton("-");
		buttonDelete.setEnabled(false);
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int index = listAlarms.getSelectedIndex();
				alarms.remove(index);
				listAlarms.clearSelection();
				buttonDelete.setEnabled(false);
			}
		});
		panelListButtons.add(buttonDelete);
	}

	private void addAlarm() {
		int hour = 0, minute = 0;
		try {
			hour = Integer.parseInt(txtHour.getText());
			minute = Integer.parseInt(txtMinute.getText());
			Alarm newAlarm = new Alarm(hour, minute);
			alarms.insert(newAlarm);
			validateGUI();
			lblActualCurrent.setText(alarms.getFirst().toString());
			lblActualTimeLeft.setText(alarms.getFirst().timeLeft());
			btnStopAlarm.setEnabled(true);
		} catch (NumberFormatException e) {
			lblHelp.setText("Not integer input");
			invalidateGUI();
		} catch (DateTimeException e) {
			lblHelp.setText("Incorrect time input");
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
