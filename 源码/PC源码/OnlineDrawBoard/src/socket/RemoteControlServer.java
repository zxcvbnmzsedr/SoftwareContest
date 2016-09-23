package socket;

import java.awt.AWTException;
import java.awt.Color;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;

import run.Run;
import util.GetString;
import util.ZoomSize;
import entity.FinalConstant;
import entity.XYFamily;
import execution.OnlineDraw;
import execution.ShowTextDialog;
import json.JSONException;
import json.JSONObject;

public class RemoteControlServer {
	private int port = 1987;
	private OnlineDraw od = new OnlineDraw();
	private DatagramSocket socket;
	private ServerThread serverthread; // 初始化线程
	private socket.controlserver.RemoteControlServer rcs = new socket.controlserver.RemoteControlServer();
	private int countt = 0;
	private int mouseordraw = 0;// 0为绘制
	private String message = null;
	private String[] messages = null;
	private boolean recognize = false;
	private double screenx = 1;
	private double screeny = 1;
	private InetAddress s = null;
	private ZoomSize zs = new ZoomSize();
	private ShowTextDialog std = new ShowTextDialog();
	private int sign = 0;
	private boolean downorup=false;
	public void start() {
		serverthread = new ServerThread();
		serverthread.start();
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		serverthread.suspend();
	}

	public class ServerThread extends Thread {
		@Override
		public void run() {
			// 创建一个DatagramSocket对象，并指定监听的端口号
			try {
				socket = new DatagramSocket(port);
			} catch (SocketException e1) {
				Run.getStart().getLu().log().log(Level.INFO, "开启1987被占用", e1);
				showText(GetString.getString(new int[] { 0, 11, 6, 1, 18, 2 }));
				Run.getStart().getTis().exit();
			}
			byte[] data = new byte[512];
			// 创建一个空的DatagramPacket对象
			DatagramPacket packet = new DatagramPacket(data, data.length);
			// 使用receive方法接收客户端所发送的数据
			while (true) {
				try {
					socket.receive(packet);
				} catch (IOException e) {
					Run.getStart().getLu().log().log(Level.SEVERE, "接受数据错误", e);
				}
				message = new String(packet.getData(), packet.getOffset(),
						packet.getLength());
				System.out.println("message->"+message);
				try {
				s = InetAddress.getByName(packet.getAddress()
						.getHostAddress());
			} catch (UnknownHostException e) {
				Run.getStart().getLu().log().log(Level.SEVERE, "获取IP错误", e);
			}
				send(packet,true);
				if (mouseordraw == 1) {
					try {
						rcs.deal(message);
					} catch (AWTException e) {
						Run.getStart().getLu().log()
								.log(Level.SEVERE, "鼠标操控错误", e);
					}
				} else if (recognize) {
					try {
						Run.getStart().getRz().getValue(message);
					} catch (JSONException e) {
						Run.getStart().getLu().log()
								.log(Level.SEVERE, "识别操作错误", e);
					}
				} else if (mouseordraw == 0) {
					JSONObject j = null;
					try {
						j = new JSONObject(message);
						final XYFamily xy = Run.getStart().getGv().get(j);
						if (xy != null) {
							if (xy.getBorder() != null
									&& !"".equals(xy.getBorder())) {
								Run.getStart()
										.getBoard()
										.setBorder(
												Integer.parseInt(xy.getBorder()) / 2);
							} else if ("ok".equals(xy.getResult())) {
								if (!Run.getStart().isActiveflag()) {
									screenx = xy.getSx();
									screeny = xy.getSy();
									od.cleanall();
									showText(GetString.getString(new int[] { 0,
											3, 1, 4 })
											+ packet.getAddress()
													.toString()
													.substring(
															1,
															packet.getAddress()
																	.toString()
																	.length())
											+ FinalConstant.getTip()[2]);
								}
							} else if (xy.getResult() == null
									&& xy.getRgb() == null) {
								doSomeThing(xy,packet);
							} else if (xy.getResult() == null
									&& xy.getRgb() != null) {
								Run.getStart()
										.getBoard()
										.setFlagcolor(
												new Color(xy.getRgb()
														.getColor()));
							}
						}
					} catch (JSONException e) {
						Run.getStart().getLu().log()
								.log(Level.INFO, "json解析错误", e);
						warning();
					}
				}
			}
		}
	}

	public void warning() {// 提示丢链接
		showText(GetString.getString(new int[] { 0, 5, 2 }));
		resumeall(false);
	}

