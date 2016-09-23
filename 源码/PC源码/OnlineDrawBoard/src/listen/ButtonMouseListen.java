package listen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import jframe.JSliderDiaog;
import run.Run;
import util.GetString;

public class ButtonMouseListen implements MouseListener {
	@Override
	public void mouseClicked(MouseEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == Run.getStart().getBoard().getTools().getUndo()) {
			Run.getStart()
					.getSi()
					.setImage(Run.getStart().getBoard().getTools().getUndo(),
							11);
			new Thread(() -> Run.getStart().getSm().send(null, "undo")).start();
			Run.getStart().getUor().undo();
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getRedo()) {
			Run.getStart()
					.getSi()
					.setImage(Run.getStart().getBoard().getTools().getRedo(),
							14);
			new Thread(() -> Run.getStart().getSm().send(null, "redo")).start();
			Run.getStart().getUor().redo();
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getClear()) {
			Run.getStart()
					.getSi()
					.setImage(Run.getStart().getBoard().getTools().getClear(),
							20);
			Run.getStart().getBoard().setFlagtool(0);
			new Thread(() -> Run.getStart().getSm().send(null, "clear"))
					.start();
			Run.getStart().getThread().getOd().cleanall(); // 此语要有，否则今生无法删除！

		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getRect()) {
			Run.getStart()
					.getSi()
					.setImage(Run.getStart().getBoard().getTools().getRect(),
							17);
			Run.getStart().getBoard().setFlagtool(4);
			if (!Run.getStart().getBoard().getPoints().isEmpty()) {
				for (int i = 0; i < Run.getStart().getBoard().getPoints()
						.size(); i++) {
					if (Run.getStart().getBoard().getPoints().get(i).getTool() == 4) {
						Run.getStart().getBoard().getPoints().remove(i);
					}
				}
				Run.getStart().getPi().paint2();
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getColorboard()) {
			Run.getStart()
					.getSi()
					.setImage(
							Run.getStart().getBoard().getTools()
									.getColorboard(), 8);
			/*
			 * 
			 * 使用 javax.swing.×包中的 JColorChooser 类的静态方法showgetDialog()（Component
			 * 
			 * component，String title，Color color ），
			 * 
			 * 该方法的参数，component是当前显示对话框的父框架，color是设置调色板初始的被选颜色
			 * 
			 * 
			 * 
			 * 该方法返回被选的颜色，类型为Color
			 */
			Color color = JColorChooser.showDialog(Run.getStart().getBoard(),
					"调色板", Run.getStart().getBoard().getFlagcolor());
			Run.getStart().getBoard().setFlagcolor(color);
			new Thread(() -> Run.getStart().getSm().send(null, "color"))
					.start();
			Run.getStart().getBoard().requestFocus();
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getOpenbutton()) {
			Run.getStart()
					.getSi()
					.setImage(
							Run.getStart().getBoard().getTools()
									.getOpenbutton(), 5);
			Run.getStart().getBoard().getTools().getOpenfile().setVisible(true);
			Run.getStart()
					.getSi()
					.setImage(
							Run.getStart().getBoard().getTools()
									.getOpenbutton(), 3);
			if (Run.getStart().getBoard().getTools().getOpenfile().getFile() != null) {
				File file = new File(Run.getStart().getBoard().getTools()
						.getOpenfile().getDirectory(), Run.getStart()
						.getBoard().getTools().getOpenfile().getFile());
				try {
					Run.getStart().setBitmap(ImageIO.read(file));
				} catch (IOException e1) {
					Run.getStart().getLu().log()
							.log(Level.SEVERE, "重置图片错误", e1);
				}
				Run.getStart().getPi().paint3();
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getStorebutton()) {
			Run.getStart()
					.getSi()
					.setImage(
							Run.getStart().getBoard().getTools()
									.getStorebutton(), 2);
			Run.getStart().getBoard().getTools().getStorefile()
					.setVisible(true);
			Run.getStart()
					.getSi()
					.setImage(
							Run.getStart().getBoard().getTools()
									.getStorebutton(), 0);
			if (Run.getStart().getBoard().getTools().getStorefile().getFile() != null) {
				File file = new File(Run.getStart().getBoard().getTools()
						.getStorefile().getDirectory(), Run.getStart()
						.getBoard().getTools().getStorefile().getFile());
				BufferedImage bi = new BufferedImage(Run.getStart().getBoard()
						.getWidth(), Run.getStart().getBoard().getHeight(),
						BufferedImage.TYPE_INT_ARGB);
				Graphics2D g2d = bi.createGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, Run.getStart().getBoard().getWidth(), Run
						.getStart().getBoard().getHeight());
				g2d.translate(0, 0);
				Run.getStart().getBoardcun1().paint(g2d, false);
				try {
					ImageIO.write(bi, "PNG", file);
				} catch (IOException e1) {
					Run.getStart().getLu().log()
							.log(Level.SEVERE, "写入文件失败", e1);
					Run.getStart()
							.getThread()
							.showText(
									GetString.getString(new int[] { 0, 16, 1,
											9, 2 }));
				}
				g2d.dispose();
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getPensize()) {
			Run.getStart()
					.getSi()
					.setImage(
							Run.getStart().getBoard().getTools().getPensize(),
							23);
			Run.getStart().getBoard().setDialog(new JSliderDiaog());
			Run.getStart().getBoard().getDialog().init();
		} else if (e.getSource() == Run.getStart().getBoard().getDialog()
				.getOk()) {
			Run.getStart()
					.getSi()
					.setImage(Run.getStart().getBoard().getDialog().getOk(), 26);
			Run.getStart()
					.getBoard()
					.setBorder(
							Run.getStart().getBoard().getDialog().getSlider()
									.getValue() / 2);
			new Thread(() -> Run.getStart().getSm().send(null, "border"))
					.start();
			Run.getStart().getBoard().getDialog().dispose();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Do nothing because of X and Y.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		deal(e, true);// 进入时
	}

	@Override
	public void mouseExited(MouseEvent e) {
		deal(e, false);// 退出时
	}

	public void deal(MouseEvent e, boolean flag) {
		if (e.getSource() == Run.getStart().getBoard().getTools().getUndo()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getUndo(),
								10);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getUndo(),
								9);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getRedo()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getRedo(),
								13);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getRedo(),
								12);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getClear()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getClear(),
								19);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getClear(),
								18);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getRect()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getRect(),
								16);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools().getRect(),
								15);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getColorboard()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getColorboard(), 7);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getColorboard(), 6);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getOpenbutton()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getOpenbutton(), 4);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getOpenbutton(), 3);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getStorebutton()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getStorebutton(), 1);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getStorebutton(), 0);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getTools()
				.getPensize()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getPensize(), 22);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getTools()
										.getPensize(), 21);
			}
		} else if (e.getSource() == Run.getStart().getBoard().getDialog()
				.getOk()) {
			if (flag) {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getDialog().getOk(),
								25);
			} else {
				Run.getStart()
						.getSi()
						.setImage(
								Run.getStart().getBoard().getDialog().getOk(),
								24);
			}
		}
	}
}
