package execution;

import java.util.ArrayList;
import java.util.List;
import run.Run;
import entity.OnePoint;
import entity.XYFamily;

public class OnlineDraw {
	private double percentX = 1;
	private double percentY = 1;
	private double leftX = 0;
	private double leftY = 0;
	private double screenzoom = 1.0;

	// 拖动
	public void drag(XYFamily xy) {
		if(Run.getStart().getThread().isDownorup())
		{
			leftX = (1 - screenzoom)
					* (Run.getStart().getBoard().getWidth()
							- Run.getStart().getBoard().getXx() - 30) / 2;
			leftY = (1 - screenzoom) * Run.getStart().getBoard().getHeight() / 2;
			if (Math.abs(1 - screenzoom) <= 0.0001) {
				leftX = 0;
				leftY = 0;
			}
			double x;
			double y;
			OnePoint pp3;
			if (Run.getStart().getBoard().getBoardlx() != 0
					&& Run.getStart().getBoard().getBoardly() != 0
					&& Run.getStart().getBoard().getBoardrx() != 0
					&& Run.getStart().getBoard().getBoardry() != 0) {
				leftX = (1 - screenzoom)
						* Math.abs(Run.getStart().getBoard().getBoardrx()
								- Run.getStart().getBoard().getBoardlx()) / 2;
				leftY = (1 - screenzoom)
						* Math.abs(Run.getStart().getBoard().getBoardry()
								- Run.getStart().getBoard().getBoardly()) / 2;
				x = getX(xy);
				y = getY(xy);
				if (Run.getStart().getBoard().getBoardlx() > Run.getStart()
						.getBoard().getBoardrx()) {
					x += Run.getStart().getBoard().getBoardrx()
							- Run.getStart().getBoard().getLeft();
				} else {
					x += Run.getStart().getBoard().getBoardlx()
							- Run.getStart().getBoard().getLeft();
				}
				if (Run.getStart().getBoard().getBoardly() > Run.getStart()
						.getBoard().getBoardry()) {
					y += Run.getStart().getBoard().getBoardry() - 30;
				} else {
					y += Run.getStart().getBoard().getBoardly() - 30;
				}
			} else {
				x = getX(xy);
				y = getY(xy);
			}
			pp3 = new OnePoint(x, y, Run.getStart().getBoard().getFlagtool(), Run
					.getStart().getBoard().getFlagcolor(), Run.getStart()
					.getBoard().getBorder(), leftX, leftY, Run.getStart()
					.getThread().getOd().getScreenzoom(), xy.getX(), xy.getY(),
					"online", true);
			if (x <= Run.getStart().getBoard().getXx() + 10) {
				pp3 = new OnePoint(-1, -1, 22, Run.getStart().getBoard()
						.getFlagcolor(), Run.getStart().getBoard().getBorder(),
						true);
			}
			Run.getStart().getBoard().getPoints().add(pp3);
			Run.getStart().getUor()
					.setCount(Run.getStart().getUor().getCount() + 1);
			Run.getStart().getPi().paint1();
		}
		
	}
	//down
	public void down()
	{
		Run.getStart().getThread().setDownorup(true);
	}
	// up
	public void up() {
		if(Run.getStart().getThread().isDownorup())
		{
			Run.getStart().getBoard().setMode(1);
			Run.getStart()
					.getBoard()
					.getPoints()
					.add(new OnePoint(-1, -1, 22, Run.getStart().getBoard()
							.getFlagcolor(), Run.getStart().getBoard().getBorder(),
							true));
			Run.getStart().getUor()
					.setCount(Run.getStart().getUor().getCount() + 1);
			Run.getStart().getUor().getAll()
					.add("" + Run.getStart().getUor().getCount());
			Run.getStart().getUor().setCount(0);
			if (!Run.getStart().getUor().getRedo().isEmpty()) {
				Run.getStart().getUor().setRedo(new ArrayList<List<OnePoint>>());
			}
			Run.getStart().getPi().paint1();
			new Thread(() -> Run.getStart().getUor().store()).start();
			Run.getStart().getThread().setDownorup(false);
		}
	}

	// cleanAll
	public void cleanall() {
		Run.getStart().getBoard().setFlagtool(0);
		Run.getStart().getBoard().setPoints(new ArrayList<OnePoint>());
		Run.getStart().getUor().setAll(new ArrayList<String>());
		Run.getStart().getUor().setRedo(new ArrayList<List<OnePoint>>());
		Run.getStart().getThread().getOd().setPercentX(1);
		Run.getStart().getThread().getOd().setPercentY(1);
		Run.getStart().getThread().getOd().setScreenzoom(1);
		Run.getStart().getBoard().setBoardry(0);
		Run.getStart().getBoard().setBoardlx(0);
		Run.getStart().getBoard().setBoardly(0);
		Run.getStart().getBoard().setBoardrx(0);
		Run.getStart().getPi().paint2();
		new Thread(() -> Run.getStart().getUor().store()).start();
	}

	// 处理x,y值
	public double getX(XYFamily xy) {
		return leftX
				+ Run.getStart().getBoard().getXx()
				+ 10
				+ screenzoom
				* percentX
				* (xy.getX() / Run.getStart().getThread().getScreenx())
				* (Run.getStart().getBoard().getWidth()
						- Run.getStart().getBoard().getXx() - 30);
	}

	public double getY(XYFamily xy) {
		return leftY + screenzoom * percentY
				* (xy.getY() / Run.getStart().getThread().getScreeny())
				* Run.getStart().getBoard().getHeight() + 30;
	}

	public double getPercentX() {
		return percentX;
	}

	public void setPercentX(double percentX) {
		this.percentX = percentX;
	}

	public double getPercentY() {
		return percentY;
	}

	public void setPercentY(double percentY) {
		this.percentY = percentY;
	}

	public double getScreenzoom() {
		return screenzoom;
	}

	public void setScreenzoom(double screenzoom) {
		this.screenzoom = screenzoom;
	}

	public double getLeftX() {
		return leftX;
	}

	public void setLeftX(double leftX) {
		this.leftX = leftX;
	}

	public double getLeftY() {
		return leftY;
	}

	public void setLeftY(double leftY) {
		this.leftY = leftY;
	}

}
