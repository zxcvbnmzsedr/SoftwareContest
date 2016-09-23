package socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import run.Run;
import util.GetString;

public class UDPListener {
	private DatagramSocket udpSocket;
	private byte[] data = new byte[256];
	private DatagramPacket udpPacket = new DatagramPacket(data, data.length);
	static int count = 0;

	public void uDPListen() {
		try {
			udpSocket = new DatagramSocket(9898);
		} catch (SocketException e) {
			Run.getStart().getLu().log().log(Level.INFO, "开启9898被占用", e);
			Run.getStart()
					.getThread()
					.showText(
							GetString
									.getString(new int[] { 0, 13, 6, 1, 18, 2 }));
			Run.getStart().getTis().exit();
		}
		while (true) {
			try {
				udpSocket.receive(udpPacket);
				if (null != udpPacket.getAddress()) {
					String ip = udpPacket.getAddress().toString().substring(1);
					Socket socket = new Socket(ip, 9999);
					socket.setTcpNoDelay(true);
					socket.close();
				}
			} catch (IOException e) {
				Run.getStart().getLu().log().log(Level.SEVERE, "获取数据错误", e);
				Run.getStart()
						.getThread()
						.showText(
								GetString.getString(new int[] { 0, 14, 6, 1,
										18, 2 }));
				Run.getStart().getTis().exit();
			}
		}
	}
}