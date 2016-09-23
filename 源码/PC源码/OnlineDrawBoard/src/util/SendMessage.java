package util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import entity.OnePoint;
import json.JSONException;
import json.JSONObject;
import run.Run;

public class SendMessage {
	public void send(OnePoint op, String type) {
		JSONObject object = new JSONObject();
		try {
			if (Run.getStart().getThread().getS() != null) {
				if ("press".equals(type) || "up".equals(type)
						|| "drag".equals(type)) {
					double x = op.getBoardrx();
					double y = op.getBoardry();
					object.put("x", x);
					object.put("y", y);
				}
				object.put("border",
						Float.valueOf(Run.getStart().getBoard().getBorder()));
				object.put("color", Run.getStart().getBoard().getFlagcolor()
						.getRGB());
				object.put("type", type);
				send(object);
			}
		} catch (JSONException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "解析json错误", e);
		}

	}

	public void send(JSONObject object) {
		byte[] buf = object.toString().getBytes();
		DatagramPacket pack = new DatagramPacket(buf, buf.length, Run
				.getStart().getThread().getS(), Run.getStart().getThread()
				.getPort());
		try {
			Run.getStart().getThread().getSocket().send(pack);
		} catch (IOException e) {
			Run.getStart().getLu().log().log(Level.SEVERE, "发送数据错误", e);
		}
	}
}
