package execution;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import entity.FinalConstant;
import run.Run;

public class SetImage {

	public void setImage(JButton button, int i) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		ImageIcon image = new ImageIcon(tk.getImage(Run.getStart().getClass()
				.getResource(FinalConstant.getImageList()[i])));
		button.setBorderPainted(false);
		image.setImage(image.getImage().getScaledInstance(150, 50,
				Image.SCALE_DEFAULT));
		button.setIcon(image);
		button.setPreferredSize(new Dimension(image.getIconWidth(), image
				.getIconHeight()));
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setFocusable(false);
		button.setContentAreaFilled(false);
	}
}
