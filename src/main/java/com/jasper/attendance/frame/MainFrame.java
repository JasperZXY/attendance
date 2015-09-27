package com.jasper.attendance.frame;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.jasper.attendance.dialog.AboutMeDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import java.awt.Toolkit;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 8209698379773734587L;
	private JPanel contentPane;
	private static final SimpleDateFormat WORK_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat VALID_DATE_FORMAT = new SimpleDateFormat("M月d号");
	private JTextArea taResult = new JTextArea();

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
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/img/app_icon.png")));
		setTitle("考勤统计");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("文件");
		menuBar.add(menu);

		JMenuItem menuItem = new JMenuItem("打开");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jdir = new JFileChooser();
				// 设置选择路径模式
				jdir.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jdir.setMultiSelectionEnabled(true);
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
					handle(jdir.getSelectedFiles());
				}
			}
		});
		menu.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("关闭");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		menu.add(menuItem_1);

		JMenu menu_1 = new JMenu("帮助");
		menuBar.add(menu_1);

		JMenuItem menuItem_2 = new JMenuItem("关于");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AboutMeDialog dialog = new AboutMeDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		menu_1.add(menuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
		);
		
		scrollPane.setViewportView(taResult);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void handle(File[] files) {
		if (files == null || files.length == 0) {
			taResult.setText("没有选择文件");
			return;
		}
		taResult.setText("开始处理。。。");
		for (int i=0; i<files.length; i++) {
			if (files[i] != null && files[i].isDirectory()) {
				taResult.append("\n" + files[i].getName() + "是文件夹，忽略");
				continue;
			}
			taResult.append("\n\n----------------" + files[i].getName() + "--------------------");
			
			//总上班时间，分钟
			int sumMinutes = 0;
			int day = 0;
			List<String> less8dayList = new ArrayList<String>();
			FileInputStream fis = null;
			Workbook wb = null;
			try {
				fis = new FileInputStream(files[i]);
				WorkbookSettings wbs = new WorkbookSettings();
				wb = Workbook.getWorkbook(fis, wbs);
				Sheet[] sheets = wb.getSheets();

				Sheet sheet = sheets[0];
				for (int j = 1; j < sheet.getRows(); j++) {
					Date dateStart = null;
					Date dateEnd = null;
					try {
						dateStart = WORK_DATE_FORMAT.parse(sheet.getRow(j)[0].getContents());
					} catch (Exception e) {
					}
					try {
						dateEnd = WORK_DATE_FORMAT.parse(sheet.getRow(j)[1].getContents());
					} catch (Exception e) {
					}
					if (dateStart == null && dateEnd != null) {
						taResult.append("\n" + VALID_DATE_FORMAT.format(dateEnd) + "有异常，第" +  + (j + 1) + "行");
					} else if (dateStart != null && dateEnd == null) {
						taResult.append("\n" + VALID_DATE_FORMAT.format(dateStart) + "有异常，第" +  + (j + 1) + "行");
					} else if (dateStart == null && dateEnd == null) {
						taResult.append("\n第" + (j + 1) + "行数据有异常");
					} else if (dateEnd.compareTo(dateStart) <= 0) {
						taResult.append("\n第" + (j + 1) + "行数据有异常");
					} else {
						day ++;
						int minutes = (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60);
						sumMinutes += minutes;
						if (minutes < 8 * 60) {
							less8dayList.add(VALID_DATE_FORMAT.format(dateStart));
						}
					}
				}
				taResult.append("\n有效上班天数：" + day + "天");
				//按一天8小时计算
				int d = sumMinutes / 60 / 8;
				int h = (sumMinutes - d * 8 * 60) / 60;
				int m = sumMinutes - d * 8 * 60 - h * 60;
				taResult.append(String.format("\n合计上班时间：%d天%d小时%d分钟", d, h, m));
				if (! less8dayList.isEmpty()) {
					taResult.append("\n有【" + less8dayList.size() + "】天的上班时间小于8小时\n");
					taResult.append(less8dayList.toString());
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					wb.close();
				} catch (Exception e) {
				}
				closeIO(fis);
			}
		}
		taResult.append("\n\n处理完");
	}
	
	private void closeIO(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
