package execution;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.net.URL;
import java.util.logging.Level;
import listen.TrayIconMouseListen;
import run.Run;
import util.GetString;

public class TrayIconSet {
	// 用来判断是否已经执行双击事件
	private boolean flag = false;
	// 用来判断是否该执行双击事件
	private int clickNum = 0;
	// 用来判断是否最小化到托盘
	private boolean flaglis = false;

	public void icon() {
		Run.getStart().getBoard().getItmOpen()
				.addActionListener(e -> menushow());
		Run.getStart().getBoard().getItmHide()
				.addActionListener(e -> menuhide());
		Run.getStart().getBoard().getItmExit().addActionListener(e -> exit());
		Run.getStart().getBoard().getPopMenu()
				.add(Run.getStart().getBoard().getItmHide());
		Run.getStart().getBoard().getPopMenu()
				.add(Run.getStart().getBoard().getItmExit());
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = getClass().getResource("/img/03.png");
		Image img = tk.getImage(url);// *.gif与该类文件同一目录
		SystemTray systemTray = SystemTray.getSystemTray(); // 获得系统托盘的实例
		TrayIcon trayIcon = new TrayIcon(img, "同步画板", Run.getStart().getBoard()
				.getPopMenu());
		try {
			systemTray.add(trayIcon); // 设置托盘的图标，*.gif与该类文件同一目录
			Run.getStart().getBoard().setIconImage(img);
			trayIcon.setImageAutoSize(true);
		} catch (AWTException e2) {
			Run.getStart().getLu().log().log(Level.SEVERE, "托盘图标设置错误", e2);
			Run.getStart()
					.getThread()
					.showText(
							GetString
									.getString(new int[] { 0, 7, 1, 9, 1, 8, 2 }));
			Run.getStart().getTis().exit();
		}
		// 双击托盘图标，软件正常显示
		trayIcon.addMouseListener(new TrayIconMouseListen());
	}

	// 右击菜单方法
	public void menushow() {
		Run.getStart().getBoard().setVisible(true);
		removeall();
		Run.getStart().getBoard().getPopMenu()
				.add(Run.getStart().getBoard().getItmHide());
		Run.getStart().getBoard().getPopMenu()
				.add(Run.getStart().getBoard().getItmExit());
		Run.getStart().getBoard().setState(0);
		flaglis = false;
	}

	public void menuhide() {
		Run.getStart().getBoard().setVisible(false);
		Run.getStart().getThread().getOd().cleanall();
		removeall();
		Run.getStart().getBoard().getPopMenu()
				.add(Run.getStart().getBoard().getItmOpen());
		Run.getStart().getBoard().getPopMenu()
				.add(Run.getStart().getBoard().getItmExit());
		flaglis = true;
	}

	public void removeall() {
		Run.getStart().getBoard().getPopMenu()
				.remove(Run.getStart().getBoard().getItmOpen());
		Run.getStart().getBoard().getPopMenu()
				.remove(Run.getStart().getBoard().getItmHide());
		Run.getStart().getBoard().getPopMenu()
				.remove(Run.getStart().getBoard().getItmExit());
	}

	public void exit() {
		new Thread(() -> Run.getStart().getSm().send(null, "break")).start();
		System.exit(0);
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}

	public boolean isFlaglis() {
		return flaglis;
	}

	public void setFlaglis(boolean flaglis) {
		this.flaglis = flaglis;
	}
}
