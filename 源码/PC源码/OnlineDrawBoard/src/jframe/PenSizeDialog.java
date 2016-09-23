package jframe;

import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PenSizeDialog extends JDialog {
	private JLabel waring;
	private JPanel panel;

	public PenSizeDialog() {
		waring = new JLabel("请 选 择 您 需 要 的 笔 画 粗 细", JLabel.CENTER);
		panel = new JPanel();
		add(waring);
		add(panel);
		setTitle("选择画笔粗细");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setIconImage(tk.getImage("img/03.png"));
		setLayout(new GridLayout(3, 1));
		setSize(350, 200);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public JLabel getWaring() {
		return waring;
	}

	public void setWaring(JLabel waring) {
		this.waring = waring;
	}

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}
