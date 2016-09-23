package util;

import run.Run;
import entity.OnePoint;
import entity.XYFamily;

public class GetFigure {
	double x = 0;
	double y = 0;
	double leftX = 0;
	double leftY = 0;
	static final String TYPE = "online";

	public OnePoint getRect(OnePoint op) {
		x = op.getBoardrx()
				* (Run.getStart().getBoard().getWidth()
						- Run.getStart().getBoard().getXx() - 10)
				+ Run.getStart().getBoard().getXx() + 10;
		y = op.getBoardry() * Run.getStart().getBoard().getHeight();
		if ("first".equals(op.getType())) {
			Run.getStart().getBoard().setBoardlx((double) x);
			Run.getStart().getBoard().setBoardly((double) y);
		} else {
			Run.getStart().getBoard().setBoardrx((double) x);
			Run.getStart().getBoard().setBoardry((double) y);
		}
		return new OnePoint(x, y, op.getTool(), op.getC(), op.getBorder(),
				op.getLeftX(), op.getLeftY(), op.getScreenzoom(),
				op.getBoardrx(), op.getBoardry(), op.getType(), false);
	}
	public void getXorY(OnePoint op,XYFamily xy)
	{
		if (TYPE.equals(op.getType())) {
			if (xy != null && xy.getX() != 0) {
				op.setScreenzoom(xy.getX());
				leftX = getLeftX(xy, op);
				leftY = getLeftY(xy, op);
				x = getX(xy, op);
				y = getY(xy, op);
			} else {
				leftX = getLeftX(null, op);
				leftY = getLeftY(null, op);
				x = getX(null, op);
				y = getY(null, op);
			}
		}
	}
	public OnePoint getPoint(OnePoint op, XYFamily xy) {
		if (TYPE.equals(op.getType())) {
			getXorY(op, xy);
			return new OnePoint(x, y, op.getTool(), op.getC(), op.getBorder(),
					op.getLeftX(), op.getLeftY(), op.getScreenzoom(),
					op.getBoardrx(), op.getBoardry(), TYPE, false);
		} else {
			x = getX(null, op);
			y = getY(null, op);
			return new OnePoint(x, y, op.getTool(), op.getC(), op.getBorder(),
					0, 0, 1, op.getBoardrx(), op.getBoardry(), null, false);
		}
	}

	public OnePoint getLine(OnePoint op, XYFamily xy) {
		if (TYPE.equals(op.getType())) {
			getXorY(op, xy);
		}
		return new OnePoint(x, y, 5, op.getC(), op.getBorder(), leftX, leftY,
				Run.getStart().getThread().getOd().getScreenzoom(),
				op.getBoardrx(), op.getBoardry(), TYPE, false);
	}

	public OnePoint getCircle(OnePoint op, XYFamily xy) {
		if (TYPE.equals(op.getType())) {
			getXorY(op, xy);
			return new OnePoint(x, y, op.getTool(), op.getC(), op.getBorder(),
					leftX, leftY, op.getScreenzoom(), 1, op.getBoardrx(),
					op.getBoardry(), op.getType(), false);
		} else {
			if (xy != null && xy.getX() != 0) {
				x = xy.getX()
						* Run.getStart().getThread().getOd().getPercentX()
						* op.getBoardrx()
						/ Run.getStart().getThread().getScreenx()
						* Run.getStart().getBoard().getWidth();
				y = xy.getX()
						* Run.getStart().getThread().getOd().getPercentY()
						* op.getBoardry()
						/ Run.getStart().getThread().getScreeny()
						* Run.getStart().getBoard().getHeight();
			} else {
				x = op.getScreenzoom()
						* Run.getStart().getThread().getOd().getPercentX()
						* op.getBoardrx()
						/ Run.getStart().getThread().getScreenx()
						* Run.getStart().getBoard().getWidth();
				y = op.getScreenzoom()
						* Run.getStart().getThread().getOd().getPercentY()
						* op.getBoardry()
						/ Run.getStart().getThread().getScreeny()
						* Run.getStart().getBoard().getHeight();
			}
			return new OnePoint(x, y, op.getTool(), op.getC(), op.getBorder(),
					0, 0, op.getScreenzoom(), 0, op.getBoardrx(),
					op.getBoardry(), null, false);
		}
	}

	public double getX(XYFamily xy, OnePoint op) {
		if (xy != null && xy.getX() != 0) {
			return getX1(leftX
					+ Run.getStart().getBoard().getXx()
					+ 10
					+ xy.getX()
					* Run.getStart().getThread().getOd().getPercentX()
					* (op.getBoardrx() / Run.getStart().getThread()
							.getScreenx())
					* (Run.getStart().getBoard().getWidth()
							- Run.getStart().getBoard().getXx() - 30));
		} else if (xy == null && op.getType() != null) {
			return getX1(leftX
					+ Run.getStart().getBoard().getXx()
					+ 10
					+ Run.getStart().getThread().getOd().getScreenzoom()
					* Run.getStart().getThread().getOd().getPercentX()
					* (op.getBoardrx() / Run.getStart().getThread()
							.getScreenx())
					* (Run.getStart().getBoard().getWidth()
							- Run.getStart().getBoard().getXx() - 30));
		} else {
			if (Run.getStart().getBoard().getBoardlx() != 0
					&& Run.getStart().getBoard().getBoardly() != 0
					&& Run.getStart().getBoard().getBoardrx() != 0
					&& Run.getStart().getBoard().getBoardry() != 0) {
				return getX1(op.getBoardrx()
						* Math.abs(Run.getStart().getBoard().getBoardlx()
								- Run.getStart().getBoard().getBoardrx())
						+ Run.getStart().getBoard().getLeft());
			} else {
				return getX1(op.getBoardrx()
						* (Run.getStart().getBoard().getWidth() - Run
								.getStart().getBoard().getLeft())
						+ Run.getStart().getBoard().getLeft());
			}
		}
	}