	public void doSomeThing(XYFamily xy,DatagramPacket packet) {
		switch (xy.getType()) {
		case "draw":
			resumeall(true);// 恢复初始状态
			break;
		case "move":// 滑动
			od.drag(xy);
			break;
		case "up":// 抬起笔
			od.up();
			break;
		case "down":// 按下
			od.down();
			break;
		case "exitdraw":// 退出绘制
			od.cleanall();
			resumeall(false);// 恢复初始状态
			break;
		case "clearAll":// 清除所有
			od.cleanall();
			break;
		case "undo":// 撤销
			Run.getStart().getUor().undo();
			break;
		case "redo":// 还原
			Run.getStart().getUor().redo();
			break;
		case "mouse":// 切换鼠标模式
			Run.getStart().getBoard().setVisible(false);
			mouseordraw = 1;
			break;
		case "recognize":// 切换识别模式
			recognize = true;
			break;
		case "norecognize":// 关闭识别模式
			recognize = false;
			break;
		case "sign":// 切换签名模式
			Run.getStart().getTcpl().openTCP();
			break;
		case "scale":// 缩放
			runThread(xy);
			break;
		case "clear":// 清除签名
			Run.getStart().getTcpl().clear();
			break;
		case "exit":// 签名模式
			Run.getStart().getTcpl().exitTCP();
			break;
		case "check"://检测链接是否断开
			send(packet,false);
			break;
		default:
			warning();
			od.cleanall();
			resumeall(false);
			break;
		}
	}
	public void send(DatagramPacket packet,boolean flag)
	{
		DatagramPacket p;
		byte[] result = "ok".getBytes();
		if(flag)
		{
			p = new DatagramPacket(result, result.length, s,
					packet.getPort());
		}
		else
		{
			p = new DatagramPacket(result, result.length, s,
					1989);
		}
		try {
			socket.send(p);
		} catch (IOException e) {
			Run.getStart().getLu().log()
					.log(Level.SEVERE, "发送返回数据错误", e);
		}
	}
	public void runThread(final XYFamily xy) {// 缩放线程
		new Thread(() -> {
			zs.zoomrect(Run.getStart().getBoard().getPoints(), xy);
			zs.zoomrect(xy, Run.getStart().getUor().getRedo());
			Run.getStart().getPi().paint2();
			Run.getStart().getThread().getOd().setScreenzoom(xy.getX());
		}).start();
	}

	public void resumeall(boolean flag) {// 还原初始状态
		sign = 0;
		mouseordraw = 0;
		recognize=false;
		od.cleanall();
		if (!flag) {
			Run.getStart().getBoard().setFlagtool(0);
			Run.getStart().getBoard().setFlagcolor(Color.black);
			Run.getStart().getBoard().setBorder(10);
		}
	}

	public void showText(String text) {// 显示提示
		if (!Run.getStart().isActiveflag()) {
			Run.getStart().setActiveflag(true);
			std.init();
			std.getMydialog().getNotiLabel().setText(text);
			std.show();
		} else {
			synchronized (this) {// 锁进程以避免多个提示框出现影响显示效果
				try {
					while (Run.getStart().isActiveflag()) {
						wait(1800);
					}
				} catch (Exception e) {
					Run.getStart().getLu().log().log(Level.SEVERE, "线程等待错误", e);
				}
			}
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public OnlineDraw getOd() {
		return od;
	}

	public void setOd(OnlineDraw od) {
		this.od = od;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

	public void setSocket(DatagramSocket socket) {
		this.socket = socket;
	}

	public ServerThread getServerthread() {
		return serverthread;
	}

	public void setServerthread(ServerThread serverthread) {
		this.serverthread = serverthread;
	}

	public socket.controlserver.RemoteControlServer getRcs() {
		return rcs;
	}

	public void setRcs(socket.controlserver.RemoteControlServer rcs) {
		this.rcs = rcs;
	}

	public int getCountt() {
		return countt;
	}

	public void setCountt(int countt) {
		this.countt = countt;
	}

	public int getMouseordraw() {
		return mouseordraw;
	}

	public void setMouseordraw(int mouseordraw) {
		this.mouseordraw = mouseordraw;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

	public boolean isRecognize() {
		return recognize;
	}

	public void setRecognize(boolean recognize) {
		this.recognize = recognize;
	}

	public double getScreenx() {
		return screenx;
	}

	public void setScreenx(double screenx) {
		this.screenx = screenx;
	}

	public double getScreeny() {
		return screeny;
	}

	public void setScreeny(double screeny) {
		this.screeny = screeny;
	}

	public InetAddress getS() {
		return s;
	}

	public void setS(InetAddress s) {
		this.s = s;
	}

	public ZoomSize getZs() {
		return zs;
	}

	public void setZs(ZoomSize zs) {
		this.zs = zs;
	}

	public ShowTextDialog getStd() {
		return std;
	}

	public void setStd(ShowTextDialog std) {
		this.std = std;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public boolean isDownorup() {
		return downorup;
	}

	public void setDownorup(boolean downorup) {
		this.downorup = downorup;
	}

}
