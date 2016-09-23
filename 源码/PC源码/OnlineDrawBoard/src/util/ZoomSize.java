package util;

import java.util.List;
import run.Run;
import entity.OnePoint;
import entity.XYFamily;

public class ZoomSize {
	int count = 0;

	public void zoomrect(XYFamily xy, List<List<OnePoint>> points) {
		for (int i = 0; i < points.size(); i++) {
			zoomrect((List<OnePoint>) points.get(i), xy);
		}
	}

	public void zoomrect(List<OnePoint> points, XYFamily xy) {
		for (int i = 0; i < points.size(); i++) {
			OnePoint op = (OnePoint) points.get(i);
			switch (op.getTool()) {
			case 0:
				op = Run.getStart().getGf().getPoint(op, xy);
				break;
			case 3:
				op = Run.getStart().getGf().getCircle(op, xy);
				break;
			case 5:
				op = Run.getStart().getGf().getLine(op, xy);
				break;
			default:
				break;
			}
			points.set(i, op);
		}
	}

	public void zoomsize(List<OnePoint> points, XYFamily xy) {
		count = 0;
		for (int i = 0; i < points.size(); i++) {
			OnePoint op = (OnePoint) points.get(i);
			if (count == 2) {
				break;
			}
			if (op.getTool() == 4 && xy == null) {
				op = Run.getStart().getGf().getRect(op);
				count++;
			}
			points.set(i, op);
		}
	}
}
