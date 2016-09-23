package listen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import run.Run;

public class SliderListen implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Run.getStart().getBoard().getDialog().requestFocus();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Run.getStart().getBoard().getDialog().requestFocus();
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
