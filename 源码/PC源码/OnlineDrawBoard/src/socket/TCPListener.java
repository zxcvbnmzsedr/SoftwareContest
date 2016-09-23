package socket;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import run.Run;
import util.GetString;

public class TCPListener extends Thread {
	ServerSocket server;
	Socket socket;
	DataInputStream dis;
	boolean flag = true;

	@Override
	public void run() {
		try {
			server = new ServerSocket(1988);
		} catch (IOException e1) {
			Run.getStart().getLu().log().log(Level.INFO, "开启1988被占用", e1);
			Run.getStart()
					.getThread()
					.showText(
							GetString
									.getString(new int[] { 0, 12, 6, 1, 18, 2 }));
			Run.getStart().getTis().exit();
		}
		while (flag) {
			try {
				socket = server.accept();
				socket.setTcpNoDelay(true);
				Run.getStart().setBitmap(receiveFile(socket));
				Run.getStart()
						.getBoard()
						.getGraphics()
						.drawImage(
								Run.getStart().getBitmap(),
								Run.getStart().getBoard().getXx() + 10,
								20,
								(int) (Run.getStart().getBoard().getWidth()
										- Run.getStart().getBoard().getLeft() - 15),
								Run.getStart().getBoard().getHeight() - 40,
								Run.getStart().getBoard().getPanel());
				Run.getStart().getUor().store();
			} catch (Exception e) {
				Run.getStart().getLu().log().log(Level.SEVERE, "服务器异常", e);
				Run.getStart()
						.getThread()
						.showText(
								GetString.getString(new int[] { 0, 7, 1, 8, 1,
										9, 2 }));
				Run.getStart().getTis().exit();
			}
		}
	}

	public BufferedImage receiveFile(Socket socket) {
		try {
			dis = new DataInputStream(socket.getInputStream());
			return ImageIO.read(dis);
		} catch (IOException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "获取图片失败", e);
			Run.getStart().getThread()
					.showText(GetString.getString(new int[] { 0, 10, 9, 2 }));
		}
		return null;
	}

	public void openTCP() {
		Run.getStart().getThread().getOd().cleanall();
		Run.getStart().getBoard().getTools().getUndo().setEnabled(false);
		Run.getStart().getBoard().getTools().getRedo().setEnabled(false);
		Run.getStart().getBoard().getTools().getRect().setEnabled(false);
		Run.getStart().getBoard().getTools().getColorboard().setEnabled(false);
		Run.getStart().getBoard().getTools().getPensize().setEnabled(false);
		Run.getStart().getBoard().getTools().getOpenbutton().setEnabled(false);
		Run.getStart().getBoard().getTools().getStorebutton().setEnabled(false);
		Run.getStart().getThread().setSign(1);
	}

	public void exitTCP() {
		Run.getStart().getBoard().getTools().getUndo().setEnabled(true);
		Run.getStart().getBoard().getTools().getRedo().setEnabled(true);
		Run.getStart().getBoard().getTools().getRect().setEnabled(true);
		Run.getStart().getBoard().getTools().getColorboard().setEnabled(true);
		Run.getStart().getBoard().getTools().getPensize().setEnabled(true);
		Run.getStart().getBoard().getTools().getOpenbutton().setEnabled(true);
		Run.getStart().getBoard().getTools().getStorebutton().setEnabled(true);
		Run.getStart().getThread().setSign(0);
	}

	public void clear() {
		Run.getStart().setBitmap(null);
		Run.getStart().getPi().paint2();
	}
}
