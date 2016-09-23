package jframe;

import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import listen.ButtonMouseListen;
import listen.SliderListen;
import run.Run;

public class JSliderDiaog extends JDialog {
	private static final long serialVersionUID = -1458262456634773513L;
	private JLabel waring;
	private Box sliderBox = new Box(BoxLayout.Y_AXIS);
	private JTextField showVal = new JTextField();
	private JButton ok;
	private JSlider slider;

	public void init() {
		waring = new JLabel("请 选 择 您 需 要 的 笔 画 粗 细", JLabel.CENTER);
		// 定义一个监听器，用于监听所有滑动条
		ok = new JButton();
		// -----------添加一个普通滑动条-----------
		slider = new JSlider(1, 100);
		slider.addChangeListener(e-> {
			Run.getStart().getBoard().getDialog().requestFocus();
			// 取出滑动条的值，并在文本中显示出来
			JSlider source = (JSlider) e.getSource();
			showVal.setText("当前选中的值为：" + source.getValue());
		});
		addSlider(slider, "画笔粗细");
		slider.setValue(Run.getStart().getBoard().getBorder() * 2);
		slider.setFocusable(false);
		add(waring);
		add(sliderBox);
		add(showVal);
		Run.getStart().getSi().setImage(ok, 24);
		ok.addMouseListener(new ButtonMouseListen());
		add(ok);
		showVal.setEditable(false);
		showVal.setText("当前选中的值为：" + slider.getValue());
		setLayout(new GridLayout(4, 1));
		pack();
		setTitle("选择画笔粗细");
		Toolkit tk = Toolkit.getDefaultToolkit();
		setIconImage(tk.getImage("img/03.png"));
		setSize(450, 300);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	// 定义一个方法，用于将滑动条添加到容器中
	public void addSlider(JSlider slider, String description) {
		slider.addMouseListener(new SliderListen());
		Run.getStart().getBoard().getDialog().requestFocus();
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(new JLabel(description + "："));
		box.add(slider);
		sliderBox.add(box);
	}

	public JSlider getSlider() {
		return slider;
	}

	public void setSlider(JSlider slider) {
		this.slider = slider;
	}

	public JButton getOk() {
		return ok;
	}

	public void setOk(JButton ok) {
		this.ok = ok;
	}
}
