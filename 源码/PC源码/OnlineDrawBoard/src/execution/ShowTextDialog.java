package execution;

import java.util.logging.Level;

import javax.swing.Timer;

import jframe.MyDialog;
import jframe.TextDialog;
import run.Run;

public class ShowTextDialog {
	private TextDialog td;
	private MyDialog mydialog = null;

	public void init() {
		mydialog = new MyDialog();
	}

	public void show() {
		int height = 100;
		TimerAction timerAction = new TimerAction();
		timerAction.setMydialog(mydialog);
		Timer timer = new Timer(0, timerAction);
		timer.start();
		try {
			for (int i = 0; i < 10; i++) {
				Thread.sleep(100);
				height = height + 5;
				timerAction.setHeight(height);
			}
			timer.stop();
			td = new TextDialog(1, timerAction);
			Thread.sleep(800);
			mydialog.dispose();
			Run.getStart().setActiveflag(false);
		} catch (InterruptedException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "线程等待错误", e);
			Thread.currentThread().interrupt();
		}
	}

	public TextDialog getTd() {
		return td;
	}

	public void setTd(TextDialog td) {
		this.td = td;
	}

	public MyDialog getMydialog() {
		return mydialog;
	}

	public void setMydialog(MyDialog mydialog) {
		this.mydialog = mydialog;
	}
}
