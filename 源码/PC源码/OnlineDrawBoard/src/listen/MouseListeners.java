package listen;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import run.Run;
import entity.OnePoint;

public class MouseListeners implements MouseListener, MouseMotionListener {
	@Override
	public void mouseClicked(MouseEvent e) {
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

	@Override
	public void mousePressed(MouseEvent e) { // 鼠标点下时候，将当前的点信息记录
		if (Run.getStart().getThread().getSign() == 0) {
			Run.getStart().getBoard().setMode(0);
			Run.getStart().getBoard().getP()[0] = e.getPoint();
			if (Run.getStart().getBoard().getFlagtool() == 4) {
				OnePoint pp1 = new OnePoint(
						e.getX(),
						e.getY(),
						Run.getStart().getBoard().getFlagtool(),
						Run.getStart().getBoard().getFlagcolor(),
						Run.getStart().getBoard().getBorder(),
						0,
						0,
						1,
						(double) (e.getX() - Run.getStart().getBoard().getXx())
								/ (double) (Run.getStart().getBoard()
										.getWidth()
										- Run.getStart().getBoard().getXx() - 10),
						(double) e.getY()
								/ (double) Run.getStart().getBoard()
										.getHeight(), "first", true);
				Run.getStart().getBoard().setBoardlx((double) e.getX());
				Run.getStart().getBoard().setBoardly((double) e.getY());
				Run.getStart().getBoard().getPoints().add(pp1);
			} else {
				final OnePoint pp1 = new OnePoint(e.getX(), e.getY(), Run
						.getStart().getBoard().getFlagtool(), Run.getStart()
						.getBoard().getFlagcolor(), Run.getStart().getBoard()
						.getBorder(), 0, 0, 1, (double) (e.getX()
						- Run.getStart().getBoard().getXx() - 10)
						/ (double) (Run.getStart().getBoard().getWidth()
								- Run.getStart().getBoard().getXx() - 10),
						(double) (e.getY())
								/ (double) Run.getStart().getBoard()
										.getHeight(), null, true);
				new Thread(() -> Run.getStart().getSm().send(pp1, "press"))
						.start();
				Run.getStart().getBoard().getPoints().add(pp1);
				Run.getStart().getUor()
						.setCount(Run.getStart().getUor().getCount() + 1);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) { // 鼠标松开时候，如果是画笔，则当前截断，是其余状态记下一枚点信息
		if (Run.getStart().getThread().getSign() == 0) {
			Run.getStart().getBoard().setMode(1);
			if (Run.getStart().getBoard().getFlagtool() == 0) {
				Run.getStart()
						.getBoard()
						.getPoints()
						.add(new OnePoint(-1, -1, 22, Run.getStart().getBoard()
								.getFlagcolor(), Run.getStart().getBoard()
								.getBorder(), true));
				new Thread(
						() -> {
							OnePoint pp1 = Run
									.getStart()
									.getBoard()
									.getPoints()
									.get(Run.getStart().getBoard().getPoints()
											.size() - 1);
							Run.getStart().getSm().send(pp1, "up");
						}).start();
				Run.getStart().getUor()
						.setCount(Run.getStart().getUor().getCount() + 1);
				Run.getStart().getUor().getAll()
						.add("" + Run.getStart().getUor().getCount());
				Run.getStart().getUor().setCount(0);
				if (!Run.getStart().getUor().getRedo().isEmpty()) {
					Run.getStart().getUor()
							.setRedo(new ArrayList<List<OnePoint>>());
				}
			} else {
				OnePoint pp2;
				if (Run.getStart().getBoard().getFlagtool() == 4) {
					pp2 = new OnePoint(
							e.getX(),
							e.getY(),
							Run.getStart().getBoard().getFlagtool(),
							Run.getStart().getBoard().getFlagcolor(),
							Run.getStart().getBoard().getBorder(),
							0,
							0,
							1,
							(double) (e.getX()
									- Run.getStart().getBoard().getXx() - 10)
									/ (double) (Run.getStart().getBoard()
											.getWidth()
											- Run.getStart().getBoard().getXx() - 10),
							(double) e.getY()
									/ (double) Run.getStart().getBoard()
											.getHeight(), "second", true);
					Run.getStart().getBoard().setBoardrx((double) e.getX());
					Run.getStart().getBoard().setBoardry((double) e.getY());
					Run.getStart()
							.getThread()
							.getOd()
							.setPercentX(
									Math.abs(Run.getStart().getBoard()
											.getBoardrx()
											- Run.getStart().getBoard()
													.getBoardlx())
											/ (double) (Run.getStart()
													.getBoard().getWidth()
													- Run.getStart().getBoard()
															.getXx() - 10));
					Run.getStart()
							.getThread()
							.getOd()
							.setPercentY(
									Math.abs(Run.getStart().getBoard()
											.getBoardry()
											- Run.getStart().getBoard()
													.getBoardly())
											/ (double) Run.getStart()
													.getBoard().getHeight());
					for (int i = 0; i < 2; i++) {
						Run.getStart()
								.getThread()
								.getZs()
								.zoomrect(
										Run.getStart().getBoard().getPoints(),
										null);
						Run.getStart()
								.getThread()
								.getZs()
								.zoomrect(null,
										Run.getStart().getUor().getRedo());
					}
					Run.getStart().setCount(1);
					Run.getStart().getBoard().setFlagtool(0);

					Run.getStart().getPi().paint2();
				} else {
					pp2 = new OnePoint(
							e.getX(),
							e.getY(),
							Run.getStart().getBoard().getFlagtool(),
							Run.getStart().getBoard().getFlagcolor(),
							Run.getStart().getBoard().getBorder(),
							0,
							0,
							1,
							(double) (e.getX()
									- Run.getStart().getBoard().getXx() - 10)
									/ (double) (Run.getStart().getBoard()
											.getWidth()
											- Run.getStart().getBoard().getXx() - 10),
							(double) e.getY()
									/ (double) Run.getStart().getBoard()
											.getHeight(), null, true);
				}
				Run.getStart().getBoard().getPoints().add(pp2);

				Run.getStart()
						.getBoard()
						.getPoints()
						.add(new OnePoint(-1, -1, 22, Run.getStart().getBoard()
								.getFlagcolor(), Run.getStart().getBoard()
								.getBorder(), true));
			}
			Run.getStart().getPi().paint1();
			new Thread(() -> Run.getStart().getUor().store()).start();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) { // 鼠标拖动时候，//当且仅当
		// flagtool＝＝0，或者表示为橡皮的时候
		// 才将拖动过程中涉及到的点全部记录下来，并且调用repain()方法，重画当前
		if (Run.getStart().getThread().getSign() == 0) {
			if (Run.getStart().getBoard().getFlagtool() == 0) {
				OnePoint pp3 = new OnePoint(
						e.getX(),
						e.getY(),
						Run.getStart().getBoard().getFlagtool(),
						Run.getStart().getBoard().getFlagcolor(),
						Run.getStart().getBoard().getBorder(),
						0,
						0,
						1,
						(double) (e.getX() - Run.getStart().getBoard().getXx())
								/ (double) (Run.getStart().getBoard()
										.getWidth()
										- Run.getStart().getBoard().getXx() - 10),
						(double) (e.getY())
								/ (double) Run.getStart().getBoard()
										.getHeight(), null, true);
				if (e.getX() <= Run.getStart().getBoard().getXx() - 5) {
					pp3 = new OnePoint(-1, -1, 22, Run.getStart().getBoard()
							.getFlagcolor(), Run.getStart().getBoard()
							.getBorder(), true);
				}
				Run.getStart().getBoard().getPoints().add(pp3);
				new Thread(
						() -> {
							OnePoint pp1 = Run
									.getStart()
									.getBoard()
									.getPoints()
									.get(Run.getStart().getBoard().getPoints()
											.size() - 1);
							if (pp1.getX() != -1) {
								Run.getStart().getSm().send(pp1, "drag");
							} else {
								Run.getStart().getSm().send(pp1, "up");
							}
						}).start();
				Run.getStart().getUor()
						.setCount(Run.getStart().getUor().getCount() + 1);
				Run.getStart().getPi().paint1();
			}
			if (Run.getStart().getBoard().getFlagtool() == 1) {
				Run.getStart().getPi().paint1();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// Do nothing because of X and Y.
	}
}
