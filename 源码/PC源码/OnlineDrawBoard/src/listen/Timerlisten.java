package listen;

import java.awt.event.MouseEvent;
import java.util.TimerTask;

import run.Run;

public class Timerlisten extends TimerTask {
	private int n = 0;
	MouseEvent e = null;

	public void setE(MouseEvent e) {
		this.e = e;
	}

	@Override
	public void run() {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (Run.getStart().getTis().isFlag()) {
				Run.getStart().getTis().setClickNum(0);
				this.cancel();
				return;
			}
			if (n == 1) {// 执行单击事件
				Run.getStart().getTis().setFlag(true);
				Run.getStart().getTis().setClickNum(0);
				n = 0;
				this.cancel();
				return;
			}
			Run.getStart().getTis()
					.setClickNum(Run.getStart().getTis().getClickNum() + 1);
			n++;
		}
	}

}
