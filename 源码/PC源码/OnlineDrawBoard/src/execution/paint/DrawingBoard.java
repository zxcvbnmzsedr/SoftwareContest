package execution.paint;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import run.Run;
import entity.OnePoint;

@SuppressWarnings("serial")
public class DrawingBoard extends jframe.DrawingBoard {
	private Graphics2D g2d;
	private double boardrx = 0;
	private double boardry = 0;
	private double boardlx = 0;
	private double boardly = 0;
	private double left = 0;

	public void paint(Graphics g, boolean bl) {
		g2d = (Graphics2D) g;

		g2d.clipRect((int) left, 0, Run.getStart().getBoard().getWidth()
				- Run.getStart().getBoard().getXx(), Run.getStart().getBoard()
				.getHeight());
		Stroke stroke = g2d.getStroke();
		clearwhat(g2d);
		if (Run.getStart().getBoard().getFlagtool() == 6) {
			g2d.drawImage(Run.getStart().getBitmap(), 0, 0, Run.getStart()
					.getBoard().getWidth(), Run.getStart().getBoard()
					.getHeight(), Run.getStart().getBoard());
			Run.getStart().getBoard().setFlagtool(0);
		}
		for (int i = 0; i < Run.getStart().getBoard().getPoints().size() - 1; i++) {
			setP1((OnePoint) Run.getStart().getBoard().getPoints().get(i));
			setP2((OnePoint) Run.getStart().getBoard().getPoints().get(i + 1));
			g2d.setColor(Run.getStart().getBoard().getP1().getC()); // ////////////需要使用Graphics2D从Graphics类中继承下来的方法
			// setColor（）设置当前的颜色
			Run.getStart().getBoard().size = new BasicStroke((float) Run
					.getStart().getBoard().getP1().getBorder(),
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g2d.setStroke(Run.getStart().getBoard().size);
			if (getP1().getTool() == getP2().getTool()) {
				switch (getP1().getTool()) {
				case 0:
					Line2D.Double line1 = new Line2D.Double(getP1().getX(),
							getP1().getY(), getP2().getX(), getP2().getY());
					g2d.draw(line1);
					break;
				case 1:
					Line2D.Double line2 = new Line2D.Double(getP1().getX(),
							getP1().getY(), getP2().getX(), getP2().getY());
					g2d.draw(line2);
					break;
				case 3:
					g2d.drawArc((int) getP1().getX(), (int) getP1().getY(),
							(int) getP2().getX(), (int) getP2().getY(), 0, 360);
					break;
				case 4:
					drawingrect(g2d, bl);
					break;
				case 5:
					g2d.drawLine((int) getP1().getX(), (int) getP1().getY(),
							(int) getP2().getX(), (int) getP2().getY());
					break;
				default:
					break;
				}
			}
		}
		g2d.setStroke(stroke);
		clearwhat(g2d);
		left = Run.getStart().getBoard().getXx() + 10;
	}

	public void drawingrect(Graphics2D g2d, boolean bl) {
		if (bl) {
			Run.getStart().getBoard().size = new BasicStroke((float) 1,
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			g2d.setStroke(Run.getStart().getBoard().size);
			Run.getStart().getBoard().size = new BasicStroke((float) Run
					.getStart().getBoard().getP1().getBorder(),
					BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			Rectangle2D.Double rect = new Rectangle2D.Double(Run.getStart()
					.getBoard().getP1().getX(), Run.getStart().getBoard()
					.getP1().getY(), Math.abs(Run.getStart().getBoard().getP2()
					.getX()
					- Run.getStart().getBoard().getP1().getX()), Math.abs(Run
					.getStart().getBoard().getP2().getY()
					- Run.getStart().getBoard().getP1().getY()));
			g2d.setColor(Color.black);
			g2d.draw(rect);
			g2d.setStroke(Run.getStart().getBoard().size);
		}

	}

	public void clearwhat(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, Run.getStart().getBoard().getXx() + 10, Run
				.getStart().getBoard().getHeight());
	}

	public Graphics2D getG2d() {
		return g2d;
	}

	public void setG2d(Graphics2D g2d) {
		this.g2d = g2d;
	}

	public double getBoardrx() {
		return boardrx;
	}

	public void setBoardrx(double boardrx) {
		this.boardrx = boardrx;
	}

	public double getBoardry() {
		return boardry;
	}

	public void setBoardry(double boardry) {
		this.boardry = boardry;
	}

	public double getBoardlx() {
		return boardlx;
	}

	public void setBoardlx(double boardlx) {
		this.boardlx = boardlx;
	}

	public double getBoardly() {
		return boardly;
	}

	public void setBoardly(double boardly) {
		this.boardly = boardly;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

}
