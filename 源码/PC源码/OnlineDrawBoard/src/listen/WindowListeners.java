package listen;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import run.Run;

public class WindowListeners implements WindowListener, WindowStateListener {
	int count = 0;

	@Override
	public void windowOpened(WindowEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void windowClosing(WindowEvent e) {
		Run.getStart().getTis().menuhide();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		if (e.getNewState() == 1 || e.getNewState() == 7) {
			Run.getStart().getTis().menuhide();
		}
	}

}
