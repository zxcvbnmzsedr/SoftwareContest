package listen;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import run.Run;

public class Listen {
	public void listen() {
		/**
		 * 添加鼠标事件的监听器，否则，鼠标的移动和点击都将无法识别！
		 * */
		Run.getStart().getBoard().addMouseListener(new MouseListeners());
		Run.getStart().getBoard().addMouseMotionListener(new MouseListeners());
		Run.getStart().getBoard().addWindowStateListener(new WindowListeners());
		Run.getStart().getBoard().addWindowListener(new WindowListeners());
		Run.getStart().getBoard().getTools().getUndo()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getRedo()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getRect()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getClear()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getColorboard()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getStorebutton()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getOpenbutton()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getPensize()
				.addMouseListener(new ButtonMouseListen());
		Run.getStart().getBoard().getTools().getOpenfile()
				.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						Run.getStart().getBoard().getTools().getOpenfile()
								.setVisible(false);
					}
				});
		Run.getStart().getBoard().getTools().getStorefile()
				.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						Run.getStart().getBoard().getTools().getStorefile()
								.setVisible(false);
					}
				});
	}
}
