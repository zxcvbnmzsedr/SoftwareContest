package jframe;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import run.Run;
import entity.OnePoint;
import execution.ChangeWindowLook;

public class DrawingBoard extends Frame {
	private static final long serialVersionUID = -3006514816491635631L;
	private Panel panel;
	private int mode = 0;
	private int flagtool = 0;
	private Color flagcolor = Color.black;
	private int border = 5;
	public transient BasicStroke size;
	private transient Point2D[] p = new Point2D[3];
	private Panel noshowpanel;
	private OnePoint p1;
	private OnePoint p2;
	private List<OnePoint> points = new ArrayList<>();
	private ChangeWindowLook cwl = new ChangeWindowLook();
	private PopupMenu popMenu = new PopupMenu();
	private MenuItem itmOpen = new MenuItem("打开");
	private MenuItem itmHide = new MenuItem("隐藏");
	private MenuItem itmExit = new MenuItem("退出");
	private Tools tools = new Tools();
	private int xx = 150;
	private JSliderDiaog dialog;

	public DrawingBoard() {
		panel = new Panel();
		noshowpanel = new Panel();
		noshowpanel.setVisible(false);
		dialog = new JSliderDiaog();
		add(tools, BorderLayout.WEST);
		add(panel, BorderLayout.EAST);
		Toolkit tk = Toolkit.getDefaultToolkit();
		setIconImage(tk.getImage("img/03.png"));// 这个是写在构造方法里的
		setTitle("同步手绘板");
		cwl.change(this);
		add(noshowpanel);
		tools.setBounds(0, 0, xx, this.getHeight());
		panel.setBounds(xx, 0, this.getWidth() - xx, this.getHeight());
		// 获取屏幕边界
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				this.getGraphicsConfiguration());
		// 取得底部边界高度，即任务栏高度
		setSize((int) Run.getStart().getGsz().getScreenSize().getWidth(),
				(int) Run.getStart().getGsz().getScreenSize().getHeight()
						- screenInsets.bottom);
		setLocationRelativeTo(null);
		setVisible(false);
	}

	public Panel getPanel() {
		return panel;
	}

	public void setPanel(Panel panel) {
		this.panel = panel;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getFlagtool() {
		return flagtool;
	}

	public void setFlagtool(int flagtool) {
		this.flagtool = flagtool;
	}

	public Color getFlagcolor() {
		return flagcolor;
	}

	public void setFlagcolor(Color flagcolor) {
		this.flagcolor = flagcolor;
	}

	public int getBorder() {
		return border;
	}

	public void setBorder(int border) {
		this.border = border;
	}

	public Point2D[] getP() {
		return p;
	}

	public void setP(Point2D[] p) {
		this.p = p;
	}

	public Panel getNoshowpanel() {
		return noshowpanel;
	}

	public void setNoshowpanel(Panel noshowpanel) {
		this.noshowpanel = noshowpanel;
	}

	public OnePoint getP1() {
		return p1;
	}

	public void setP1(OnePoint p1) {
		this.p1 = p1;
	}

	public OnePoint getP2() {
		return p2;
	}

	public void setP2(OnePoint p2) {
		this.p2 = p2;
	}

	public List<OnePoint> getPoints() {
		return points;
	}

	public void setPoints(List<OnePoint> points) {
		this.points = points;
	}

	public PopupMenu getPopMenu() {
		return popMenu;
	}

	public void setPopMenu(PopupMenu popMenu) {
		this.popMenu = popMenu;
	}

	public MenuItem getItmOpen() {
		return itmOpen;
	}

	public void setItmOpen(MenuItem itmOpen) {
		this.itmOpen = itmOpen;
	}

	public MenuItem getItmHide() {
		return itmHide;
	}

	public void setItmHide(MenuItem itmHide) {
		this.itmHide = itmHide;
	}

	public MenuItem getItmExit() {
		return itmExit;
	}

	public void setItmExit(MenuItem itmExit) {
		this.itmExit = itmExit;
	}

	public Tools getTools() {
		return tools;
	}

	public void setTools(Tools tools) {
		this.tools = tools;
	}

	public int getXx() {
		return xx;
	}

	public void setXx(int xx) {
		this.xx = xx;
	}

	public JSliderDiaog getDialog() {
		return dialog;
	}

	public void setDialog(JSliderDiaog dialog) {
		this.dialog = dialog;
	}

}
