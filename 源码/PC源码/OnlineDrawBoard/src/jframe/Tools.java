package jframe;

import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.LinkedList;
import javax.swing.JButton;
import run.Run;

@SuppressWarnings("serial")
public class Tools extends Panel {
	private JButton undo;
	private JButton redo;
	private JButton rect;
	private JButton clear;
	private JButton colorboard;
	private JButton storebutton;
	private JButton openbutton;
	private JButton pencolor;
	private JButton pensize;
	private FileDialog storefile;
	private FileDialog openfile;

	public Tools() {
		undo = new JButton();
		redo = new JButton();
		rect = new JButton();
		clear = new JButton();
		colorboard = new JButton();
		storebutton = new JButton();
		openbutton = new JButton();

		pensize = new JButton();
		pencolor = new JButton();

		LinkedList<JButton> buttonlist = new LinkedList<>();
		buttonlist.add(storebutton);
		buttonlist.add(openbutton);
		buttonlist.add(colorboard);
		buttonlist.add(undo);
		buttonlist.add(redo);
		buttonlist.add(rect);
		buttonlist.add(clear);
		buttonlist.add(pensize);
		for (int i = 0; i < buttonlist.size(); i++) {
			if (i == 0) {
				Run.getStart().getSi().setImage(buttonlist.get(i), i);
			} else {
				Run.getStart().getSi().setImage(buttonlist.get(i), i * 3);
			}
		}
		storefile = new FileDialog(Run.getStart().getBoard(), "存储文件",
				FileDialog.SAVE);
		storefile.setFile("图像1.png");
		storefile.setVisible(false);

		openfile = new FileDialog(Run.getStart().getBoard(), "打开文件",
				FileDialog.LOAD);
		openfile.setVisible(false);

		add(storebutton);
		add(openbutton);

		add(undo);
		add(redo);
		add(rect);
		add(clear);
		add(colorboard);
		add(pensize);
		setLayout(new GridLayout(8, 1));
		setSize(80, 500);
		setVisible(true);
	}

	public JButton getUndo() {
		return undo;
	}

	public void setUndo(JButton undo) {
		this.undo = undo;
	}

	public JButton getRedo() {
		return redo;
	}

	public void setRedo(JButton redo) {
		this.redo = redo;
	}

	public JButton getRect() {
		return rect;
	}

	public void setRect(JButton rect) {
		this.rect = rect;
	}

	public JButton getClear() {
		return clear;
	}

	public void setClear(JButton clear) {
		this.clear = clear;
	}

	public JButton getColorboard() {
		return colorboard;
	}

	public void setColorboard(JButton colorboard) {
		this.colorboard = colorboard;
	}

	public JButton getStorebutton() {
		return storebutton;
	}

	public void setStorebutton(JButton storebutton) {
		this.storebutton = storebutton;
	}

	public JButton getOpenbutton() {
		return openbutton;
	}

	public void setOpenbutton(JButton openbutton) {
		this.openbutton = openbutton;
	}

	public JButton getPensize() {
		return pensize;
	}

	public void setPensize(JButton pensize) {
		this.pensize = pensize;
	}

	public JButton getPencolor() {
		return pencolor;
	}

	public void setPencolor(JButton pencolor) {
		this.pencolor = pencolor;
	}

	public FileDialog getStorefile() {
		return storefile;
	}

	public void setStorefile(FileDialog storefile) {
		this.storefile = storefile;
	}

	public FileDialog getOpenfile() {
		return openfile;
	}

	public void setOpenfile(FileDialog openfile) {
		this.openfile = openfile;
	}

}
