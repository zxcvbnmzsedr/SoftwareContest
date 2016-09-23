package execution;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import run.Run;
import util.CreateFile;
import util.JarTool;
import entity.OnePoint;

public class UndoOrRedo {
	private static int o = 1;
	private List<String> all = new ArrayList<>();
	private List<List<OnePoint>> redo = new ArrayList<>();
	private int count = 0;
	private String savepath = JarTool.getJarPath();
	//undo
	public void undo() {
		if (!all.isEmpty() && !Run.getStart().getBoard().getPoints().isEmpty()) {
			int p = Run.getStart().getBoard().getPoints().size();
			List<OnePoint> lp = new ArrayList<>();
			for (int i = 1; i <= Integer.parseInt(all.get(all.size() - 1)); i++) {
				lp.add((OnePoint) Run.getStart().getBoard().getPoints()
						.get(p - i));
				Run.getStart().getBoard().getPoints().remove(p - i);
			}
			redo.add(lp);
			all.remove(all.size() - 1);
			Run.getStart().getPi().paint2();
			new Thread(() -> store()).start();
		}
	}

	// redo
	public void redo() {
		if (!redo.isEmpty()) {
			List<OnePoint> lp = redo.get(redo.size() - 1);
			for (int i = lp.size() - 1; i >= 0; i--) {
				Run.getStart().getBoard().getPoints().add(lp.get(i));
				count++;
			}
			redo.remove(redo.size() - 1);
			all.add(Integer.toString(count));
			count = 0;
			Run.getStart().getPi().paint2();
			new Thread(() -> store()).start();
		}
	}

	// store
	public void store() {
		BufferedImage bi = new BufferedImage(Run.getStart().getBoard()
				.getWidth(), Run.getStart().getBoard().getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, Run.getStart().getBoard().getWidth(), Run.getStart()
				.getBoard().getHeight());
		g2d.translate(0, 0);
		Run.getStart().getBoardcun1().paint(g2d, false);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long start = System.currentTimeMillis();
		String path = "D:\\OnlineDrawBoard";
		File file;
		if (savepath == null) {
			CreateFile.file(path);
		} else {
			path = savepath;
		}
		path += "\\Picture";
		CreateFile.file(path);
		path += "\\" + sdf.format(start);
		CreateFile.file(path);
		try {
			file = new File(path + "\\" + o + ".png");
			ImageIO.write(bi, "PNG", file);
		} catch (IOException e1) {
			Run.getStart().getLu().log().log(Level.SEVERE, "存取文件错误", e1);
		}
		g2d.dispose();
	}

	public List<String> getAll() {
		return all;
	}

	public void setAll(List<String> all) {
		this.all = all;
	}

	public List<List<OnePoint>> getRedo() {
		return redo;
	}

	public void setRedo(List<List<OnePoint>> redo) {
		this.redo = redo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getSavepath() {
		return savepath;
	}

	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
}
