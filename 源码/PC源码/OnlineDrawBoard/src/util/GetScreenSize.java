package util;

import java.awt.Dimension;
import java.awt.Toolkit;
import run.Run;

public class GetScreenSize {
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
	private double showx;
	private double showy;

	public Dimension getScreenSize() {
		return screenSize;
	}

	public double getX() {
		showx = Run.getStart().getBoard().getWidth() / dpi;
		return showx;
	}

	public double getY() {
		showy = Run.getStart().getBoard().getHeight() / dpi;
		return showy;
	}
}
