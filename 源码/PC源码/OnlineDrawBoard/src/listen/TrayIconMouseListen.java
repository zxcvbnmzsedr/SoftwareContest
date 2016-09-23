package listen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.Timer;

import run.Run;

public class TrayIconMouseListen implements MouseListener {

	@Override
	public void mouseClicked(final MouseEvent e) {
		Run.getStart().getTis().setFlag(false);
		if (Run.getStart().getTis().getClickNum() == 1
				&& e.getButton() == MouseEvent.BUTTON1) {// 1时执行双击事件
			if (Run.getStart().getBoard().isVisible()) {
				Run.getStart().getTis().menuhide();
			} else {
				Run.getStart().getTis().menushow();
			}
			Run.getStart().getTis().setClickNum(0);
			Run.getStart().getTis().setFlag(true);
			return;
		}
		// 定义定时器
		Timer timer = new Timer();
		// 定时器开始执行，延时0.2秒后确定是否执行单击事件
		Timerlisten tl = new Timerlisten();
		tl.setE(e);
		timer.schedule(tl, new Date(), 200);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Do nothing because of X and Y.
	}

}
