package jframe;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JDialog;
import javax.swing.JLabel;
import run.Run;
import execution.ChangeWindowLook;

public class MyDialog extends JDialog {

	private static final long serialVersionUID = 2791827603307165823L;
	private JLabel notiLabel = null;
	private JLabel notiLabel2 = null;
	private ChangeWindowLook cwl = new ChangeWindowLook();

	public MyDialog() {
		super();
		init();
	}

	private void init() {
		notiLabel = new JLabel("连接成功!", JLabel.CENTER);
		Toolkit tk = Toolkit.getDefaultToolkit();
		URL url = getClass().getResource("/img/03.png");
		setIconImage(tk.getImage(url));// 这个是写在构造方法里的
		setTitle("同步手绘板");
		cwl.change(this);
		this.getContentPane().setLayout(new BorderLayout());
		this.setLocation((int) Run.getStart().getGsz().getScreenSize()
				.getWidth() - 200, (int) Run.getStart().getGsz()
				.getScreenSize().getHeight() - 20);
		add(notiLabel);
		this.setVisible(true);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	public JLabel getNotiLabel() {
		return notiLabel;
	}

	public void setNotiLabel(JLabel notiLabel) {
		this.notiLabel = notiLabel;
	}

	public JLabel getNotiLabel2() {
		return notiLabel2;
	}

	public void setNotiLabel2(JLabel notiLabel2) {
		this.notiLabel2 = notiLabel2;
	}

	public long getSerialVersionUID() {
		return serialVersionUID;
	}

}