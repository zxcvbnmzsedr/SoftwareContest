package execution.paint;

import java.awt.image.BufferedImage;
import run.Run;

public class PaintImage {

	// 继续绘制
	public void paint1() {
		Run.getStart().getBoard()
				.paint(Run.getStart().getBitmap().createGraphics(), true);
		Run.getStart()
				.getBoard()
				.getGraphics()
				.drawImage(Run.getStart().getBitmap(), 0, 0,
						Run.getStart().getBoard().getWidth(),
						Run.getStart().getBoard().getHeight(),
						Run.getStart().getBoard());
	}

	// 刷新界面绘制
	public void paint2() {
		Run.getStart()
				.getBoard()
				.getGraphics()
				.clearRect(0, 0, Run.getStart().getBoard().getWidth(),
						Run.getStart().getBoard().getHeight());
		Run.getStart().setBitmap(
				new BufferedImage(Run.getStart().getBoard().getWidth(), Run
						.getStart().getBoard().getHeight(),
						BufferedImage.TYPE_INT_ARGB));
		Run.getStart().getBoard()
				.paint(Run.getStart().getBitmap().createGraphics(), true);
		Run.getStart()
				.getBoard()
				.getGraphics()
				.drawImage(Run.getStart().getBitmap(), 0, 0,
						Run.getStart().getBoard().getWidth(),
						Run.getStart().getBoard().getHeight(),
						Run.getStart().getBoard());
	}

	// 即时刷新界面
	public void paint3() {
		Run.getStart().getBoard().setFlagtool(6);
		Run.getStart().getBoard()
				.paint(Run.getStart().getBoard().getGraphics(), false);
	}
}
