package execution;

import java.awt.Component;
import java.util.logging.Level;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import run.Run;
import util.GetString;

public class ChangeWindowLook {
	public void change(Component t) {
		// 替换winodws窗口主题
		String look = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(look);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "更换窗口主题错误", e);
			Run.getStart()
					.getThread()
					.showText(
							GetString
									.getString(new int[] { 0, 7, 1, 9, 1, 8, 2 }));
			Run.getStart().getTis().exit();
		}
		SwingUtilities.updateComponentTreeUI(t);
	}
}
