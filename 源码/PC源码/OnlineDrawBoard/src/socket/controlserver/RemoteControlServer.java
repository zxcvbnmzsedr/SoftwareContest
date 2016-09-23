package socket.controlserver;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

import entity.Mouse;
import json.JSONException;
import json.JSONObject;
import run.Run;
import util.GetString;

public class RemoteControlServer {
	private int port;
	private double mx; // 电脑鼠标的横坐标
	private double my; // 电脑鼠标的纵坐标
	String message = null;
	String[] messages = null;

	public void deal(String message) throws AWTException {
		try {
			JSONObject object = new JSONObject(message);
			Mouse ms = Run.getStart().getGv().getMouse(object);
			if ("break".equals(ms.getType())) {
				Run.getStart().getThread().setMouseordraw(0);
				Run.getStart().getBoard().setVisible(true);
			} else if ("mouse".equals(ms.getType()) && ms.getKey() == null) {
				mouseMove(String.valueOf(Float.valueOf("" + ms.getX())) + ","
						+ Float.valueOf("" + ms.getY()));
			} else if ("leftButton".equals(ms.getType())) {
				leftButton(ms.getState());
			} else if ("rightButton".equals(ms.getType())) {
				rightButton(ms.getState());
			} else if ("mousewheel".equals(ms.getType())) {
				mouseWheel(ms.getMousewheel());
			} else if ("keyboard".equals(ms.getType())) {
				keyBoard(ms.getKey());
			}else if("check".equals(ms.getType())){
				Run.getStart().getThread().send(null,false);
			} else {
				Run.getStart().getThread().setMouseordraw(0);
				Run.getStart().getBoard().setVisible(true);
			}
		} catch (JSONException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "鼠标操控json解析错误", e);
			Run.getStart()
					.getThread()
					.showText(
							GetString.getString(new int[] { 0, 17, 9, 1, 7, 1,
									2 }));
		}

	}

	public void mouseMove(String info) {
		String[] args = info.split(",");
		String x = args[0];
		String y = args[1];
		float px = Float.parseFloat(x);
		float py = Float.parseFloat(y);

		PointerInfo pinfo = MouseInfo.getPointerInfo(); // 得到鼠标的坐标
		java.awt.Point p = pinfo.getLocation();
		mx = p.getX(); // 得到当前电脑鼠标的坐标
		my = p.getY();
		java.awt.Robot robot;
		try {
			robot = new Robot();
			robot.mouseMove((int) mx + (int) px, (int) my + (int) py);
		} catch (AWTException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "移动鼠标错误", e);
		}
	}

	public void leftButton(String info) throws AWTException {
		java.awt.Robot robot = new Robot();
		if ("down".equals(info)) {
			robot.mousePress(InputEvent.BUTTON1_MASK);
		} else if ("release".equals(info)) {
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} else if ("up".equals(info)) {
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		} else if ("click".equals(info)) {
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
	}

	public void rightButton(String info) throws AWTException {
		java.awt.Robot robot = new Robot();
		if ("down".equals(info)) {
			robot.mousePress(InputEvent.BUTTON3_MASK);
		} else if ("release".equals(info)) {
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
		} else if ("up".equals(info)) {
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
		}
	}

	public void mouseWheel(String info) throws AWTException {
		java.awt.Robot robot = new Robot();
		float num = Float.parseFloat(info);
		if (num > 0) {
			robot.mouseWheel(1);
		} else {
			robot.mouseWheel(-1);
		}
	}

	public void keyBoard(JSONObject info) throws AWTException {

		String key = "";
		String type = "";
		try {
			key = info.getString("whichkey");
			type = info.getString("type");
		} catch (JSONException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "json解析出错", e);
			Run.getStart().getThread().warning();
		}
		java.awt.Robot robot = new Robot();
		if ("ppt".equals(type)) {
			if ("lastpage".equals(key)) {
				// 上一页
				robot.keyPress(KeyEvent.VK_LEFT);
				robot.keyRelease(KeyEvent.VK_LEFT);
			} else if ("nextpage".equals(key)) {
				// 下一页
				robot.keyPress(KeyEvent.VK_RIGHT);
				robot.keyRelease(KeyEvent.VK_RIGHT);
			} else if ("exitplay".equals(key)) {
				// 退出播放
				robot.keyPress(KeyEvent.VK_ESCAPE);
				robot.keyRelease(KeyEvent.VK_ESCAPE);
			} else if ("showfromnow".equals(key)) {
				// 从当前页播放
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_F5);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.keyRelease(KeyEvent.VK_F5);
			} else if ("showfromfirst".equals(key)) {
				// 从头播放
				robot.keyPress(KeyEvent.VK_F5);
				robot.keyRelease(KeyEvent.VK_F5);
			}
		}
		else if("check".equals(type)){
			Run.getStart().getThread().send(null,false);
		} 
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public double getMx() {
		return mx;
	}

	public void setMx(double mx) {
		this.mx = mx;
	}

	public double getMy() {
		return my;
	}

	public void setMy(double my) {
		this.my = my;
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

}