	public double getX1(double t) {
		if (Run.getStart().getBoard().getBoardlx() != 0
				&& Run.getStart().getBoard().getBoardly() != 0
				&& Run.getStart().getBoard().getBoardrx() != 0
				&& Run.getStart().getBoard().getBoardry() != 0) {
			if (Run.getStart().getBoard().getBoardlx() > Run.getStart()
					.getBoard().getBoardrx()) {
				return t + Run.getStart().getBoard().getBoardrx()
						- Run.getStart().getBoard().getLeft();
			} else {
				return t + Run.getStart().getBoard().getBoardlx()
						- Run.getStart().getBoard().getLeft();
			}
		}
		return t;
	}

	public double getY(XYFamily xy, OnePoint op) {
		if (xy != null && xy.getX() != 0) {
			return getY1(leftY + xy.getX()
					* Run.getStart().getThread().getOd().getPercentY()
					* op.getBoardry() / Run.getStart().getThread().getScreeny()
					* Run.getStart().getBoard().getHeight() + 30);
		} else if (xy == null && op.getType() != null) {
			return getY1(leftY
					+ Run.getStart().getThread().getOd().getScreenzoom()
					* Run.getStart().getThread().getOd().getPercentY()
					* (op.getBoardry() / Run.getStart().getThread()
							.getScreeny())
					* Run.getStart().getBoard().getHeight() + 30);
		} else {
			if (Run.getStart().getBoard().getBoardlx() != 0
					&& Run.getStart().getBoard().getBoardly() != 0
					&& Run.getStart().getBoard().getBoardrx() != 0
					&& Run.getStart().getBoard().getBoardry() != 0) {
				return getY1(op.getBoardry()
						* Math.abs(Run.getStart().getBoard().getBoardly()
								- Run.getStart().getBoard().getBoardry()) + 30);
			} else {
				return getY1(op.getBoardry()
						* Run.getStart().getBoard().getHeight() + 30);
			}
		}
	}

	public double getY1(double t) {
		if (Run.getStart().getBoard().getBoardlx() != 0
				&& Run.getStart().getBoard().getBoardly() != 0
				&& Run.getStart().getBoard().getBoardrx() != 0
				&& Run.getStart().getBoard().getBoardry() != 0) {
			if (Run.getStart().getBoard().getBoardly() > Run.getStart()
					.getBoard().getBoardry()) {
				return t + Run.getStart().getBoard().getBoardry() - 30;
			} else {
				return t + Run.getStart().getBoard().getBoardly() - 30;
			}
		}
		return t;
	}

	public double getLeftX(XYFamily xy, OnePoint op) {
		if (Run.getStart().getBoard().getBoardlx() != 0
				&& Run.getStart().getBoard().getBoardly() != 0
				&& Run.getStart().getBoard().getBoardrx() != 0
				&& Run.getStart().getBoard().getBoardry() != 0) {
			if (xy != null && xy.getX() != 0) {
				return (1 - xy.getX())
						* Math.abs(Run.getStart().getBoard().getBoardrx()
								- Run.getStart().getBoard().getBoardlx()) / 2;
			} else {
				return (1 - op.getScreenzoom())
						* Math.abs(Run.getStart().getBoard().getBoardrx()
								- Run.getStart().getBoard().getBoardlx()) / 2;
			}
		} else {
			if (xy != null && xy.getX() != 0) {
				return (1 - xy.getX())
						* (Run.getStart().getBoard().getWidth()
								- Run.getStart().getBoard().getXx() - 30) / 2;
			} else {
				return (1 - op.getScreenzoom())
						* (Run.getStart().getBoard().getWidth()
								- Run.getStart().getBoard().getXx() - 30) / 2;
			}
		}
	}

	public double getLeftY(XYFamily xy, OnePoint op) {
		if (Run.getStart().getBoard().getBoardlx() != 0
				&& Run.getStart().getBoard().getBoardly() != 0
				&& Run.getStart().getBoard().getBoardrx() != 0
				&& Run.getStart().getBoard().getBoardry() != 0) {
			if (xy != null && xy.getX() != 0) {
				return (1 - xy.getX())
						* Math.abs(Run.getStart().getBoard().getBoardly()
								- Run.getStart().getBoard().getBoardry()) / 2;
			} else {
				return (1 - op.getScreenzoom())
						* Math.abs(Run.getStart().getBoard().getBoardly()
								- Run.getStart().getBoard().getBoardry()) / 2;
			}
		} else {
			if (xy != null && xy.getX() != 0) {
				return (1 - xy.getX()) * Run.getStart().getBoard().getHeight()
						/ 2;
			} else {
				return (1 - op.getScreenzoom())
						* (Run.getStart().getBoard().getWidth()
								- Run.getStart().getBoard().getXx() - 30) / 2;
			}
		}
	}
}
