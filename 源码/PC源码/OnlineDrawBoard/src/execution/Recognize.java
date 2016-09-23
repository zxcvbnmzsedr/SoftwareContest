package execution;

import java.awt.Color;
import java.util.LinkedList;
import run.Run;
import json.JSONArray;
import json.JSONException;
import json.JSONObject;
import entity.OnePoint;
import entity.XYFamily;

public class Recognize {
	double leftX = 0;
	double leftY = 0;
	double x;
	double y;
	public void add(OnePoint point) {
		Run.getStart().getBoard().getPoints().add(point);
		Run.getStart().getUor()
				.setCount(Run.getStart().getUor().getCount() + 1);
		Run.getStart().getPi().paint1();
	}

	public void addnull() {
		OnePoint point = new OnePoint(-1, -1, 22, Run.getStart().getBoard()
				.getFlagcolor(), Run.getStart().getBoard().getBorder(), true);
		add(point);
		Run.getStart().getUor().getAll()
				.add("" + Run.getStart().getUor().getCount());
		Run.getStart().getUor().setCount(0);
	}
	public void getXorY()
	{
		leftX = (1 - Run.getStart().getThread().getOd()
				.getScreenzoom())
				* (Run.getStart().getBoard().getWidth()
						- Run.getStart().getBoard().getXx() - 30)
				/ 2;
		leftY = (1 - Run.getStart().getThread().getOd()
				.getScreenzoom())
				* Run.getStart().getBoard().getHeight() / 2;
		if (Run.getStart().getThread().getOd().getScreenzoom() == 1) {
			leftX = 0;
			leftY = 0;
		}
	}
	public void getLastXorY()
	{
		if (Run.getStart().getBoard().getBoardlx() != 0
				&& Run.getStart().getBoard().getBoardly() != 0
				&& Run.getStart().getBoard().getBoardrx() != 0
				&& Run.getStart().getBoard().getBoardry() != 0) {
			if (Run.getStart().getBoard().getBoardlx() > Run
					.getStart().getBoard().getBoardrx()) {
				x += Run.getStart().getBoard().getBoardrx()-Run.getStart().getBoard().getLeft();
			} else {
				x += Run.getStart().getBoard().getBoardlx()-Run.getStart().getBoard().getLeft();
			}
			if (Run.getStart().getBoard().getBoardly() > Run
					.getStart().getBoard().getBoardry()) {
				y += Run.getStart().getBoard().getBoardry() - 30;
			} else {
				y += Run.getStart().getBoard().getBoardly() - 30;
			}
		}
	}
	public void getValue(String message) throws JSONException {
		if (message.contains("line") || message.contains("circle")) {
			JSONArray array = new JSONArray(message);
			JSONObject object;
			if ("line".equals(array.get(0).toString())) {
				for (int i = 1; i < array.length(); i++) {
					object = array.getJSONObject(i);
					getXorY();
					x = leftX
							+ Run.getStart().getBoard().getXx()
							+ 10
							+ Run.getStart().getThread().getOd()
									.getScreenzoom()
							* Run.getStart().getThread().getOd().getPercentX()
							* (object.getDouble("x") / Run.getStart()
									.getThread().getScreenx())
							* (Run.getStart().getBoard().getWidth()
									- Run.getStart().getBoard().getXx() - 30);
					y = leftY
							+ Run.getStart().getThread().getOd()
									.getScreenzoom()
							* Run.getStart().getThread().getOd().getPercentY()
							* (object.getDouble("y") / Run.getStart()
									.getThread().getScreeny())
							* Run.getStart().getBoard().getHeight() + 30;
					getLastXorY();
					OnePoint point = new OnePoint(x, y, 5, Run.getStart()
							.getBoard().getFlagcolor(), Run.getStart()
							.getBoard().getBorder(), leftX, leftY, Run
							.getStart().getThread().getOd().getScreenzoom(),
							object.getDouble("x"), object.getDouble("y"),
							"online", true);
					add(point);
				}
				addnull();
			} else {
				LinkedList<OnePoint> pointlist = new LinkedList<>();
				for (int i = 1; i < array.length(); i++) {
					object = array.getJSONObject(i);
					if (i == 1) {
						getXorY();
						x = leftX
								+ Run.getStart().getBoard().getXx()
								+ 10
								+ Run.getStart().getThread().getOd()
										.getScreenzoom()
								* Run.getStart().getThread().getOd()
										.getPercentX()
								* (object.getDouble("x") / Run.getStart()
										.getThread().getScreenx())
								* (Run.getStart().getBoard().getWidth()
										- Run.getStart().getBoard().getXx() - 30);
						y = leftY
								+ Run.getStart().getThread().getOd()
										.getScreenzoom()
								* Run.getStart().getThread().getOd()
										.getPercentY() * object.getDouble("y")
								/ Run.getStart().getThread().getScreeny()
								* Run.getStart().getBoard().getHeight() + 30;
						getLastXorY();
					} else {
						x = Run.getStart().getThread().getOd().getScreenzoom()
								* Run.getStart().getThread().getOd()
										.getPercentX() * object.getDouble("x")
								/ Run.getStart().getThread().getScreenx()
								* Run.getStart().getBoard().getWidth();
						y = Run.getStart().getThread().getOd().getScreenzoom()
								* Run.getStart().getThread().getOd()
										.getPercentY() * object.getDouble("y")
								/ Run.getStart().getThread().getScreeny()
								* Run.getStart().getBoard().getHeight();
					}
					OnePoint point = new OnePoint(x, y, 3, Run.getStart()
							.getBoard().getFlagcolor(), Run.getStart()
							.getBoard().getBorder(), 0, 0, 1,
							object.getDouble("x"), object.getDouble("y"), null,
							true);
					pointlist.add(point);
				}
				OnePoint point = new OnePoint(pointlist.get(0).getX(),
						pointlist.get(0).getY(), 3, Run.getStart().getBoard()
								.getFlagcolor(), Run.getStart().getBoard()
								.getBorder(), leftX, leftY, Run.getStart()
								.getThread().getOd().getScreenzoom(), 1,
						pointlist.get(0).getBoardrx(), pointlist.get(0)
								.getBoardry(), "online", true);
				add(point);
				point = new OnePoint(pointlist.get(1).getX(), pointlist.get(1)
						.getY(), 3, Run.getStart().getBoard().getFlagcolor(),
						Run.getStart().getBoard().getBorder(), 0, 0,
						Run.getStart().getThread().getOd().getScreenzoom(), 0,
						pointlist.get(1).getBoardrx(), pointlist.get(1)
								.getBoardry(), null, true);
				add(point);
				addnull();
			}
		} else {
			JSONObject j = new JSONObject(message);
			final XYFamily xy = Run.getStart().getGv().get(j);
			if (xy.getRgb() != null) {
				Run.getStart().getBoard()
						.setFlagcolor(new Color(xy.getRgb().getColor()));
			} else if (xy.getBorder() != null) {
				Run.getStart().getBoard()
						.setBorder(Integer.parseInt(xy.getBorder()));
			} else {
				switch (xy.getType()) {
				case "clearAll":
					Run.getStart().getThread().getOd().cleanall();
					break;
				case "undo":
					Run.getStart().getUor().undo();
					break;
				case "redo":
					Run.getStart().getUor().redo();
					break;
				case "norecognize":
					Run.getStart().getThread().setRecognize(false);
					break;
				case "scale":
					Run.getStart().getThread().runThread(xy);
					break;
				case "exitdraw":// 退出绘制
					Run.getStart().getThread().getOd().cleanall();
					Run.getStart().getThread().resumeall(false);// 恢复初始状态
					break;
				case "check"://检测链接是否断开
					Run.getStart().getThread().send(null,false);
					break;
				default:
					Run.getStart().getThread().warning();
					Run.getStart().getThread().getOd().cleanall();
					Run.getStart().getThread().resumeall(false);
					break;
				}
			}
		}
	}
}
