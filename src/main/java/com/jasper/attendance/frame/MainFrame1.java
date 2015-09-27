package com.jasper.attendance.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridBagLayoutInfo;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jasper.attendance.dialog.AboutMeDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MainFrame1 extends JFrame {
	private static final long serialVersionUID = 7423624865739519502L;

	private static final long DAY_IN_MILLIS = 24 * 60 * 60 * 1000;

	private JPanel contentPane;

	JTextArea taLog = new JTextArea();
	private JTable table;
	private JPanel panelCalendar;
	private JTextField tfYearFrom;
	private JTextField tfMonthFrom;
	private JTextField tfDayFrom;
	private JTextField tfDayTo;
	private JTextField tfMonthTo;
	private JTextField tfYearTo;
	int aa =1;
	
	private Calendar calendarStart;
	private Calendar calendarEnd;
	private static final SimpleDateFormat WORK_DATE_FORMAT = new SimpleDateFormat("yyMMdd");
	private Set<String> workDate = new HashSet<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// windows风格
		try {
			javax.swing.UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame1 frame = new MainFrame1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame1() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 505);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("文件");
		menuBar.add(menu);

		JMenuItem menuItem_1 = new JMenuItem("打开");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jdir = new JFileChooser();
				// 设置选择路径模式
				jdir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jdir.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						return ".xls";
					}

					@Override
					public boolean accept(File f) {
						if (f == null) {
							return false;
						}
						if (f.isDirectory()) { // 文件夹可见
							return true;
						}
						return f.getName().endsWith(".xls");
					}
				});
				// 设置对话框标题
				jdir.setDialogTitle("请选择excel文件");
				if (JFileChooser.APPROVE_OPTION == jdir.showOpenDialog(null)) {// 用户点击了确定
					taLog.append(jdir.getSelectedFile().getAbsolutePath());
				}
			}
		});
		menu.add(menuItem_1);

		JMenuItem menuItem_2 = new JMenuItem("关闭");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menu.add(menuItem_2);

		JMenu menu_1 = new JMenu("帮助");
		menuBar.add(menu_1);

		JMenuItem menuItem = new JMenuItem("关于");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AboutMeDialog dialog = new AboutMeDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		menu_1.add(menuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		panelCalendar = new JPanel();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 379, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelCalendar, 0, 0, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE)
					.addGap(136))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelCalendar, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JSeparator separator = new JSeparator();
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 273, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(290, Short.MAX_VALUE))
		);
		scrollPane.setViewportView(taLog);
		taLog.setWrapStyleWord(true);
		panel_2.setLayout(gl_panel_2);
		
		tfYearFrom = new JTextField();
		tfYearFrom.setText("2015");
		tfYearFrom.setColumns(10);
		
		tfMonthFrom = new JTextField();
		tfMonthFrom.setText("8");
		tfMonthFrom.setColumns(10);
		
		tfDayFrom = new JTextField();
		tfDayFrom.setText("10");
		tfDayFrom.setColumns(10);
		
		tfDayTo = new JTextField();
		tfDayTo.setText("10");
		tfDayTo.setColumns(10);
		
		tfMonthTo = new JTextField();
		tfMonthTo.setText("8");
		tfMonthTo.setColumns(10);
		
		tfYearTo = new JTextField();
		tfYearTo.setText("2015");
		tfYearTo.setColumns(10);
		
		JLabel label_1 = new JLabel("到");
		
		JButton button = new JButton("确定选择的时间，用于确定");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelCalendar.removeAll();
				initDate2();
				panelCalendar.validate();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(button)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(tfYearFrom, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfMonthFrom, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(tfDayFrom, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfYearTo, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(tfMonthTo, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(tfDayTo, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(93, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(tfYearTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfMonthTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfDayTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfYearFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(tfMonthFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(tfDayFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button)
					.addContainerGap(49, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);

		// table = new JTable();
		// panel.add(table);
		//开始时间为上个月1号
		calendarStart = Calendar.getInstance();
		calendarStart.set(Calendar.MONTH, calendarStart.get(Calendar.MONTH) - 1);
		calendarStart.set(Calendar.DAY_OF_MONTH, 1);
		tfYearFrom.setText("" + calendarStart.get(Calendar.YEAR));
		tfMonthFrom.setText("" + (calendarStart.get(Calendar.MONTH) + 1));
		tfDayFrom.setText("" + calendarStart.get(Calendar.DAY_OF_MONTH));
		
		//结束时间为上个月最后一天
		calendarEnd = Calendar.getInstance();
		calendarEnd.set(Calendar.DAY_OF_MONTH, 1);
		calendarEnd.add(Calendar.DAY_OF_MONTH, -1);
		tfYearTo.setText("" + calendarEnd.get(Calendar.YEAR));
		tfMonthTo.setText("" + (calendarEnd.get(Calendar.MONTH) + 1));
		tfDayTo.setText("" + calendarEnd.get(Calendar.DAY_OF_MONTH));
		
//		initDate();
		initDate2();
		contentPane.setLayout(gl_contentPane);
	}

	private void initDate() {
		if (! checkDateInput()) {
			return;
		}
		
		/*
		 * 设置JTable的列名
		 */
		String[] columnNames = { "姓名", "学号", "性别", "工期", "学院", "学校" };

		/*
		 * 初始化JTable里面各项的值，设置两个一模一样的实体"赵匡义"学生。
		 */
		Object[][] obj = new Object[2][6];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				switch (j) {
				case 0:
					obj[i][j] = "赵匡义" + System.currentTimeMillis();
					break;
				case 1:
					obj[i][j] = "" + aa ++;
					break;
				case 2:
					obj[i][j] = "男";
					break;
				case 3:
					obj[i][j] = "两年";
					break;
				case 4:
					obj[i][j] = "计算机技术学院";
					break;
				case 5:
					obj[i][j] = "北京理工大学";
					break;
				}
			}
		}
		table = new JTable(obj, columnNames);
		/*
		 * 设置JTable自动调整列表的状态，此处设置为关闭
		 */
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		JScrollPane scroll = new JScrollPane(table); 
		scroll.setPreferredSize(new Dimension(376, 339));
		panelCalendar.add(scroll);
	}
	
	private void initDate2() {
		if (! checkDateInput()) {
			return;
		}

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panelCalendar.setLayout(new BorderLayout());
		panelCalendar.add(BorderLayout.NORTH, new JScrollPane(panel));
		
		String[] heads = {"日", "一", "二", "三", "四", "五", "六"};
		int row = 0;
		int column = 0;
		
		for (int i=0; i<heads.length; i++) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridy = 0;
			gridBagConstraints.gridx = i;
			gridBagConstraints.weightx = 1;
			gridBagConstraints.fill = GridBagConstraints.CENTER;
			panel.add(new JLabel(heads[i], null, SwingConstants.CENTER), gridBagConstraints);
		}
		row = 1;
		
		Calendar calendar = (Calendar) calendarStart.clone();
		while (true) {
			if (calendar.compareTo(calendarEnd) > 0) {
				break;
			}
			
		}
		
		
//		panel.add(new JLabel("一", null, SwingConstants.CENTER), new GridBagConstraints());
//		panel.add(new JLabel("二", null, SwingConstants.CENTER), new GridBagConstraints());
//		panel.add(new JLabel("三", null, SwingConstants.CENTER), new GridBagConstraints());
//		panel.add(new JLabel("四", null, SwingConstants.CENTER), new GridBagConstraints());
//		panel.add(new JLabel("五", null, SwingConstants.CENTER), new GridBagConstraints());
//		panel.add(new JLabel("六", null, SwingConstants.CENTER), new GridBagConstraints());
//		
//		JLabel jLabel = new JLabel("xxxxx");
//		panel.add(new JLabel("七月份"), new GridBagConstraints());
//		panel.add(new JLabel("七月份"), new GridBagConstraints());
//		for (int i=0; i<140; i++) {
//			panel.add(new JLabel("六         " + i), new GridBagConstraints());
//		}
	}
	
	private boolean checkDateInput() {
		try {
			calendarStart.set(Calendar.YEAR, Integer.parseInt(tfYearFrom.getText()));
			calendarStart.set(Calendar.MONTH, Integer.parseInt(tfMonthFrom.getText()) - 1);
			calendarStart.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tfDayFrom.getText()));
			calendarEnd.set(Calendar.YEAR, Integer.parseInt(tfYearTo.getText()));
			calendarEnd.set(Calendar.MONTH, Integer.parseInt(tfMonthTo.getText()) - 1);
			calendarEnd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(tfDayTo.getText()));
			return true;
		} catch (Exception e) {
			addLog("时间输入有误！");
		}
		return false;
	}
	
	private void addLog(String log) {
		if ("".equals(taLog.getText())) {
			taLog.setText(log);
		} else {
			taLog.append("\n" + log);
		}
	}
}
