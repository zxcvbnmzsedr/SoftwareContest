package execution;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jframe.MyDialog;
import run.Run;

public class TimerAction implements ActionListener {
	private MyDialog mydialog = null;

	private int height = 0;

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		mydialog.setLocation((int) Run.getStart().getGsz().getScreenSize()
				.getWidth() - 200, (int) Run.getStart().getGsz()
				.getScreenSize().getHeight()
				- 34 - height);
		mydialog.setSize(new Dimension(200, height));
	}

	public MyDialog getMydialog() {
		return mydialog;
	}

	public void setMydialog(MyDialog mydialog) {
		this.mydialog = mydialog;
	}
}
